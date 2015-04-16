package UGmont.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by flemoal on 15/04/15.
 */
@Entity
@Table(name="SALLE")
@XmlRootElement(name = "salle")
public class Salle {
    @Id
    @Column(name="SALLE_ID")
    private int salleId;

    @Column(name="NUMERO_SALLE")
    private int numeroSalle;

    @Column(name="NB_PLACES")
    private int nbPlaces;

    @Column(name="SUPPORT_3D")
    private boolean support3d;

    @ManyToOne
    @JoinColumn(name="FILM_ID")
    private Film film;

    public void setFilm (Film film) {
        this.film = film;
    }

    @XmlAttribute(name="id")
    public int getSalleId() {
        return salleId;
    }

    public void setSalleId(int salleId) {
        this.salleId = salleId;
    }

    @XmlAttribute(name="numero")
    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }


    @XmlAttribute(name="nbPlaces")
    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    @XmlAttribute(name="support3d")
    public boolean isSupport3d() {
        return support3d;
    }

    public void setSupport3d(boolean support3d) {
        this.support3d = support3d;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "salleId=" + salleId +
                ", numeroSalle=" + numeroSalle +
                ", nbPlaces=" + nbPlaces +
                ", support3d=" + support3d +
                ", film=" + film +
                '}';
    }
}
