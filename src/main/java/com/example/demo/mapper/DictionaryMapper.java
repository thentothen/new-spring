package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.DictionaryUse;

@Mapper
public interface DictionaryMapper {
    
     List<DictionaryUse> getList();
     Object add(@Param("record") DictionaryUse record);
     int update(@Param("record") DictionaryUse record);
     Object delete(@Param("id") Long id);
}
