package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/18 0018 - 15:05
 */
@Data
public class AboutVO extends BaseVO {
    @NotNull(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "社交账号不能为空")
    private String tencent;
    private String avatarUrl;

}
