package com.bulianglin.spring.test.V1;


import com.bulianglin.spring.ioc.BeanDefinition;
import com.bulianglin.spring.po.User;
import com.bulianglin.spring.service.UserService;
import com.bulianglin.spring.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestIoc {

    private Map<String,BeanDefinition> beanDefinitionMap = new HashMap<>();

    private Map<String,Object> singletonObjects = new HashMap<>();

    @Test
    public void test(){
        //UserService userService = (UserService) getBean("userService");
        UserService userService = new UserServiceImpl();
        //使用者
        Map<String,Object> map = new HashMap<>();
        map.put("username","空度日");
        List<User> users = userService.queryUsers(map);
        System.out.println(users);
    }

    @Before
    public void Before(){
        //BeanDefinition注册流程

    }


    private Object creatBeanInstance(BeanDefinition bd){
        //构造器创建

        // 获取构造器

        // 通过反射去创建Bean实例
        return null;
    }



    //public Constructor get
}



