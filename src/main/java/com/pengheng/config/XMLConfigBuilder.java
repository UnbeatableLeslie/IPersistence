package com.pengheng.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pengheng.io.Resource;
import com.pengheng.pojo.Configuration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法将配置文件解析封装configuration 方法
     *
     * @param inputStream
     * @return
     */
    public Configuration parseConfiguration(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //获取文件跟对象
        Element rootElement = document.getRootElement();
        // 通过 xpath 表达式 // 查找任意节点下的 property节点
        List<Element> list = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
//        configuration.setDataSource(comboPooledDataSource);

        //数据库存放成功
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("driverClass"));
        basicDataSource.setUrl(properties.getProperty("jdbcUrl"));
        basicDataSource.setUsername(properties.getProperty("username"));
        basicDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(basicDataSource);

        //mapper.xml 解析
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resource.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }

        return configuration;
    }
}
