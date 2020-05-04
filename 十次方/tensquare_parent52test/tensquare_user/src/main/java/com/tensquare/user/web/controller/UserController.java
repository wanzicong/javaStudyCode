package com.tensquare.user.web.controller;

import java.util.HashMap;
import java.util.Map;

import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import constants.StatusCode;
import utils.JwtUtil;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 1  增加
     */
    @PostMapping
    public ResultDTO add(@RequestBody User user) {
        userService.saveUser(user);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 2 修改
     */
    @PutMapping("/{id}")
    public ResultDTO edit(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.updateUser(user);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 3  删除
     */
    @DeleteMapping("/{id}")
    public ResultDTO remove(@PathVariable String id) {
        userService.deleteUserById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 4  查询全部数据
     */
    @GetMapping
    public ResultDTO list() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", userService.findUserList());
    }

    /**
     * 5  根据ID查询
     */
    @GetMapping("/{id}")
    public ResultDTO listById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", userService.findUserById(id));
    }

    /**
     * 6  根据条件查询
     */
    @PostMapping("/search")
    public ResultDTO list(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", userService.findUserList(searchMap));
    }

    /**
     * 7  分页+多条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public ResultDTO listPage(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageResponse = userService.findUserListPage(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<User>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

    /**
     * 8  发送短信验证码
     */
    @PostMapping("/sendsms/{mobile}")
    public ResultDTO sendSmsCheckcode(@PathVariable String mobile) {
        userService.saveSmsCheckcode(mobile);
        return new ResultDTO(true, StatusCode.OK, "发送成功");
    }

    /**
     * 9  用户注册
     */
    @PostMapping("/register/{checkcode}")
    public ResultDTO register(@RequestBody User user, @PathVariable String checkcode) {
        System.out.println(user);
        userService.saveUser(user, checkcode);
        return new ResultDTO(true, StatusCode.OK, "用户注册成功");
    }

    /**
     * 10  用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDTO login(@RequestBody User user) {
        user = userService.login(user.getMobile(), user.getPassword());
        /*用户登录成功*/
        if (user != null) {
            /*颁发token*/
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
            /*构建Map，用来封装token（最终转json）*/
            Map<String, String> map = new HashMap();
            /*授权token*/
            map.put("token", token);
            /* 用户昵称*/
            map.put("name", user.getNickname());
            /*用户头像*/
            map.put("avatar", user.getAvatar());
            return new ResultDTO(true, StatusCode.OK, "登录成功", map);
        } else {
            return new ResultDTO(false, StatusCode.LOGINERROR, "登录失败");
        }
    }

    /**
     * 11 增加粉丝数
     */
    @RequestMapping(value = "/incfans/{userid}/{x}", method = RequestMethod.POST)
    public void incFanscount(@PathVariable String userid, @PathVariable int x) {
        userService.incFanscount(userid, x);
    }


    /**
     * 12 增加关注数
     */
    @RequestMapping(value = "/incfollow/{userid}/{x}", method = RequestMethod.POST)
    public void incFollowcount(@PathVariable String userid, @PathVariable int x) {
        userService.incFollowcount(userid, x);
    }

}
