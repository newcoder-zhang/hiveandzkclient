package com.huawei.www;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveClientUtil {
    public static Statement getStatement(){
        String hostname="jdbc:hive2://beifeng";
        String name="root";
        String pwd="123456";
        String dbname="db14";
        String port="10000";
        String url=hostname+":"+port+"/"+dbname;
        try {
            return DriverManager.getConnection(url,name,pwd).createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
