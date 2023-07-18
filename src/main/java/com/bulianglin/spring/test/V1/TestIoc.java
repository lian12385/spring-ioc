package com.bulianglin.spring.test.V1;


import com.bulianglin.spring.dao.UserDao;
import com.bulianglin.spring.dao.UserDaoImpl;
import com.bulianglin.spring.ioc.BeanDefinition;
import com.bulianglin.spring.po.User;
import com.bulianglin.spring.service.UserService;
import com.bulianglin.spring.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
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
        //旧代码 , 需要手动设置
//        UserServiceImpl userService = new UserServiceImpl();
//        UserDaoImpl userDao = new UserDaoImpl();
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/im_bird?characterEncoding=utf8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        userDao.setDataSource(dataSource);
//        userService.setUserDao(userDao);

        UserService userService = (UserService) getBean("userService");

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

    private Object getBean(String name) {
        Object bean = this.singletonObjects.get(name);
        if (bean != null){
            return bean;
        }
        BeanDefinition bd = this.beanDefinitionMap.get(name);
        if (bd == null){
            return null;
        }
        if ("singleton".equals(bd.getScope())){
            bean = creatBeanInstance(bd);
            this.singletonObjects.put(name,bean);
        }
        else if("prototype".equals(bd.getScope())){
            bean = creatBeanInstance(bd);
        }
        return bean;
    }

    private Object creatBeanInstance(BeanDefinition bd){
        //构造器创建

        // 获取构造器

        // 通过反射去创建Bean实例
        return null;
    }



    //public Constructor get
}



