package com.figth_club.leaderboard_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class BoardController {

    @RequestMapping("/leaderboard")
    public String infos(){
        return "leaderboard";
    }
}
