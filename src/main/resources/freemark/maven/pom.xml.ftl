<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${pom.groupId}</groupId>
    <artifactId>${pom.artifactId}</artifactId>
    <version>${pom.version}</version>
    <name>${pom.projectName}</name>
    <description>${pom.projectDescription}</description>

    <properties>
        <java.version>8</java.version>
        <maven.compiler.source><#noparse>${java.version}</#noparse></maven.compiler.source>
        <maven.compiler.target><#noparse>${java.version}</#noparse></maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <mysql.version>8.0.19</mysql.version>
        <mybatis-plus.version>3.1.2</mybatis-plus.version>
        <#if global.lombokEnabled>
        <lombok.version>1.18.10</lombok.version>
        </#if>
        <#if global.springBoot.enabled>
        <spring-cloud.version>Hoxton.SR4</spring-cloud.version>
        <spring-boot.version>2.2.6.RELEASE</spring-boot.version>
        <#if global.springBoot.micaEnabled>
        <mica.version>1.2.2</mica.version>
        </#if>
        <dynamic-datasource.version>2.5.8</dynamic-datasource.version>
        <druid-spring-boot-starter.version>1.1.22</druid-spring-boot-starter.version>
        <#if global.springBoot.jasyptEnabled>
        <jasypt-spring-boot-starter.version>3.0.3</jasypt-spring-boot-starter.version>
        </#if>
        <#if global.springBoot.okhttpEnabled>
        <okhttp.version>4.3.1</okhttp.version>
        </#if>
        <#if global.springBoot.dockerEnabled>
        <registry.username>username</registry.username>
        <registry.password>password</registry.password>
        <image.tag><#noparse>${version}</#noparse></image.tag>
        </#if>
        </#if>
    </properties>

    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version><#noparse>${mysql.version}</#noparse></version>
        </dependency>
        <#if global.maven.enabled>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version><#noparse>${mybatis-plus.version}</#noparse></version>
        </dependency>
        </#if>
        <#if global.springBoot.enabled>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version><#noparse>${dynamic-datasource.version}</#noparse></version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <#if global.springBoot.actuatorEnabled>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.prometheusEnabled>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.rabbitmqEnabled>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.streamRabbitEnabled>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.kafkaEnabled>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.streamKafkaEnabled>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-kafka</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.redisEnabled>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.redisLockEnabled>
        <dependency>
            <groupId>net.dreamlu</groupId>
            <artifactId>mica-plus-lock</artifactId>
            <#if !global.springBoot.micaEnabled>
            <version>1.2.2</version>
            </#if>
        </dependency>
        </#if>
        <#if global.springBoot.mongoEnabled>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.druidEnabled>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version><#noparse>${druid-spring-boot-starter.version}</#noparse></version>
        </dependency>
        </#if>
        <#if global.springBoot.micaEnabled>
        <dependency>
            <groupId>net.dreamlu</groupId>
            <artifactId>mica-boot</artifactId>
        </dependency>
        </#if>
        <#if global.springBoot.jasyptEnabled>
        <dependency>
            <groupId>com.github.ulisesbocchio</groupId>
            <artifactId>jasypt-spring-boot-starter</artifactId>
            <version><#noparse>${jasypt-spring-boot-starter.version}</#noparse></version>
        </dependency>
        </#if>
        <#if global.springBoot.okhttpEnabled>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version><#noparse>${okhttp.version}</#noparse></version>
        </dependency>
        </#if>
        <#if global.springBoot.freemarkerEnabled>
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
        </dependency>
        </#if>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        </#if>
        <#if global.controller.enabled  && !global.springBoot.enabled>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        </#if>
        <#if global.lombokEnabled>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version><#noparse>${lombok.version}</#noparse></version>
            <scope>provided</scope>
        </dependency>
        </#if>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <#if global.springBoot.enabled>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version><#noparse>${spring-boot.version}</#noparse></version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            </#if>
            <#if global.springBoot.enabled>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version><#noparse>${spring-cloud.version}</#noparse></version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            </#if>
            <#if global.springBoot.micaEnabled>
            <dependency>
                <groupId>net.dreamlu</groupId>
                <artifactId>mica-bom</artifactId>
                <version><#noparse>${mica.version}</#noparse></version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            </#if>
        </dependencies>
    </dependencyManagement>

    <build>
        <finalName><#noparse>${project.artifactId}</#noparse></finalName>
        <#if global.springBoot.enabled>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <#if global.springBoot.dockerEnabled>
            <#noparse>
            <plugin>
                <groupId>com.google.cloud.tools</groupId>
                <artifactId>jib-maven-plugin</artifactId>
                <version>2.2.0</version>
                <executions>
                    <execution>
                        <phase>install</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <allowInsecureRegistries>true</allowInsecureRegistries>
                    <from>
                        <image>registry.cn-hangzhou.aliyuncs.com/kancy/openjdk:8-jre-alpine</image>
                    </from>
                    <to>
                        <image>registry.cn-hangzhou.aliyuncs.com/kancy/${project.artifactId}</image>
                        <auth>
                            <username>${registry.username}</username>
                            <password>${registry.password}</password>
                        </auth>
                        <tags>
                            <tag>${image.tag}</tag>
                            <tag>latest</tag>
                        </tags>
                    </to>
                    <container>
                        <creationTime>USE_CURRENT_TIMESTAMP</creationTime>
                        <volumes>/tmp</volumes>
                        <workingDirectory>/home</workingDirectory>
                        <environment>
                            <TZ>Asia/Shanghai</TZ>
                        </environment>
                    </container>
                </configuration>
            </plugin>
            </#noparse>
            </#if>
        </plugins>
        </#if>
    </build>

    <#if global.maven.repoEnabled>
    <repositories>
        <repository>
            <id>local-repo</id>
            <url>${global.maven.repoUrl}</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>local-repo</id>
            <url>${global.maven.repoUrl}</url>
        </pluginRepository>
    </pluginRepositories>
    </#if>
</project>