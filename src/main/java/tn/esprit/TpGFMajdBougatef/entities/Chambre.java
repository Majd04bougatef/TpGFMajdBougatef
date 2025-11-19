package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "chambre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idChambre;
    Long numeroChambre;

    @Enumerated(EnumType.STRING)
    TypeChambre typeC;

    @ManyToOne
    @JoinColumn(name = "idBloc")
    Bloc bloc;

    @ManyToMany
    @JoinTable(
            name = "chambre_reservation",
            joinColumns = @JoinColumn(name = "idChambre"),
            inverseJoinColumns = @JoinColumn(name = "idReservation")
    )
    List<Reservation> reservations;
}
