package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	// Pure derived method: matches year of anneeUniversitaire and university name.
	// NOTE: Spring Data does not natively support YEAR() function in derived names; we keep method returning list
	// and will filter year at service layer if strictly necessary.
	List<Reservation> findByChambresBlocFoyerUniversiteNomUniversite(String nomUniversite);
	List<Reservation> findByAnneeUniversitaireAndChambresBlocFoyerUniversiteNomUniversite(Date anneeUniversitaire, String nomUniversite);
}
