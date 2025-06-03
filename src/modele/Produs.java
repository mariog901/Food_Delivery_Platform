package modele;
import repository.HasId;
public class Produs implements HasId{
    private int id;
    private String nume;
    private double pret;
    private String descriere;
    private int restaurantId;

    public Produs(int id,String nume, double pret, String descriere,int restaurantId) {
        this.id=id;
        this.nume=nume;
        this.pret=pret;
        this.descriere=descriere;
        this.restaurantId = restaurantId;
    }
    public Produs(String nume, double pret, String descriere,int restaurantId){
        this.nume = nume;
        this.pret = pret;
        this.descriere = descriere;
        this.restaurantId = restaurantId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public int getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
