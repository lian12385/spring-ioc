package com.bulianglin.spring.dao;


import lombok.Data;
import com.bulianglin.spring.po.User;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class UserDaoImpl implements UserDao {
    private BasicDataSource dataSource;

    public void init(){
        System.out.println("userDaoImpl初始化方法调用");
    }

    @Override
    public List<User> queryUserList(Map<String, Object> param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            String sql = "select * from user";
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                userList.add(user);
            }
        }
        catch (Exception e){
            System.out.println("get an Exception");
        }
        return userList;
    }
}
