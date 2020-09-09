package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/16 0016 - 20:00
 */
@Data
public class ParkReviewVO extends BaseVO {
    @NotNull(message = "所有人姓名不能为空")
    private String ownerName;
    @NotNull(message = "手机号不能为空")
    private String mobile;
    @NotNull(message = "身份证号不能为空")
    private String idCard;
    @NotNull(message = "性别号不能为空")
    private String sex;
    @NotNull(message = "社区名不能为空")
    private String communityName;
    @NotNull(message = "社区地址不能为空")
    private String communityAddress;
    @NotNull(message = "车位地址不能为空")
    private String parkAddress;
    @NotNull(message = "车位号不能为空")
    private String parkName;
}
