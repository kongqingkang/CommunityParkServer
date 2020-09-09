package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/30 0030 - 7:25
 */
@Data
public class AdminIndexDTO {
    private Integer userTotal;//用户总数
    private Integer todayAdd;//今月增长
    private Integer freePark;//空余车位数
    private Integer rentInfoTotal;//求租信息
    private Integer rentOutInfoTotal;//出租信息
    private Integer noIdentify;//实名认证待审核数
    private Integer noParkIdentify;//车位认证待审核数
    private String truthName;//真实姓名
}
