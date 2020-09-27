package xyz.navyd.mbg.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ExampleMapper<T> {
  List<T> selectByExample(Object example);

  int countByExample(Object example);

  int updateByExample(@Param("record") T record, @Param("example") Object example);

  int updateByExampleSelective(@Param("record") T record, @Param("example") Object example);

  int deleteByExample(Object example);

  List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds);
}
