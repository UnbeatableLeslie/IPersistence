package com.pengheng.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class Configuration {

    private DataSource dataSource;

    /**
     * key : statementId  = namespace + id
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
