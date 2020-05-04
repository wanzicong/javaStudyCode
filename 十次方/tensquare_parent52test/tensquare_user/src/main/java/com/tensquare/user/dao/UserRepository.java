package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    /*根据电话号码查询用户*/
    User findByMobile(String mobile);

    /*跟新粉丝数*/
    @Modifying
    @Query("update User u set u.fanscount=u.fanscount+?2 where u.id=?1")
    public void incFanscount(String userid, int x);

    /*** 更新关注数 * @param userid 用户ID * @param x 粉丝数 */
    @Modifying
    @Query("update User u set u.followcount=u.followcount+?2 where u.id=?1")
    public void incFollowcount(String userid, int x);

}
