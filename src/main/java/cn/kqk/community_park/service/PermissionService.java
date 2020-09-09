package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.Permission;
import cn.kqk.community_park.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class PermissionService{

    @Resource
    private PermissionMapper permissionMapper;


    public int updateBatch(List<Permission> list) {
        return permissionMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<Permission> list) {
        return permissionMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<Permission> list) {
        return permissionMapper.batchInsert(list);
    }


    public int insertOrUpdate(Permission record) {
        return permissionMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Permission record) {
        return permissionMapper.insertOrUpdateSelective(record);
    }

}
