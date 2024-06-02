package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Entity.User;
import com.zyj.msp.Exception.ParameterNullException;
import com.zyj.msp.Mapper.UserMapper;
import com.zyj.msp.Service.UserService;
import com.zyj.msp.Utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByName(String username) {
        if (!StringUtils.hasText(username)) {
            throw new ParameterNullException();
        }
        User user = userMapper.getUserByName(username);
        return user;
    }

    @Override
    public User getUser(Integer userId) {
        User user = userMapper.getUser(userId);
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        int i = userMapper.saveUser(user);
        return i > 0;
    }

    @Override
    public boolean updateUser(User user) {
        int i = userMapper.updateUser(user);
        return i > 0;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        int i = userMapper.deleteUser(userId);
        return i > 0;
    }

    @Override
    public List<User> listUsers() {
        List<User> users = userMapper.listUsers();
        return users;
    }

    @Override
    public List<User> limitUsers(Integer pageSize, Integer index) {
        if (pageSize <= 0 || index <= 0) {
            throw new ParameterNullException("参数异常");
        }
        int offset = DataUtil.getOffset(pageSize, index);
        List<User> users = userMapper.limitUsers(pageSize, offset);
        return users;
    }
}
