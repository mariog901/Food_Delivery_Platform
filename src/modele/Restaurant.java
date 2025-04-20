package modele;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String nume;
    private Adresa adresa;
    private double rating;
    private String tipBucatarie;
    private Meniu meniu;
    private List<Recenzie>recenzii;
    private List<Comanda> comenzi;

    public Restaurant(String nume, Adresa adresa,String tipBucatarie, String meniu, double rating) {
        this.nume = nume;
        this.adresa = adresa;
        this.tipBucatarie = tipBucatarie;
        this.meniu = new Meniu();
        this.recenzii=new ArrayList<>();
        this.rating = 0;
        this.comenzi=new ArrayList<>();
    }

    public void adaugaRecenzie(Recenzie recenzie){
        recenzii.add(recenzie);
        calculeazaRating();
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Meniu getMeniu(){
        return meniu;
    }

    public void setMeniu(Meniu meniu) {
        this.meniu = meniu;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Recenzie> getRecenzii() {
        return recenzii;
    }
    public List<Comanda>getComenzi() {
        return new ArrayList<>(comenzi);
    }
    public void setRecenzii(List<Recenzie> recenzii) {
        this.recenzii = recenzii;
    }
    public String getTipBucatarie() {
        return tipBucatarie;
    }
    public void setTipBucatarie(String tipBucatarie) {
        this.tipBucatarie = tipBucatarie;
    }
    public void calculeazaRating(){
        if(recenzii.isEmpty()){
            rating = 0;
            return;
        }
        rating=recenzii.stream().mapToDouble(Recenzie::getNota).average().orElse(0);
    }

    public String afiseazaMeniu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Meniu restaurant ").append(nume).append(":\n");
        for (Produs produs : meniu.getProduse()) {
            sb.append("- ").append(produs.getNume())
                    .append(" (").append(produs.getPret()).append(" lei)\n");
        }
        return sb.toString();
    }
    public void adaugaComanda(Comanda comanda){
        this.comenzi.add(comanda);
    }
    public void adaugaProduse(Produs produs){
        this.meniu.adaugaProduse(produs);
    }
    public void adaugaProduse(List<Produs>produse){
        for (Produs produs:produse){
            this.meniu.adaugaProduse(produs);
        }
    }
    public Produs gasesteProdusDupaNume(String nume) {
        if (nume == null || meniu == null) {
            return null;
        }

        for (Produs produs : meniu.getProduse()) {
            if (produs.getNume() != null && produs.getNume().equalsIgnoreCase(nume)) {
                return produs;
            }
        }
        return null;
    }

}

