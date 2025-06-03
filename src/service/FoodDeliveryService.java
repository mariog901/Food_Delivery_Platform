package service;
import modele.Restaurant;
import modele.Utilizator;
import modele.Livrator;
import modele.Comanda;
import modele.Produs;
import modele.Recenzie;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.RecenzieRepository;
import repository.ComandaRepository;
import repository.RestaurantRepository;

public class FoodDeliveryService {
    private final RecenzieRepository recenzieRepository;
    private final List<Restaurant>restaurante;
    private final List<Utilizator>utilizatori;
    private final List<Livrator> livratori;
    private final List<Comanda>comenzi;
    private final Map<String,List<Restaurant>>restauranteDupaTip;
    private final AuditService auditService;
    private final ComandaRepository comandaRepository;
    public FoodDeliveryService() {
        this.restaurante = new ArrayList<>();
        this.utilizatori = new ArrayList<>();
        this.livratori = new ArrayList<>();
        this.comenzi = new ArrayList<>();
        this.restauranteDupaTip = new HashMap<>();
        this.auditService = AuditService.getInstance();
        this.recenzieRepository = new RecenzieRepository();
        this.comandaRepository = new ComandaRepository();
    }
    public void adaugaRestaurante(Restaurant restaurant) {
        auditService.logAction("adaugaRestaurante");
        restaurante.add(restaurant);
        restauranteDupaTip.computeIfAbsent(restaurant.getTipBucatarie(),k->new ArrayList<>()).add(restaurant);

    }
    public void adaugaUtilizator(Utilizator utilizator) {
        auditService.logAction(("adaugaUtilizator"));
        utilizatori.add(utilizator);
    }
    public void adaugaLivrator(Livrator livrator) {
        auditService.logAction(("adaugaLivrator"));
        livratori.add(livrator);
    }
    public Comanda creeazaComanda(Restaurant restaurant, Utilizator utilizator, List <Produs>produse) {
        Comanda comanda= new Comanda(utilizator,restaurant,produse);
        comenzi.add(comanda);
        utilizator.adaugaComanda(comanda);
        restaurant.adaugaComanda(comanda);
        auditService.logAction("creeazaComanda_IN_MEMORIE");
        return comanda;
    }
    public List<Restaurant> cautaRestauranteDupaTip(String tipBucatarie) {
        auditService.logAction("cautaRestauranteDupaTip");
        return restauranteDupaTip.getOrDefault(tipBucatarie, new ArrayList<>());
    }
    public List<Comanda> getComenziUtilizator(Utilizator utilizator) {
        auditService.logAction("getComenziUtilizator");
        return utilizator.getIstoricComenzi();
    }
    public List<Comanda> getComenziRestaurant(Restaurant restaurant) {
        auditService.logAction("getComenziRestaurant");
        return restaurant.getComenzi();
    }
    public void adaugaRecenzie(Comanda comanda, double nota, String comentariu) {
        auditService.logAction("adaugaRecenzie_INIT");
        if(comanda==null){
            System.err.println("Comanda invalida");
            auditService.logAction("adaugaRecenzie_EROARE");
            return;
        }
        if (comanda.getId() <= 0) {
            System.err.println(
                    "Comanda nu are un ID valid (nu a fost salvata in DB). Recenzia nu poate fi adaugata."
            );
            auditService.logAction(
                    "adaugaRecenzie_EROARE_COMANDA_ID_INVALID"
            );
            return;
        }
        Recenzie recenzie = new Recenzie(
                nota,
                comentariu,
                comanda.getUtilizator(),
                comanda.getId()
        );


        Optional<Recenzie> recenzieSalvataOpt =
                recenzieRepository.create(recenzie);

        if (recenzieSalvataOpt.isPresent()) {
            Recenzie recenzieSalvata = recenzieSalvataOpt.get(); // Acum are ID din DB

            // 2. Asociaza recenzia (cu ID-ul ei din DB) la obiectul Comanda din memorie
            //    si actualizeaza rating-ul restaurantului.
            comanda.adaugaRecenzie(recenzieSalvata);
            actualizeazaRatingRestaurantInDB(comanda.getRestaurant());
            auditService.logAction("adaugaRecenzie_SUCCES");
            System.out.println(
                    "Recenzie adaugata si salvata pentru comanda #" +
                            comanda.getId()
            );
        } else {
            auditService.logAction("adaugaRecenzie_EROARE_SALVARE_DB");
            System.err.println(
                    "Eroare la salvarea recenziei in baza de date pentru comanda #" +
                            comanda.getId()
            );
        }
    }
    public List<Restaurant> getRestauranteSortateDupaRating() {
        List<Restaurant> sortate = new ArrayList<>(restaurante);
        sortate.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
        auditService.logAction("getRestauranteSortateDupaRating");
        return sortate;
    }
    public void asigneazaLivrator(Comanda comanda, Livrator livrator) {
        livrator.adaugaComanda(comanda);
        comanda.actualizeazaStatus("in curs de livrare");
        auditService.logAction("asigneazaLivrator");
    }
    public void afiseazaComenziUtilizator(int idUtilizator) {
        auditService.logAction("afiseazaComenziUtilizator");
        Utilizator utilizator = utilizatori.stream()
                .filter(u -> u.getId() == idUtilizator)
                .findFirst()
                .orElse(null);

        if (utilizator == null) {
            System.out.println("Utilizatorul cu ID " + idUtilizator + " nu a fost gasit!");
            auditService.logAction("afiseazaComenziUtilizator_EROARE_UTILIZATOR_NEGASIT");
            return;
        }

        List<Comanda> comenzi = utilizator.getIstoricComenzi();
        if (comenzi.isEmpty()) {
            System.out.println("Utilizatorul " + utilizator.getNume() + " nu are comenzi!");
            auditService.logAction(
                    "afiseazaComenziUtilizator_FARA_COMENZI"
            );
            return;
        }

        System.out.println("Comenzi pentru " + utilizator.getNume() + ":");
        for (Comanda comanda : comenzi) {
            System.out.println("- Comanda #" + comanda.getId() +
                    ", Restaurant: " + (comanda.getRestaurant()!=null ? comanda.getRestaurant().getNume(): "N/A" )+
                    ", Total: " + comanda.getCost() + " lei" +
                    ", Status: " + comanda.getStatus());
        }
        auditService.logAction("afiseazaComenziUtilizator_SUCCES");
    }


