package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.dto.UserIdentifyDTO;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.bean.vo.UserReviewVO;
import cn.kqk.community_park.service.UserReviewService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auhtor kqk
 * @date 2020/4/13 0013 - 15:42
 */
@RestController
public class UserReviewController {
    @Autowired
    private UserReviewService userReviewService;


    /**
     * 后台-分页获取用户审核列表
     * @param indexVO
     * @return
     */
    @PostMapping("/admin/userIdentifyList")
    public CommonResponse userIdentifyList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = userReviewService.userIdentifyList(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 用户提交实名认证
     * @param reviewVO
     * @param files
     * @return
     */
    @PostMapping(value = "/userReview",headers = "content-type=multipart/*")
    public CommonResponse userReview(UserReviewVO reviewVO, @RequestParam("userReviwFiles") MultipartFile[] files){
        userReviewService.userReview(reviewVO,files);
        return ResultUtils.success("提交成功~待审核！");
    }

    /**
     * 后台-获取图片
     * @param userReviewId
     * @return
     */
    @PostMapping("/admin/reviewPhoto/{userReviewId}")
    public CommonResponse reviewPhoto(@PathVariable String userReviewId){
        UserIdentifyDTO u = userReviewService.reviewPhoto(userReviewId);
        return ResultUtils.success("获取成功！",u);
    }

    /**
     * 管理员审核
     * @param userReviewId
     * @return
     */
    @PostMapping("/admin/adminReviewUser/{userReviewId}")
    public CommonResponse adminReviewUser(@PathVariable String userReviewId){
        userReviewService.adminReviewUser(userReviewId);
        return ResultUtils.success("审核成功！");
    }

    /**
     * 驳回申请
     * @param userReviewId
     * @return
     */
    @PostMapping("/admin/adminUnReviewUser/{userReviewId}")
    public CommonResponse adminUnReviewUser(@PathVariable String userReviewId){
        userReviewService.adminUnReviewUser(userReviewId);
        return ResultUtils.success("驳回成功！");
    }


    /**
     * 用户提交实名认证的页面
     * @return
     */
    @GetMapping("/getUserReview")
    public ModelAndView getUserReview(){
        ModelAndView mv = new ModelAndView("identification");
        return mv;
    }

}
