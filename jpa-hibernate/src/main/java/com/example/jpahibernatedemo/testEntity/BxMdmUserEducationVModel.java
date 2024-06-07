package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;


/**
 * 人员教育信息对象 bx_mdm_user_education_v
 *
 * @author author
 * @date 2024-05-25
 */
@Data

public class BxMdmUserEducationVModel
{

    /**
        $column.columnComment
     */

    private Long id;

    /**
        $column.columnComment
     */

    private Long userId;

    /**
        入学日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date startDate;

    /**
        毕业日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date endDate;

    /**
        学校
     */

    private String school;

    /**
        专业
     */

    private String specialty;

    /**
        学历
     */

    private String qualification;

    /**
        学位
     */

    private String degree;

    /**
        是否是最高学历，0是1否
     */

    private String highestQualification;

    /**
        是否是最高学位，0是1否
     */

    private String highestDegree;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

    /**
        当前版本
     */

    private Integer version;

}
