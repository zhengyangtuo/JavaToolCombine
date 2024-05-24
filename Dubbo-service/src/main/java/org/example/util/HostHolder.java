package org.example.util;

import org.example.pojo.User;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private ThreadLocal<User> user = new ThreadLocal<User>();
    {
        System.out.println("HostHolder init");
    }
    public User getUser() {
        return user.get();
    }

    public void setUser(User user) {
        this.user.set(user);
    }
    public void clear(){
        user.remove();
    }
}
