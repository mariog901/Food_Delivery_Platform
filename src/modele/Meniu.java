package modele;

import java.util.ArrayList;
import java.util.List;

public class Meniu {
    private List<Produs>produse;
    public Meniu(){
        this.produse=new ArrayList<>();
    }
    public List<Produs> getProduse() {
        return new ArrayList<>(produse);
    }
    public void adaugaProduse(Produs produs){
        produse.add(produs);
    }
}
