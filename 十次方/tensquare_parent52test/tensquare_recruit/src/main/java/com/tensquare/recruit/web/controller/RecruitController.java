package com.tensquare.recruit.web.controller;

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

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    /**
     * 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO findAll() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultDTO findById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", recruitService.findById(id));
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
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", recruitService.findSearch(searchMap));
    }

    /**
     * 增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO add(@RequestBody Recruit recruit) {
        recruitService.add(recruit);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultDTO update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruit.setId(id);
        recruitService.update(recruit);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultDTO delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 查询推荐的职位
     */
    @RequestMapping(value = "/search/recommend", method = RequestMethod.GET)
    public ResultDTO recommend() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", recruitService.recommend());
    }

    /**
     * 查询最新的列表
     */
    @RequestMapping(value = "/search/newlist", method = RequestMethod.GET)
    public ResultDTO newlist() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", recruitService.newlist());
    }
}
