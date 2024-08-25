package com.zomatouser.dboperation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableConstraintInfo {
    private String CONSTRAINT_CATALOG;
    private String CONSTRAINT_SCHEMA;
    private String CONSTRAINT_NAME;
    private String TABLE_CATALOG;
    private String TABLE_SCHEMA;
    private String TABLE_NAME;
    private String COLUMN_NAME;
    private Long ORDINAL_POSITION;
    private Long POSITION_IN_UNIQUE_CONSTRAINT;
    private String REFERENCED_TABLE_SCHEMA;
    private String REFERENCED_TABLE_NAME;
    private String REFERENCED_COLUMN_NAME;
}
