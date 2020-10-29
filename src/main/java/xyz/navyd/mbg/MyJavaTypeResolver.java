package xyz.navyd.mbg;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.util.*;


/**
 * 定制java resolve type行为
 * <p>支持使用正则表达式匹配column替换为指定的java type，要求对应的java type必须在classpath中。
 * </p>
 * <p>
 * 支持自定义column name对应的type handler.可以不用在classpath中
 * </p>
 * 示例：
 * <p>
 * {@code <property name="regex..*price$" value="javaType=org.joda.money.Money,typeHandler=xyz.navyd.mbg.dao.typehandler.MoneyTypeHandler"/>/>}
 *
 * @author navyd
 */
@Slf4j
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final String PREFIX_REGEX = "regex.";
    private static final String DELIMITER_LIST = ",";
    private static final String DELIMITER_KEY_VALUE = "=";
    private static final String KEY_TYPE_HANDLER = "typeHandler";
    private static final String KEY_JAVA_TYPE = "javaType";

    private final Map<String, ColumnType> columnRegexes = new HashMap<>();

    @Data
    static class ColumnType {
        private String javaType;
        private String typeHandler;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        // to find column types
        properties.forEach((k, v) -> {
            String name = k.toString();
            if (!name.startsWith(PREFIX_REGEX)) {
                return;
            }
            String regex = name.substring(PREFIX_REGEX.length());
            ColumnType type = new ColumnType();
            for (var s : v.toString().split(DELIMITER_LIST)) {
                var keyVal = s.split(DELIMITER_KEY_VALUE);
                if (keyVal.length != 2) {
                    log.warn("skipped property: {}. value is not a key Val form: {}", name, s);
                    return;
                }
                if (KEY_JAVA_TYPE.equalsIgnoreCase(keyVal[0].trim())) {
                    type.setJavaType(keyVal[1].trim());
                } else if (KEY_TYPE_HANDLER.equalsIgnoreCase(keyVal[0].trim())) {
                    type.setTypeHandler(keyVal[1].trim());
                } else {
                    log.warn("skipped unsupported regex {} -> key-val: {}", regex, s);
                }
            }
            this.columnRegexes.put(regex, type);
            log.debug("found regex property: {} => {}", regex, type);
        });
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        var name = introspectedColumn.getActualColumnName();
        for (var entry : this.columnRegexes.entrySet()) {
            if (name.matches(entry.getKey())) {
                var type = entry.getValue();
                log.debug("column {} are matched. Preparing to replace type: {} -> {}", name, introspectedColumn.getJdbcTypeName(), type);
                introspectedColumn.setTypeHandler(type.typeHandler);
                return new FullyQualifiedJavaType(type.javaType);
            }
        }
        return super.calculateJavaType(introspectedColumn);
    }
}
