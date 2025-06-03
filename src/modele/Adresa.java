package modele;
import repository.HasId;
public class Adresa implements HasId  {
    private int id;
    private String strada;
    private String numar;
    private String oras;
    private String codPostal;

    public Adresa(int id,String strada, String numar, String oras, String codPostal) {
        this.id = id;
        this.strada = strada;
        this.numar = numar;
        this.oras = oras;
        this.codPostal = codPostal;
    }
    public Adresa(String strada, String numar, String oras, String codPostal) {
        this.strada = strada;
        this.numar = numar;
        this.oras = oras;
        this.codPostal = codPostal;
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }


    @Override
    public String toString() {
        return "Adresa{" +
                "id=" + id +
                "strada='" + strada + '\'' +
                ", numar='" + numar + '\'' +
                ", oras='" + oras + '\'' +
                ", codPostal='" + codPostal + '\'' +
                '}';
    }
}
