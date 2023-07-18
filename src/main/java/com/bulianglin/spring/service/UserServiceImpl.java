package com.bulianglin.spring.service;

import com.bulianglin.spring.dao.UserDao;
import com.bulianglin.spring.dao.UserDaoImpl;
import com.bulianglin.spring.po.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserServiceImpl implements UserService{
    private UserDaoImpl userDao;
    @Override
    public List<User> queryUsers(Map<String, Object> parm) {
        return userDao.queryUserList(parm);
    }
}
