package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.DictionaryUse;
import com.example.demo.service.DictionaryService;


@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @GetMapping("/list")
    public JSONObject getList() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dictionaryService.getList());
        return jsonObject;
    }
    
    @PostMapping("/add")
    public JSONObject add(@RequestBody DictionaryUse param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dictionaryService.add(param));
        return jsonObject;
    }

    @PostMapping("/update")
    public JSONObject update(@RequestBody DictionaryUse param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dictionaryService.update(param));
        return jsonObject;
    }

    @GetMapping("/delete")
    public JSONObject update(@RequestParam Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", dictionaryService.delete(id));
        return jsonObject;
    }
    
}
