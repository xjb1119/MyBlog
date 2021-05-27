package com.xjb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bo
 * @create 2021-04-24 11:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagIndex {

    private Long tagId;
    private String tagName;
    private Long blogCount;

}
