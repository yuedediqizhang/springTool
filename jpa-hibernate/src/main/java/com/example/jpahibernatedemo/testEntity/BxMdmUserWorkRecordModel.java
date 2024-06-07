package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;


/**
 * 人员工作履历信息对象 bx_mdm_user_work_record
 *
 * @author author
 * @date 2024-05-22
 */
@Data

public class BxMdmUserWorkRecordModel
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
        履历开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date startDate;

    /**
        结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date endDate;

    /**
        单位
     */

    private String unit;

    /**
        职务
     */

    private String post;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

    /**
        当前版本
     */

    private Integer version;

}
