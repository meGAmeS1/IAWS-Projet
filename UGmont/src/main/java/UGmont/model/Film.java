package UGmont.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
