package com.tensquare.web.controller;

import com.tensquare.service.NoFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoFriendController {
    @Autowired
    NoFriendService noFriendService;


}
