package com.isspark.admin.domain.vo.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xuzheng
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class UpdateUserReqVo extends Model<UpdateUserReqVo> {

    @NotNull(message = "用户ID不能为空!")
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空!")
    private String name;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空!")
    private String realName;

    /**
     * 0-男，1-女
     */
    @Range(min = 0L,max = 1L,message = "性别输入错误！")
    private Integer sex;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空!")
    private String mobile;

    /**
     * 用户类型：1-管理员，2-商户，3-普通用户
     */
    private Integer type;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    private String password;

    @Range(min = 0L,max = 1L,message = "状态输入错误！")
    private Integer status = 0;

    private List<Integer> roleIds;

}
