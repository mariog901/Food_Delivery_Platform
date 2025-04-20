package modele;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
public class Comanda {
    private static int nextId=1;

    private int Id;
    private Utilizator utilizator;
    private Restaurant restaurant;
    private double cost;
    private Recenzie recenzie;
    private List<Produs> produse;
    private String status;
    private LocalDateTime dataPlasare;
    private LocalDateTime dataLivrare;

    public Comanda(Utilizator utilizator, Restaurant restaurant,List<Produs>produse) {
        this.Id=nextId++;
        this.utilizator=utilizator;
        this.restaurant=restaurant;
        this.produse=new ArrayList<>(produse);
        this.cost=produse.stream().mapToDouble(Produs::getPret).sum();
        this.status="plasata";
        this.dataPlasare=LocalDateTime.now();
    }

    public void adaugaRecenzie(Recenzie recenzie){
        this.recenzie=recenzie;
        restaurant.adaugaRecenzie(recenzie);
    }
    public void actualizeazaStatus(String status){
        this.status=status;
        if(status.equals("plasata")){
            this.dataPlasare=LocalDateTime.now();
        }
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Comanda.nextId = nextId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Recenzie getRecenzie() {
        return recenzie;
    }

    public void setRecenzie(Recenzie recenzie) {
        this.recenzie = recenzie;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPlasare() {
        return dataPlasare;
    }

    public void setDataPlasare(LocalDateTime dataPlasare) {
        this.dataPlasare = dataPlasare;
    }

    public LocalDateTime getDataLivrare() {
        return dataLivrare;
    }

    public void setDataLivrare(LocalDateTime dataLivrare) {
        this.dataLivrare = dataLivrare;
    }
}
