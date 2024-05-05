package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

//@Transactional
@Repository
public interface UserMapper {
    @Insert("insert into user(name,userId,age,phone,email) values(#{name},#{userId},#{age},#{phone},#{email})")
    public void save(User user);
    @Update("update user set name=#{name},age=#{age},phone=#{phone},email=#{email} where userId = #{userId}")
    public void update(User user);

    @Delete("delete from user where userId = #{userId}")
    public void delete(int userId);

    @Select("select * from user where userId=${userId}")
    public User select(int userId);

    @Select("select * from user")
    public List<User> getAll();
}
