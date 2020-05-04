package com.tensquare.web.controller;

import com.tensquare.service.FriendService;
import constants.StatusCode;
import entity.ResultDTO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 1 添加好友
     * islike = 0  不喜欢
     * islike = 1 喜欢
     */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public ResultDTO addFriend(@PathVariable String friendid, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new ResultDTO(false, StatusCode.ACCESSERROR, "无权访问");
        }
        if (type.equals("1")) {
            if (friendService.addFriend(claims.getId(), friendid) == 0) {
                return new ResultDTO(false, StatusCode.REMOTEERROR, "重复添加好友");
            }
        } else {
            /*不喜欢*/
            friendService.addNoFriend(claims.getId(), friendid);
        }
        return new ResultDTO(true, StatusCode.OK, "操作成功");
    }

    /**
     * 2  删除好友
     */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public ResultDTO remove(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null) {
            return new ResultDTO(false, StatusCode.ACCESSERROR, "无权访问");
        }
        friendService.deleteFriend(claims.getId(), friendid);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

}