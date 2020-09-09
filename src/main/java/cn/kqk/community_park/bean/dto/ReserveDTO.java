package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/26 0026 - 10:02
 */
@Data
public class ReserveDTO {
    private String rentInfoId;
    private Integer status;
    //消息
    private String messageId;
    private String senderUserId;
    private String reciptUserId;
    private String content;
    private String messagType;
    private String createTime;
    private Integer readFlag;
}
