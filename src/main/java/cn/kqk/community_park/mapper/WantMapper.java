package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Want;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface WantMapper extends BaseMapper<Want> {
    int batchInsert(@Param("list") List<Want> list);

    int insertOrUpdate(Want record);

    int insertOrUpdateSelective(Want record);

    Want selectInfoByUserIdAndRentInfoId(@Param("userId") String userId, @Param("rentInfoId") String rentInfoId);

    List<Want> selectStatusByWantIdAndRentInfoId(@Param("rentInfoId") String rentInfoId);

    List<Want> selectWantIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    List<Want> selectAlluserIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    int updateStatusByWantId(@Param("wantId") String wantId);
}
