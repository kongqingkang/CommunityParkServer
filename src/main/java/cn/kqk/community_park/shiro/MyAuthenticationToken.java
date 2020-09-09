package cn.kqk.community_park.shiro;

import cn.kqk.common.bean.vo.request.Login;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;


/**
 * @author lhr
 * 2020/1/2 16:19
 * 自定义shiro的token
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyAuthenticationToken implements AuthenticationToken {
    private String account;
    private String password;
    private Boolean rememberMe;
    private Integer type;
    private Integer autoLogin;

    public MyAuthenticationToken(Login login) {
        this.account = login.getUserAccount();
        this.password = login.getPassword();
        this.rememberMe = login.getRememberMe();
        this.type = login.getType();
        this.autoLogin = login.getAutoLogin();
    }

    @Override
    public Object getPrincipal() {
        return getAccount();
    }

    @Override
    public Object getCredentials() {
        return getPassword();
    }
}
