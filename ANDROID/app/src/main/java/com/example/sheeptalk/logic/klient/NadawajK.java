package com.example.sheeptalk.logic.klient;

import java.io.*;
import java.net.Socket;

public class NadawajK extends Thread
{
    Socket sock;
    BufferedReader klaw;
    PrintWriter outp;
    String str;
    OdbiorK watekOdbierajacy;
    InputStream is;
    boolean isRunning = true;

    public NadawajK(Socket sock) throws IOException
    {
        this.sock=sock;
        this.klaw=new BufferedReader(new InputStreamReader(System.in));
        this.outp=new PrintWriter(sock.getOutputStream());
        this.is = System.in;
    }

    public void podajWatekOdbierajacy(OdbiorK odbior){
        this.watekOdbierajacy = odbior;
    }

    public void run()
    {
        try{
            while (isRunning){

                //System.out.println("<Wysylamy:> ");
                //str=klaw.readLine();

                byte[] inputData = new byte[1024];
                int result = is.read(inputData, 0, is.available());

                if(result >0){
                    str = new String(inputData);
                    str = str.substring(0, result - 1);
                    System.out.println("<Wysyłamy:> result [" + result + "] " + str);

                    if(str.equalsIgnoreCase("exit")){
                        outp.println(str);
                        outp.flush();

                        killOdbior();
                        System.out.println("NEXIT");
                        break;
                    }

                    outp.println(str);
                    outp.flush();
                }



            }
        }catch(Exception e){System.out.println("Yolooooo XD. "+e);
        e.printStackTrace();}
        
    }

    private void killOdbior() {
        watekOdbierajacy.killME();
    }

    public void killME(){
        isRunning = false;
    }
}
