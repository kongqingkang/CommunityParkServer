package cn.kqk.community_park.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message implements Serializable {
    /**
     * 消息编号
     */
    @Id
    @Column(name = "message_id")
    private String messageId;

    /**
     * 发送人编号
     */
    @Column(name = "send_user_id")
    private String sendUserId;

    /**
     * 接收人编号
     */
    @Column(name = "recipt_user_id")
    private String reciptUserId;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 消息类别
     */
    @Column(name = "message_type")
    private Integer messageType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否已读
     */
    @Column(name = "read_flag")
    private Integer readFlag;

    private static final long serialVersionUID = 1L;
}
