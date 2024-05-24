package org.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class RequestController {
//    @DubboReference
//    private UserService userService;
//    @GetMapping(produces="application/json")
//
//    public ResultType selectAll()
//    {
//        List<User>  users = userService.getAll();
//        return  ResultType.success(ReturnCode.SUCCESS,users);
//    }
//    @GetMapping("/{id}")
//    public ResultType getById(@PathVariable("id") int id)
//    {
//        User user = userService.selectById(id);
//        if (user == null)
//        {
//            throw new BussinessException("用户不存在",ReturnCode.BUSSINESS_ERROR);
//        }
//        return ResultType.success(ReturnCode.SUCCESS,user);
//    }
//    @PutMapping
//    public ResultType updateUser(@RequestBody User user)
//    {
//        System.out.println("updateUser:" +user.toString());
//        Boolean flag = userService.updateById(user);
//        return ResultType.success(ReturnCode.SUCCESS,flag);
//    }
//    @PostMapping
//    public ResultType addUser(@RequestBody User user)
//    {
//
//        System.out.println("addUser");
//        Boolean flag = userService.addUser(user);
//        return ResultType.success(ReturnCode.SUCCESS,flag);
//    }
//    @DeleteMapping("/{id}")
//    public ResultType deleteUser(@PathVariable("id") int id)
//    {
//        System.out.println("deleteUser "+id);
//        Boolean flag = userService.deleteById(id);
//        return ResultType.success(ReturnCode.SUCCESS,flag);
//    }
}
