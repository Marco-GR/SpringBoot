package com.example.myFirstSpringApp.controller;

import com.example.myFirstSpringApp.payload.response.PagedResponse;
import com.example.myFirstSpringApp.payload.response.PollResponse;
import com.example.myFirstSpringApp.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import sun.plugin.util.UserProfile;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping
    public PagedResponse<PollResponse> getPolls(UserProfile currentUser, @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER), @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE)){
        return pollService.getAllPolls(currentUser, page, size);
    }

}
