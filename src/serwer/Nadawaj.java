package serwer;



import java.io.*;
import java.net.Socket;

class Nadawaj extends Thread
{
    Socket sock;
    BufferedReader klaw;
    PrintWriter outp;
    String str;
    Odbior watekOdbierajacy;
    boolean isRunning = true;
    InputStream is;

    public void podajWatekOdbierajacy(Odbior odbior){
        this.watekOdbierajacy = odbior;
    }

    public Nadawaj(Socket sock) throws IOException
    {
        this.sock=sock;
        this.klaw=new BufferedReader(new InputStreamReader(System.in));
        this.outp=new PrintWriter(sock.getOutputStream());
        this.is = System.in;

    }

    public void run()
    {
        try{
            while (isRunning){

                //System.out.println("<Wysylamy:> ");
                //str=klaw.readLine();

                byte[] inputData = new byte[1024];
                int result = is.read(inputData, 0, is.available());

                if (result > 0){
                    str = new String(inputData);
                    str = str.substring(0, result - 1);
                    System.out.println("<Wysyłamy:> result [" + result + "] " + str);

                    if(str.equalsIgnoreCase("exit")){
                        outp.println(str);
                        outp.flush();

                        System.out.println("NEXIT");
                        break;
                    }

                    outp.println(str);
                    outp.flush();
                }


            }
        }catch(Exception e){System.out.println("Yolooooo XD. ");
        e.printStackTrace();}




    }

    private void killOdbior() {
        watekOdbierajacy.killME();
    }

    public void killME(){
        isRunning = false;
    }
}
