package com.zomatouser.dboperation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDescInfoBean {
    private String field;
    private String type;
    private String isNull;
    private String key;
    private String defaultValue;
    private String extra;
}
