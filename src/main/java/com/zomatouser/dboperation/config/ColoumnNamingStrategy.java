package com.zomatouser.dboperation.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class ColoumnNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
//        return super.toPhysicalTableName(logicalName, context);
        if (logicalName == null){
            return null;
        }

        final String newName  = logicalName.getText();
        return Identifier.toIdentifier(newName);
    }
}

