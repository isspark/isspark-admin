package com.isspark.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description：TODO
 * @Date ：2020/1/19 11:36
 * @Author ：HX0012875
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /*通用错误*/
    UNKNOWN_ERROR("-1", "未知错误"),
    IO_EX("30","网络IO异常"),
    PARSE_EX("31", "解析异常"),
    SUCCESS("200", "成功"),
    HTTP_RET_NULL("-100","网络请求返回未空"),
    INFO_RET_NOT_SUCCESS("-101","信息返回未成功"),

    /*异常错误*/
    ERROR("500", "服务器繁忙"),

    /*登录授权错误*/
    INCORRECT_PWD("400","账号密码不匹配！"),
    UNAUTH("401","你没有权限访问该资源！"),
    LOGIN_ERROR("999","账号或密码错误！");

    private String code;
    private String msg;
}
