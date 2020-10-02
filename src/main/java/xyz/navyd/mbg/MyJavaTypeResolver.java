package xyz.navyd.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * 定制java resolve type行为
 * <p>支持使用is_前缀的直接使用Boolean类型</p>
 * @author navyd
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final String BOOLEAN_PREFIX = "is_";

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.getActualColumnName().startsWith(BOOLEAN_PREFIX)) {
            return new FullyQualifiedJavaType(Boolean.class.getName());
        } else {
            return super.calculateJavaType(introspectedColumn);
        }
    }
}
