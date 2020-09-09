package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.dto.AdminIndexDTO;
import cn.kqk.community_park.service.RentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理的controller
 * @auhtor kqk
 * @date 2020/4/30 0030 - 7:32
 */
@RestController
public class IndexInfoController {
    @Autowired
    private RentInfoService rentInfoService;

    /**
     *后台首页获取到的数据
     * @return
     */
    @GetMapping("/admin/getIndexInfo")
    public CommonResponse adminIndexInfo(){
        AdminIndexDTO adminIndexDTO = rentInfoService.adminIndexInfo();
        return ResultUtils.success("获取成功",adminIndexDTO);
    }

    @GetMapping("/admin/userIdentify")
    public ModelAndView userIdentify(){
        ModelAndView mv = new ModelAndView("/admin/userIdenList");
        return mv;
    }

    @GetMapping("/admin/parkIdentify")
    public ModelAndView parkIdentify(){
        ModelAndView mv = new ModelAndView("/admin/parkIdenList");
        return mv;
    }
}
