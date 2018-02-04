package com.huawei.www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Statement statement = HiveClientUtil.getStatement();

        String sql="select * from student";
        ResultSet result = statement.executeQuery(sql);
        while (result.next()){
            String id = result.getString(1);
            String name = result.getString(2);
            System.out.println(id+":"+name);
        }
    }
}
