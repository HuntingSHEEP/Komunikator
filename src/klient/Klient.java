package klient;

import java.io.*;
import java.net.*;

public class Klient
{
   public static final int PORT=50007;
   public static final String HOST = "127.0.0.1";

   public static void main(String[] args) throws IOException
   {
      //TODO: Klient łączy pod wskazany adres  na porcie Z PULI PORTÓW; POWIEDZMY NA POCZĄTEK STAŁA PULA PORTÓW;  PRÓBUJEMY ŁĄCZYĆ SIĘ Po kolei do skutku
       //TODO: zamykanie połączenie od strony klienta za pomocą jednej komendy

      //nawiazanie polaczenia z serwerem
      Socket sock;
      sock=new Socket(HOST,PORT);
      System.out.println("Nawiazalem polaczenie: "+sock);


      //tworzenie wątku nadającego
      NadawajK nadawanie = new NadawajK(sock);
      nadawanie.start();

      //tworzenie watka odbierajacego
      OdbiorK odbieranie = new OdbiorK(sock);
      odbieranie.start();

      //przekazanie referencji do watku
       nadawanie.podajWatekOdbierajacy(odbieranie);

      //zamykanie polaczenia
      //sock.close();
   }
}
