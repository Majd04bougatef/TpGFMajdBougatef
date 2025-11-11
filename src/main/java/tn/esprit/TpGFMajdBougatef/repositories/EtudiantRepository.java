package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.TpGFMajdBougatef.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
}
