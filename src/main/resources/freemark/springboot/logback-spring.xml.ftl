<#noparse><configuration>
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径 -->
    <property resource="application.yml" />
    <springProperty scope="context" name="springAppName" source="spring.application.name"/>

    <property name="LOG_NAME" value="spring"/>
    <property name="LOG_HOME" value="logs"/>

    <property name="LOG_FILE" value="${LOG_HOME}/${LOG_NAME}-%d{yyyy-MM-dd}.%i.log"/>
    <property name="LOG_ERROR_FILE" value="${LOG_HOME}/${LOG_NAME}-%d{yyyy-MM-dd}.err.%i.log"/>

    <!-- 彩色日志 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}" />

    <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符
    <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %class{50}.%method[%file:%line] - %msg%n</pattern>
    -->
    <property name="LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %class{50}.%method[%file:%line] - %msg%n" />

    <!-- 控制台输出设置 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <!-- 控制台输出设置 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <!-- 按照每天生成日志文件 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--日志文件输出的文件名 -->
            <fileNamePattern>${LOG_FILE}</fileNamePattern>
            <maxFileSize>100MB</maxFileSize>
            <!--日志文件保留天数 -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
    </appender>
    <!-- 按照每天生成错误日志文件 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--日志文件输出的文件名 -->
            <fileNamePattern>${LOG_ERROR_FILE}</fileNamePattern>
            <maxFileSize>100MB</maxFileSize>
            <!--日志文件保留天数 -->
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--过滤掉spring和hibernate的一些无用的debug信息-->
    <logger name="org.springframework" level="INFO"/>

    <!-- 默认 -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
    </root>
    <!-- 日志输出级别 -->
    <springProfile name="dev">
        <root level="INFO">
            <appender-ref ref="FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="CONSOLE" />
        </root>
    </springProfile>
    <springProfile name="test">
        <root level="INFO">
            <appender-ref ref="FILE" />
            <appender-ref ref="ERROR_FILE" />
            <appender-ref ref="STDOUT" />
        </root>
    </springProfile>
    <springProfile name="prd">
        <root level="INFO">
            <appender-ref ref="FILE" />
            <appender-ref ref="ERROR_FILE" />
        </root>
    </springProfile>

</configuration></#noparse>