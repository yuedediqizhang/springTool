package com.example.jpahibernatedemo.testEntity;

import lombok.Data;


/**
 * 部门主数据对象 bx_mdm_dept
 *
 * @author author
 * @date 2024-05-24
 */
@Data

public class BxMdmDeptModel
{

    /**
        部门id
     */

    private Long deptId;
    private Long parentId;
    private String ancestors;
    private Integer orderNum;

    /**
        部门编码
     */

    private String deptCode;

    /**
        部门层级
     */

    private Integer grade;

    /**
        部门名称
     */

    private String deptName;

    /**
        负责人工号
     */

    private String managerId;

    /**
        负责人姓名
     */

    private String managerName;

    /**
        联系电话
     */

    private String phone;

    /**
        邮箱
     */

    private String email;

    /**
        地址
     */

    private String address;

    /**
        部门描述
     */

    private String description;

    /**
        部门状态（0正常 1停用）
     */

    private String status;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

    /**
        当前版本
     */
    private Integer version;

    private String uuid;

    private String errorMsg;

}
