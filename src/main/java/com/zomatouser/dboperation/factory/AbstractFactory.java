package com.zomatouser.dboperation.factory;

import com.zomatouser.dboperation.dto.DatabaseNameDto;

import java.util.List;
import java.util.Map;

public abstract class AbstractFactory {

    public abstract List<DatabaseNameDto> create(List<Map<String, Object>> tables);
}
