package cn.kqk.common.bean.vo.request;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @author lhr
 * 2019/12/27 19:32
 * 登录
 */
@Data
public class Login extends BaseVO {
    @NotNull(message = "账号不能为空")
    private String userAccount;
    @NotNull(message = "密码不能为空")
    private String password;
    private Boolean rememberMe;
    private Integer type;
    private Integer autoLogin;
}
