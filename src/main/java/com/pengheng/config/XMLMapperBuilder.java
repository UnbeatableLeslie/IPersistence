package com.pengheng.config;

import com.pengheng.pojo.Configuration;
import com.pengheng.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream in) throws DocumentException {
        Document document = new SAXReader().read(in);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.selectNodes("//select");
        for (Element selectElement : selectElements) {
            MappedStatement mappedStatement = new MappedStatement();
            String id = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            String parameterType = selectElement.attributeValue("parameterType");
            String sqlText = selectElement.getTextTrim();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);

            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

    }
}
