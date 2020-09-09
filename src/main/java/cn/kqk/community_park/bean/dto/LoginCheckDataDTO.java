package cn.kqk.community_park.bean.dto;

import cn.kqk.community_park.bean.User;
import cn.kqk.community_park.bean.UserLogin;
import lombok.Data;

/**
 * @author lhr
 * @version 1.0
 * @className LoginCheckDataDTO
 * @description 登录检查数据
 * @date 2020/4/10 20:05
 */
@Data
public class LoginCheckDataDTO {
    private User user;
    private UserLogin userLogin;
}
