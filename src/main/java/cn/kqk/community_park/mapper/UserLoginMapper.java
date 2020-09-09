package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface UserLoginMapper extends BaseMapper<UserLogin> {
    int updateBatch(List<UserLogin> list);

    int updateBatchSelective(List<UserLogin> list);

    int batchInsert(@Param("list") List<UserLogin> list);

    int insertOrUpdate(UserLogin record);

    int insertOrUpdateSelective(UserLogin record);

    UserLogin selectByAccount(String account);

    UserLogin selectTypeByAccount(@Param("account") String account);

    UserLogin selTypeByAccount(@Param("account") String account);

    int selectByAccountOrMobile(@Param("account") String account, @Param("mobile") String mobile);

    int selectAuthSameByUserLoginId(@Param("auth") String auth, @Param("userId") String userId);

    List<UserLogin> selectUserIdByAuth(@Param("auth") String auth, @Param("userId") String userId);

    UserLogin updateAuthByUserLoginId(@Param("auth") String auth, @Param("userLoginId") String userLoginId);

    UserLogin selectUserLoginIdByUserId(@Param("userId") String userId);

    int AuthUpdateBatch(@Param("list") List<UserLogin> list,@Param("auth") String auth);

}
