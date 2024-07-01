package com.example.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

@RestController
@RequestMapping("/upload")
public class Upload {

     // 上传文件保存的目录
     private static final String UPLOAD_DIR = "E:/work/newspring/demo/uploads/images/";

     @PostMapping("/image")
     @ResponseBody
     public JSONObject uploadImage(@RequestParam("file") MultipartFile file) {
         if (file.isEmpty()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errno", 1);
            jsonObject.put("message", "Please select a file to upload.");
            return jsonObject;
         }
 
         try {
             // 创建上传目录
             File uploadDir = new File(UPLOAD_DIR);
             if (!uploadDir.exists()) {
                 uploadDir.mkdirs();
             }

             long timestamp = System.currentTimeMillis();
             // 保存文件
             byte[] bytes = file.getBytes();
             String fileName ="img_" + timestamp + "_" + file.getOriginalFilename();
             Path path = Paths.get(UPLOAD_DIR + fileName);
             Files.write(path, bytes);
             
             JSONObject jsonObject = new JSONObject();
             JSONObject jsonObject2 = new JSONObject();
             jsonObject.put("errno", 0);
             jsonObject2.put("url", fileName);
             jsonObject.put("data", jsonObject2);
             return jsonObject;
         } catch (IOException e) {
             e.printStackTrace();
             JSONObject jsonObject = new JSONObject();
            jsonObject.put("errno", 1);
            jsonObject.put("message", "error");
            return jsonObject;
         }
     }
    
}
