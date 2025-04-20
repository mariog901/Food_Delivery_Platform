package modele;

public class Masina extends Vehicul{
    private int pretulMasinii;
    private String tipCaroserie;
    public Masina(String marca,String model, int anFabricatie,int pretulMasinii,String tipCaroserie){
        super(marca,model,anFabricatie);
        this.tipCaroserie=tipCaroserie;
        this.pretulMasinii=pretulMasinii;
    }

    public int getPretulMasinii() {
        return pretulMasinii;
    }

    public String getTipCaroserie() {
        return tipCaroserie;
    }
}
