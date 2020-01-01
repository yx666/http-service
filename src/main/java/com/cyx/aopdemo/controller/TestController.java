package com.cyx.aopdemo.controller;

import com.cyx.aopdemo.model.User;
import com.cyx.aopdemo.service.NameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource(name = "nameServiceImpl")
    private NameService nameService;

    @RequestMapping("/1")
    @ResponseBody
    public String test(){

        return nameService.getName("cyx");
    }

    @RequestMapping("/2")
    @ResponseBody
    public String test2(){

        return nameService.getUser("cyx","12").toString();
    }

    @RequestMapping("/3")
    @ResponseBody
    public String test3(){
      nameService.getUserVoid("cyx","12");
      return "OK";
    }

    @RequestMapping("/4")
    @ResponseBody
    public String test4(){
        nameService.getUserList("cyx","12");
        return "OK";
    }
}
