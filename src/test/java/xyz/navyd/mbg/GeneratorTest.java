package xyz.navyd.mbg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import test.dao.UserInfoMapper;


public class GeneratorTest {
  @BeforeAll
  static void exe() throws Exception {
    //MBG 执行过程中的警告信息
    var warnings = new ArrayList<String>();
    //读取我们的 MBG 配置文件
    var is = GeneratorTest.class.getResourceAsStream("/generatorConfig.xml");
    var cp = new ConfigurationParser(warnings);
    var config = cp.parseConfiguration(is);

    is.close();// 优化
    //当生成的代码重复时，覆盖原代码
    var callback = new DefaultShellCallback(true);
    //创建 MBG
    var myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    //执行生成代码
    myBatisGenerator.generate(null);
    //输出警告信息
    for (String warning : warnings) {
      System.out.println(warning);
    }
  }

  @Test
  void basics() throws IOException {
    InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    try (SqlSession session = sqlSessionFactory.openSession()) {
      var mapper = session.getMapper(UserInfoMapper.class);
      var user = mapper.selectByPrimaryKey(1L);
      Assertions.assertTrue(user.isPresent());
    }
  }
}