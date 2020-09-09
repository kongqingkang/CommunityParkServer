package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.UserRole;
import cn.kqk.community_park.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class UserRoleService{

    @Resource
    private UserRoleMapper userRoleMapper;


    public int batchInsert(List<UserRole> list) {
        return userRoleMapper.batchInsert(list);
    }


    public int insertOrUpdate(UserRole record) {
        return userRoleMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserRole record) {
        return userRoleMapper.insertOrUpdateSelective(record);
    }

}
