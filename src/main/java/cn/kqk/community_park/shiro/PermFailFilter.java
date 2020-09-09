package cn.kqk.community_park.shiro;

import cn.kqk.common.exception.BaseException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author lhr
 * 2019/12/23 19:45
 * 授权失败过滤器
 */
public class PermFailFilter extends PermissionsAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            //跳转至登录页
            saveRequest(request);
            throw new BaseException(101, "登录过期，请重新登录");
        } else {
            //给前端提示无接口访问权限的错误码
            saveRequest(request);
            throw new BaseException(102, "无权限请求对应api接口");
        }
    }
}
