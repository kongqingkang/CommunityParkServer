package cn.kqk.community_park.controller;

import cn.kqk.common.bean.vo.request.Login;
import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.utils.http.ResultUtils;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.vo.EditPassword;
import cn.kqk.community_park.service.LoginService;
import cn.kqk.community_park.service.UserLoginService;
import cn.kqk.community_park.shiro.MyAuthenticationToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lhr
 * 2020/3/25 15:44
 * 登录
 */

@RestController
public class LoginController {
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private LoginService loginService;

    /**
     * 管理员登录
     * @param login
     * @return
     */
//    @PostMapping("/admin/login")
//    public CommonResponse adminLogin(@RequestBody Login login){
//        //添加用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        MyAuthenticationToken token = new MyAuthenticationToken(login);
//        subject.login(token);
//        Map<String, String> map = new HashMap<>();
//        map.put("token", subject.getSession().getId().toString());
//        return ResultUtils.success("登录成功", map);
//    }

    /**
     * 登录
     * @param login
     * @return
     */
    @PostMapping("/login")
    public CommonResponse login(@RequestBody Login login){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        MyAuthenticationToken token = new MyAuthenticationToken(login);
        try {
            subject.login(token);
            Map<String, String> map = new HashMap<>();
            map.put("token", subject.getSession().getId().toString());
            return ResultUtils.success("登录成功", map);
        }catch (UnknownAccountException e){
//            return ResultUtils.error(101, "用户名不存在！");
            return ResultUtils.error(101,"用户名不存在！");
        }catch (IncorrectCredentialsException e){
            return ResultUtils.error(102, "密码错误！");
        }
    }

    @GetMapping("/get_info")
    public CommonResponse getUserInfo(HttpServletRequest request){
        // BaseUser user = (BaseUser)SecurityUtils.getSubject().getPrincipal();
        // LoginData loginData = loginService.getLoginData(user.getId(),request);
        // return ResultUtils.success("",loginData);
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        return ResultUtils.success(getUser);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public CommonResponse logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtils.success("登出成功");
    }

    @PostMapping("/admin/logout")
    public CommonResponse adminLogout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtils.success("登出成功");
    }

    @RequestMapping("/unLogin")
    public CommonResponse unLogin(){
        return ResultUtils.error(100, "未登录");
    }

    /**
     * 修改密码
     * @param EditPassword
     * @return
     */
    @PostMapping("/editThePassword")
    public CommonResponse editThePassword(@RequestBody EditPassword EditPassword){
        userLoginService.editThePassword(EditPassword);
        return ResultUtils.success("修改成功！");
    }
    /**
     * 个人中心
     * @return
     */
    @GetMapping("/about")
    public ModelAndView about(){
        ModelAndView mv = new ModelAndView("about");
        return mv;
    }

    /**
     * 修改密码
     * @return
     */
    @GetMapping("/editPassword")
    public ModelAndView editPassword(){
        ModelAndView mv = new ModelAndView("editpassword");
        return mv;
    }

    @GetMapping("/admin/index")
    public ModelAndView adminIndex(){
        ModelAndView mv = new ModelAndView("admin/index");
        return mv;
    }


}
