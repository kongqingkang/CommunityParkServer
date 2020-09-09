package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.dto.ParkReviewDTO;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.bean.vo.ParkReviewVO;
import cn.kqk.community_park.service.ParkReviewService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auhtor kqk
 * @date 2020/4/16 0016 - 18:24
 */
@RestController
public class ParkReviewController {

    @Autowired
    private ParkReviewService parkReviewService;

    /**
     * 后台车位审核列表
     * @param indexVO
     * @return
     */
    @PostMapping("/admin/parkIdentifyList")
    public CommonResponse parkIdentifyList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = parkReviewService.parkIdentifyList(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 后台-获取图片
     * @param parkReviewId
     * @return
     */
    @PostMapping("/admin/reviewParkPhoto/{parkReviewId}")
    public CommonResponse reviewParkPhoto(@PathVariable String parkReviewId) {
        ParkReviewDTO p =parkReviewService.reviewParkPhoto(parkReviewId);
        return ResultUtils.success("获取成功！",p);
    }

    /**
     * 后台-管理员审核通过车位
     * @param parkReviewId
     * @return
     */
    @PostMapping("/admin/adminReviewPark/{parkReviewId}")
    public CommonResponse adminReviewPark(@PathVariable String parkReviewId) {
        parkReviewService.adminReviewPark(parkReviewId);
        return ResultUtils.success("审核成功！");
    }

    /**
     * 后台-管理员审核驳回车位
     * @param parkReviewId
     * @return
     */
    @PostMapping("/admin/adminUnReviewPark/{parkReviewId}")
    public CommonResponse adminUnReviewPark(@PathVariable String parkReviewId){
        parkReviewService.adminUnReviewPark(parkReviewId);
        return ResultUtils.success("驳回成功！");
    }

    /**
     * 前台-用户提交车位审核
     * @param reviewVO
     * @param files
     * @return
     */
    @PostMapping(value = "/parkReview",headers = "content-type=multipart/*")
    public CommonResponse ParkReview(ParkReviewVO reviewVO, @RequestParam("parkReviwFiles") MultipartFile[] files){
        parkReviewService.parkReview(reviewVO,files);
        return ResultUtils.success("提交成功~待审核！");
    }

    @GetMapping("/getParkReview")
    public ModelAndView getParkReview(){
        ModelAndView mv = new ModelAndView("parkidentification");
        return mv;
    }
}
