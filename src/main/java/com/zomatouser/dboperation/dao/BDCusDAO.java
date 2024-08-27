package com.zomatouser.dboperation.dao;

import com.zomatouser.dboperation.dto.DatabaseNameDto;
import com.zomatouser.dboperation.dto.TableDescInfoBean;
import com.zomatouser.dboperation.factory.DataBaseNameFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

        tableNames.stream().filter(Objects::nonNull).forEach(tablename ->{
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

    public List<String> getTableFieldsInformation(String tableName){
        List<String> fieldNames = new ArrayList<>();
        if (tableName != null && !tableName.isEmpty()){
            StringBuilder sql = new StringBuilder(DESCRIBE_TABLE).append(tableName);
            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql.toString());
            if (!CollectionUtils.isEmpty(result)){
                for(Map<String, Object> resultMap : result){
                   fieldNames.add( resultMap.get ("Field") != null ? (String) resultMap.get("Field") : "");
                }
            }else{
                log.info("NO table present of the given name : {} ", tableName);
            }
        }
        return fieldNames;
    }


}

