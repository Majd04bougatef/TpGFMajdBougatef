package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "universite")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Universite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversite;

    private String nomUniversite;

    private String adresse;

    // Bidirectional 1-1 with Foyer (Universite parent, Foyer child)
    @OneToOne(mappedBy = "universite", cascade = CascadeType.ALL)
    private Foyer foyer;
}
