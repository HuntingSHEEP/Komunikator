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
      Serwer serwer = new Serwer();

      Baza baza = new Baza();

      /*
      baza.initializeBase();
      baza.ddl("insert into klienci values(0, 'Krzys', '123')");
      baza.ddl("insert into klienci values(1, 'Owca', '123')");
      baza.ddl("insert into klienci values(2, 'Baran', '123')");
      baza.ddl("insert into klienci values(3, 'Ciele', '123')");
       */

      ResultSet wynikZapytania = baza.dml("select * from klienci");
      try{
         while(wynikZapytania.next()){
            System.out.println("name = " + wynikZapytania.getString("name") + "; id = " + wynikZapytania.getInt("id") + "; pass = " + wynikZapytania.getString("pass"));
         }
      }catch (Exception e){}

      baza.closeConnection();
   }

}
