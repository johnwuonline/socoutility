package com.soco.dbconnect;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.soco.log.Log;

public class dbconnect {
    /** Start test
     * @param args none expected.
     */
    public static void main(String[] args) {
        dbconnect con = new dbconnect();
        con.checkAndCreateTable();
    }
    
    /*
     * executeSQL
     * */
    public void exectuteSQL(String sql){
        Connection connection = null;
        Statement statement = null;
 
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) 
                try { 
                    statement.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null) 
                try { 
                    connection.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    
    /*
     * executeSQL
     * */
    public HeartBeatTable exectuteQuery(String sql){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        HeartBeatTable hb = new HeartBeatTable();
 
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) 
                try { 
                    statement.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null) 
                try { 
                    connection.close(); 
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return hb;
    }
    
    /*
     * check and create the heartbeat table
     * */
    public void checkAndCreateTable(){
        String table_name = "heartbeat";
        String query_table_sql = "show tables;";
        String create_table_sql = "create table heartbeat(id bigint not null, " +
                            		"user_id bigint, " +
                            		"user_address varchar(20), " +
                            		"update_time varchar(20));";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DataSource.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query_table_sql);
            boolean is_exist = false;
            while(resultSet.next()){
                String tableName = resultSet.getString(1);
                Log.log("table:"+tableName);
                if(tableName.equals(table_name)){
                    is_exist = true;
                    break;
                }
            }
            if(!is_exist){
                this.exectuteSQL(create_table_sql);
                Log.log("Create table:"+table_name+" successfully.");
            }else{
                Log.log("Create table:"+table_name+" existent.");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
