package com.isspark.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_resource")
public class SysResource extends Model<SysResource> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 路径
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型：0-菜单，1-页面，2-按钮
     */
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

    private Date creatTime;

    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