    public void afiseazaComenziRestaurant(String numeRestaurant) {
        auditService.logAction("afiseazaComenziRestaurant_INIT");
        Restaurant restaurant = restaurante.stream()
                .filter(r -> r.getNume().equalsIgnoreCase(numeRestaurant))
                .findFirst()
                .orElse(null);

        if (restaurant == null) {
            System.out.println("Restaurantul \"" + numeRestaurant + "\" nu a fost gasit!");
            auditService.logAction(
                    "afiseazaComenziRestaurant_EROARE_RESTAURANT_NEGASIT"
            );
            return;
        }

        List<Comanda> comenzi = restaurant.getComenzi();
        if (comenzi.isEmpty()) {
            System.out.println("Restaurantul " + restaurant.getNume() + " nu are comenzi!");
            auditService.logAction("afiseazaComenziRestaurant_FARA_COMENZI");
            return;
        }

        System.out.println("Comenzi pentru restaurantul " + restaurant.getNume() + ":");
        for (Comanda comanda : comenzi) {
            System.out.println("- Comanda #" + comanda.getId() +
                    ", Client: " + (comanda.getUtilizator()!=null ? comanda.getUtilizator().getNume() : "N/A") +
                    ", Total: " + comanda.getCost() + " lei" +
                    ", Status: " + comanda.getStatus());
        }
        auditService.logAction("afiseazaComenziRestaurant_SUCCES");
    }

    public void actualizeazaRatingRestaurantInDB(Restaurant restaurant) {
        try {
            RestaurantRepository restaurantRepository = new RestaurantRepository();
            restaurantRepository.update(restaurant);
            auditService.logAction("actualizeazaRatingRestaurant_" + restaurant.getId());
        } catch (Exception e) {
            System.err.println("Eroare la actualizarea rating-ului in DB: " + e.getMessage());
            auditService.logAction("actualizeazaRatingRestaurant_EROARE");
        }
    }
}