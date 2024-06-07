package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;


/**
 * 人员合同信息对象 bx_mdm_user_contract
 *
 * @author author
 * @date 2024-05-22
 */
@Data
public class BxMdmUserContractModel
{


    private Long id;

    /**
        $column.columnComment
     */

    private Long userId;

    /**
        “固定期限”、“无固定期限”
     */

    private String termType;

    /**
        必填，填首次签订合同日期，如果中途离职后再次入职，填再次入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date startDate;

    /**
        必填，无固定期限为空
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date endDate;

    /**
        必填“固定期限合同”、“无固定期限合同”、“实习协议”、“返聘协议”、“其他”
     */

    private String type;

    /**
        合同主体单位,必填，不允许空白
     */

    private String mainUnit;

    /**
        合同人员类别,了解清楚含义
     */

    private String userType;

    /**
        合同附件
     */

    private String file;

    /**
        合同签订日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date contractCreateTime;

    /**
        当前版本
     */

    private Integer version;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

}
