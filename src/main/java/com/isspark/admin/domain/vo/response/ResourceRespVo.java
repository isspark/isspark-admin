package com.isspark.admin.domain.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @version V1.0
 * @class: ResourceRespVo
 * @description:
 * @author: xuzheng
 * @create: 2020-05-26 17:04
 **/
@Data
public class ResourceRespVo {

    private Long id;
    private String name;

    private String value;

    private String url;

    private Integer type;

    private String icon;

    private Integer sort;

    private String remark;

    private Integer parentId;

    private List<ResourceRespVo> children;
}
