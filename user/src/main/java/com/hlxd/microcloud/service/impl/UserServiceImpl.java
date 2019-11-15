package com.hlxd.microcloud.service.impl;


import com.hlxd.microcloud.dao.UserMapper;
import com.hlxd.microcloud.entity.SysUser;
import com.hlxd.microcloud.entity.User;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-03-06
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserInfo(String name, String password) {
        UserInfo userInfo = new UserInfo();
        User user = userMapper.getUserInfo(name,password);
        if(null != user){
            String superUser = "";
            SysUser sysUser = new SysUser();
            sysUser.setUserId(1);
            sysUser.setUsername(name);
            sysUser.setPassword(password);
            sysUser.setFlag(superUser);
            userInfo.setSysUser(sysUser);
        }
        String[] permission =new String[1];
        for(int i=0;i<1;i++){
            permission[i]="/demo/user/user/userInfo";
        }
        String[] roles = new String[1];
        for(int i=0;i<1;i++){
            roles[i]="admin";
        }
        userInfo.setRoles(roles);
        userInfo.setPermissions(permission);
        return userInfo;
    }

    @Override
    public User getUser(String name, String password) {
        return userMapper.getUserInfo(name, password);
    }
}
