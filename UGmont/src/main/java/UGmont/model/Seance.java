package UGmont.model;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by flemoal on 15/04/15.
 */
public class Seance implements Serializable {
    @Id
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="FILM_ID")
    private Film film;

    @Id
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="SALLE_ID")
    private Salle salle;
}
