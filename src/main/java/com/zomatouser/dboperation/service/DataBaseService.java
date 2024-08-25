package com.zomatouser.dboperation.service;

import com.zomatouser.dboperation.dao.BDCusDAO;
import com.zomatouser.dboperation.dto.DatabaseNameDto;
import com.zomatouser.dboperation.dto.TableDescInfoBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataBaseService {

    private final BDCusDAO dbCustomDao;

    public List<DatabaseNameDto> getAllDBNames(){
        List<DatabaseNameDto> allDBNames = dbCustomDao.getAllDBNames();
        return allDBNames;
    }

    public List<String> getAllTableNames(){
        return dbCustomDao.allTablesNames();
    }

    public List<Map<String, List<TableDescInfoBean>>> getTableInformation(){
        List<String> allTableNames = getAllTableNames();
        if (!allTableNames.isEmpty()){
            return dbCustomDao.getTableDescription(allTableNames);
        }else{
            log.error("No Table found the dataBase");
        }
        return null;
    }
}
