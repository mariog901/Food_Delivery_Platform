package modele;

import java.util.ArrayList;
import java.util.List;

public class Livrator {
    private String nume;
    private int rating;
    private Vehicul vehicul;
    private boolean status;
    private List<Comanda>comenziLivrate;
    private String telefon;

    public Livrator(String nume,String telefon,Vehicul vehicul){
        this.nume = nume;
        this.telefon = telefon;
        this.vehicul=vehicul;
        this.status=true;
        this.comenziLivrate=new ArrayList<>();

    }

    public void adaugaComanda(Comanda comanda){
        comenziLivrate.add(comanda);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Vehicul getVehicul() {
        return vehicul;
    }

    public void setVehicul(Vehicul vehicul) {
        this.vehicul = vehicul;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Comanda> getComenziLivrate() {
        return new ArrayList<>(comenziLivrate);
    }

    public void setComenziLivrate(List<Comanda> comenziLivrate) {
        this.comenziLivrate = comenziLivrate;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
