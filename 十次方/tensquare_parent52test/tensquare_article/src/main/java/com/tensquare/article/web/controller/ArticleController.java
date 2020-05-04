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

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 1  查询全部数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO findAll() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", articleService.findAll());
    }

    /**
     * 2 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultDTO findById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", articleService.findById(id));
    }

    /**
     * 3  分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Article>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 4  根据条件查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResultDTO findSearch(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

    /**
     * 5  增加
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO add(@RequestBody Article article) {
        articleService.add(article);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 6 修改
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultDTO update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 7  删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultDTO delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 8 文章的审核
     */
    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.PUT)
    public ResultDTO examine(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new ResultDTO(true, StatusCode.OK, "审核成功");
    }

    /**
     * 9 文章的点赞
     */
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public ResultDTO updateThumbup(@PathVariable String articleId) {
        articleService.updateThumbup(articleId);
        return new ResultDTO(true, StatusCode.OK, "点赞成功");
    }
}
