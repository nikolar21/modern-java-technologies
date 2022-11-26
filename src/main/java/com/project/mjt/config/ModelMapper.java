package com.project.mjt.config;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper extends org.modelmapper.ModelMapper {

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream().map(sourceElement -> this.map(sourceElement, targetClass)).collect(Collectors.toList());
    }

    public <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
        List<T> result =  source.stream().map(sourceElement -> this.map(sourceElement, targetClass)).collect(Collectors.toList());
        return new PageImpl<>(result, source.getPageable(), source.getTotalElements());
    }
}
