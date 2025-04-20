package modele;

public abstract class Vehicul {
    private String marca;
    private String model;
    private int anFabricatie;
    public Vehicul(String marca, String model, int anFabricatie) {
        this.marca = marca;
        this.model = model;
        this.anFabricatie = anFabricatie;
    }

    public String getMarca() {
        return marca;
    }

    public String getModel() {
        return model;
    }

    public int getAnFabricatie() {
        return anFabricatie;
    }
}
