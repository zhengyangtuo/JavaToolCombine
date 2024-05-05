package org.example.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.UserService;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User selectById(int id) {
        return userMapper.select(id);
    }

    @Override
    public Boolean updateById(User user) {
        userMapper.update(user) ;
        return true;
    }

    @Override
    public Boolean addUser(User user) {
        userMapper.save(user);
        return  true;
    }

    @Override
    public Boolean deleteById(int id) {
        userMapper.delete(id);
        return true;
    }
}
