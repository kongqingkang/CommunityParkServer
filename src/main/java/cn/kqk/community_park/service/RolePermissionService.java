package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.RolePermission;
import cn.kqk.community_park.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class RolePermissionService{

    @Resource
    private RolePermissionMapper rolePermissionMapper;


    public int batchInsert(List<RolePermission> list) {
        return rolePermissionMapper.batchInsert(list);
    }


    public int insertOrUpdate(RolePermission record) {
        return rolePermissionMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(RolePermission record) {
        return rolePermissionMapper.insertOrUpdateSelective(record);
    }

}
