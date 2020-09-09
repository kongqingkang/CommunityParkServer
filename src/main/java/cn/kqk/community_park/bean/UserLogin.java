package cn.kqk.community_park.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login")
public class UserLogin implements Serializable {
    /**
     * 用户登录id
     */
    @Id
    @Column(name = "user_login_id")
    private String userLoginId;

    /**
     * 账号，可以有多种登录方式
     */
    @Column(name = "account")
    private String account;

    /**
     * 密码
     */
    @Column(name = "auth")
    private String auth;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 登录的类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 登录的方式是否可用
     */
    @Column(name = "login_status_flag")
    private Integer loginStatusFlag;

    private static final long serialVersionUID = 1L;
}
