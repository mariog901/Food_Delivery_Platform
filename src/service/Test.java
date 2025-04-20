package service;
import modele.Restaurant;
import modele.Adresa;
import modele.Comanda;
import modele.Livrator;
import modele.Masina;
import modele.Utilizator;
import modele.Produs;
import modele.Vehicul;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        FoodDeliveryService service = new FoodDeliveryService();

        // 1. Initializare date de test
        System.out.println("=== Initializare date de test ===");

        // Creare adrese
        Adresa adresaRestaurant1 = new Adresa("Str. Mihai Eminescu", "10", "Bucuresti", "010101");
        Adresa adresaRestaurant2 = new Adresa("Bd. Unirii", "25", "Bucuresti", "010102");
        Adresa adresaUtilizator1 = new Adresa("Str. George Enescu", "15", "Bucuresti", "010103");
        Adresa adresaUtilizator2 = new Adresa("Str. Tudor Arghezi", "7", "Cluj-Napoca", "400000");

        // Adaugare restaurante
        Restaurant restaurantItalian = new Restaurant("Pizza Delight", adresaRestaurant1, "italiana", "meniu pizza", 0);
        Restaurant restaurantAsian = new Restaurant("Wok Master", adresaRestaurant2, "asiatica", "meniu asiatic", 0);

        service.adaugaRestaurante(restaurantItalian);
        service.adaugaRestaurante(restaurantAsian);

        // Adaugare produse in meniuri
        System.out.println("\n=== Adaugare produse in meniuri ===");

        List<Produs> produseItaliene = Arrays.asList(
                new Produs("Pizza Margherita", 30.0, "Pizza cu sos de rosii si mozzarella"),
                new Produs("Pizza Quattro Formaggi", 35.0, "Pizza cu 4 tipuri de branza"),
                new Produs("Paste Carbonara", 25.0, "Paste cu sos carbonara"),
                new Produs("Pizza Casei",40.0,"Pizza noastra faimoasa")
        );

        List<Produs> produseAsiatice = Arrays.asList(
                new Produs("Sushi Sake", 40.0, "Sushi cu somon"),
                new Produs("Ramen", 28.0, "Supe ramen traditionala"),
                new Produs("Sushi Philadelphia" , 48.0 , "Sushi cu somon si castravete")
        );

        restaurantItalian.adaugaProduse(produseItaliene);


        restaurantAsian.adaugaProduse(produseAsiatice);

        // Adaugare utilizatori
        Utilizator utilizator1 = new Utilizator(1, "Ion Popescu", "ion.popescu@email.com", "0722123456", adresaUtilizator1);
        Utilizator utilizator2 = new Utilizator(2, "Maria Ionescu", "maria.ionescu@email.com", "0723123456", adresaUtilizator2);

        service.adaugaUtilizator(utilizator1);
        service.adaugaUtilizator(utilizator2);

        // Adaugare livratori
        Vehicul masina1 = new Masina("Dacia", "Logan", 2018, 15000, "berlina");
        Vehicul masina2 = new Masina("Volkswagen", "Golf", 2020, 20000, "hatchback");

        Livrator livrator1 = new Livrator("Andrei Ionescu", "0722333444", masina1);
        Livrator livrator2 = new Livrator("Mihai Popescu", "0722444555", masina2);

        service.adaugaLivrator(livrator1);
        service.adaugaLivrator(livrator2);

        System.out.println("Datele de test au fost incarcate cu succes!\n");

        // 2. Testare functionalitati restaurante
        System.out.println("=== Testare functionalitati restaurante ===");

        // Afisare restaurante sortate dupa rating
        System.out.println("Restaurante sortate dupa rating:");
        service.getRestauranteSortateDupaRating().forEach(r ->
                System.out.println("- " + r.getNume() + " (" + r.getTipBucatarie() + "), Rating: " + r.getRating()));

        // Cautare restaurante dupa tip
        System.out.println("\nRestaurante italiene:");
        service.cautaRestauranteDupaTip("italiana").forEach(r ->
                System.out.println("- " + r.getNume()));

        System.out.println("\nRestaurante asiatice:");
        service.cautaRestauranteDupaTip("asiatica").forEach(r ->
                System.out.println("- " + r.getNume()));

        // 3. Testare functionalitati comenzi
        System.out.println("\n=== Testare functionalitati comenzi ===");

        // Creare comenzi
        List<Produs> comanda1Produse = new ArrayList<>();
        Produs pm = restaurantItalian.gasesteProdusDupaNume("Pizza Margherita");
        Produs pc = restaurantItalian.gasesteProdusDupaNume("Paste Carbonara");
        Produs pcasei = restaurantItalian.gasesteProdusDupaNume("Pizza Casei");

        if (pm != null) comanda1Produse.add(pm);
        if (pc != null) comanda1Produse.add(pc);
        if(pcasei!=null) comanda1Produse.add(pcasei);

        List<Produs> comanda2Produse = new ArrayList<>();
        Produs r = restaurantAsian.gasesteProdusDupaNume("Ramen");
        Produs sp = restaurantAsian.gasesteProdusDupaNume("Sushi Philadelphia");


        if (r != null) comanda2Produse.add(r);
        if (sp != null) comanda2Produse.add(sp);

        Comanda comanda1 = service.creeazaComanda(restaurantItalian, utilizator1, comanda1Produse);
        Comanda comanda2 = service.creeazaComanda(restaurantAsian, utilizator2, comanda2Produse);
        Comanda comanda3 = service.creeazaComanda(restaurantItalian,utilizator1,comanda1Produse);


        System.out.println("Au fost create 2 comenzi:");
        System.out.println("- Comanda #" + comanda1.getId() + " pentru " + utilizator1.getNume() + ", total: " + comanda1.getCost() + " lei");
        System.out.println("- Comanda #" + comanda2.getId() + " pentru " + utilizator2.getNume() + ", total: " + comanda2.getCost() + " lei");

        // Asignare livratori
        service.asigneazaLivrator(comanda1, livrator1);
        service.asigneazaLivrator(comanda2, livrator2);
        service.asigneazaLivrator(comanda3,livrator1);

        System.out.println("\nComenzi asignate livratorilor:");
        System.out.println("- Livrator " + livrator1.getNume() + " are " + livrator1.getComenziLivrate().size() + " comenzi");
        System.out.println("- Livrator " + livrator2.getNume() + " are " + livrator2.getComenziLivrate().size() + " comenzi");

        // 4. Testare functionalitati recenzii
        System.out.println("\n=== Testare functionalitati recenzii ===");

        service.adaugaRecenzie(comanda1, 5, "Foarte buna pizza si pastele erau excelente!");
        service.adaugaRecenzie(comanda2, 4, "Sushi-ul a fost bun, dar livrarea a intarziat putin.");
        service.adaugaRecenzie(comanda3,  4.6, "Excelenta pizza , dar a intarziat curierul cu 5-6 minute");

        System.out.println("Recenzii adaugate. Ratinguri actualizate:");
        System.out.println("- " + restaurantItalian.getNume() + ": " + restaurantItalian.getRating());
        System.out.println("- " + restaurantAsian.getNume() + ": " + restaurantAsian.getRating());

        // 5. Testare afisare istoric comenzi
        System.out.println("\n=== Testare istoric comenzi ===");

        System.out.println("Istoric comenzi pentru " + utilizator1.getNume() + ":");
        service.afiseazaComenziUtilizator(1);

        System.out.println("\nIstoric comenzi pentru " + utilizator2.getNume() + ":");
        service.afiseazaComenziUtilizator(2);

        System.out.println("\n");
        service.afiseazaComenziRestaurant("Pizza Delight");
        System.out.println("\n");
        service.afiseazaComenziRestaurant("Wok Master");

        // 6. Testare actualizare status comenzi
        System.out.println("\n=== Testare actualizare status comenzi ===");

        comanda1.actualizeazaStatus("livrata");
        comanda1.setDataLivrare(LocalDateTime.now());

        System.out.println("Status comanda #" + comanda1.getId() + ": " + comanda1.getStatus());
        System.out.println("Data livrare: " + comanda1.getDataLivrare());

        // 7. Testare vehicule livratori
        System.out.println("\n=== Testare detalii livratori ===");

        System.out.println("Detalii livrator " + livrator1.getNume() + ":");
        System.out.println("- Vehicul: " + livrator1.getVehicul().getMarca() + " " + livrator1.getVehicul().getModel());
        if (livrator1.getVehicul() instanceof Masina) {
            Masina m1 = (Masina) livrator1.getVehicul();
            System.out.println("- Tip caroserie: " + m1.getTipCaroserie());
        }

        System.out.println("\nDetalii livrator " + livrator2.getNume() + ":");
        System.out.println("- Vehicul: " + livrator2.getVehicul().getMarca() + " " + livrator2.getVehicul().getModel());
        if (livrator2.getVehicul() instanceof Masina) {
            Masina m2 = (Masina) livrator2.getVehicul();
            System.out.println("- Tip caroserie: " + m2.getTipCaroserie());
        }


        // 8. Testare adaugare produse noi
        System.out.println("\n=== Testare adaugare produse noi ===");

        Produs tiramisu = new Produs("Tiramisu", 15.0, "Desert italian clasic");
        restaurantItalian.adaugaProduse(tiramisu);

        System.out.println("Produse actuale in meniul " + restaurantItalian.getNume() + ":");
        restaurantItalian.getMeniu().getProduse().forEach(p ->
                System.out.println("- " + p.getNume() + " (" + p.getPret() + " lei)"));
        System.out.println("Produse actuale in meniul " + restaurantAsian.getNume() + ":");
        restaurantAsian.getMeniu().getProduse().forEach(p ->
                System.out.println("- " + p.getNume() + " (" + p.getPret() + " lei)"));
    }
}
