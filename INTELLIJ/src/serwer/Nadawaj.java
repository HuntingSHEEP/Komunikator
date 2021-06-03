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
    String bufor;

    public void podajWatekOdbierajacy(Odbior odbior){
        this.watekOdbierajacy = odbior;
    }

    public Nadawaj(Socket sock) throws IOException
    {
        this.sock=sock;
        this.klaw=new BufferedReader(new InputStreamReader(System.in));
        this.outp=new PrintWriter(sock.getOutputStream());
        this.is = System.in;
        this.bufor = "";
    }

    public void run()
    {
        try{
            /*
            while (isRunning){

                //byte[] inputData = new byte[1024];
                //int result = is.read(inputData, 0, is.available());

                int result = bufor.length();
                //System.out.println("Bufor ["+bufor+"] "+ result);
                //System.out.println(bufor+"; "+bufor.length());
                if(bufor.length() > 0){
                    System.out.println(bufor+"; "+bufor.length());
                }

                if (result > 0){
                    //str = new String(inputData);
                    //str = str.substring(0, result - 1);
                    str = bufor;
                    //bufor = "";

                    System.out.println("<Wysyłamy:> result [" + result + "] " + str);

                    if(str.equalsIgnoreCase("exit")){
                        outp.println(str);
                        outp.flush();

                        killOdbior();
                        System.out.println("NEXIT");
                        killOdbior();
                        break;
                    }

                    outp.println(str);
                    outp.flush();
                }
                sleep(100);

            }

             */
        }catch(Exception e){System.out.println("Yolooooo XD. ");
        e.printStackTrace();}
    }

    private void killOdbior() {
        watekOdbierajacy.killME();
    }

    public void killME(){
        isRunning = false;
    }

    public void sendMessage(String message) {
        this.bufor = message;
        outp.println(this.bufor);
        outp.flush();
        //System.out.println("wyslano: "+message+";length: "+this.bufor.length());
    }
}
