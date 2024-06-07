package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;


/**
 * 人员证书信息对象 bx_mdm_user_certificate_v
 *
 * @author author
 * @date 2024-05-25
 */
@Data
public class BxMdmUserCertificateVModel
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
        特种作业资格证名称
     */

    private String soqCertificate;

    /**
        特种作业资格证件有效期
     */

    private String soqValidityPeriod;

    /**
        特种作业资格证件附件
     */

    private String soqFile;

    /**
        特种作业资格证起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date soqStartDate;

    /**
        特种作业资格证复审到期日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date soqEndDate;

    /**
        特种作业资格证评定日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date soqAssessDate;

    /**
        专业技术资格证名称
     */

    private String ptqCertificate;

    /**
        专业技术资格证附件
     */

    private String ptqFile;

    /**
        专业技术职务等级
     */

    private String ptqLevel;

    /**
        专业技术职务评定日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date ptqAssessDate;

    /**
        专业技术职务获取日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date ptqGainDate;

    /**
        技术职称
     */

    private String technicalTitle;

    /**
        当前版本
     */

    private Integer version;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

}
