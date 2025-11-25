package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT DISTINCT res FROM Chambre c " +
			"JOIN c.reservations res " +
			"JOIN c.bloc b " +
			"JOIN b.foyer f " +
			"JOIN f.universite u " +
			"WHERE u.nomUniversite = :nomUniversite")
	List<Reservation> findReservationsByUniversite(@Param("nomUniversite") String nomUniversite);
}
