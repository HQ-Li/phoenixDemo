package com.hqli.www.controller;

import com.hqli.www.PhoenixApplication;
import com.hqli.www.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseBody
    public List<String> testURL(String data){
        logger.info(data);
        //testService.search(data);
        return testService.search(data);
    }
}
