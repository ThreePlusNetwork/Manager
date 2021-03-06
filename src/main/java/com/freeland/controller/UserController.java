package com.freeland.controller;

import com.freeland.dao.po.User;
import com.freeland.dao.po.UserPossession;
import com.freeland.model.ResponseWrapper;
import com.freeland.service.UserPossessionService;
import com.freeland.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author heiqie
 * @date 2018/7/11
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPossessionService userPossessionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "测试", notes = "测试", httpMethod = "GET")
    public ResponseWrapper<String> test() {
        return ResponseWrapper.success("你可以访问");
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ApiOperation(value = "创建新用户", notes = "创建新的用户", httpMethod = "POST")
    public ResponseWrapper<String> addNewUser(@NotBlank @RequestParam(name = "name") String name,
                                              @NotBlank @RequestParam(name = "password") String password) {
        boolean success = userService.register(User.builder().username(name).password(password).build());
        if (success) {
            return ResponseWrapper.success("success");
        }
        return ResponseWrapper.error("该用户已经被注册");
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息详情", notes = "通过用户id获取用户信息", httpMethod = "GET")
    public ResponseWrapper<User> retrieveUsers(@NotBlank @RequestParam(name = "id") Long id) {
        return ResponseWrapper.success(userService.findById(id));
    }


    @RequestMapping(value = "/user/score", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户金币", notes = "通过用户id获取用户金币数量", httpMethod = "GET")
    public Long retrieveUserScore(@NotBlank @RequestParam(name = "id") Long id) {
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getScore();
    }

    @RequestMapping(value = "/user/token", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户token数量", notes = "通过用户id获取用户token数量", httpMethod = "GET")
    public Long retrieveUserToken(@NotBlank @RequestParam(name = "id") Long id) {
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getToken();
    }
}
