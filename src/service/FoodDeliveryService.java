package service;
import modele.Restaurant;
import modele.Utilizator;
import modele.Livrator;
import modele.Comanda;
import modele.Produs;
import modele.Recenzie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDeliveryService {
    private List<Restaurant>restaurante;
    private List<Utilizator>utilizatori;
    private List<Livrator> livratori;
    private List<Comanda>comenzi;
    private Map<String,List<Restaurant>>restauranteDupaTip;

    public FoodDeliveryService() {
        this.restaurante = new ArrayList<>();
        this.utilizatori = new ArrayList<>();
        this.livratori = new ArrayList<>();
        this.comenzi = new ArrayList<>();
        this.restauranteDupaTip = new HashMap<>();
    }
    public void adaugaRestaurante(Restaurant restaurant) {
        restaurante.add(restaurant);
        restauranteDupaTip.computeIfAbsent(restaurant.getTipBucatarie(),k->new ArrayList<>()).add(restaurant);

    }
    public void adaugaUtilizator(Utilizator utilizator) {
        utilizatori.add(utilizator);
    }
    public void adaugaLivrator(Livrator livrator) {
        livratori.add(livrator);
    }
    public Comanda creeazaComanda(Restaurant restaurant, Utilizator utilizator, List <Produs>produse) {
        Comanda comanda= new Comanda(utilizator,restaurant,produse);
        comenzi.add(comanda);
        utilizator.adaugaComanda(comanda);
        restaurant.adaugaComanda(comanda);
        return comanda;
    }
    public List<Restaurant> cautaRestauranteDupaTip(String tipBucatarie) {
        return restauranteDupaTip.getOrDefault(tipBucatarie, new ArrayList<>());
    }
    public List<Comanda> getComenziUtilizator(Utilizator utilizator) {
        return utilizator.getIstoricComenzi();
    }
    public List<Comanda> getComenziRestaurant(Restaurant restaurant) {
        return restaurant.getComenzi();
    }
    public void adaugaRecenzie(Comanda comanda, double nota, String comentariu) {
        Recenzie recenzie = new Recenzie(nota, comentariu, comanda.getUtilizator());
        comanda.adaugaRecenzie(recenzie);
    }
    public List<Restaurant> getRestauranteSortateDupaRating() {
        List<Restaurant> sortate = new ArrayList<>(restaurante);
        sortate.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
        return sortate;
    }
    public void asigneazaLivrator(Comanda comanda, Livrator livrator) {
        livrator.adaugaComanda(comanda);
        comanda.actualizeazaStatus("in curs de livrare");
    }
    public void afiseazaComenziUtilizator(int idUtilizator) {
        Utilizator utilizator = utilizatori.stream()
                .filter(u -> u.getIdClient() == idUtilizator)
                .findFirst()
                .orElse(null);

        if (utilizator == null) {
            System.out.println("Utilizatorul cu ID " + idUtilizator + " nu a fost gasit!");
            return;
        }

        List<Comanda> comenzi = utilizator.getIstoricComenzi();
        if (comenzi.isEmpty()) {
            System.out.println("Utilizatorul " + utilizator.getNume() + " nu are comenzi!");
            return;
        }

        System.out.println("Comenzi pentru " + utilizator.getNume() + ":");
        for (Comanda comanda : comenzi) {
            System.out.println("- Comanda #" + comanda.getId() +
                    ", Restaurant: " + comanda.getRestaurant().getNume() +
                    ", Total: " + comanda.getCost() + " lei" +
                    ", Status: " + comanda.getStatus());
        }
    }

    /**
     * afiseaza toate comenzile unui restaurant
     * @param numeRestaurant Numele restaurantului
     */
    public void afiseazaComenziRestaurant(String numeRestaurant) {
        Restaurant restaurant = restaurante.stream()
                .filter(r -> r.getNume().equalsIgnoreCase(numeRestaurant))
                .findFirst()
                .orElse(null);

        if (restaurant == null) {
            System.out.println("Restaurantul \"" + numeRestaurant + "\" nu a fost gasit!");
            return;
        }

        List<Comanda> comenzi = restaurant.getComenzi();
        if (comenzi.isEmpty()) {
            System.out.println("Restaurantul " + restaurant.getNume() + " nu are comenzi!");
            return;
        }

        System.out.println("Comenzi pentru restaurantul " + restaurant.getNume() + ":");
        for (Comanda comanda : comenzi) {
            System.out.println("- Comanda #" + comanda.getId() +
                    ", Client: " + comanda.getUtilizator().getNume() +
                    ", Total: " + comanda.getCost() + " lei" +
                    ", Status: " + comanda.getStatus());
        }
    }
}

