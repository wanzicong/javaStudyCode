package com.tensquare.base.web.controller;

import com.tensquare.base.po.Label;
import com.tensquare.base.service.LabelService;
import constants.StatusCode;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理的表现层
 */
//@Controller
//@ResponseBody
@RestController
@RequestMapping("/label")
@CrossOrigin//允许跨域请求
public class LabelController {


    @Autowired
    private LabelService labelService;

    /**
     * 1 添加一个标签
     */
    @PostMapping
    public ResultDTO add(@RequestBody Label label) {
        labelService.saveLabel(label);
        return new ResultDTO(true, StatusCode.OK, "添加成功");
    }

    /**
     * 2  修改一个
     */
    @PutMapping("/{id}")
    public ResultDTO edit(@RequestBody Label label, @PathVariable String id) {
        label.setId(id);
        labelService.updateLabel(label);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 3  删除一个
     */
    @DeleteMapping("/{id}")
    public ResultDTO remove(@PathVariable String id) {
        //调用service
        labelService.deleteLabelById(id);
        //返回结果
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 4 查询所有
     */
    @GetMapping
    public ResultDTO list() {
        return new ResultDTO(true, StatusCode.OK, "查询成功"
                , labelService.findLabelList());
    }

    /**
     * 5 根据id查询一个
     */
    @GetMapping("/{id}")
    public ResultDTO listById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", labelService.findLabelById(id));
    }

    /**
     * 6 根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Label label) {
        List<Label> list = labelService.findSearch(label);
        return new ResultDTO(true, StatusCode.OK, "查询成功");
    }

    /**
     * 7. 条件 + 分页
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public ResultDTO pageQuery(@RequestBody Label label, @PathVariable int page, @PathVariable int size) {
        Page<Label> pageData = labelService.pageQuery(label,page,size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Label>(pageData.getTotalElements(),pageData.getContent()));
    }
}
