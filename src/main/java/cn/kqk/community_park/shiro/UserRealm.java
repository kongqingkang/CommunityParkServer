package cn.kqk.community_park.shiro;

import cn.kqk.community_park.bean.User;
import cn.kqk.community_park.bean.UserLogin;
import cn.kqk.community_park.bean.dto.LoginCheckDataDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Value("${server-config.salt}")
    private String salt;

    @Autowired
    private LoginService loginService;

    @Override
    //权限获取（getAuthorizationInfo 方法） 获取指定身份的权限，并返回相关信息
    //UsernamePasswordToken（账户密码验证令牌）
    /**
     * 授权方法
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> role = new HashSet<>();
        Set<String> permission = new HashSet<>();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(role);
        authorizationInfo.setStringPermissions(permission);

        return authorizationInfo;
    }

    /**
     * 认证方法
     * //身份验证（getAuthenticationInfo 方法）验证账户和密码，并返回相关信息
     * @param token
     * @return authenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MyAuthenticationToken myToken = (MyAuthenticationToken) token;
        //获取用户的输入的账号.
        String account = myToken.getAccount();
        // 根据账号获取数据
        LoginCheckDataDTO loginCheckData = loginService.getLoginCheckData(account);
        UserDTO userDTO = loginService.getUser(account);
        User user = loginCheckData.getUser();
        UserLogin userLogin = loginCheckData.getUserLogin();
//        UserLogin ul = loginService.getUserType(account);
        // 账号不存在
        if (user == null){
            throw new UnknownAccountException("用户名不存在");
        }
        // 账号被锁定
        if (user.getStatus() == 1){
            throw new LockedAccountException("用户被锁定");
        }
        // 将信息传给shiro处理
        return new SimpleAuthenticationInfo(userDTO, userLogin.getAuth(), ByteSource.Util.bytes(salt), getName());
    }

}
