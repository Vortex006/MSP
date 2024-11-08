package com.vortex.msp.Service;

import com.vortex.msp.Entity.User;

import java.util.List;

public interface UserService {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getUserByName(String username);

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    User getUser(Integer userId);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Integer userId);

    List<User> listUsers();

    List<User> limitUsers(Integer pageSize, Integer index);
}
