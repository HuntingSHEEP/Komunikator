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
        String[] komendy = new String[10];
        komendy[0] = "create table klienci (id integer not null primary key, name string, pass string)";
        komendy[1] = "create table rozmowy (id string not null primary key, name string, idkl0 integer, foreign key (idkl0) references klienci (id))";

        String[] dropTables = new String[10];
        dropTables[0] = "drop table if exists rozmowy";
        dropTables[1] = "drop table if exists klienci";



        try{
            for(int i=0; i<dropTables.length; i++){
                if(dropTables[i] != null){
                    statement.executeUpdate(dropTables[i]);
                }
            }

            for(int i=0; i<komendy.length; i++){
                if(komendy[i] != null){
                    statement.executeUpdate(komendy[i]);
                }
            }
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
