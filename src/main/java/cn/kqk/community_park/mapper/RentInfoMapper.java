package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.RentInfo;
import cn.kqk.community_park.bean.dto.*;
import cn.kqk.community_park.bean.vo.RentInfoVO;
import cn.kqk.community_park.bean.vo.RentOutInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface RentInfoMapper extends BaseMapper<RentInfo> {
    int batchInsert(@Param("list") List<RentInfo> list);

    int insertOrUpdate(RentInfo record);

    int insertOrUpdateSelective(RentInfo record);
    
    int selectTotleRentInfo();

    int updateViewsByRentInfoId(@Param("rentUserId") String rentUserId);

    List<IndexRentInfoDTO> findIndexInfo();

    List<IndexRentInfoDTO> findIndexInfoById(@Param("userId") String userId);

    List<IndexRentInfoDTO> findIndexInfoByIdTypeIdOne(@Param("userId") String userId);

    List<ArchivesRentInfoDTO> findIndexParkInfoById(@Param("userId") String userId);

    List<RentInfo> getLatestRentInfoLimitSix();

    List<RentInfo> getLatestRentOutInfoLimitSix();

    List<RentInfo> getMostViewsInfo();

    RentInfo rentInfoDetail(@Param("rentInfoId") String rentInfoId);

    RentInfoDTO findRentInfoDetail(@Param("rentInfoId") String rentInfoId);

    RentOutInfoDTO findRentOutInfoDetail(@Param("rentInfoId") String rentInfoId);

    int updateRentInfoVOByRentInfoId(RentInfoVO rentvo, String rentInfoId);

    int updateByRentInfoId(RentInfoVO rentvo, @Param("publishTime") Date publishTime, @Param("rentInfoId") String rentInfoId);

    int updateByRentOutInfoId(RentOutInfoVO rentOutInfoVO, @Param("publishTime") Date publishTime, @Param("rentOutInfoId") String rentOutInfoId);

    RentInfo selectUserIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    int UpdateTypeIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    RentInfo selectuserIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    List<RentInfo> selectAlluserIdByRentInfoId(@Param("rentInfoId") String rentInfoId);

    List<IndexRentInfoDTO> selectAllRentInfoByDescription(@Param("keyword") String keyword);

    int selectTotalAddThisMonth();

    int selectRentInfoTotalByTypeId(@Param("typeId") int typeId);


}
