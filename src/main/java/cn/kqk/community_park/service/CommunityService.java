package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.Community;
import cn.kqk.community_park.mapper.CommunityMapper;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class CommunityService{
    private CommunityMapper communityMapper;
    public CommunityService(CommunityMapper communityMapper) {
        this.communityMapper = communityMapper;
    }


    public int updateBatch(List<Community> list) {
        return communityMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<Community> list) {
        return communityMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<Community> list) {
        return communityMapper.batchInsert(list);
    }


    public int insertOrUpdate(Community record) {
        return communityMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Community record) {
        return communityMapper.insertOrUpdateSelective(record);
    }

}
