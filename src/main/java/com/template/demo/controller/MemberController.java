package com.template.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created jixinshi on 2018-11-27.
 */
@RequestMapping(value = "/member")
@RestController
public class MemberController {

    @GetMapping(value = "/user")
    public String getUser(){
        return "lalala";
    }
}
