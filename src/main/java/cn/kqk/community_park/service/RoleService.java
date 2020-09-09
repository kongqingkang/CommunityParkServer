package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.Role;
import cn.kqk.community_park.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class RoleService{

    @Resource
    private RoleMapper roleMapper;


    public int updateBatch(List<Role> list) {
        return roleMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<Role> list) {
        return roleMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<Role> list) {
        return roleMapper.batchInsert(list);
    }


    public int insertOrUpdate(Role record) {
        return roleMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Role record) {
        return roleMapper.insertOrUpdateSelective(record);
    }

}
