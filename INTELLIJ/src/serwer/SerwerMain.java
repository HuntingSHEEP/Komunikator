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
      Baza baza = new Baza();
      Serwer serwer = new Serwer(baza);
      //testBazy();
   }

   private static void testBazy(){
      Baza baza = new Baza();

      //baza.initializeBase();
      //przykładowe dane KLIENTOW
      baza.ddl("insert into KLIENT values(0, 'A', '123')");
      baza.ddl("insert into KLIENT values(1, 'B', '123')");
      baza.ddl("insert into KLIENT values(2, 'C', '123')");
      baza.ddl("insert into KLIENT values(3, 'D', '123')");
      //przykładowe dane POKOI
      baza.ddl("insert into POKOJ values (0, 'X0')");
      baza.ddl("insert into POKOJ values (1, 'X1')");
      baza.ddl("insert into POKOJ values (2, 'X2')");
      //przykładowe dane KLIENT_POKOJ
      //pokój X0
      baza.ddl("insert into KLIENT_POKOJ values (0, 0)");
      baza.ddl("insert into KLIENT_POKOJ values (1, 0)");
      baza.ddl("insert into KLIENT_POKOJ values (2, 0)");
      //pokój X1
      baza.ddl("insert into KLIENT_POKOJ values (0, 1)");
      baza.ddl("insert into KLIENT_POKOJ values (3, 1)");
      //pokój X1
      baza.ddl("insert into KLIENT_POKOJ values (0, 2)");
      baza.ddl("insert into KLIENT_POKOJ values (2, 2)");
      //przykładowe dane ROZMOWA X0
      baza.ddl("insert into ROZMOWA values (0, 0, 0, '2021-06-03 20:30:01', 'no siema')");
      baza.ddl("insert into ROZMOWA values (1, 0, 2, '2021-06-03 20:32:31', 'ELOOO')");
      baza.ddl("insert into ROZMOWA values (2, 0, 1, '2021-06-03 20:35:31', 'BEEEEE')");



      ResultSet wynikZapytania = baza.dml("select IMIE, ID from KLIENT");
      try{
         System.out.println("------- TABELA KLIENT -------");
         while(wynikZapytania.next()){
            System.out.println("IMIE = " + wynikZapytania.getString("IMIE") + "; ID = " +
                    wynikZapytania.getInt("ID"));
         }
      }catch (Exception e){}

      wynikZapytania = baza.dml("select ID, NAZWA from POKOJ");
      try{
         System.out.println("\n------- TABELA POKOJ -------");
         while(wynikZapytania.next()){
            System.out.println("NAZWA = " + wynikZapytania.getString("NAZWA") + "; id = " +
                    wynikZapytania.getInt("id"));
         }
      }catch (Exception e){}

      wynikZapytania = baza.dml("select k.IMIE, p.NAZWA from KLIENT_POKOJ kp, KLIENT k, POKOJ p " +
              "WHERE kp.ID_KLIENT = k.ID AND kp.ID_POKOJ = p.ID ");
      try{
         System.out.println("\n------- TABELA KLIENT_POKOJ -------");
         while(wynikZapytania.next()){
            System.out.println("POKOJ : " + wynikZapytania.getString("NAZWA") + "; KLIENT = " +
                    wynikZapytania.getString("IMIE"));
         }
      }catch (Exception e){}

      wynikZapytania = baza.dml("select ROWID, ID_POKOJU, ID_UCZESTNIKA, DATA, TRESC from ROZMOWA");
      try{
         System.out.println("\n------- TABELA ROZMOWA -------");
         while(wynikZapytania.next()){
            System.out.println("ROWID = " + wynikZapytania.getInt("ROWID") +
                    "; ID_POKOJU = " + wynikZapytania.getInt("ID_POKOJU") +
                    "; ID_UCZESTNIKA: " + wynikZapytania.getString("ID_UCZESTNIKA") +
                    "; DATA: " + wynikZapytania.getString("DATA")+
                    "; TREŚĆ: " + wynikZapytania.getString("TRESC")
            );
         }
      }catch (Exception e){}

      baza.closeConnection();
   }

}
