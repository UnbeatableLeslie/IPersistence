package com.pengheng.pojo;

/**
 * @project IPersistence_test
 * @remark
 * @Author Administrator
 * @date 2020/10/24
 */
public class MappedStatement {

    /**
     * 执行XML方法id
     */
    private String id;

    /**
     * 返回参数
     */
    private String resultType;

    /**
     * 请求参数
     */
    private String parameterType;

    /**
     * 执行SQL
     */
    private String sql;

    /**
     * 执行类型
     */
    private SqlCommandType commandType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlCommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(SqlCommandType commandType) {
        this.commandType = commandType;
    }
}
