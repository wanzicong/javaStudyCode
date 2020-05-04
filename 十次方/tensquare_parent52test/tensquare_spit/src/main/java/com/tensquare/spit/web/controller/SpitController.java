package com.tensquare.spit.web.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import constants.StatusCode;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ResultDTO findAll() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public ResultDTO findByID(@PathVariable String spitId) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public ResultDTO deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public ResultDTO update(@PathVariable String spitId, @RequestBody Spit spit) {
        spit.setId(spitId);
        spitService.update(spit);
        return new ResultDTO(true, StatusCode.OK, "更新成功");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultDTO save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new ResultDTO(true, StatusCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{parentId}/{page}/{size}", method = RequestMethod.POST)
    public ResultDTO findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentId(parentId, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 点赞的功能
     * 难度:较复杂
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public ResultDTO resultDTO(@PathVariable String spitId) {
        /*点赞之前判断是否已经点赞 暂时还没做认证 暂时先把userID写死*/
        String userId = "123";
        /*判断用户是否已经点赞*/
        if (redisTemplate.opsForValue().get("thumbup_" + userId) != null) {
            return new ResultDTO(false, StatusCode.REPERROR, "不能重复点赞");
        }
        spitService.thumbup(spitId);
        /*向缓存中添加点赞成功的标识*/
        redisTemplate.opsForValue().set("thumbup_" + userId, "点赞成功");
        return new ResultDTO(true, StatusCode.OK, "点赞成功");
    }
}

