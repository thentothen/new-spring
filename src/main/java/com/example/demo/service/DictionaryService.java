package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.DictionaryUse;
import com.example.demo.mapper.DictionaryMapper;

@Service
public class DictionaryService {
    
    @Autowired
    DictionaryMapper dictionaryMapper;

    public List<DictionaryUse> getList(){
        return dictionaryMapper.getList();
    }

    public Object add(DictionaryUse record) {
        return dictionaryMapper.add(record);
    }

    public int update(DictionaryUse record) {
        return dictionaryMapper.update(record);
    }

    public Object delete(Long id){
        return dictionaryMapper.delete(id);
    }
}
