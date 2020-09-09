package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/17 0017 - 21:52
 */
@Data
public class EditPassword extends BaseVO {
    @NotNull(message = "原密码不能为空")
    private String oldPassword;
    @NotNull(message = "新密码不能为空")
    private String password;
}
