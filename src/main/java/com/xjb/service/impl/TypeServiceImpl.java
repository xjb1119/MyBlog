package com.xjb.service.impl;

import com.xjb.NotFoundException;
import com.xjb.dao.TypeMapper;
import com.xjb.pojo.Type;
import com.xjb.service.TypeService;
import com.xjb.vo.TypeIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-17 16:31
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public int saveType(Type type) {

        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getTypeName(String name) {
        return typeMapper.getTypeName(name);
    }

    @Transactional
    @Override
    public Type getTypeId(Long id) {

        return typeMapper.getTypeId(id);
    }


    @Transactional
    @Override
    public int updateType(Type type) {
        Type t = typeMapper.getTypeId(type.getId());
        if (t == null){
            throw new NotFoundException("不存在该类型");
        }
        return typeMapper.updateType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {

        return typeMapper.deleteType(id);
    }


    @Transactional
    @Override
    public List<Type> getAllType() {

        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public List<TypeIndex> getTypeDescIndex(Integer typeCount) {

        return typeMapper.getTypeDescIndex(typeCount);
    }
}
