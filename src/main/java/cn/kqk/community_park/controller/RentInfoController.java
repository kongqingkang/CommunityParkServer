package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.RentInfo;
import cn.kqk.community_park.bean.dto.IndexRentInfoDTO;
import cn.kqk.community_park.bean.dto.RentInfoDTO;
import cn.kqk.community_park.bean.dto.RentOutInfoDTO;
import cn.kqk.community_park.bean.vo.ArchivesVO;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.bean.vo.RentInfoVO;
import cn.kqk.community_park.bean.vo.RentOutInfoVO;
import cn.kqk.community_park.service.RentInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @auhtor kqk
 * @date 2020/4/17 0017 - 10:54
 */
@RestController
public class RentInfoController {

    @Autowired
    private RentInfoService rentInfoService;

    /**
     * 跳转到求租详情页
     * @param rentInfoId
     * @return
     */
    @GetMapping("/rentInfoDetail/{rentInfoId}")
    public CommonResponse rentInfoDetail(@PathVariable String rentInfoId){
        RentInfoDTO rentInfoDTO = rentInfoService.rentInfoDetail(rentInfoId);
        System.out.println(rentInfoDTO);
        return ResultUtils.success("",rentInfoDTO);
    }

    /**
     * 跳转到出租详情页
     * @param rentInfoId
     * @return
     */
    @GetMapping("/rentOutInfoDetail/{rentInfoId}")
    public CommonResponse rentOutInfoDetail(@PathVariable String rentInfoId){
        RentOutInfoDTO rentOutInfoDTO = rentInfoService.rentOutInfoDetail(rentInfoId);
        System.out.println(rentOutInfoDTO);
        return ResultUtils.success("",rentOutInfoDTO);
    }

    /**
     * 求租信息删除
     * @param rentInfoId
     * @return
     */
    @PostMapping("/deleteRentInfo/{rentInfoId}")
    public CommonResponse deleteRentInfo(@PathVariable String rentInfoId){
        rentInfoService.deleteRentInfo(rentInfoId);
        return ResultUtils.success("删除成功！");
    }

    /**
     *求租修改方法
     * @param
     * @return
     */
    @PostMapping("/editRentInfo/{rentInfoId}")
    public CommonResponse editRentInfo(@RequestBody RentInfoVO rentInfoVO,@PathVariable String rentInfoId){
        rentInfoService.editRentInfo(rentInfoVO,rentInfoId);
        return ResultUtils.success("修改成功！");
    }
    /**
     *出租修改方法
     * @param
     * @return
     */
    @PostMapping("/editRentOutInfo/{rentInfoId}")
    public CommonResponse editRentOutInfo(@RequestBody RentOutInfoVO rentOutInfoVO,@PathVariable String rentInfoId){
        rentInfoService.editRentOutInfo(rentOutInfoVO,rentInfoId);
        return ResultUtils.success("修改成功！");
    }

