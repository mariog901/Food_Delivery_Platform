package modele;
import java.util.ArrayList;
import java.util.List;


public class Utilizator  {
    private int idClient;
    private String nume;
    private String email;
    private String telefon;
    private Adresa adresa;
    private List<Comanda> istoricComenzi;

    public Utilizator(int idClient, String nume, String email, String telefon, Adresa adresa) {
        this.idClient = idClient;
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
        this.adresa = adresa;
        this.istoricComenzi = new ArrayList<>();
    }

    public void adaugaComanda(Comanda comanda){
        istoricComenzi.add(comanda);
    }


    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }
    public List<Comanda>getIstoricComenzi() {
        return new ArrayList<>(istoricComenzi);
    }
}
