package com.isspark.admin.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @version V1.0
 * @class: AddRoleReqVo
 * @description:
 * @author: xuzheng
 * @create: 2020-05-26 16:13
 **/
@Data
public class AddRoleReqVo {

    /**
     * 名称
     */
    @NotBlank(message = "角色名不能为空！")
    private String name;

    /**
     * 值
     */
    @NotBlank(message = "角色编码不能为空！")
    private String value;

    /**
     * 描述备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父角色ID
     */
    private Long parentId;
}
