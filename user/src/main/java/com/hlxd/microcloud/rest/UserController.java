package com.hlxd.microcloud.rest;


import com.alibaba.fastjson.JSON;
import com.hlxd.microcloud.entity.R;
import com.hlxd.microcloud.entity.User;
import com.hlxd.microcloud.entity.UserInfo;
import com.hlxd.microcloud.feign.AuthService;
import com.hlxd.microcloud.service.UserService;
import com.hlxd.microcloud.util.EHCacheUtil;
import com.hlxd.microcloud.util.JedisPoolUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-03-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String key = "444235786425912D";

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    @GetMapping("/login")
    public Map  getUser(
            @RequestParam(value = "userName") String username,
            @RequestParam(value = "passWord") String password) throws Exception {
        Map returnMap = new HashMap();
        User user = userService.getUser(username, password);
        if(null != user){
            Map paramMap = new HashMap();
            paramMap.put("username", username);
            paramMap.put("password", password);
            paramMap.put("grant_type", "password");
            paramMap.put("scope", "all");
            returnMap =authService.getToken(paramMap);
            UserInfo userInfo = userService.getUserInfo(username,password);
            JedisPoolUtils.setPool(String.valueOf(returnMap.get("access_token")),JSON.toJSONString(userInfo));
        }else{
            returnMap.put("msg","用户不存在");
        }
        return returnMap;
    }

    @GetMapping("/userInfo")
    public R<UserInfo> getUserInfo(
            @RequestParam(value = "userName") String username,
            @RequestParam(value = "passWord") String password) throws Exception {
        /*logger.info("获取用户互信息");
        String userName = new String(AES.decrypt(username,key.getBytes("utf-8")));
        logger.info("解析后的用户名为"+userName);
        String passwords = null;
        String pwd = null;
        if(!type.equals("1")){
            passwords= new String(AES.decrypt(password,key.getBytes("utf-8")));
            logger.info("解析后的密码为"+passwords);
            if(type.equals("2")){
                pwd= SecuritySHA1Utils.shaEncode(passwords);
                logger.info("加密后的密码为"+pwd);
            }else{
                pwd = passwords;
            }
        }*/
        return new R<>(userService.getUserInfo(username, password));
    }






}

