package serwer;

import java.io.*;
import java.net.*;

/*
if (server != null && !server.isClosed()) {
        try {
        server.close();
        } catch (IOException e)
        {
        e.printStackTrace(System.err);
        }
        }

        */


public class Serwer
{
   public static final int PORT=50007;

   public static void main(String args[]) throws IOException
   {
      //tworzenie gniazda serwerowego
      ServerSocket serv;
      serv=new ServerSocket(PORT);

      //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
      System.out.println("Nasluchuje: "+serv);
      Socket sock;

      sock=serv.accept();
      System.out.println("Jest polaczenie: "+sock);

      //tworzenie wątku nadającego
      nadaj(sock);

      //tworzenie watka odbierajacego
      odbior(sock);



      //zamykanie polaczenia
      //serv.close();
      //sock.close();
   }

   public static synchronized void odbior(Socket sock){
     try{
        new Odbior(sock).start();
     }catch(Exception e){System.out.println("sync serwer.Odbior");}
   }

   public static synchronized void nadaj(Socket sock){
     try{
        new Nadawaj(sock).start();
     }catch(Exception e){System.out.println("sync Nadaj");}

   }
}
