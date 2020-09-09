package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/5/3 0003 - 11:32
 */
@Data
public class UserListDTO {
    private String userId;
    private String truthName;
    private String mobile;
    private String identityCard;
    private String loginTime;
    private Integer status;
    private Integer identifyFlag;

}
