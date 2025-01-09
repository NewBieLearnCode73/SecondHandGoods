package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.TypeRepository;

import com.dinhchieu.demo.service.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;


    @Override
    public List<String> getAllTypeName() {
        return typeRepository.getAllTypeName();
    }
}
