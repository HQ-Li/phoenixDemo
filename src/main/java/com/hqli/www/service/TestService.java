package com.hqli.www.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TestService {
    Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    public Connection connection;

    public List<String> search(String query){
        String sql = "";
        List<String> lstResult = new ArrayList<>();
        try {
            logger.error("Query: " + query);
            if (connection == null) {
                logger.error("Null connection");
                return null;
            }
            if (query == null || query.trim().length() <= 0) {
                query = "";
                sql = "select * from  SYSTEM.CATALOG";
            } else {
                query = "%" + query.toUpperCase() + "%";
                sql = "select * from  SYSTEM.CATALOG WHERE upper(TABLE_NAME) like ? LIMIT ?";
            }

            logger.info("sql:" + sql);
            PreparedStatement ps = connection
                    .prepareStatement(sql);
            if ( query.length() > 1 ) {
                ps.setString(1, query);
                ps.setInt(2, 100);
            }
            ResultSet res = ps.executeQuery();
            int colSize = ps.getMetaData().getColumnCount();
            String tmpColName = "";
            for (int jjj=1; jjj<=colSize; jjj++) {
                tmpColName += ps.getMetaData().getColumnName(jjj) + "  ";
            }
            lstResult.add(tmpColName);
            logger.info("clow name:" + tmpColName);
            logger.info("colSize:" + colSize);
            int iii = 1;
            while (res.next()){

                String colData = "";
                for (int jjj=1; jjj<=colSize; jjj++) {
                    colData += res.getString(jjj);
                }
                lstResult.add(colData);
                iii++;
            }
            logger.info("rowSize:" + iii);
            res.close();
            ps.close();
//            connection.close();
//            res = null;
//            ps = null;
//            connection = null;

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString());
        }
        return lstResult;
    }
}
