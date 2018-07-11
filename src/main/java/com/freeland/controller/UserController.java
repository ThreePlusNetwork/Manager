package com.freeland.controller;

import com.freeland.dao.po.User;
import com.freeland.dao.po.UserPossession;
import com.freeland.service.UserPossessionService;
import com.freeland.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPossessionService userPossessionService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ApiOperation(value = "创建新用户", notes = "创建新的用户", httpMethod = "POST")
    public User addNewUser(@RequestParam(name = "name") String name,
                           @RequestParam(name = "age") Integer age){
        return userService.insert(User.builder().name(name).age(age).build());
    }


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息详情", notes = "通过用户id获取用户信息", httpMethod = "GET")
    public User retrieveUsers(@RequestParam(name = "id") Long id){
        return userService.findById(id);
    }


    @RequestMapping(value = "/user/score",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户金币", notes = "通过用户id获取用户金币数量", httpMethod = "GET")
    public Long retrieveUserScore(@RequestParam(name = "id") Long id){
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getScore();
    }

    @RequestMapping(value = "/user/token",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户token数量", notes = "通过用户id获取用户token数量", httpMethod = "GET")
    public Long retrieveUserToken(@RequestParam(name = "id") Long id){
        UserPossession userPossession = userPossessionService.findByUserId(id);
        if (userPossession == null) {
            return null;
        }
        return userPossession.getToken();
    }
}
