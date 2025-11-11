package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {

	// Pure derived method: matches year of anneeUniversitaire and university name.
	// NOTE: Spring Data does not natively support YEAR() function in derived names; we keep method returning list
	// and will filter year at service layer if strictly necessary.
	List<Reservation> findByChambre_Bloc_Foyer_Universite_NomUniversite(String nomUniversite);
	List<Reservation> findByAnneeUniversitaireAndChambre_Bloc_Foyer_Universite_NomUniversite(Date anneeUniversitaire, String nomUniversite);
}
