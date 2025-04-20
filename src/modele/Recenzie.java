package modele;

import java.time.LocalDateTime;

public class Recenzie {
    private double nota;
    private String comentariu;
    private Utilizator utilizator;
    private LocalDateTime data;

    public Recenzie(double nota, String comentariu, Utilizator utilizator) {
        this.nota = nota;
        this.comentariu = comentariu;
        this.utilizator = utilizator;
        this.data = LocalDateTime.now();
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
}
