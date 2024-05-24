package org.example.controller;

import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @GetMapping("/register")
    public ResultType getRegisterData(){
        return null;
    }
    @GetMapping("/login")
    public ResultType getLoginData(){
        return null;
    }
    @GetMapping("/resetPwd")
    public ResultType getResetPwd(){
        return null;
    }
    @PostMapping("/register")
    public ResultType register(){
        return null;
    }
    @GetMapping("/activation/{userId}/{code}")
    public ResultType activateUser(@PathVariable("userId")int userId,
                                   @PathVariable("code")String code){
        return null;
    }
    @GetMapping("/kaptcha")
    public ResultType getKaptcha(){
        return null;
    }
    @PostMapping("/login")
    public ResultType login(@RequestParam("username") String name,
                            @RequestParam("password")String password,
                            @RequestParam("code")String code,
                            @RequestParam(value="rememberMe",required = false) boolean remember,
                            @CookieValue("kaptchaOwner")String kaptchaOwner){
        return null;
    }
    @GetMapping("/logout")
    public ResultType logout(@CookieValue("ticket") String ticket){
        return null;
    }
    @PostMapping("/resetPwd")
    public ResultType resetPwd(@RequestParam("username")String username,
                               @RequestParam("password")String password,
                               @RequestParam("emailVerifyCode") String emailVerifyCode,
                               @RequestParam("kaptchaCode")String kaptchaCode,
                               @CookieValue("kaptchaOwner")String kaptchaOwner){
        return null;
    }
    @PostMapping("/sendEmailCodeForResetPwd")
    public ResultType sendEmailCodeForResetPwd(@CookieValue(value="kaptchaOwner",required = false)String kaptchaOwner,
                                               @RequestParam(value="kaptcha")String kaptcha,
                                               @RequestParam("username")String username){
        return null;
    }

}
