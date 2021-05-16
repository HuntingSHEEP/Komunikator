package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Baza {
    Connection connection;
    Statement statement;

    public Baza(){
        createConnection();
    }

    private void createConnection() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean closeConnection(){
        try
        {
            if(connection != null)
                connection.close();
            return  true;
        }
        catch(SQLException e)
        {
            // connection close failed.
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean initializeBase(){
        try{
            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean ddl(String sql){
        try{
            statement.executeUpdate(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet dml(String sql){
        ResultSet rs = null;

        try{
            rs = statement.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }




}
