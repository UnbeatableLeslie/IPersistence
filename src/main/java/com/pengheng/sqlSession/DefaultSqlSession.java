package com.pengheng.sqlSession;

import com.pengheng.pojo.Configuration;
import com.pengheng.pojo.MappedStatement;

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
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <E> E selectOne(String statementId, Object... params) throws Exception {
        List<E> list = this.selectList(statementId, params);
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
            String statementId = className+"."+methodName;
            //准备参数
            //获取被调用方法的返回类型
            Type genericReturnType = method.getGenericReturnType();
            //判断是否进行了泛型类型参数化
            if(genericReturnType instanceof ParameterizedType){
                List<Object> objects = selectList(statementId,args);
                return objects;
            }
            return selectOne(statementId,args);
        });
        return (E) obj;
    }
}
