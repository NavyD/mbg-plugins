package xyz.navyd.mbg;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * xxxMapper.java生成时禁用所有方法，典型用法super mapper:
 * <p>{@code public interface UserInfoMapper extends BaseMapper<UserInfo> {}}</p>
 * @author navyd
 */
public class DisableAllMethodJavaClientGenerator extends JavaMapperGenerator {
  private String project;

  public DisableAllMethodJavaClientGenerator() {
    super(null);
    this.project = null;
  }

  @Override
  public String getProject() {
    if (this.project == null) {
      this.project = this.context.getJavaClientGeneratorConfiguration().getTargetProject();
    }
    return this.project;
  }

  /**
   * 该实现在{@link JavaMapperGenerator#getCompilationUnits()}仅去除了对多个方法的添加
   * @return
   */
  @Override
  public List<CompilationUnit> getCompilationUnits() {
    // project path 从context中取得，null可导致无法输出xxxMapper.java文件error
    if (this.getProject() == null) {
      throw new IllegalStateException("未找到Project path");
    }
    progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
        introspectedTable.getFullyQualifiedTable().toString()));
    CommentGenerator commentGenerator = context.getCommentGenerator();

    FullyQualifiedJavaType type = new FullyQualifiedJavaType(
        introspectedTable.getMyBatis3JavaMapperType());
    Interface interfaze = new Interface(type);
    interfaze.setVisibility(JavaVisibility.PUBLIC);
    commentGenerator.addJavaFileComment(interfaze);

    String rootInterface = introspectedTable
        .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
    if (!stringHasValue(rootInterface)) {
      rootInterface = context.getJavaClientGeneratorConfiguration()
          .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
    }

    if (stringHasValue(rootInterface)) {
      FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
          rootInterface);
      interfaze.addSuperInterface(fqjt);
      interfaze.addImportedType(fqjt);
    }

    // remove all methods

    List<CompilationUnit> answer = new ArrayList<>();
    if (context.getPlugins().clientGenerated(interfaze, introspectedTable)) {
      answer.add(interfaze);
    }
    List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
    if (extraCompilationUnits != null) {
      answer.addAll(extraCompilationUnits);
    }
    return answer;
  }
}
