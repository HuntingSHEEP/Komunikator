package serwer;

import baza.Baza;

import java.io.*;
import java.net.*;
import java.security.spec.ECField;
import java.sql.ResultSet;


public class SerwerMain
{

   public static void main(String args[]) throws IOException
   {
      //Serwer serwer = new Serwer();

      Baza baza = new Baza();
      baza.initializeBase();
      baza.ddl("insert into person values(1, 'Krzys')");
      baza.ddl("insert into person values(2, 'Natalia')");
      baza.ddl("insert into person values(3, 'Owca')");
      baza.ddl("insert into person values(4, 'COOOOOO')");

      ResultSet wynikZapytania = baza.dml("select * from person");
      try{
         while(wynikZapytania.next()){
            System.out.println("name = " + wynikZapytania.getString("name") + "; id = " + wynikZapytania.getInt("id"));
         }
      }catch (Exception e){}

      baza.closeConnection();
   }

}
