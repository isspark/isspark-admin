package com.isspark.admin.domain.vo.request;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Data
public class AddResourceReqVo{

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不能为空!")
    private String name;

    /**
     * 值
     */
    @NotBlank(message = "资源编码不能为空!")
    private String value;

    /**
     * 路径
     */
    private String url;

    /**
     * 类型：0-菜单，1-页面，2-按钮
     */
    @NotNull(message = "类型不能为空!")
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父借点名称
     */
    private Integer parentId;

    /**
     * 备注
     */
    private String remark;


}
