package com.isspark.admin.common.config.shiro;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.isspark.admin.common.domain.Result;
import com.isspark.admin.common.enums.ResultEnum;
import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.service.SysResourceService;
import com.isspark.admin.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * TODO(一句话描述该类的功能)
 * </p>
 *
 * @author xuzheng
 * @since 2020/5/24 23:43
 */
@Slf4j
@Component
public class URLMatchFilter extends PathMatchingFilter {

    @Autowired
    SysResourceService resourceService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String requestURI = getPathWithinApplication(request);
        log.info("request url:{}",requestURI);
        String token = WebUtils.toHttp(request).getHeader("token");
        // 如果没有登录，就跳转到登录页面
        if (StringUtils.isBlank(token)) {
            setResponse(response);
            return false;
        }

        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        Set<String> urls = getDontNeedPermissionUrl();
        boolean isPass = isPass(requestURI,urls);
        if (isPass) {
            return true;
        } else {
            boolean hasPermission = false;
            String userName = JWTUtil.getUsername(token);
            if(StringUtils.isBlank(userName)){
                setResponse(response);
                return false;
            }
            List<SysResource> permissionUrls = resourceService.getResourceByUserName(userName);
            if(CollectionUtils.isEmpty(permissionUrls)){
                setResponse(response);
                return false;
            }
            hasPermission = permissionUrls.stream().anyMatch(tmp -> requestURI.contains(tmp.getUrl()));
            if (hasPermission){
                return true;
            } else {
                setResponse(response);
                return false;
            }
        }

    }

    private Boolean isPass(String url,Set<String> permsUrls){
        return permsUrls.stream().anyMatch(tmp -> url.contains(tmp));

    }

    protected void setResponse(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json");
        httpResponse.getWriter().write(JSONUtil.toJsonStr(Result.fail(ResultEnum.UNAUTH)));
    }

    private Set<String> getDontNeedPermissionUrl(){
        Set<String> urls = new HashSet<>();
        urls.add("/login");
        //exclude api doc page
        urls.add("/swagger-resources");
        urls.add("/v2/api-docs");
        urls.add("/v2/api-docs-ext");
        urls.add("/doc.html");
        urls.add("/webjars");
        //注册
        urls.add("/signUp");
        urls.add("/unAuth");
        urls.add("/user/info");
        return urls;
    }
}
