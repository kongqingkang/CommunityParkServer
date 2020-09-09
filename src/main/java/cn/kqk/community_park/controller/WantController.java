package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.service.WantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auhtor kqk
 * @date 2020/4/26 0026 - 9:49
 */
@RestController
public class WantController {
    @Autowired
    private WantService wantService;

    /**
     * 在求租详情里拥有车位的人预留车位
     * @param rentInfoId
     * @return
     */
    @PostMapping("reserverInfo/{rentInfoId}")
    public CommonResponse reserverInfo(@PathVariable String rentInfoId){
        wantService.reserveRent(rentInfoId);
        return ResultUtils.success("预留成功！");
    }

    /**
     * 在求租详情里发布人确认租车位
     * @param rentInfoId
     * @return
     */
    @PostMapping("confirmOutInfo/{rentInfoId}")
    public CommonResponse confirmOutInfo(@PathVariable String rentInfoId){
        wantService.confirmOutInfo(rentInfoId);
        return ResultUtils.success("确认成功！");
    }


    /**
     * 在出租详情里求租车位的人预定车位
     * @param rentInfoId
     * @return
     */
    @PostMapping("reserveRentOut/{rentInfoId}")
    public CommonResponse reserveRentOut(@PathVariable String rentInfoId){
        wantService.reserveRentOut(rentInfoId);
        return ResultUtils.success("预订成功！");
    }

    /**
     * 在求租详情里发布人确认租车位
     * @param rentInfoId
     * @return
     */
    @PostMapping("confirmInfo/{rentInfoId}")
    public CommonResponse confirmInfo(@PathVariable String rentInfoId){
        wantService.confirmInfo(rentInfoId);
        return ResultUtils.success("确认成功！");
    }
}
