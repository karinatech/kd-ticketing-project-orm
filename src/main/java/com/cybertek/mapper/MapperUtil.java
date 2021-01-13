package com.cybertek.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {
    private ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
//    public <T>T convertToEntity(Object obj,T convertedObj){
//        return modelMapper.map(obj, (Type) convertedObj.getClass());
//    }
//    public <T>T convertToDto(Object obj,T convertedObj){
//        return modelMapper.map(obj, (Type) convertedObj.getClass());
//    }
    public <T>T convert(Object obj,T convertedObj){
        return modelMapper.map(obj, (Type) convertedObj.getClass());
    }
}
