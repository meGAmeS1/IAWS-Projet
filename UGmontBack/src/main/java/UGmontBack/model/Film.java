package UGmontBack.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by flemoal on 15/04/15.
 */
@Entity
@Table(name="FILM")
public class Film {
    @Id
    @Column(name="FILM_ID")
    private int filmId;

    @Column(name="IMDB_ID")
    private String imdbId;

    @OneToMany(mappedBy="film")
    private Set<Salle> salles;

    public Set<Salle> getSalles() { return salles; }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", imdbId=" + imdbId +
                '}';
    }
}
