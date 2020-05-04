package com.tensquare.qa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import utils.IdWorker;

/**
 * 服务层
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;
    @Autowired
    IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Map whereMap, int page, int size) {
        Specification<Problem> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     */
    public List<Problem> findSearch(Map whereMap) {
        Specification<Problem> specification = createSpecification(whereMap);
        return problemDao.findAll(specification);
    }
    /**
     * 根据ID查询实体
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }
    /**
     * 增加
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }
    /**
     * 修改
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }
    /**
     * 删除
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }
    /**
     * 动态条件构建
     */
    private Specification<Problem> createSpecification(Map searchMap) {
        return new Specification<Problem>() {
            @Override
            public Predicate toPredicate(Root<Problem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 内容
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 昵称
                if (searchMap.get("nikename") != null && !"".equals(searchMap.get("nikename"))) {
                    predicateList.add(cb.like(root.get("nikename").as(String.class), "%" + (String) searchMap.get("nikename") + "%"));
                }
                // 是否解决
                if (searchMap.get("solve") != null && !"".equals(searchMap.get("solve"))) {
                    predicateList.add(cb.like(root.get("solve").as(String.class), "%" + (String) searchMap.get("solve") + "%"));
                }
                // 
                if (searchMap.get("replyname") != null && !"".equals(searchMap.get("replyname"))) {
                    predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + (String) searchMap.get("replyname") + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
    /**
     * 最新问题列表的查询 分页
     */
    public Page<Problem> newlist(String labelid ,int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return  problemDao.newlist(labelid , pageable);
    }
    /**
     * 最火问题列表的查询 分页
     */
    public Page<Problem> hotlist(String labelid ,int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return  problemDao.hotlist(labelid , pageable);
    }
    /**
     * 等待回答问题回答列表的查询 分页
     */
    public Page<Problem> waitlist(String labelid ,int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return  problemDao.waitlist(labelid , pageable);
    }
}
