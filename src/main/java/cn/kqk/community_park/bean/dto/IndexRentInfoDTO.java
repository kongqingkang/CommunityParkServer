package cn.kqk.community_park.bean.dto;

import lombok.Data;

import java.util.List;

/**
 * @auhtor kqk
 * @date 2020/4/19 0019 - 19:00
 */
@Data
public class IndexRentInfoDTO {
    private String title;
    private String rentInfoId;
    //private String userId;
    private String description;
    private String truthName;
    private String avatarUrl;
    private Integer views;
    private String typeId;
    private String publishTime;
    private List<IndexRentInfoDTO> indexRentInfoDTOList;
    // private String mobile;
    //private String communityName;
    //private String communityAddress;
    //private Date startTime;
    //private Date endTime;
    //private Integer longFlag;
    //信息总数
    //private Integer totleinfo;
    //private Long price;
}
