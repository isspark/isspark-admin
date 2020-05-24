package com.isspark.admin.common.config.shiro;

import com.isspark.admin.domain.entity.SysResource;
import com.isspark.admin.service.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
public class URLMatchFilter extends PathMatchingFilter {

    @Autowired
    SysResourceService resourceService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String requestURI = getPathWithinApplication(request);

        log.info("request url:{}",requestURI);
        Subject subject = SecurityUtils.getSubject();
        // 如果没有登录，就跳转到登录页面
        if (!subject.isAuthenticated()) {
            throw new AuthorizationException("未授权的访问，请先登录！");
//            WebUtils.issueRedirect(request, response, "/login");
//            return false;
        }

        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        Set<String> urls = getDontNeedPermissionUrl();
        boolean needInterceptor = needFilter(requestURI,urls);
        if (!needInterceptor) {
            return true;
        } else {
            boolean hasPermission = false;
            String userName = subject.getPrincipal().toString();
            List<SysResource> permissionUrls = resourceService.getResourceByUserName(userName);
            hasPermission = permissionUrls.stream().anyMatch(tmp -> requestURI.contains(tmp.getUrl()));
            if (hasPermission)
                return true;
            else {
                UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
                subject.getSession().setAttribute("ex", ex);
                WebUtils.issueRedirect(request, response, "/unauthorized");
                return false;
            }
        }

    }

    private Boolean needFilter(String url,Set<String> permsUrls){
        return permsUrls.stream().anyMatch(tmp -> url.contains(tmp));
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
        return urls;
    }
}
