package com.example.jpahibernatedemo.testEntity;


import lombok.Data;


/**
 * 用户家庭成员信息对象 bx_mdm_user_family_memeber
 *
 * @author author
 * @date 2024-05-22
 */
@Data

public class BxMdmUserFamilyMemeberModel
{

    /**
        $column.columnComment
     */

    private Long id;

    /**
        用户主键
     */

    private Long userId;

    /**
        家庭成员姓名
     */

    private String name;

    /**
        与本人关系
     */

    private String relation;

    /**
        政治面貌
     */

    private String politicalLandscape;

    /**
        联系方式
     */

    private String phone;

    /**
        工作单位
     */

    private String unit;

    /**
        职务
     */

    private String post;

    /**
        职业
     */

    private String occupation;

    /**
        当前版本
     */

    private Integer version;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

}
