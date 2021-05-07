package serwer;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Nadawaj extends Thread
{
    Socket sock;
    BufferedReader klaw;
    PrintWriter outp;
    String str;
    Odbior watekOdbierajacy;
    boolean isRunning = true;

    public void podajWatekOdbierajacy(Odbior odbior){
        this.watekOdbierajacy = odbior;
    }

    public Nadawaj(Socket sock) throws IOException
    {
        this.sock=sock;
        this.klaw=new BufferedReader(new InputStreamReader(System.in));
        this.outp=new PrintWriter(sock.getOutputStream());
    }

    public void run()
    {
        try{
            while (isRunning){
                System.out.println("<Wysylamy:> ");
                str=klaw.readLine();

                if(str.equalsIgnoreCase("exit")){
                    outp.println(str);
                    outp.flush();

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

    public void killME(){
        isRunning = false;
    }
}
