package serwer;

public class Polaczenie {
    private Nadawaj watekNadajacy;
    private Odbior watekOdbierajacy;

    Polaczenie(Nadawaj watekNadajacy, Odbior watekOdbierajacy){
        this.watekNadajacy = watekNadajacy;
        this.watekOdbierajacy = watekOdbierajacy;
    }
}
