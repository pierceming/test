package cn.itcast.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.itcast.domain.User;


public interface UserMapper {
	
	@Select("select * from t_user where id=#{id}")
	public User getOne(Long id);
	
	@Select("select * from t_user")
	public List<User> findAll();
	
	@Insert("insert into t_user values(null,#{name},#{age},#{gender},#{birthday},#{salary})")
	public void add(User user);
	
	@Delete("delete from t_user where id=#{id}")
	public void delete(Long id);
	
}
