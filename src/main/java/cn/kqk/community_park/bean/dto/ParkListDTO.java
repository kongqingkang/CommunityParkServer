package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/5/4 0004 - 13:00
 */
@Data
public class ParkListDTO {
    private String parkId;
    private String ownerName;
    private String mobile;
    private String communityName;
    private String location;
    private String parkName;
    private Integer rentoutFlag;
    private Integer identifyFlag;
}
