package com.example.demo.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.IssueMapper;


@RestController
@RequestMapping("/issue")
public class IssueController {
    

    @Autowired
    private IssueMapper IssueMapper;

    @GetMapping("/list")
    public JSONObject getList(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", IssueMapper.getList());
        jsonObject.put("messate","");
        jsonObject.put("status", "200");
        return jsonObject;
    }

    @PostMapping("/add")
    public JSONObject add(@RequestBody Issue issue){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", IssueMapper.addIssues(issue));
        jsonObject.put("messate","");
        jsonObject.put("status", "200");
        return jsonObject;
    }
    @PostMapping("/update")
    public JSONObject update(@RequestBody Issue issue){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", IssueMapper.updateIssues(issue.getId(), issue));
        jsonObject.put("messate","");
        jsonObject.put("status", "200");
        return jsonObject;
    }

    @PostMapping("/delete")
    public JSONObject delete(@Param(value = "id") Long id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", IssueMapper.deletIssues(id));
        jsonObject.put("messate","");
        jsonObject.put("status", "200");
        return jsonObject;
    }

}
