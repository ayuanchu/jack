package com.kancy.mybatisplus.generator.config;

import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * DataSourceConfig
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 17:27
 **/
@Data
public class DataSourceConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString();
    private String name;
    private String remark;
    private String driver =  DatabaseDriverEnum.MySQL.name();
    private String driverClassName = DatabaseDriverEnum.valueOf(driver).getDriverClassName();
    private String jdbcUrl = "jdbc:mysql://localhost:3306/mysql?serverTimezone=GMT%2B8&useSSL=false&characterEncoding=utf8&connectTimeout=3000";
    private String username = "root";
    private String password = "root123";

    private String database;
    private String host;
    private String port;

    private transient boolean isActive;

    public DataSourceConfig() {
        parseJdbcUrl(getJdbcUrl());
    }

    public void update(DataSourceConfig config) {
        this.setName(config.getName());
        this.setRemark(config.getRemark());
        this.setDriver(config.getDriver());
        this.setJdbcUrl(config.getJdbcUrl());
        this.setUsername(config.getUsername());
        this.setPassword(config.getPassword());
        this.setDatabase(config.getDatabase());
        parseJdbcUrl(config.getJdbcUrl());
    }

    private void parseJdbcUrl(String jdbcUrl) {
        if (Objects.nonNull(jdbcUrl) && jdbcUrl.startsWith("jdbc:mysql")){
            String httpUrl = jdbcUrl.replace("jdbc:mysql","http");
            try {
                URL url = new URL(httpUrl);
                this.setHost(url.getHost());
                this.setPort(String.valueOf(url.getPort()));
                this.setDatabase(url.getPath().replace("/",""));
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
            return;
        }

        if (Objects.nonNull(jdbcUrl) && jdbcUrl.startsWith("jdbc:oracle")){
            String substring = jdbcUrl.substring(jdbcUrl.lastIndexOf("@") + 1);
            String[] strings = substring.split(":", 3);
            try {
                this.setHost(strings[0]);
                this.setPort(strings[1]);
                this.setDatabase(strings[2]);
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
            return;
        }
    }

    public String getNameAndHostAndPort() {
        return String.format("%s@%s:%s", getName(),getHost(),getPort());
    }

    public static String getDefaultJdbcUrl(String driver){
        if (StringUtils.equalsIgnoreCase(driver, "mysql")){
            return "jdbc:mysql://localhost:3306/mysql?serverTimezone=GMT%2B8&useSSL=false&characterEncoding=utf8&connectTimeout=3000";
        }else if(StringUtils.equalsIgnoreCase(driver, "oracle")){
            return "jdbc:oracle:thin:@localhost:1521:orcl";
        }
        return "";
    }

    @Override
    public String toString() {
        return String.format("<html><font%s>%s@%s:%s%s<font></html>",
                isActive() ? " color='red'" : "", getName(),
                getHost(),
                getPort(),
                isActive() ? " （ 已连接 ）" : "");
    }

    public String showInfo() {
        return "{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceConfig that = (DataSourceConfig) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
