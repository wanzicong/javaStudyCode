package com.tensquare.service;

import com.tensquare.dao.NOFriendDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoFriendService {
    @Autowired
    NOFriendDao noFriendDao;


}
