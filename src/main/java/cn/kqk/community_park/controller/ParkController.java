package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.service.ParkService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auhtor kqk
 * @date 2020/5/4 0004 - 11:42
 */
@RestController
public class ParkController {

    @Autowired
    private ParkService parkService;

    @PostMapping("/admin/parkList")
    public CommonResponse parkList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = parkService.parkList(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }
    @PostMapping("/admin/adminLockPark/{parkId}")
    public CommonResponse adminLockPark(@PathVariable String parkId) {
        parkService.adminLockPark(parkId);
        return ResultUtils.success("封禁成功！");
    }
    @PostMapping("/admin/adminUnLockPark/{parkId}")
    public CommonResponse adminUnLockPark(@PathVariable String parkId) {
        parkService.adminUnLockPark(parkId);
        return ResultUtils.success("封禁成功！");
    }

    /**
     * 后台-车位管理页面
     * @return
     */
    @GetMapping("/admin/parkList")
    public ModelAndView parkList(){
        ModelAndView mv = new ModelAndView("admin/parkList");
        return mv;
    }
}

