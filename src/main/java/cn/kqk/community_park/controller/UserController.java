package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.vo.IndexVO;
import cn.kqk.community_park.bean.vo.Register;
import cn.kqk.community_park.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @auhtor kqk
 * @date 2020/4/11 0011 - 16:42
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param register
     * @return
     */
    @PostMapping("/register")
    public CommonResponse register(@RequestBody Register register){
        userService.userRegister(register);
        return ResultUtils.success("注册成功！");
    }

    /**
     * 后台-用户列表
     * @param indexVO
     * @return
     */
    @PostMapping("/admin/userList")
    public CommonResponse userList(@RequestBody IndexVO indexVO){
        System.out.println(indexVO);
        PageInfo pageInfo = userService.userList(indexVO.getPageNum(),indexVO.getPageSize());
        System.out.println(pageInfo);
        return ResultUtils.success("成功",pageInfo);
    }

    /**
     * 后台-管理员封禁用户
     * @param userId
     * @return
     */
    @PostMapping("/admin/adminLockUser/{userId}")
    public CommonResponse adminLockUser(@PathVariable String userId) {
        userService.adminLockUser(userId);
        return ResultUtils.success("封禁成功！");
    }

    /**
     * 后台-管理员解封用户
     * @param userId
     * @return
     */
    @PostMapping("/admin/adminUnLockUser/{userId}")
    public CommonResponse adminUnLockUser(@PathVariable String userId) {
        userService.adminUnLockUser(userId);
        return ResultUtils.success("封禁成功！");
    }


    /**
     * 注册
     * @return
     */
    @GetMapping("/reg")
    public ModelAndView reg(){
        ModelAndView mv = new ModelAndView("register");
        return mv;
    }

    /**
     * 登录
     * @return
     */
    @GetMapping("/login")
    public ModelAndView goLogin(){
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    /**
     * 登录
     * @return
     */
    @GetMapping("/admin/login")
    public ModelAndView adminLogin(){
        ModelAndView mv = new ModelAndView("admin/login");
        return mv;
    }

    /**
     * 首页
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @GetMapping("/admin/userList")
    public ModelAndView userList(){
        ModelAndView mv = new ModelAndView("admin/userList");
        return mv;
    }

}
