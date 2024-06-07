package com.example.jpahibernatedemo.testEntity;


import lombok.Data;


/**
 * 推送接口对象 bx_mdm_user_interface_config
 *
 * @author author
 * @date 2024-05-24
 */
@Data

public class BxMdmUserInterfaceConfigModel
{

    /**
        
     */

    private Long id;

    /**
        调用任务名称
     */

    private String taskJobMethod;

    /**
        系统id
     */

    private Long openSystemId;

    /**
        推动类型 1.定时. 2.实时
     */

    private Integer pushType;

    /**
        推送url
     */

    private String pushInterfaceUrl;

    /**
        定时任务id
     */

    private Long taskJobId;

    /**
        定时任务表达式
     */

    private String taskJobCron;

    /**
        当前版本
     */

    private Integer status;



    private Integer pageSize;

    /**
        表信息
     */

    private String tableInfo;

    /**
     业务类型 1：用户  2：部门 3：客户
     */
    private String serviceType;

}
