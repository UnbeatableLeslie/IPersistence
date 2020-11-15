package com.pengheng.config;

import com.pengheng.pojo.Configuration;
import com.pengheng.pojo.MappedStatement;
import com.pengheng.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        List<Element> insertElements = rootElement.selectNodes("//insert");
        List<Element> updateElements = rootElement.selectNodes("//update");
        List<Element> deleteElements = rootElement.selectNodes("//delete");
        List<Element> elementList = new ArrayList<>();
        elementList.addAll(selectElements);
        elementList.addAll(insertElements);
        elementList.addAll(updateElements);
        elementList.addAll(deleteElements);
        elementList.forEach(element -> {
            MappedStatement mappedStatement = new MappedStatement();
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            try{
                //通过标签判断是SELECT 还是 INSERT 等操作
                mappedStatement.setCommandType(SqlCommandType.valueOf(element.getName().toUpperCase()));
            }catch (Exception e){
                mappedStatement.setCommandType(SqlCommandType.UNKNOW);
            }
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        });

    }
}
