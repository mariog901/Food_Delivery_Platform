package service;
import modele.*;
import repository.*;
import utils.DatabaseReset;

import java.sql.SQLException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws SQLException {
        DatabaseReset.resetAllTables();
        FoodDeliveryService service = new FoodDeliveryService();
        AdresaRepository adresaRepository = new AdresaRepository();
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        ProdusRepository produsRepository = new ProdusRepository();
        ComandaRepository comandaRepository = new ComandaRepository();
        RecenzieRepository recenzieRepository = new RecenzieRepository();

        System.out.println("🍕═══════════════════════════════════════════════════════════════🍕");
        System.out.println("           SISTEM FOOD DELIVERY - TESTARE COMPLETA             ");
        System.out.println("🍕═══════════════════════════════════════════════════════════════🍕\n");


        System.out.println(" ----- CREARE ADRESE -----");
        Optional<Adresa> adresa1Opt = adresaRepository.create(new Adresa("Str. Mihai Eminescu", "10", "Bucuresti", "010101"));
        Optional<Adresa> adresa2Opt = adresaRepository.create(new Adresa("Bd. Unirii", "25", "Bucuresti", "010102"));
        Optional<Adresa> adresa3Opt = adresaRepository.create(new Adresa("Str. George Enescu", "15", "Bucuresti", "010103"));
        Optional<Adresa> adresa4Opt = adresaRepository.create(new Adresa("Str. Tudor Arghezi", "7", "Cluj-Napoca", "400000"));
        Optional<Adresa> adresa5Opt = adresaRepository.create(new Adresa("Calea Victoriei", "120", "Bucuresti", "010104"));
        Optional<Adresa> adresa6Opt = adresaRepository.create(new Adresa("Str. Republicii", "45", "Cluj-Napoca", "400001"));
        Optional<Adresa> adresa7Opt = adresaRepository.create(new Adresa("Bd. Magheru", "88", "Bucuresti", "010105"));
        Optional<Adresa> adresa8Opt = adresaRepository.create(new Adresa("Str. Memorandumului", "33", "Cluj-Napoca", "400002"));

        if (!adresa1Opt.isPresent() || !adresa2Opt.isPresent() || !adresa3Opt.isPresent() ||
                !adresa4Opt.isPresent() || !adresa5Opt.isPresent() || !adresa6Opt.isPresent() ||
                !adresa7Opt.isPresent() || !adresa8Opt.isPresent()) {
            System.err.println(" Eroare la salvarea adreselor!");
            return;
        }

        Adresa adresa1 = adresa1Opt.get(), adresa2 = adresa2Opt.get(), adresa3 = adresa3Opt.get(), adresa4 = adresa4Opt.get();
        Adresa adresa5 = adresa5Opt.get(), adresa6 = adresa6Opt.get(), adresa7 = adresa7Opt.get(), adresa8 = adresa8Opt.get();

        System.out.println(" Adrese salvate cu succes: " + adresa1.getId() + "-" + adresa8.getId());

        // 2. Creare utilizatori extinsi
        System.out.println("\n ----- CREARE UTILIZATORI -----");
        Utilizator[] utilizatori = {
                new Utilizator(0, "Ion Popescu", "ion.popescu@email.com", "0722123456", adresa3),
                new Utilizator(0, "Maria Ionescu", "maria.ionescu@email.com", "0723123456", adresa4),
                new Utilizator(0, "Alexandru Popa", "alex.popa@email.com", "0724123456", adresa7),
                new Utilizator(0, "Ana Georgescu", "ana.georgescu@email.com", "0725123456", adresa8),
                new Utilizator(0, "Cristian Dumitrescu", "cristian.d@email.com", "0726123456", adresa5)
        };

        for (int i = 0; i < utilizatori.length; i++) {
            Optional<Utilizator> utilOpt = utilizatorRepository.create(utilizatori[i]);
            if (utilOpt.isPresent()) {
                utilizatori[i] = utilOpt.get();
                service.adaugaUtilizator(utilizatori[i]);
                System.out.println(" " + utilizatori[i].getNume() + " (ID: " + utilizatori[i].getId() + ")");
            }
        }

        System.out.println("\n ----- CREARE RESTAURANTE -----");
        Restaurant[] restaurante = {
                new Restaurant(0, " Pizza Delight", adresa1, "italiana", 0),
                new Restaurant(0, " Wok Master", adresa2, "asiatica", 0),
                new Restaurant(0, " Doner Kebab House", adresa5, "turceasca", 0),
                new Restaurant(0, " Burger Palace", adresa6, "americana", 0),
                new Restaurant(0, " La Nonna", adresa7, "italiana", 0)
        };

        for (int i = 0; i < restaurante.length; i++) {
            Optional<Restaurant> restOpt = restaurantRepository.create(restaurante[i]);
            if (restOpt.isPresent()) {
                restaurante[i] = restOpt.get();
                service.adaugaRestaurante(restaurante[i]);
                System.out.println(" " + restaurante[i].getNume() + " (ID: " + restaurante[i].getId() + ") - " + restaurante[i].getTipBucatarie());
            }
        }

        // 4. Creare produse diverse pentru fiecare restaurant
        System.out.println("\n ----- CREARE MENIURI -----");

        // Pizza Delight
        Produs[] produseItalian1 = {
                new Produs("Pizza Margherita", 32.0, "Pizza clasica cu sos de rosii si mozzarella", restaurante[0].getId()),
                new Produs("Pizza Quattro Stagioni", 45.0, "Pizza cu sunca, ciuperci, masline si anghinare", restaurante[0].getId()),
                new Produs("Paste Carbonara", 28.0, "Paste cu sos carbonara autentic", restaurante[0].getId()),
                new Produs("Lasagna Bolognese", 38.0, "Lasagna cu sos bolognese si branza", restaurante[0].getId())
        };

        // Wok Master
        Produs[] produseAsian = {
                new Produs("Sushi Mix", 55.0, "12 bucati sushi variante", restaurante[1].getId()),
                new Produs("Pad Thai", 35.0, "Taitei thailandezi cu creveti", restaurante[1].getId()),
                new Produs("Wok de Vita", 42.0, "Vita cu legume la wok", restaurante[1].getId()),
                new Produs("Supa Tom Yum", 25.0, "Supa thailandeza picanta", restaurante[1].getId())
        };

        // Doner Kebab House
        Produs[] produseTurcesc = {
                new Produs("Doner Kebab", 22.0, "Kebab in lipie cu salata si sosuri", restaurante[2].getId()),
                new Produs("Adana Kebab", 35.0, "Kebab picant la gratar", restaurante[2].getId()),
                new Produs("Lahmacun", 18.0, "Pizza turceasca cu carne", restaurante[2].getId()),
                new Produs("Baklava", 15.0, "Desert traditional turcesc", restaurante[2].getId())
        };

        // Burger Palace
        Produs[] produseAmerican = {
                new Produs("Classic Burger", 25.0, "Burger clasic cu vita si branza", restaurante[3].getId()),
                new Produs("BBQ Burger", 32.0, "Burger cu sos BBQ si bacon", restaurante[3].getId()),
                new Produs("Chicken Wings", 28.0, "Aripioare de pui picante", restaurante[3].getId()),
                new Produs("Cartofi Wedges", 15.0, "Cartofi wedges cu sosuri", restaurante[3].getId())
        };

        // La Nonna
        Produs[] produseItalian2 = {
                new Produs("Risotto ai Funghi", 38.0, "Risotto cu ciuperci porcini", restaurante[4].getId()),
                new Produs("Osso Buco", 65.0, "Jarret de vita cu risotto", restaurante[4].getId()),
                new Produs("Tiramisu", 22.0, "Desert italian clasic", restaurante[4].getId()),
                new Produs("Bruschetta", 18.0, "Bruschetta cu rosii si busuioc", restaurante[4].getId())
        };

        List<Produs> toateProdusele = new ArrayList<>();
        Produs[][] allProduseArrays = {produseItalian1, produseAsian, produseTurcesc, produseAmerican, produseItalian2};

        for (int i = 0; i < allProduseArrays.length; i++) {
            System.out.println(" Meniul " + restaurante[i].getNume() + ":");
            for (Produs produs : allProduseArrays[i]) {
                Optional<Produs> prodOpt = produsRepository.create(produs);
                if (prodOpt.isPresent()) {
                    Produs savedProdus = prodOpt.get();
                    toateProdusele.add(savedProdus);
                    restaurante[i].adaugaProduse(savedProdus);
                    System.out.println(" " + savedProdus.getNume() + " - " + savedProdus.getPret() + " lei");
                }
            }
            System.out.println();
        }

        // 5. Plasare comenzi multiple
        System.out.println(" ----- PLASARE COMENZI -----");
        List<Comanda> comenziSalvate = new ArrayList<>();

        // Comanda 1: Ion la Pizza Delight
        List<Produs> produseComanda1 = Arrays.asList(toateProdusele.get(0), toateProdusele.get(2)); // Pizza + Paste
        Comanda comanda1 = new Comanda(utilizatori[0], restaurante[0], produseComanda1);
        Optional<Comanda> cmd1Opt = comandaRepository.create(comanda1);
        if (cmd1Opt.isPresent()) {
            comanda1 = cmd1Opt.get();
            comandaRepository.saveProdusePentruComanda(comanda1);
            comenziSalvate.add(comanda1);
            System.out.println(" Comanda #" + comanda1.getId() + ": " + utilizatori[0].getNume() +
                    " -> " + restaurante[0].getNume() + " (" + comanda1.getCost() + " lei)");
        }

        // Comanda 2: Maria la Wok Master
        List<Produs> produseComanda2 = Arrays.asList(toateProdusele.get(4), toateProdusele.get(6)); // Sushi + Wok
        Comanda comanda2 = new Comanda(utilizatori[1], restaurante[1], produseComanda2);
        Optional<Comanda> cmd2Opt = comandaRepository.create(comanda2);
        if (cmd2Opt.isPresent()) {
            comanda2 = cmd2Opt.get();
            comandaRepository.saveProdusePentruComanda(comanda2);
            comenziSalvate.add(comanda2);
            System.out.println(" Comanda #" + comanda2.getId() + ": " + utilizatori[1].getNume() +
                    " -> " + restaurante[1].getNume() + " (" + comanda2.getCost() + " lei)");
        }

        // Comanda 3: Alexandru la Burger Palace
        List<Produs> produseComanda3 = Arrays.asList(toateProdusele.get(12), toateProdusele.get(14)); // Burger + Wings
        Comanda comanda3 = new Comanda(utilizatori[2], restaurante[3], produseComanda3);
        Optional<Comanda> cmd3Opt = comandaRepository.create(comanda3);
        if (cmd3Opt.isPresent()) {
            comanda3 = cmd3Opt.get();
            comandaRepository.saveProdusePentruComanda(comanda3);
            comenziSalvate.add(comanda3);
            System.out.println(" Comanda #" + comanda3.getId() + ": " + utilizatori[2].getNume() +
                    " -> " + restaurante[3].getNume() + " (" + comanda3.getCost() + " lei)");
        }

        // Comanda 4: Ana la La Nonna
        List<Produs> produseComanda4 = Arrays.asList(toateProdusele.get(16), toateProdusele.get(18)); // Risotto + Tiramisu
        Comanda comanda4 = new Comanda(utilizatori[3], restaurante[4], produseComanda4);
        Optional<Comanda> cmd4Opt = comandaRepository.create(comanda4);
        if (cmd4Opt.isPresent()) {
            comanda4 = cmd4Opt.get();
            comandaRepository.saveProdusePentruComanda(comanda4);
            comenziSalvate.add(comanda4);
            System.out.println(" Comanda #" + comanda4.getId() + ": " + utilizatori[3].getNume() +
                    " -> " + restaurante[4].getNume() + " (" + comanda4.getCost() + " lei)");
        }

        // Comanda 5: Cristian la Doner Kebab House
        List<Produs> produseComanda5 = Arrays.asList(toateProdusele.get(8), toateProdusele.get(11)); // Doner + Baklava
        Comanda comanda5 = new Comanda(utilizatori[4], restaurante[2], produseComanda5);
        Optional<Comanda> cmd5Opt = comandaRepository.create(comanda5);
        if (cmd5Opt.isPresent()) {
            comanda5 = cmd5Opt.get();
            comandaRepository.saveProdusePentruComanda(comanda5);
            comenziSalvate.add(comanda5);
            System.out.println(" Comanda #" + comanda5.getId() + ": " + utilizatori[4].getNume() +
                    " -> " + restaurante[2].getNume() + " (" + comanda5.getCost() + " lei)");
        }

        // 6. Adaugare recenzii multiple
        System.out.println("\n ===== ADAUGARE RECENZII =====");

        // Recenzii pentru Pizza Delight
        service.adaugaRecenzie(comanda1, 4, "Pizza delicioasa, pastele excelente!");

        // Comanda suplimentara pentru Pizza Delight (pentru rating)
        List<Produs> produseExtra1 = Arrays.asList(toateProdusele.get(1)); // Pizza Quattro Stagioni
        Comanda comandaExtra1 = new Comanda(utilizatori[2], restaurante[0], produseExtra1);
        Optional<Comanda> cmdExtra1Opt = comandaRepository.create(comandaExtra1);
        if (cmdExtra1Opt.isPresent()) {
            comandaExtra1 = cmdExtra1Opt.get();
            comandaRepository.saveProdusePentruComanda(comandaExtra1);
            service.adaugaRecenzie(comandaExtra1, 5.0, "Cea mai buna pizza din oras!");
        }

        // Recenzii pentru Wok Master
        service.adaugaRecenzie(comanda2, 4.8, "Sushi fresh, wok-ul perfect condimentat!");

        List<Produs> produseExtra2 = Arrays.asList(toateProdusele.get(7)); // Supa Tom Yum
        Comanda comandaExtra2 = new Comanda(utilizatori[4], restaurante[1], produseExtra2);
        Optional<Comanda> cmdExtra2Opt = comandaRepository.create(comandaExtra2);
        if (cmdExtra2Opt.isPresent()) {
            comandaExtra2 = cmdExtra2Opt.get();
            comandaRepository.saveProdusePentruComanda(comandaExtra2);
            service.adaugaRecenzie(comandaExtra2, 4.2, "Supa prea picanta ,dar gustoasa!");
        }

        // Recenzii pentru restul restaurantelor
        service.adaugaRecenzie(comanda3, 4.0, "Burger bun, aripioarele crocante!");
        service.adaugaRecenzie(comanda4, 4.9, "Experienta culinara extraordinara! Osso Buco perfect!");
        service.adaugaRecenzie(comanda5, 3.8, "Doner gustos, baklava dulce. Servire rapida.");

        // 7. Demonstrare functionalitati
        System.out.println("\n ----- DEMONSTRARE FUNCTIONALITATI -----");

        // Incarcare restaurante cu rating-uri actualizate
        List<Restaurant> restauranteDinDB = restaurantRepository.getAll();
        System.out.println(" RESTAURANTE CU RATING-URI ACTUALIZATE:");
        for (Restaurant r : restauranteDinDB) {
            System.out.printf("   %-25s | Rating: %.1f | Produse: %d | Tip: %s%n",
                    r.getNume(), r.getRating(), r.getMeniu().getProduse().size(), r.getTipBucatarie());
        }

        // Cautare dupa tip bucatarie
        System.out.println("\n RESTAURANTE ITALIENE:");
        service.cautaRestauranteDupaTip("italiana").forEach(r ->
                System.out.println("    " + r.getNume() + " - Rating: " + String.format("%.1f", r.getRating())));

        System.out.println("\n RESTAURANTE ASIATICE:");
        service.cautaRestauranteDupaTip("asiatica").forEach(r ->
                System.out.println("    " + r.getNume() + " - Rating: " + String.format("%.1f", r.getRating())));

        System.out.println("\n RESTAURANTE AMERICANE:");
        service.cautaRestauranteDupaTip("americana").forEach(r ->
                System.out.println("    " + r.getNume() + " - Rating: " + String.format("%.1f", r.getRating())));

        System.out.println("\n RESTAURANTE TURCESTI:");
        service.cautaRestauranteDupaTip("turceasca").forEach(r ->
                System.out.println("    " + r.getNume() + " - Rating: " + String.format("%.1f", r.getRating())));

        // Top restaurante dupa rating
        System.out.println("\n TOP RESTAURANTE DUPA RATING:");
        List<Restaurant> topRestaurante = service.getRestauranteSortateDupaRating();
        for (int i = 0; i < topRestaurante.size(); i++) {
            Restaurant r = topRestaurante.get(i);
            String medal = i == 0 ? "" : i == 1 ? "" : i == 2 ? " " : " ";
            System.out.printf("   %s %d. %-25s | %.1f | %s%n",
                    medal, (i+1), r.getNume(), r.getRating(), r.getTipBucatarie());
        }

        // Statistici finale
        System.out.println("\n ----- STATISTICI FINALE ------");
        System.out.println(" Adrese create: " + adresa8.getId());
        System.out.println(" Utilizatori inregistrati: " + utilizatori.length);
        System.out.println(" Restaurante active: " + restaurante.length);
        System.out.println(" Produse in meniuri: " + toateProdusele.size());
        System.out.println(" Comenzi plasate: " + (comenziSalvate.size() + 2)); // +2 pentru comenzile extra
        System.out.println(" Recenzii acordate: " + (comenziSalvate.size() + 2));

        double ratingMediu = restauranteDinDB.stream()
                .mapToDouble(Restaurant::getRating)
                .filter(rating -> rating > 0)
                .average()
                .orElse(0.0);
        System.out.printf(" Rating mediu sistem: %.1f%n", ratingMediu);

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("             TESTARE COMPLETA CU SUCCES!                      ");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}