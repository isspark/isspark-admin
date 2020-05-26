package com.isspark.admin.domain.vo.request;

import com.isspark.admin.domain.vo.PageBaseReqVo;
import lombok.Data;

/**
 * <p>
 * TODO(一句话描述该类的功能)
 * </p>
 *
 * @author xuzheng
 * @since 2020/5/26 22:36
 */
@Data
public class UserPageReqVo extends PageBaseReqVo {

    private String username;

    private String mobile;
}
