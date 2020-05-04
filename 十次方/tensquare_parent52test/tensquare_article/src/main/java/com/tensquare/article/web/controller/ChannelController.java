package com.tensquare.article.web.controller;

import java.util.Map;

import constants.StatusCode;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.article.pojo.Channel;
import com.tensquare.article.service.ChannelService;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    /**
     * 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO findAll() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", channelService.findAll());
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultDTO findById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", channelService.findById(id));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", channelService.findSearch(searchMap));
    }

    /**
     * 增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO add(@RequestBody Channel channel) {
        channelService.add(channel);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultDTO update(@RequestBody Channel channel, @PathVariable String id) {
        channel.setId(id);
        channelService.update(channel);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultDTO delete(@PathVariable String id) {
        channelService.deleteById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }
}
