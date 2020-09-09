package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/13 0013 - 15:36
 */
@Data
public class UserReviewVO extends BaseVO {
    @NotNull(message = "姓名不能为空")
    private String truthName;
    @NotNull(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "身份证号不能为空")
    private String identityCard;
    @NotNull(message = "性别不能为空")
    private String sex;
    //@NotNull(message = "身份证照正面不能为空")
    private String card_url1;
   // @NotNull(message = "身份证照反面不能为空")
    private String card_url2;
}
