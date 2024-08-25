package com.zomatouser.dboperation.dao;

import com.zomatouser.dboperation.dto.DatabaseNameDto;
import com.zomatouser.dboperation.dto.TableConstraintInfo;
import com.zomatouser.dboperation.dto.TableDescInfoBean;
import com.zomatouser.dboperation.factory.DataBaseNameFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BDCusDAO {

    private final JdbcTemplate jdbcTemplate;
    private final DataBaseNameFactory dataBaseNameFactory;


    @Value("${dbappp.database.name}")
    private String DATABASE_NAME;

    private static final String GET_DB_NAME = "SHOW DATABASES";
    private static final String GET_ALL_TABLES = "SHOW TABLES";
    private static final String SHOW_ALL_DATA_FROM_DB = "SELECT * FROM ";
    private static final String DESCRIBE_TABLE = "DESC ";
    private String GET_CONSTRAINT = "SELECT * FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = ? ";


    public List<DatabaseNameDto> getAllDBNames() {
        List<Map<String, Object>> allDatabaseNames = jdbcTemplate.queryForList(GET_DB_NAME);
        List<DatabaseNameDto> databaseNameDtoList = dataBaseNameFactory.create(allDatabaseNames);
        return databaseNameDtoList;
    }

    public List<String> allTablesNames() {
        List<String> tablesName = new ArrayList<>();
        tablesName = jdbcTemplate.queryForList(GET_ALL_TABLES, String.class);
        return tablesName;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Map<String, List<TableDescInfoBean>>> getTableDescription(List<String> tableNames) {
        List<List<TableDescInfoBean>> allTableDesc = new ArrayList<>();
        List<Map<String, List<TableDescInfoBean>>> allTableDescMapList = new ArrayList<>();

        tableNames.stream().filter(Objects::nonNull).forEach(tablename -> {
            Map<String, List<TableDescInfoBean>> tableDescMap = new HashMap<>();
            tableDescMap.put(tablename, gettableInfo(tablename));
            allTableDescMapList.add(tableDescMap);
        });

        return allTableDescMapList;
    }

    private List<TableDescInfoBean> gettableInfo(String tableName) {
        StringBuilder descTableQuery = new StringBuilder(DESCRIBE_TABLE).append(tableName);
        List<Map<String, Object>> listOfTableData = jdbcTemplate.queryForList(descTableQuery.toString());

        List<TableDescInfoBean> tableDescInfoBeans = new ArrayList<>();
        listOfTableData.forEach(tableInfo -> {
            TableDescInfoBean tableDescInfoBean = new TableDescInfoBean();
            tableDescInfoBean.setField(tableInfo.get("Field") != null ? (String) tableInfo.get("Field") : "");
            tableDescInfoBean.setType(tableInfo.get("Type") != null ? (String) tableInfo.get("Type") : "");
            tableDescInfoBean.setExtra(tableInfo.get("Extra") != null ? (String) tableInfo.get("Extra") : "");
            tableDescInfoBean.setKey(tableInfo.get("Key") != null ? (String) tableInfo.get("Key") : "");
            tableDescInfoBean.setDefaultValue(tableInfo.get("DefaultValue") != null ? (String) tableInfo.get("DefaultValue") : "");
            tableDescInfoBeans.add(tableDescInfoBean);
        });
        return tableDescInfoBeans;
    }

    public List<TableConstraintInfo> getConstraintInfo() {

        if (DATABASE_NAME == null || DATABASE_NAME.isEmpty()) {
            log.error("database name is null or empty");
        }
        List<TableConstraintInfo> constraintInfos = new ArrayList<>();
        if (log.isDebugEnabled()) {
            log.info("fetching database constraints");
        }
        List<Map<String, Object>> constaintList = jdbcTemplate.queryForList(GET_CONSTRAINT, DATABASE_NAME);
        constaintList.stream().forEach(tableConstraintInfo -> {
            TableConstraintInfo constraintInfo = new TableConstraintInfo();
            constraintInfo.setCONSTRAINT_CATALOG(tableConstraintInfo.get("CONSTRAINT_CATALOG") != null ? (String) tableConstraintInfo.get("CONSTRAINT_CATALOG") : "");
            constraintInfo.setCONSTRAINT_SCHEMA(tableConstraintInfo.get("CONSTRAINT_SCHEMA") != null ? (String) tableConstraintInfo.get("CONSTRAINT_SCHEMA") : "");
            constraintInfo.setCONSTRAINT_NAME(tableConstraintInfo.get("CONSTRAINT_NAME") != null ? (String) tableConstraintInfo.get("CONSTRAINT_NAME") : "");
            constraintInfo.setTABLE_CATALOG(tableConstraintInfo.get("TABLE_CATALOG") != null ? (String) tableConstraintInfo.get("TABLE_CATALOG") : "");
            constraintInfo.setTABLE_SCHEMA(tableConstraintInfo.get("TABLE_SCHEMA") != null ? (String) tableConstraintInfo.get("TABLE_SCHEMA") : "");
            constraintInfo.setTABLE_NAME(tableConstraintInfo.get("TABLE_NAME") != null ? (String) tableConstraintInfo.get("TABLE_NAME") : "");
            constraintInfo.setCOLUMN_NAME(tableConstraintInfo.get("COLUMN_NAME") != null ? (String) tableConstraintInfo.get("COLUMN_NAME") : "");
            constraintInfo.setORDINAL_POSITION(tableConstraintInfo.get("ORDINAL_POSITION") != null ? (Long) tableConstraintInfo.get("ORDINAL_POSITION") : null);
            constraintInfo.setPOSITION_IN_UNIQUE_CONSTRAINT(tableConstraintInfo.get("POSITION_IN_UNIQUE_CONSTRAINT") != null ? (Long) tableConstraintInfo.get("POSITION_IN_UNIQUE_CONSTRAINT") : null);
            constraintInfo.setREFERENCED_TABLE_SCHEMA(tableConstraintInfo.get("REFERENCED_TABLE_SCHEMA") != null ? (String) tableConstraintInfo.get("REFERENCED_TABLE_SCHEMA") : "");
            constraintInfo.setREFERENCED_TABLE_NAME(tableConstraintInfo.get("REFERENCED_TABLE_NAME") != null ? (String) tableConstraintInfo.get("REFERENCED_TABLE_NAME") : "");
            constraintInfo.setREFERENCED_COLUMN_NAME(tableConstraintInfo.get("REFERENCED_COLUMN_NAME") != null ? (String) tableConstraintInfo.get("REFERENCED_COLUMN_NAME") : "");
            constraintInfos.add(constraintInfo);
        });
        return constraintInfos;
    }


}

