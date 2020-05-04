package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.po.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签管理的业务
 */
@Service
public class LabelService {

    @Resource
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 1 标签的保存
     */
    public void saveLabel(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 2 更新一个标签
     */
    public void updateLabel(Label label) {
        labelDao.save(label);
    }

    /**
     * 3 删除一个标签
     */
    public void deleteLabelById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 4  查询全部标签
     */
    public List<Label> findLabelList() {
        return labelDao.findAll();
    }

    /**
     * 5  根据ID查询标签
     */
    public Label findLabelById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 6 根据多条件查询
     */
    public List<Label> findSearch(Label label) {
        /*root 根对象 , query 查询条件的关键字 Oder by ..... ,cb 用来封装条件的对象*/
        return labelDao.findAll(new Specification<Label>() {
                                    @Override
                                    public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                                        System.out.println("");
                                        List<Predicate> list = new ArrayList<>();
                                        if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                                            Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                                            list.add(predicate);
                                        }
                                        if (label.getState() != null && !"".equals(label.getState())) {
                                            Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                                            list.add(predicate);
                                        }
                                        Predicate[] parr = new Predicate[list.size()];
                                        parr = list.toArray(parr);
                                        return cb.and(parr);
                                    }
                                }
        );
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        /*分装一个分页对象*/
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        }, pageable);
    }
}
