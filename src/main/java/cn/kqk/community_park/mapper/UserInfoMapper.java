package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    int batchInsert(@Param("list") List<UserInfo> list);

    int insertOrUpdate(UserInfo record);

    int insertOrUpdateSelective(UserInfo record);
    
    int updateUserInfo(@Param("mobile") String mobile, @Param("address") String address, @Param("tencent") String tencent, @Param("userId") String userId);

    UserInfo selectUserInfoIdByUserId(@Param("userId") String userId);

    int updateAvatarUrlByUserInfoId(@Param("avatarUrl") String avatarUrl, @Param("userInfoId") String userInfoId);

    UserInfo selectUserInfoByUserId(@Param("userId") String userId);

}
