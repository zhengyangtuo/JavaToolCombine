package org.example.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.UserService;
import org.example.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class MyController {
    @DubboReference
    private UserService userService;
    @GetMapping
    public List<User> selectAll()
    {
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") int id)
    {
        return userService.selectById(id);
    }
    @PutMapping
    public Boolean updateUser(@RequestBody User user)
    {
        System.out.println("updateUser:" +user.toString());
        return userService.updateById(user);
    }
    @PostMapping
    public Boolean addUser(@RequestBody User user)
    {

        System.out.println("addUser");
        return userService.addUser(user);
    }
    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable("id") int id)
    {
        System.out.println("deleteUser "+id);
        return userService.deleteById(id);
    }
}
