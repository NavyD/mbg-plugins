package xyz.navyd.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * 定制java resolve type行为
 * <p>支持使用is_前缀的直接使用Boolean类型.使用property: {@link ConfigConsts#PREFIX_IS_TO_BOOLEAN}。
 * 该属性不会移除model类上的is_前缀。如果需要可以使用{@code <columnRenamingRule searchString="is_" replaceString=""/>}
 * </p>
 *
 * @author navyd
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final String BOOLEAN_PREFIX = "is_";
    private boolean prefixIsToBoolean = false;

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.prefixIsToBoolean = StringUtility
                .isTrue(properties
                        .getProperty(ConfigConsts.PREFIX_IS_TO_BOOLEAN));
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        if (this.prefixIsToBoolean && introspectedColumn.getActualColumnName().startsWith(BOOLEAN_PREFIX)) {
            return new FullyQualifiedJavaType(Boolean.class.getName());
        } else {
            return super.calculateJavaType(introspectedColumn);
        }
    }
}
