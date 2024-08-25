package com.zomatouser.dboperation.factory;

import com.zomatouser.dboperation.dto.DatabaseNameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataBaseNameFactory extends AbstractFactory {

    @Override
    public List<DatabaseNameDto> create(List<Map<String, Object>> tables) {
        List<DatabaseNameDto> databaseNameDtoList = new ArrayList<DatabaseNameDto>();

        tables.forEach(table -> {
            DatabaseNameDto databaseNameDto = new DatabaseNameDto();
            databaseNameDto.setDatabaseName( (String) table.get("Database"));
            databaseNameDtoList.add(databaseNameDto);
        });
        return databaseNameDtoList;
    }
}
