package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Issue;

@Mapper
public interface IssueMapper {

    List<Issue> getList();
    Object addIssues(@Param("record") Issue record);
    int updateIssues(@Param("id") Long id, @Param("record") Issue record);
    int deletIssues(@Param("id") Long id);
    
}
