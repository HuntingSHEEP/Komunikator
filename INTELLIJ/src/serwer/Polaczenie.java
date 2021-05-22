package serwer;

import java.io.IOException;
import java.net.Socket;

public class Polaczenie {
    private Nadawaj watekNadajacy;
    private Odbior watekOdbierajacy;
    private boolean isActive;
    private int ID;
    private String NAZWA;

    Polaczenie(Nadawaj watekNadajacy, Odbior watekOdbierajacy){
        this.watekNadajacy = watekNadajacy;
        this.watekOdbierajacy = watekOdbierajacy;
        this.isActive = true;
        this.ID = -1;
        this.NAZWA = null;
    }

    Polaczenie(Socket sock) throws IOException {
        //tworzenie wątku nadającego
        Nadawaj watekNadajacy = new Nadawaj(sock);
        watekNadajacy.start();

        //tworzenie watka odbierajacego
        Odbior watekOdbierajacy = new Odbior(sock);
        watekOdbierajacy.start();

        //wzajemne przekazanie referancji
        watekOdbierajacy.setWatekNadajacy(watekNadajacy);
        watekNadajacy.podajWatekOdbierajacy(watekOdbierajacy);

        this.watekNadajacy = watekNadajacy;
        this.watekOdbierajacy = watekOdbierajacy;
        this.isActive = true;
        this.ID = -1;
        this.NAZWA = null;
    }

    public void setID(int id){
        this.ID = id;
    }

    public int getID(){
        return ID;
    }

    public void setNAZWA(String name){
        this.NAZWA = name;
    }

    public String getNAZWA(){
        return NAZWA;
    }

    public boolean isActive(){
        return isActive;
    }

    public Nadawaj getWatekNadajacy(){
        return  watekNadajacy;
    }

    public Odbior getWatekOdbierajacy(){
        return watekOdbierajacy;
    }


    public boolean newMessage() {
        return watekOdbierajacy.newMessage();
    }

    public String getMessage() {
        return watekOdbierajacy.getMessage();
    }

    public void sendMessage(String message) {
        watekNadajacy.sendMessage(message);
    }
}
