package com.tensquare.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import utils.IdWorker;

import com.tensquare.user.dao.UserRepository;
import com.tensquare.user.pojo.User;

/**
 * 服务层
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdWorker idWorker;
    //注入RedisTemplate
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BCryptPasswordEncoder encode;

    /**
     * 1 增加
     */
    public void saveUser(User user) {
        user.setId(idWorker.nextId() + "");
        /*密码加密*/
        user.setPassword(encode.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * 2  用户自助注册保存用户
     */
    public void saveUser(User user, String checkcode) {
        //1.校验验证码(和redis中的对比)
        //获取redis中的验证码
        String checkcodeRedis = redisTemplate.opsForValue().get("sms.checkcode." + user.getMobile());
        //判断
        //判断验证码是否为空
        if (checkcodeRedis == null) {
            throw new RuntimeException("请点击获取短信验证码");
        }
        //判断是否相等
        if (!checkcodeRedis.equals(checkcode)) {
            throw new RuntimeException("验证码输入不正确");
        }
        System.out.println(user.getPassword());
        System.out.println(encode.encode(user.getPassword()));
        user.setPassword(encode.encode(user.getPassword()));
        //2.保存用户到数据库
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
//      user.setUpdatedate(new Date());//更新日期
//		user.setLastdate(new Date());//最后登陆日期
        userRepository.save(user);

        //3.用户注册成功后，清除redis中的验证码
        redisTemplate.delete("sms.checkcode." + user.getMobile());
    }

    /**
     * 3 修改
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * 4   删除
     */
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    /**
     * 5 查询全部列表
     */
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    /**
     * 6  根据ID查询实体
     */
    public User findUserById(String id) {
        return userRepository.findById(id).get();
    }

    /**
     * 7 根据条件查询列表
     */
    public List<User> findUserList(Map whereMap) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //Specification条件查询
        return userRepository.findAll(specification);
    }

    /**
     * 8  组合条件分页查询
     */
    public Page<User> findUserListPage(Map whereMap, int page, int size) {
        //构建Spec查询条件
        Specification<User> specification = getUserSpecification(whereMap);
        //构建请求的分页对象
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userRepository.findAll(specification, pageRequest);
    }

    /**
     * 9  根据参数Map获取Spec条件对象
     */
    private Specification<User> getUserSpecification(Map searchMap) {

        return (Specification<User>) (root, query, cb) -> {
            //临时存放条件结果的集合
            List<Predicate> predicateList = new ArrayList<Predicate>();
            //属性条件
            // ID
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
            }
            // 手机号码
            if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
            }
            // 密码
            if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
            }
            // 昵称
            if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
            }
            // 性别
            if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
            }
            // 头像
            if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
            }
            // E-Mail
            if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
            }
            // 兴趣
            if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
            }
            // 个性
            if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
            }

            //最后组合为and关系并返回
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };

    }

    /**
     * 10 发送短信验证码（保存手机验证码到redis和mq中）
     */
    public void saveSmsCheckcode(String mobile) {
        //1.生成随机6位验证码
        Random random = new Random();
        //最大值
        int max = 999999;
        //最小值
        int min = 100000;
        //生成随机码
        int checkcode = random.nextInt(max);
        //修正
        if (checkcode < min) {
            checkcode += min;
        }
        System.out.println(mobile + "随机生成的验证码是：" + checkcode);

        //2.保存手机号和验证码
        //1）redis,过期时间为5分钟
        redisTemplate.opsForValue().set("sms.checkcode." + mobile, checkcode + "", 5, TimeUnit.MINUTES);
        //2）mq
        //封装数据的map
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode + "");
        // 使用默认直连交换机 
        rabbitTemplate.convertAndSend("sms.checkcode", map);
    }

    /**
     * 11 普通用户的登录
     */
    public User login(String mobile, String password) {
        User userLogin = userRepository.findByMobile(mobile);
        if (userLogin== null){
            return null;
        }
        if (encode.matches(password, userLogin.getPassword())) {
            return userLogin;
        }else {
            return null;
        }
    }

    /**
     * 12  跟新粉丝数
     */
    @Transactional
    public void incFanscount(String userid, int x) {
        userRepository.incFanscount(userid, x);
    }

    /**
     * 13 跟新 关注数
     */
    @Transactional
    public void incFollowcount(String userid, int x) {
        userRepository.incFollowcount(userid, x);
    }
}
