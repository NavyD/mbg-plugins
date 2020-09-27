package xyz.navyd.mbg.plugin;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * 支持对每个mapper使用可泛型的SuperMapper。
 * <p>如：{@code public interface UserInfoMapper extends BaseMapper<UserInfo>}</p>
 * <p>如：{@code public interface BonusEventLogMapper extends BaseMapper<BonusEventLog>}</p>
 * @author navyd
 */
public class SuperMapperClientPlugin extends PluginAdapter {
  private static final String PROPERTY_SUPER_MAPPER = "superMapper";
  private static final String PROPERTY_HAS_GENERIC_ENTITY = "hasGenericEntity";

  private String superMapper;
  private Boolean hasGenericEntity;

  public SuperMapperClientPlugin() {
  }

  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  @Override
  public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
    if (this.superMapper == null || this.superMapper.isEmpty()) {
      return true;
    }
    // import super mapper
    FullyQualifiedJavaType superMapperType = new FullyQualifiedJavaType(this.superMapper);
    interfaze.addImportedType(superMapperType);
    // add entity generic
    if (this.hasGenericEntity) {
      String entityName = introspectedTable.getBaseRecordType();
      // import entity
      interfaze.addImportedType(new FullyQualifiedJavaType(entityName));
      // get entity shortname
      int i = entityName.lastIndexOf('.');
      entityName = entityName.substring(i < 0 ? 0 : i + 1, entityName.length());
      FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(
          superMapperType.getShortName() + "<" + entityName + ">"
      );
      interfaze.addSuperInterface(entityType);
    }
    return true;
  }

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
    properties.forEach((key, value) -> {
      if (PROPERTY_SUPER_MAPPER.equalsIgnoreCase(key.toString())) {
        this.superMapper = value.toString();
      } else if (PROPERTY_HAS_GENERIC_ENTITY.equalsIgnoreCase(key.toString())) {
        this.hasGenericEntity = Boolean.valueOf(value.toString());
      }
    });
  }
}
