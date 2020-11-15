package com.pengheng.sqlSession;

import com.pengheng.pojo.Configuration;
import com.pengheng.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object ... params) throws Exception;

    int update(Configuration configuration, MappedStatement mappedStatement, Object ... params) throws Exception;
}
