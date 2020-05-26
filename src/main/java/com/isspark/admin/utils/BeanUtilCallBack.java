package com.isspark.admin.utils;

/**
 * <p>
 * TODO(一句话描述该类的功能)
 * </p>
 *
 * @author xuzheng
 * @since 2020/5/26 23:06
 */
public interface BeanUtilCallBack<S, T> {

    /**
     * 定义默认回调方法
     *
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
