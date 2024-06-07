package com.example.jpahibernatedemo.testEntity;


import lombok.Data;


/**
 * 主数据人员岗位信息对象 bx_mdm_user_post
 *
 * @author author
 * @date 2024-05-21
 */
@Data

public class BxMdmUserPostModel
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
        岗位（人员工作信息）亚苗修正一下后勤人员岗位名称
     */

    private String name;

    /**
        岗位序列辅助列
     */

    private String auxiliarySequence;

    /**
        岗位序列;新增字段，需要添加
     */

    private String sequence;

    /**
        岗位子序列
     */

    private String subsequence;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

    /**
        当前版本
     */

    private Integer version;

}
