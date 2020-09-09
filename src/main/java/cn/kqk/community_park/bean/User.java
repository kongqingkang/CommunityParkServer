package cn.kqk.community_park.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "`user`")
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 管理状态 是否被封禁
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;

    /**
     * 登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 实名认证标记
     */
    @Column(name = "identify_flag")
    private Integer identifyFlag;

    /**
     * 手机号验证标记
     */
    @Column(name = "mobile_flag")
    private Integer mobileFlag;

    private static final long serialVersionUID = 1L;
}
