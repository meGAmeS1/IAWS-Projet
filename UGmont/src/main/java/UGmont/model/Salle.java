package UGmont.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by flemoal on 15/04/15.
 */
@Entity
@Table(name="SALLE")
public class Salle {
    @Id
    @Column(name="SALLE_ID")
    private int salleId;

    @Column(name="NUMERO_SALLE")
    private int numeroSalle;

    @Column(name="NB_PLACES")
    private int nbPlaces;

    @Column(name="SUPPORT_3D")
    private int support3d;

    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getSupport3d() {
        return support3d;
    }

    public void setSupport3d(int support3d) {
        this.support3d = support3d;
    }
}
