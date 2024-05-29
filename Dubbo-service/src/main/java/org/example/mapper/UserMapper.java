package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.User;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

//@Transactional
@Mapper
public interface UserMapper {

    User findUserById(int userId);
}
