package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import constants.StatusCode;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO save(@RequestBody Article article) {
        articleService.save(article);
        return new ResultDTO(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}", method = RequestMethod.GET)
    public ResultDTO findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageData = articleService.findByKey(key, page, size);
        return new ResultDTO(
                true, StatusCode.OK, "查询成功", new PageResultDTO<Article>(pageData.getTotalElements(), pageData.getContent()));
    }
}
