package xyz.navyd.mbg.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;


/**
 * A MyBatis Generator plugin to use Lombok's annotations.
 * For example, use @Data annotation instead of getter ands setter.
 *
 * <p>在entity上可用lombok注解</p>
 *
 * @author Paolo Predonzani (http://softwareloop.com/)
 * @author navyd
 */
@Slf4j
public class LombokPlugin extends PluginAdapter {

  private final Collection<Annotations> annotations;

  /**
   * LombokPlugin constructor
   */
  public LombokPlugin() {
    annotations = new LinkedHashSet<>(Annotations.values().length);
  }

  /**
   * @param warnings list of warnings
   * @return always true
   */
  @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  /**
   * Intercepts base record class generation
   *
   * @param topLevelClass     the generated base record class
   * @param introspectedTable The class containing information about the table as
   *                          introspected from the database
   * @return always true
   */
  @Override
  public boolean modelBaseRecordClassGenerated(
      TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable
  ) {
    addAnnotations(topLevelClass);
    return true;
  }

  /**
   * Intercepts primary key class generation
   *
   * @param topLevelClass     the generated primary key class
   * @param introspectedTable The class containing information about the table as
   *                          introspected from the database
   * @return always true
   */
  @Override
  public boolean modelPrimaryKeyClassGenerated(
      TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable
  ) {
    addAnnotations(topLevelClass);
    return true;
  }

  /**
   * Intercepts "record with blob" class generation
   *
   * @param topLevelClass     the generated record with BLOBs class
   * @param introspectedTable The class containing information about the table as
   *                          introspected from the database
   * @return always true
   */
  @Override
  public boolean modelRecordWithBLOBsClassGenerated(
      TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable
  ) {
    addAnnotations(topLevelClass);
    return true;
  }

  /**
   * Prevents all getters from being generated.
   * See SimpleModelGenerator
   *
   * @param method             the getter, or accessor, method generated for the specified
   *                           column
   * @param topLevelClass      the partially implemented model class
   * @param introspectedColumn The class containing information about the column related
   *                           to this field as introspected from the database
   * @param introspectedTable  The class containing information about the table as
   *                           introspected from the database
   * @param modelClassType     the type of class that the field is generated for
   */
  @Override
  public boolean modelGetterMethodGenerated(
      Method method,
      TopLevelClass topLevelClass,
      IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable,
      ModelClassType modelClassType
  ) {
    return false;
  }

  /**
   * Prevents all setters from being generated
   * See SimpleModelGenerator
   *
   * @param method             the setter, or mutator, method generated for the specified
   *                           column
   * @param topLevelClass      the partially implemented model class
   * @param introspectedColumn The class containing information about the column related
   *                           to this field as introspected from the database
   * @param introspectedTable  The class containing information about the table as
   *                           introspected from the database
   * @param modelClassType     the type of class that the field is generated for
   * @return always false
   */
  @Override
  public boolean modelSetterMethodGenerated(
      Method method,
      TopLevelClass topLevelClass,
      IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable,
      ModelClassType modelClassType
  ) {
    return false;
  }

  /**
   * Adds the lombok annotations' imports and annotations to the class
   *
   * @param topLevelClass the partially implemented model class
   */
  private void addAnnotations(TopLevelClass topLevelClass) {
    for (Annotations annotation : annotations) {
      topLevelClass.addImportedType(annotation.javaType);
      topLevelClass.addAnnotation(annotation.asAnnotation());
    }
  }

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
    if (properties.isEmpty()) {
        log.warn("No lombok annotation is set");
        return;
    }
    for (String annotationName : properties.stringPropertyNames()) {
      if (annotationName.contains(".")) {
        // Not an annotation name
        continue;
      }
      String value = properties.getProperty(annotationName);
      // The annotation is disabled, skip it
      if (!Boolean.parseBoolean(value)) {
        log.info("lombok annotation disabled: {}={}", annotationName, value);
        continue;
      }
      Annotations annotation = Annotations.getValueOf(annotationName);
      if (annotation == null) {
          log.info("The corresponding lombok annotation was not found for property name: {}",
              annotationName);
          continue;
      }
      // 找到lombok注解关联属性
      String optionsPrefix = annotationName + ".";
      for (String propertyName : properties.stringPropertyNames()) {
          if (propertyName.startsWith(optionsPrefix)) {
              String propertyValue = properties.getProperty(propertyName);
              // 注入属性
              annotation.appendOptions(propertyName, propertyValue);
          }
      }
      annotations.add(annotation);
      annotations.addAll(Annotations.getDependencies(annotation));
    }
    log.info("found annotations from properties: {}", this.annotations);
  }

  enum Annotations {
    /**
     * {@link lombok.Data}
     */
    DATA("data", "@Data", "lombok.Data"),
    BUILDER("builder", "@Builder", "lombok.Builder"),
    ALL_ARGS_CONSTRUCTOR("allArgsConstructor", "@AllArgsConstructor", "lombok.AllArgsConstructor"),
    NO_ARGS_CONSTRUCTOR("noArgsConstructor", "@NoArgsConstructor", "lombok.NoArgsConstructor"),
    ACCESSORS("accessors", "@Accessors", "lombok.experimental.Accessors"),
    TO_STRING("toString", "@ToString", "lombok.ToString"),
    SUPER_BUILDER("superBuilder", "@SuperBuilder", "lombok.experimental.SuperBuilder"),
    EQUALS_AND_HASH_CODE("EqualsAndHashCode", "@EqualsAndHashCode", "lombok.EqualsAndHashCode");


    private final String paramName;
    private final String name;
    private final FullyQualifiedJavaType javaType;
    private final List<String> options;


    Annotations(String paramName, String name, String className) {
      this.paramName = paramName;
      this.name = name;
      this.javaType = new FullyQualifiedJavaType(className);
      this.options = new ArrayList<>();
    }

    private static Annotations getValueOf(String paramName) {
        for (Annotations annotation : Annotations.values()) {
            if (String.CASE_INSENSITIVE_ORDER.compare(paramName, annotation.paramName) == 0) {
                return annotation;
            }
        }

      return null;
    }

    private static Collection<Annotations> getDependencies(Annotations annotation) {
        if (annotation == ALL_ARGS_CONSTRUCTOR) {
            return Collections.singleton(NO_ARGS_CONSTRUCTOR);
        } else {
            return Collections.emptyList();
        }
    }

    // A trivial quoting.
    // Because Lombok annotation options type is almost String or boolean.
    private static String quote(String value) {
        if (Boolean.TRUE.toString().equals(value) || Boolean.FALSE.toString().equals(value))
        // case of boolean, not passed as an array.
        {
            return value;
        }
      return value.replaceAll("[\\w]+", "\"$0\"");
    }

    private void appendOptions(String key, String value) {
      String keyPart = key.substring(key.indexOf(".") + 1);
      String valuePart = value.contains(",") ? String.format("{%s}", value) : value;
      this.options.add(String.format("%s = %s", keyPart, quote(valuePart)));
    }

    private String asAnnotation() {
      if (options.isEmpty()) {
        return name;
      }
      StringBuilder sb = new StringBuilder();
      sb.append(name);
      sb.append("(");
      boolean first = true;
      for (String option : options) {
        if (first) {
          first = false;
        } else {
          sb.append(", ");
        }
        sb.append(option);
      }
      sb.append(")");
      return sb.toString();
    }
  }
}
