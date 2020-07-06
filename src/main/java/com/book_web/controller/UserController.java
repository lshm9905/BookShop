package com.book_web.controller;

import com.book_web.pojo.User;
import com.book_web.pojo.VueLoginInfo;
import com.book_web.pojo.VueResultInfo;
import com.book_web.service.UserService;
import com.book_web.utils.ResultFactory;

import java.io.IOException;
import java.util.Objects;

//import static sun.rmi.transport.proxy.CGIHandler.RequestMethod;

@Controller
@EnableAutoConfiguration
public class UserController {
    @Autowired
    public UserService userService;

    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public VueResultInfo userLogin(@Valid @RequestBody VueLoginInfo loginInfo, BindingResult bindingResult) throws IOException {
        User user = new User();
        user.setUsername(loginInfo.getUsername());
        user.setPassword(loginInfo.getPassword());
        user = userService.userLogin(user);
        //VueResultInfo vueResultInfo = null;

        VueResultInfo vueResultInfo=null;
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            vueResultInfo = ResultFactory.buildFailResult(message);
        } else {
            if (user != null&& Objects.equals(user.getPassword(),loginInfo.getPassword())) {
                vueResultInfo = ResultFactory.buildSuccessResult("登陆成功。");
            } else {
                String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
                vueResultInfo = ResultFactory.buildFailResult(message);
            }
        }
        return vueResultInfo;
    }
}

