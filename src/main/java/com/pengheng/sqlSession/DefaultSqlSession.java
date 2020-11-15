package com.pengheng.sqlSession;

import com.pengheng.pojo.Configuration;
import com.pengheng.pojo.MappedStatement;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int update(MappedStatement mappedStatement, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.update(configuration, mappedStatement, params);
    }

    @Override
    public <E> List<E> selectList(MappedStatement mappedStatement, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <E> E selectOne(MappedStatement mappedStatement, Object... params) throws Exception {
        List<E> list = this.selectList(mappedStatement, params);
        if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        }
    }

    @Override
    public <E> E getMapper(Class<?> clazz) {
        System.out.println();
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            //通过代理模式来对接口方法增强
            //准备statementId ： namespace.id
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            String statementId = className + "." + methodName;
            MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

            switch (mappedStatement.getCommandType()) {
                case SELECT:
                    //准备参数
                    //获取被调用方法的返回类型
                    Type genericReturnType = method.getGenericReturnType();
                    //判断是否进行了泛型类型参数化
                    if (genericReturnType instanceof ParameterizedType) {
                        List<Object> objects = selectList(mappedStatement, args);
                        return objects;
                    }
                    return selectOne(mappedStatement, args);
                case UPDATE:
                case INSERT:
                case DELETE:
                    return this.update(mappedStatement, args);
                default:
                    return null;
            }
        });
        return (E) obj;
    }
}
