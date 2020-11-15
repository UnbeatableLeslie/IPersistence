package com.pengheng.sqlSession;

import com.pengheng.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public interface SqlSession {

    //增删改操作调用接口
    int update(MappedStatement mappedStatement, Object... params) throws Exception;

    //查询所有
    <E> List<E> selectList(MappedStatement mappedStatement, Object... params) throws Exception;

    //查询单个
    <E> E selectOne(MappedStatement mappedStatement, Object... params) throws Exception;

    //查询单个
    <E> E getMapper(Class<?> clazz);

}
