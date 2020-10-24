package com.pengheng.sqlSession;

import java.sql.SQLException;
import java.util.List;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public interface SqlSession {

    //查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //查询单个
    <E> E selectOne(String statementId, Object... params) throws Exception;

    //查询单个
    <E> E getMapper(Class<?> clazz);

}
