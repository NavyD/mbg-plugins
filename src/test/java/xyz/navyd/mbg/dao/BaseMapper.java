package xyz.navyd.mbg.dao;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseMapper<T> extends ExampleMapper<T> {
  default List<T> selectAll() {
    return selectByExample(null);
  }

  Optional<T> selectByPrimaryKey(Object key);

  int insert(T record);

  int insertSelective(T record);

  int updateByPrimaryKey(T record);

  int updateByPrimaryKeySelective(T record);

  int deleteByPrimaryKey(Object key);
}
