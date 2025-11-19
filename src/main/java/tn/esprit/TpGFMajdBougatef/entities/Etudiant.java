package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "etudiant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEtudiant;
    String nomEtudiant;
    String prenomEtudiant;
    long cin;
    String ecole;
    Date dateNaissance;

    @ManyToMany
    @JoinTable(
            name = "etudiant_reservation",
            joinColumns = @JoinColumn(name = "idEtudiant"),
            inverseJoinColumns = @JoinColumn(name = "idReservation")
    )
    List<Reservation> reservations;
}
