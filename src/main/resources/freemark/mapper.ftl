package ${mapper.packageName};

import ${entity.className};
import ${mapper.supperClassPackageName}.${mapper.supperClassSimpleName};
import org.apache.ibatis.annotations.Mapper;

/**
 * ${tableRemark}(${tableName})数据Mapper
 *
 * @author ${author}
 * @since ${datetime}
 * @description ${description}
*/
@Mapper
public interface ${mapper.name} extends ${mapper.supperClassSimpleName}${r"<"}${entity.name}> {

}