    /**
     *搜索
     * @param indexVO
     * @return
     */
    @PostMapping("/searchInfo")
    public CommonResponse searchInfo(@RequestBody IndexVO indexVO){
        PageInfo<IndexRentInfoDTO> pageInfo;
        pageInfo = rentInfoService.searchInfo(indexVO.getKeyword(),indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }


    /**
     * 求租信息发布
     * @param rentInfoVO
     * @return
     */
    @PostMapping("/rentInput")
    public CommonResponse rentInput(@RequestBody RentInfoVO rentInfoVO){
        rentInfoService.rentInfoInput(rentInfoVO);
        return ResultUtils.success("求租发布成功！");
    }

    /**
     * 出租信息发布
     * @param RentOutInfoVO
     * @return
     */
    @PostMapping("/rentOutInput")
    public CommonResponse rentOutInput(@RequestBody RentOutInfoVO RentOutInfoVO){
        rentInfoService.rentOutInfoInput(RentOutInfoVO);
        return ResultUtils.success("出租发布成功！");
    }

    /**
     * 分页获取首页的信息
     * @param indexVO
     * @return
     */
    @PostMapping("/indexRentInfoList")
    public CommonResponse indexRentInfoList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = rentInfoService.indexRentInfo(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 出租信息和车位信息 3 车位审核的信息
     * @param archivesVO
     * @return
     */
    @PostMapping("/myParkReviewAndParkInfo")
    public CommonResponse myParkReviewAndParkInfo(@RequestBody ArchivesVO archivesVO){
        System.out.println(archivesVO);
        PageInfo pageInfo = rentInfoService.archivesParkInfoList(archivesVO.getPageNum(),archivesVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 出租信息和车位信息1
     * @param archivesVO
     * @return
     */
    @PostMapping("/archivesRentInfoList")
    public CommonResponse archivesRentInfoList(@RequestBody ArchivesVO archivesVO){
        System.out.println(archivesVO);
        PageInfo pageInfo = rentInfoService.archivesRentInfoList(archivesVO.getPageNum(),archivesVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 出租信息和车位信息2
     * @param archivesVO
     * @return
     */
    @PostMapping("/archivesRentOutInfoList")
    public CommonResponse archivesRentOutInfoList(@RequestBody ArchivesVO archivesVO){
        System.out.println(archivesVO);
        PageInfo pageInfo = rentInfoService.archivesRentOutInfoList(archivesVO.getPageNum(),archivesVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }


    /**
     * 首页获取最新发布的求租信息
     * @return
     */
    @PostMapping("/indexRentLatestInfo")
    public CommonResponse indexRentLatestInfo(){
        List<RentInfo> list = rentInfoService.getLatestRentInfo();
        return ResultUtils.success("成功",list);
    }

    /**首页获取最新发布的出租信息
     *
     * @return
     */
    @PostMapping("/indexRentOutLatestInfo")
    public CommonResponse indexRentOutLatestInfo(){
        List<RentInfo> list = rentInfoService.getLatestRentOutInfo();
        return ResultUtils.success("成功",list);
    }

    /**
     * 获首页取最多浏览的信息
     * @return
     */
    @PostMapping("/getMostViewsInfo")
    public CommonResponse getMostViewsInfo(){
        List<RentInfo> list = rentInfoService.getMostViewsInfo();
        return ResultUtils.success("成功",list);
    }

    /**
     * 页面跳转到求租发布页
     * @return mv
     */
    @GetMapping("/getRentInput")
    public ModelAndView getRentInput(){
        ModelAndView mv = new ModelAndView("rent-input");
        return mv;
    }

    /**
     * 从详情页跳转到出租input页进行修改
     * @param rentInfoId
     * @return
     */
    @GetMapping("editRentOutInput/{rentInfoId}")
    public ModelAndView editRentOutInput(@PathVariable String rentInfoId){
        ModelAndView mv = new ModelAndView("editrentout");
        return mv;
    }

    /**
     * 从详情页跳转到input页进行修改
     * @param rentInfoId
     * @return
     */
    @GetMapping("editRentInput/{rentInfoId}")
    public ModelAndView editRentInput(@PathVariable String rentInfoId){
        ModelAndView mv = new ModelAndView("editRent");
        return mv;
    }

    /**
     * 页面到出租发布页
     * @return
     */
    @GetMapping("/getRentOutInput")
    public ModelAndView getRentOutInput(){
        ModelAndView mv = new ModelAndView("rentout-input");
        return mv;
    }

    /**
     * 跳转到我的出租
     * @return
     */
    @GetMapping("/archivesRenout")
    public ModelAndView archivesRenout(){
        ModelAndView mv = new ModelAndView("archives-rentout");
        return mv;
    }

    /**
     * 跳转到我的车位
     * @return
     */

    @GetMapping("/archivesParkReview")
    public ModelAndView archivesParkReview(){
        ModelAndView mv = new ModelAndView("archives-parkreview");
        return mv;
    }

    /**
     * 跳转到我的求租
     * @return
     */
    @GetMapping("/archives")
    public ModelAndView archives(){
        ModelAndView mv = new ModelAndView("archives");
        return mv;
    }

    /**
     * 根据id页面跳转到求租详情页
     * @param rentInfoId
     * @return
     */
    @GetMapping("rentinfo/{rentInfoId}")
    public ModelAndView rentinfo(@PathVariable String rentInfoId){
        ModelAndView mv = new ModelAndView("rentinfo");
        return mv;
    }

    /**
     * 根据id页面跳转到出租详情页
     * @param rentInfoId
     * @return
     */
    @GetMapping("rentoutinfo/{rentInfoId}")
    public ModelAndView rentoutinfo(@PathVariable String rentInfoId){
        ModelAndView mv = new ModelAndView("rentoutinfo");
        return mv;
    }

}
