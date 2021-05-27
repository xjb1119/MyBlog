package com.xjb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 在博客管理页面显示博客数据实体类
 * @author Bo
 * @create 2021-04-18 21:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogManage {

    private Long id;
    private String title;
    private Date updateTime;
    private boolean recommend;
    private boolean published;
    private Long typeId;
    private String typeName;

}
