package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idReservation;
    Date anneeUniversitaire;
    boolean estValide;

    @ManyToMany(mappedBy = "reservations")
    List<Etudiant> etudiants;

    @ManyToMany(mappedBy = "reservations")
    List<Chambre> chambres;
}
