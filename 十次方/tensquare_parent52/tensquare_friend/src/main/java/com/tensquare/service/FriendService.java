package com.tensquare.service;

import com.tensquare.client.UserClient;
import com.tensquare.dao.FriendDao;
import com.tensquare.dao.NOFriendDao;
import com.tensquare.pojo.Friend;
import com.tensquare.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FriendService {
    @Autowired
    private UserClient userClient;
    @Autowired
    FriendDao friendDao;
    @Autowired
    NOFriendDao noFriendDao;

    /**
     * 2 添加非好友
     * 不喜欢的操作
     */
    @Transactional
    public int addFriend(String userid, String friendid) {
        /*判断用户是否添加了好友，否者不操作*/
        if (friendDao.selectCount(userid, friendid) > 0) {
            return 0;
        }
        /*像喜欢表中添加数据*/
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        /*判断对方是否喜欢你*/
        if (friendDao.selectCount(friendid, userid) > 0) {
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
        }
        return 1;
    }

    /**
     * 2 添加非好友
     * 不喜欢的操作
     */
    @Transactional
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        userClient.incFollowcount(userid, 1);//增加自己的关注数
        userClient.incFanscount(friendid, 1);//增加对方的粉丝数
    }


    /**
     * 3  删除好友
     */
    @Transactional
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid, friendid);
        friendDao.updateLike(friendid, userid, "0");
        addNoFriend(userid, friendid);//向不喜欢表中添加记录
        userClient.incFollowcount(userid, -1);//减少自己的关注数
        userClient.incFanscount(friendid, -1);//减少对方的粉丝数
    }
}
