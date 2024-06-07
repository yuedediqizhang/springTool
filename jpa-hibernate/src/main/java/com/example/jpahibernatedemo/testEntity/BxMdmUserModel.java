package com.example.jpahibernatedemo.testEntity;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 用户信息对象 bx_mdm_user
 *
 * @author author
 * @date 2024-05-21
 */
@Data

public class BxMdmUserModel
{

    /**
        用户编号
     */

    private Long userId;

    /**
        工号
     */

    private String userNo;

    /**
        部门编号
     */

    private Long deptId;

    /**
        部门名称，必填，该字段为薪资发放部门，做成下拉菜单形式--“综合管理分部、石膏板车间、龙骨车间、粉料车间、矿棉车间、装饰板车间、外墙板车间、安全环保办公室、精益生产分部、市场中心、销售部”，需提供组织架构图（需要跟邱总确认）
     */
    private String deptName;

    /**
        薪资归属部门
     */

    private String deptSalary;

    /**
        所在部门
     */

    private String currentDept;

    /**
        姓名
     */

    private String userName;

    /**
        曾用名
     */

    private String oldUserName;

    /**
        身份证号
     */

    private String cardNo;

    /**
        用户邮箱
     */

    private String email;

    /**
        民族
     */

    private String nationality;

    /**
        所属板块公司，选填板块公司名称（通常为龙牌公司），空白的请补全
     */

    private String sectorCompany;

    /**
        单位名称,该字段为发薪字段，为员工劳动合同所在公司的全称
     */

    private String unitName;

    /**
        手机号码
     */

    private String phonenumber;

    /**
        家庭电话
     */

    private String homePhone;

    /**
        $column.columnComment
     */

    private String homeAddress;

    /**
        出生日期
     */

    private String birthday;

    /**
        籍贯
     */

    private String nativePlace;

    /**
        户口性质
     */

    private String residenceType;

    /**
        户口所在地
     */

    private String residenceAddress;

    /**
        到职时间，为计算在新岗位履职时间字段，为新岗位的到职日期，在员工发生生“调动、升职”等异动后需要进行维护
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date postTime;

    /**
        人员类别。必填，对内聘工和合同工进行定义。原则上都是合同工。在“其他”里增加“劳务外包人员”，该字段为主数据，人员入职时需维护，维护好后将会自动带入“人员类别（任职信息”
     */
   private String type;

    /**
        政治面貌
     */

    private String politicalLandscape;

    /**
        必填，为计算司龄字段，填第一次进入公司的那一天；如果中间有离职，填再次入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date currentWorkTime;

    /**
        参加工作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date firstWorkTime;

    /**
        最高学历
     */

    private String highestQualification;

    /**
        公司地址
     */

    private String companyAddress;

    /**
        入党（团）时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date partyDate;

    /**
        一级部门
     */

    private String firstLevelDept;

    /**
        二级部门
     */

    private String secondLevelDept;

    /**
        紧急联系人
     */

    private String emergencyContactName;

    /**
        紧急联系人电话
     */

    private String emergencyContactPhone;

    /**
        必填，婚姻状况“未婚、已婚、丧偶、离异”
     */

    private String maritalStatus;

    /**
        身份必填，用于确定退休年龄字段，需要确认字段有用与否
     */

    private String identity;

    /**
        年龄
     */

    private Integer age;

    /**
        用户性别（0男 1女 2未知）
     */

    private String sex;

    /**
        头像地址
     */

    private String avatar;

    /**
        密码
     */

    private String password;

    /**
        帐号状态（0正常 1停用）
     */

    private String status;

    /**
        最后登录IP
     */

    private String loginIp;

    /**
        最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")

    private Date loginDate;

    /**
        删除标志（0代表存在 2代表删除）
     */

    private String delFlag;

    /**
        当前版本
     */


    private Integer version;

    /**
     备用字段1
     */

    private String desc1;
    /**
     备用字段2
     */

    private String desc2;
    /**
     备用字段3
     */

    private String desc3;
    /**
     备用字段4
     */

    private String desc4;
    /**
     备用字段5
     */

    private String desc5;


    private String uuid;

    /**
     * 岗位信息
     */
    private List<BxMdmUserPostModel> postModels;

    /**
     * 教育信息
     */
    private List<BxMdmUserEducationModel> educationModels;

    /**
     * 家庭信息
     */
    private List<BxMdmUserFamilyMemeberModel> familyMemeberModels;

    /**
     * 工作记录
     */
    private List<BxMdmUserWorkRecordModel> workRecordModels;

    /**
     * 任职记录
     */
    private List<BxMdmUserRankModel> rankModels;

    /**
     * 技能证书
     */
    private List<BxMdmUserCertificateModel> certificateModels;

    /**
     * 合同
     */
    private List<BxMdmUserContractModel> contractModels;


}
