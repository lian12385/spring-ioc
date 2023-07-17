package com.bulianglin.spring.service;

import com.bulianglin.spring.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> queryUsers(Map<String,Object> parm);
}
