package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Park;
import cn.kqk.community_park.bean.dto.ParkListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface ParkMapper extends BaseMapper<Park> {
    int batchInsert(@Param("list") List<Park> list);

    int insertOrUpdate(Park record);

    int insertOrUpdateSelective(Park record);

    Park selectIdentifyFlagByParkIdAndName(@Param("communityId") String communityId, @Param("parkName") String parkName, @Param("location") String location);

    Park selectIdentifyFlagByParkAddressAndName(@Param("parkName") String parkName, @Param("parkAddress") String parkAddress);

    Park selectParkInfoById(@Param("parkId") String parkId);

    List<Park> selectRentoutFlagByParkId(@Param("parkId") String parkId);

    int updateRentOutFlagByParkId(@Param("parkId") String parkId);

    int selectFreePark();

    int updateIdentifyFlagByParkId(@Param("identifyFlag") Integer identifyFlag, @Param("parkId") String parkId);

    List<ParkListDTO> findPark();

    Park selectRentoutFlagByParkIdSingle(@Param("parkId") String parkId);
    
    int updateRentoutFlagByParkId(@Param("rentoutFlag") Integer rentoutFlag, @Param("parkId") String parkId);
}
