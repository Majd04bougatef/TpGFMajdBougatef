package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "universite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUniversite;
    String nomUniversite;
    String adresse;

    @OneToOne
    @JoinColumn(name = "idFoyer")
    Foyer foyer;
}
