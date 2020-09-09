package cn.kqk.community_park.bean.vo;

import cn.kqk.common.annotate.NotNull;
import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/17 0017 - 9:22
 */
@Data
public class RentInfoVO extends BaseVO {
    @NotNull(message = "标题不能为空！")
    private String title;
    @NotNull(message = "姓名不能为空！")
    private String truthName;
    @NotNull(message = "手机号不能为空！")
    private String mobile;
//    @NotNull(message = "QQ/微信不能为空！")
//    private String tencent;
    @NotNull(message = "长短租不能为空！")
    private Integer longFlag;
    @NotNull(message = "开始时间不能为空！")
    private String startTime;
    @NotNull(message = "结束时间不能为空！")
    private String endTime;
    @NotNull(message = "价格不能为空！")
    private Long price;
    @NotNull(message = "备注不能为空！")
    private String description;
}
