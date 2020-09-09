package cn.kqk.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author lhr
 * 2020/3/28 13:20
 * 消息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBean implements Serializable {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送者id
     */
    private String senderId;
    /**
     * 发送者名称
     */
    private String senderName;
    /**
     * 发送者推送软件账号
     */
    private String senderMessageAccount;
    /**
     * 接受者id
     */
    private String receiverId;
    /**
     * 接受者名称
     */
    private String receiverName;
    /**
     * 接收者消息推送软件账号
     */
    private String receiverMessageAccount;
    /**
     * 链接键值对，键是链接名称，值是链接
     */
    private Map<String, String> links;
    /**
     * 消息类别（自定义，用以在发送方法中判断以什么格式拼接消息）
     */
    private String type;
    /**
     * 是否为系统消息
     */
    private boolean isSystem;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 额外数据
     */
    private Object extraData;
}
