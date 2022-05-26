spring:
<#if global.springBoot.druidEnabled>
  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
</#if>
  datasource:
<#if global.springBoot.hakiriEnabled>
    dynamic:
      hikari:
        connection-test-query: SELECT 1
</#if>
<#if global.springBoot.druidEnabled>
    druid:
      stat-view-servlet:
        loginUsername: admin
        loginPassword: 123456
    dynamic:
      druid:
        filters: stat,wall
        connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
        useGlobalDataSourceStat: true
        pool-prepared-statements: true
        max-pool-prepared-statement-per-connection-size: 20
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 300000
        test-while-idle: true
        test-on-borrow: false
        test-on-return: false
        validation-query: SELECT 1
</#if>
      primary: master
      datasource:
        master:
          driverClassName: ${driverClassName}
          url: ${jdbcUrl}
          username: "${datasource.username}"
          password: "${datasource.password}"
<#if global.springBoot.hakiriEnabled>
          hikari:
            max-pool-size: 10
            min-idle: 5
</#if>
<#if global.springBoot.druidEnabled>
          druid:
            max-active: 10
            initial-size: 1
            max-wait: 60000
            min-idle: 5
</#if>

mybatis-plus:
  mapper-locations: classpath*:/${global.mapperXml.packageName}/*Mapper.xml
  typeAliasesPackage: ${entity.packageName}
  global-config:
    db-config:
      id-type: id_worker
      insert-strategy: not_empty
      update-strategy: not_empty
      select-strategy: not_empty
    datacenter-id: <#noparse>${nodeId:${spring.nodeId:1}}</#noparse>
    worker-id: <#noparse>${nodeId:${spring.nodeId:1}}</#noparse>
    banner: false
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
    jdbc-type-for-null: null