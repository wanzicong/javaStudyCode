package com.tensquare.user.web.controller;

import java.util.HashMap;
import java.util.Map;

import com.tensquare.user.util.JwtUtil;
import entity.PageResultDTO;
import entity.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;

import constants.StatusCode;

/**
 * 控制器层
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 1 增加
     */
    @PostMapping
    public ResultDTO add(@RequestBody Admin admin) {
        adminService.saveAdmin(admin);
        return new ResultDTO(true, StatusCode.OK, "增加成功");
    }

    /**
     * 2 修改
     */
    @PutMapping("/{id}")
    public ResultDTO edit(@RequestBody Admin admin, @PathVariable String id) {
        admin.setId(id);
        adminService.updateAdmin(admin);
        return new ResultDTO(true, StatusCode.OK, "修改成功");
    }

    /**
     * 3 删除
     */
    @DeleteMapping("/{id}")
    public ResultDTO remove(@PathVariable String id) {
        adminService.deleteAdminById(id);
        return new ResultDTO(true, StatusCode.OK, "删除成功");
    }

    /**
     * 4  查询全部数据
     */
    @GetMapping
    public ResultDTO list() {
        return new ResultDTO(true, StatusCode.OK, "查询成功", adminService.findAdminList());
    }

    /**
     * 5 根据ID查询
     */
    @GetMapping("/{id}")
    public ResultDTO listById(@PathVariable String id) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", adminService.findAdminById(id));
    }

    /**
     * 6 根据条件查询
     */
    @PostMapping("/search")
    public ResultDTO list(@RequestBody Map searchMap) {
        return new ResultDTO(true, StatusCode.OK, "查询成功", adminService.findAdminList(searchMap));
    }

    /**
     * 7 分页+多条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public ResultDTO listPage(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageResponse = adminService.findAdminListPage(searchMap, page, size);
        return new ResultDTO(true, StatusCode.OK, "查询成功", new PageResultDTO<Admin>(pageResponse.getTotalElements(), pageResponse.getContent()));
    }

    /**
     * 8 登录操作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultDTO login(@RequestBody Admin admin) {
        /*判断用户是否存在数据库中*/
        Admin adminLogin = adminService.login(admin);
        if (adminLogin != null) {
            /*签发token*/
            String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
            /*构建Map，用来封装token（最终转json）*/
            Map<String, String> map = new HashMap();
            /*自定义的claim*/
            map.put("token", token);
            /*用来给前端直接显示用户名用的，如果前端需要其他的东东，则也可以继续添加*/
            map.put("name", admin.getLoginname());
            return new ResultDTO(true, StatusCode.OK, "登录成功", map);
        } else {
            /*使得前后端可以通话的操作用JWT实现*/
            return new ResultDTO(false, StatusCode.LOGINERROR, "登录失败");
        }
    }
}
