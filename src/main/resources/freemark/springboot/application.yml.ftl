server:
  port: 8088

spring:
  application:
    name: application
  profiles:
    active: dev,db
<#if global.springBoot.redisEnabled >
  redis:
    host: redis.kancy.top
    port: 6379
    password: root123
    database: 10
</#if>

<#if global.springBoot.redisLockEnabled >
mica:
  lock:
    address: redis://47.100.244.76:6379
    password: root123
</#if>

<#if global.springBoot.prometheusEnabled >
<#noparse>
management:
  metrics:
    tags:
      application: ${spring.application.name}
  endpoints:
    web:
      exposure:
        include: health,prometheus
</#noparse>
</#if>