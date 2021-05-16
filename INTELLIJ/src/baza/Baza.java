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

        try
        {
            // create a database connection



            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");

            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name") + "; id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }


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

    private boolean initializeBase(){
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
            rs = statement.executeQuery("select * from person");
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }




}
