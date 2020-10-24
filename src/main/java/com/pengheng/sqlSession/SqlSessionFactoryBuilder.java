package com.pengheng.sqlSession;

import com.pengheng.config.XMLConfigBuilder;
import com.pengheng.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory builder(InputStream in) throws PropertyVetoException, DocumentException {
        //第一 使用Dom4j 解析dom文件 将解析出来的文件封装到configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfiguration(in);

        //第二 创建SqlsessionFactory对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
