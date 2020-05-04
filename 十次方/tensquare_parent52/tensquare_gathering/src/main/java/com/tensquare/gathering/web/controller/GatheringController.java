package com.tensquare.gathering.web.controller;
import java.util.Map;

import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;
import constants.StatusCode;
/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

    @Autowired
    private GatheringService gatheringService;


    /**
     * 增加
     */
    @PostMapping
    public ResultDTO add(@RequestBody Gathering gathering  ){
        gatheringService.add(gathering);
        return new ResultDTO(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     */
    @PutMapping("/{id}")
    public ResultDTO edit(@RequestBody Gathering gathering, @PathVariable String id ){
        gathering.setId(id);
        gatheringService.update(gathering);
        return new ResultDTO(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResultDTO remove(@PathVariable String id ){
        gatheringService.deleteById(id);
        return new ResultDTO(true,StatusCode.OK,"删除成功");
    }
    /**
     * 查询所有的数据
     */
    @GetMapping
    public ResultDTO list(){
        return new ResultDTO(true,StatusCode.OK,"查询成功",gatheringService.findAll());
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public ResultDTO listById(@PathVariable String id){
        return new ResultDTO(true,StatusCode.OK,"查询成功",gatheringService.findById(id));
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public ResultDTO list( @RequestBody Map searchMap){
        return new ResultDTO(true,StatusCode.OK,"查询成功",gatheringService.findSearch(searchMap));
    }

    /**
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public ResultDTO listPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
        Page<Gathering> pageResponse = gatheringService.findSearch(searchMap, page, size);
        return  new ResultDTO(true,StatusCode.OK,"查询成功",  new PageResultDTO<Gathering>(pageResponse.getTotalElements(), pageResponse.getContent()) );
    }

}