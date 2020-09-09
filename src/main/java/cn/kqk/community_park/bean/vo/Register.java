package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/12 0012 - 19:48
 */
@Data
public class Register extends BaseVO {
    @NotNull(message = "账号不能为空")
    private String account;
    @NotNull(message = "密码不能为空")
    private String auth;
    @NotNull(message = "手机号不能为空")
    private String mobile;
}
