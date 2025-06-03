package modele;

import java.time.LocalDate;
import java.time.LocalDateTime;
import repository.HasId;
public class Recenzie implements HasId{
    private int id;
    private double nota;
    private String comentariu;
    private Utilizator utilizator;
    private LocalDateTime data;
    private int comandaId;


    public Recenzie(double nota, String comentariu, Utilizator utilizator,int comandaId) {
        this.nota = nota;
        this.comentariu = comentariu;
        this.utilizator = utilizator;
        this.data = LocalDateTime.now();
        this.comandaId = comandaId;
    }

    public Recenzie(int id,double nota, String comentariu, Utilizator utilizator,LocalDateTime data, int comandaId) {
        this.id=id;
        this.nota = nota;
        this.comentariu = comentariu;
        this.utilizator = utilizator;
        this.data = data;
        this.comandaId = comandaId;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    public double getNota() {
        return nota;
    }

    public String getComentariu() {
        return comentariu;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public LocalDateTime getData() {
        return data;
    }
    public int getComandaId() {
        return comandaId;
    }

    public void setComandaId(int comandaId) {
        this.comandaId = comandaId;
    }
}
