package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.service.MessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auhtor kqk
 * @date 2020/4/26 0026 - 14:12
 */
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/readMessage")
    public CommonResponse readMessage(){
        messageService.readMessage();
        return ResultUtils.success("成功");
    }

    /**
     * 首页获取未读信息总数
     * @return
     */
    @PostMapping("/messageCount")
    public CommonResponse messageCount(){
        int messageCount = messageService.messageCount();
        return ResultUtils.success("成功",messageCount);
    }

    @PostMapping("/messageList")
    public CommonResponse messageList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = messageService.messageList(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    @GetMapping("/message")
    public ModelAndView getRentInput(){
        ModelAndView mv = new ModelAndView("message");
        return mv;
    }

    /**
     * 根据id页面跳转到出租详情页
     * @param userId
     * @return
     */
    @GetMapping("userInfoDetail/{userId}")
    public ModelAndView userInfoDetail(@PathVariable String userId){
        ModelAndView mv = new ModelAndView("about-other");
        return mv;
    }
}
