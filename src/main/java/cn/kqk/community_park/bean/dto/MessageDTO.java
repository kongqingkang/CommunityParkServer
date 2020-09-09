package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/26 0026 - 14:13
 */
@Data
public class MessageDTO {
    private String content;
    private Integer messageType;
    private String createTime;
    private Integer readFlag;
    private String truthName;
    private String userId;
}
