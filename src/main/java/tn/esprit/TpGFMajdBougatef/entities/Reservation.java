package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservation")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {
    @Id
    private String idReservation; // String per diagram

    @Temporal(TemporalType.DATE)
    private Date anneeUniversitaire; // store academic year start date

    private boolean estValide;

    @ManyToOne
    @JoinColumn(name = "chambre_id", referencedColumnName = "idChambre")
    @JsonIgnore
    private Chambre chambre;

    // Parent side of bidirectional many-to-many with Etudiant
    @ManyToMany
    @JoinTable(name = "reservation_etudiants",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id"))
    private List<Etudiant> etudiants;
}
