package tn.esprit.TpGFMajdBougatef.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "bloc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBloc;
    String nomBloc;
    Long capaciteBloc;

    @ManyToOne
    @JoinColumn(name = "idFoyer")
    Foyer foyer;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Chambre> chambres;
}
