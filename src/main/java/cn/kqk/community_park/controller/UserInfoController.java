package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.dto.AboutDTO;
import cn.kqk.community_park.bean.dto.SelfDTO;
import cn.kqk.community_park.bean.vo.AboutVO;
import cn.kqk.community_park.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor kqk
 * @date 2020/4/17 0017 - 21:56
 */
@RestController
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping(value = "/editPersonalInfo",headers = "content-type=multipart/*")
    public CommonResponse editPersonalInfo(AboutVO aboutVO,@RequestParam(value="userAvatarFiles",required=false) MultipartFile files){
        userInfoService.editPersonalInfo(aboutVO,files);
        AboutDTO aboutDTO = userInfoService.getAboutInfo();
        Map<String, String> map = new HashMap<>();
        map.put("avatarUrl",aboutDTO.getAvatarUrl() );
        return ResultUtils.success("修改成功！",map);
    }

    @PostMapping("getAboutInfo")
    public CommonResponse  getAboutInfo(){
        AboutDTO aboutDTO = userInfoService.getAboutInfo();
        return ResultUtils.success("获取成功",aboutDTO);
    }

    @GetMapping("getSelfInfo")
    public CommonResponse  getSelfInfo(){
        SelfDTO selfDTO = userInfoService.getSelfInfo();
        return ResultUtils.success("获取成功",selfDTO);
    }

    @GetMapping("/admin/getSelfInfo")
    public CommonResponse  getAdminSelfInfo(){
        SelfDTO selfDTO = userInfoService.getSelfInfo();
        return ResultUtils.success("获取成功",selfDTO);
    }

    /**
     * 跳转到about-other详情页
     * @param userId
     * @return
     */
    @GetMapping("/userOtherInfoDetail/{userId}")
    public CommonResponse userInfoDetail(@PathVariable String userId){
        AboutDTO aboutDTO = userInfoService.getgetAboutInfoByUserId(userId);
        System.out.println(aboutDTO);
        return ResultUtils.success("",aboutDTO);
    }
}
