package com.xjb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bo
 * @create 2021-04-24 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeIndex {

    private Long typeId;
    private String typeName;
    private Long blogCount;
}
