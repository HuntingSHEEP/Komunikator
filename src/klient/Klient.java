package klient;

import java.io.*;
import java.net.*;

public class Klient
{
   public static final int PORT=50007;
   public static final String HOST = "127.0.0.1";

   public static void main(String[] args) throws IOException
   {
      //nawiazanie polaczenia z serwerem
      Socket sock;
      sock=new Socket(HOST,PORT);
      System.out.println("Nawiazalem polaczenie: "+sock);


      //tworzenie wątku nadającego
      new NadawajK(sock).start();
      //tworzenie watka odbierajacego
      new OdbiorK(sock).start();

      //zamykanie polaczenia
      //sock.close();
   }
}
