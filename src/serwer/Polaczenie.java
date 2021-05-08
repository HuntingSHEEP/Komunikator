package serwer;

import java.io.IOException;
import java.net.Socket;

public class Polaczenie {
    private Nadawaj watekNadajacy;
    private Odbior watekOdbierajacy;
    private boolean isActive;

    Polaczenie(Nadawaj watekNadajacy, Odbior watekOdbierajacy){
        this.watekNadajacy = watekNadajacy;
        this.watekOdbierajacy = watekOdbierajacy;
        this.isActive = true;
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
    }

    public boolean isActive(){
        return isActive;
    }
}
