package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.RentPark;
import cn.kqk.community_park.mapper.RentParkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class RentParkService{

    @Resource
    private RentParkMapper rentParkMapper;


    public int batchInsert(List<RentPark> list) {
        return rentParkMapper.batchInsert(list);
    }


    public int insertOrUpdate(RentPark record) {
        return rentParkMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(RentPark record) {
        return rentParkMapper.insertOrUpdateSelective(record);
    }

}
