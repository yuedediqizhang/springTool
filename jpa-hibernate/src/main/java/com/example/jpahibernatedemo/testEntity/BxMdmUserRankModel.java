package com.example.jpahibernatedemo.testEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;


/**
 * 人员任职记录对象 bx_mdm_user_rank
 *
 * @author author
 * @date 2024-05-22
 */
@Data

public class BxMdmUserRankModel
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
        任职起始日期（任职信息）
     */

    private Date startDate;

    /**
        任职起始日期（兼职借用信息）
     */

    private Date sidelineStartDate;

    /**
        任职终止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date endDate;

    /**
        人员类别
     */

    private String userType;

    /**
        任职公司
     */

    private String company;

    /**
        任职部门
     */

    private String deptName;

    /**
        任职部门Id
     */

    private Long deptId;

    /**
        职务级别
     */

    private String rankLevel;

    /**
        待遇级别
     */

    private String treatmentLevel;

    /**
        总部职级
     */

    private String headquartersRankLevel;

    /**
        板块公司职级
     */

    private String sectorCompanyLevel;

    /**
        当前版本
     */

    private Integer version;

}
