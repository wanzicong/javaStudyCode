package com.tensquare.qa.web.controller;

import java.util.Map;

import com.tensquare.qa.client.LabelClient;
import constants.StatusCode;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private LabelClient labelClient;

    /**
     * 1 查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO findAll() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 2 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultDTO findById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }

    /**
     * 3 分页+多条件查询
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 4 根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 5  增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO add(@RequestBody Problem problem) {
        problemService.add(problem);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 6 修改
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultDTO update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 7 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultDTO delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 8 最新问题的列表查询
     */
    @RequestMapping(value = "/newlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public ResultDTO newlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.newlist(labelid, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 9 最新问题的列表查询
     */
    @RequestMapping(value = "/hotlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public ResultDTO hotlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotlist(labelid, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 10 最新问题的列表查询
     */
    @RequestMapping(value = "/waitlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
    public ResultDTO waitlist(@PathVariable String labelid, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.waitlist(labelid, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 11 调用lable findById 方法
     * 微服务之间的调用
     */
    @GetMapping("/label/{labelid}")
    public ResultDTO findLabelById(@PathVariable String labelid) {
        ResultDTO resultDTO = labelClient.listById(labelid);
        return resultDTO;
    }
}
