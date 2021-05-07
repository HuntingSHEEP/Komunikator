package klient;

import java.io.*;
import java.net.Socket;

class NadawajK extends Thread
{
    Socket sock;
    BufferedReader klaw;
    PrintWriter outp;
    String str;
    OdbiorK watekOdbierajacy;
    InputStream is;


    public NadawajK(Socket sock) throws IOException
    {
        this.sock=sock;
        this.klaw=new BufferedReader(new InputStreamReader(System.in));
        this.outp=new PrintWriter(sock.getOutputStream());
    }

    public void podajWatekOdbierajacy(OdbiorK odbior){
        this.watekOdbierajacy = odbior;
    }

    public void run()
    {
        try{
            while (true){
                System.out.println("<Wysylamy:> ");
                byte[] inputData = new byte[1024];
                int result = is.read(inputData, 0, is.available());
                str = new String(inputData).substring(0, result - 1);

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
        }catch(Exception e){System.out.println("Yolooooo XD. "+e);}
        
    }

    private void killOdbior() {
        watekOdbierajacy.killME();
    }
}
