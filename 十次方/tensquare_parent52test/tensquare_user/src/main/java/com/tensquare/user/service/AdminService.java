package com.tensquare.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import utils.IdWorker;

import com.tensquare.user.dao.AdminRepository;
import com.tensquare.user.pojo.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    BCryptPasswordEncoder encoder;

    /**
     * 1 增加
     */
    public void saveAdmin(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        admin.setState("1");
        /*密码加密*/
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    /**
     * 2 修改
     */
    public void updateAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    /**
     * 3  删除
     */
    public void deleteAdminById(String id) {
        adminRepository.deleteById(id);
    }

    /**
     * 4 查询全部列表
     */
    public List<Admin> findAdminList() {
        return adminRepository.findAll();
    }

    /**
     * 5  根据ID查询实体
     */
    public Admin findAdminById(String id) {
        return adminRepository.findById(id).get();
    }

    /**
     * 6  根据条件查询列表
     */
    public List<Admin> findAdminList(Map whereMap) {
        //构建Spec查询条件
        Specification<Admin> specification = getAdminSpecification(whereMap);
        //Specification条件查询
        return adminRepository.findAll(specification);
    }

    /**
     * 7 组合条件分页查询
     */
    public Page<Admin> findAdminListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<Admin> specification = getAdminSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminRepository.findAll(specification, pageRequest);
    }

    /**
     * 8  根据参数Map获取Spec条件对象
     */
    private Specification<Admin> getAdminSpecification(Map searchMap) {

        return (Specification<Admin>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 登陆名称
            if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 状态
            if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

    /**
     * 9  登录的操作
     */
    public Admin login(Admin admin) {

        /*先根据用户名查对象*/
        Admin adminLogin = adminRepository.findByLoginname(admin.getLoginname());
        /*拿用户输入的密码和数据库中的密码,进行比较判断是否相同*/
        if (adminLogin != null && encoder.matches(admin.getPassword(), adminLogin.getPassword())) {
            return adminLogin;
        }
        /*返回null值说明用户输入用户名错误*/
        return null;
    }
}
