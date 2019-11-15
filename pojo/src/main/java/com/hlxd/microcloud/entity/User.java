package com.hlxd.microcloud.entity;

import lombok.Data;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author admin
 * @since 2019-02-22
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 部门ID
     */
    private String deptId;
    /**
     * 公司代码
     */
    private String companyCode;
    /**
     * 工厂代码
     */
    private String factoryNo;
    /**
     * 性别
     */
    private Integer sex;
    /**
     *  是否有效
     */
    private Integer enable;
    /**
     *  创建人
     */
    private String creator;
    /**
     *  创建时间
     */
    private String createTime;
    /**
     * 权限列表
     * */
    private List<String> roleList;
    /**
     * 修改角色ID
     * */
     private String roleId;



}
