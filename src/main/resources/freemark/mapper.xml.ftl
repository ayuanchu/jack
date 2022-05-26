<#noparse><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
</#noparse>
<mapper namespace="${mapper.className}">
    <sql id="tableName">
        ${tableName}
    </sql>

    <#if global.baseColumnEnabled>
    <sql id="baseColumn">
        ${baseColumn}
    </sql>
    </#if>

</mapper>