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
        komendy[0] = "CREATE TABLE KLIENT (" +
                "ID INTEGER NOT NULL PRIMARY KEY, " +
                "IMIE STRING, " +
                "PASS STRING)";

        komendy[1] = "CREATE TABLE POKOJ (" +
                "ID INTEGER PRIMARY KEY," +
                "NAZWA STRING)";

        komendy[2] = "CREATE TABLE KLIENT_POKOJ ("+
                "ID_KLIENT INTEGER, " +
                "ID_POKOJ INTEGER, " +
                "PRIMARY KEY (ID_KLIENT, ID_POKOJ), " +
                "FOREIGN KEY (ID_KLIENT) REFERENCES KLIENT (ID), " +
                "FOREIGN KEY (ID_POKOJ) REFERENCES POKOJ (ID) )";

        komendy[3] = "CREATE TABLE ROZMOWA (" +
                "ROWID INTEGER, " +
                "ID_POKOJU INTEGER, " +
                "ID_UCZESTNIKA INTEGER, " +
                "DATA DATE, " +
                "TRESC STRING, " +
                "PRIMARY KEY (ROWID), " +
                "FOREIGN KEY (ID_POKOJU) REFERENCES POKOJ (ID), " +
                "FOREIGN KEY (ID_UCZESTNIKA) REFERENCES KLIENT (ID) )";


        String[] dropTables = new String[10];

        dropTables[3] = "drop table if exists KLIENT";
        dropTables[2] = "drop table if exists POKOJ";
        dropTables[1] = "drop table if exists KLIENT_POKOJ";
        dropTables[0] = "drop table if exists ROZMOWA";



        try{
            for(int i=0; i<dropTables.length; i++){
                if(dropTables[i] != null){
                    statement.executeUpdate(dropTables[i]);
                }
            }

            for(int i=0; i<komendy.length; i++){
                if(komendy[i] != null){
                    statement.executeUpdate(komendy[i]);
                    //System.out.println(komendy[i]);
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
