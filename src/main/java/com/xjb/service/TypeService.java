package com.xjb.service;

import com.xjb.pojo.Type;
import com.xjb.vo.TypeIndex;

import java.util.List;


/**
 * @author Bo
 * @create 2021-04-17 16:24
 */
public interface TypeService {

    //新增分类
    int saveType(Type type);

    //根据名字查询分类
    Type getTypeName(String name);

    //按id查询分类
    Type getTypeId(Long id);

    //根据type中的id更新分类
    int updateType(Type type);

    //按id删除分类
    int deleteType(Long id);

    //查询所有分类
    List<Type> getAllType();

    //查询各分类所对应的博客数量，并按博客数量倒叙排序返回typeCount条数据
    List<TypeIndex> getTypeDescIndex(Integer typeCount);

}
