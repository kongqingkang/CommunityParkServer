package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.User;
import cn.kqk.community_park.bean.dto.UserListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int batchInsert(@Param("list") List<User> list);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);

    User selectMobileFlagById(@Param("user_id") String user_id);

    User selectUserId(@Param("user_id") String user_id);

    int updateIdentifyFlagById(@Param("identifyFlag") Integer identifyFlag, @Param("userId") String userId);

    int selectUserAccount();

    List<UserListDTO> findUser();

    int updateStatusByUserId(@Param("status") Integer status, @Param("userId") String userId);
    int updateIdentifyFlagByIdAndNumber(@Param("identifyFlag") Integer identifyFlag, @Param("userId") String userId);
}
