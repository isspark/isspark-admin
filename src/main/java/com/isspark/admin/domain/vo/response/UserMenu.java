package com.isspark.admin.domain.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @version V1.0
 * @class: UserMenu
 * @description:
 * @author: xuzheng
 * @create: 2020-05-25 10:37
 **/
@Data
public class UserMenu {

    private Long id;
    private String name;

    private String value;

    private String url;

    private String type;

    private Integer sort;

    private String remark;

    private Integer parentId;

    private List<UserMenu> children;
}
