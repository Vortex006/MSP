//package com.zyj.msp.ServiceImpl;
//
//import com.zyj.msp.Entity.LoginUser;
//import com.zyj.msp.Entity.User;
//import com.zyj.msp.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//@Service
//public class LoginServiceImpl implements UserDetailsService {
//
//    private final UserService userService;
//
//    @Autowired
//    public LoginServiceImpl(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = userService.getUserByName(s);
//        if (ObjectUtils.isEmpty(user)) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//        System.out.println(user);
//        return new LoginUser(user);
//    }
//}
