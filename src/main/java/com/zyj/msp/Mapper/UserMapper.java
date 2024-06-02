package com.zyj.msp.Mapper;

import com.zyj.msp.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名获取用户
     *
     * @param userName 用户名
     */
    User getUserByName(String userName);

    /**
     * 根据用户id获取用户
     *
     * @param userId 用户id
     */
    User getUser(Integer userId);

    int saveUser(User user);

    int updateUser(User user);

    int deleteUser(Integer userId);

    List<User> listUsers();

    List<User> limitUsers(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);
}
