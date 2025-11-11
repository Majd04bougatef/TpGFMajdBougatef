package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;

import java.util.List;

public interface BlocRepository extends JpaRepository<Bloc, Long> {
	// Derived query traversing Bloc.foyer.universite.nomUniversite
	List<Bloc> findByFoyerUniversiteNomUniversite(String nom);
}
