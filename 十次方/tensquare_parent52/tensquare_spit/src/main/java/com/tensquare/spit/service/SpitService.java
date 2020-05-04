package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 保存数据-发表吐槽
     */
    public void save(Spit spit) {
        spit.setId(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        /*如果添加的评论是一个二级评论的业务处理*/
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            /*说明即将发布这个吐槽(评论是一个有根节点的吐槽)*/
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spit.setComment(0);
        spit.setState("1");
        spitDao.save(spit);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        spitDao.findByParentid(parentId, pageable);
        return null;
    }

    /**
     * 点赞功能
     */
    public void thumbup(String spitId) {
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup((spit.getThumbup() == null ? 0 : spit.getThumbup()) + 1);
        spitDao.save(spit);
    }
}
