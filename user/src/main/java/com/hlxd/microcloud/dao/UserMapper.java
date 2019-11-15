package com.hlxd.microcloud.dao;



import com.hlxd.microcloud.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-03-06
 */
public interface UserMapper {
    User getUserInfo(@Param("name")String name, @Param("passWord")String password);

}
