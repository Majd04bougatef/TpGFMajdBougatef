package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "foyer")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Foyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFoyer;

    private String nomFoyer;

    private Long capaciteFoyer;

    // Child side of 1-1 with Universite
    @OneToOne
    @JoinColumn(name = "universite_id", referencedColumnName = "idUniversite")
    @JsonIgnore
    private Universite universite;

    // One foyer has many blocs
    @OneToMany(mappedBy = "foyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bloc> blocs;
}
