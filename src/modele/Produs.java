package modele;

public class Produs {
    private String nume;
    private double pret;
    private String descriere;

    public Produs(String nume, double pret, String descriere){
        this.nume=nume;
        this.pret=pret;
        this.descriere=descriere;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
