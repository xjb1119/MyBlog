package com.xjb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Bo
 * @create 2021-04-24 12:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogIndex {

    private Long blogId;
    private String title;
    private String content;
    private String description;
    private String firstPicture;
    private String flag;    //标记(原创/转载/翻译)
    private boolean appreciation;    //赞赏开启
    private boolean shareStatement;    //版权开启
    private boolean commentabled;    //评论开启
    private Date updateTime;
    private Integer views;
    private Integer commentCount;
    private String typeName;
    private String nickname;
    private String avatar;




}
