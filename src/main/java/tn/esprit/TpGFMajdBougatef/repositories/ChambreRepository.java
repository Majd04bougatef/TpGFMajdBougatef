package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.TypeChambre;

import java.util.Date;
import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    // Chambres par nom université (join navigation)
    @Query("SELECT c FROM Chambre c WHERE c.bloc.foyer.universite.nomUniversite = :nom")
    List<Chambre> findByUniversiteNom(@Param("nom") String nomUniversite);

    // JPQL solution for chambres par bloc & type
    @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.typeC = :type")
    List<Chambre> findByBlocAndType_JPQL(@Param("idBloc") long idBloc, @Param("type") TypeChambre type);

    // Keywords (derived query) solution
    List<Chambre> findByBloc_IdBlocAndTypeC(long idBloc, TypeChambre typeC);

    // Non réservées par université & type for current academic year
    @Query("SELECT c FROM Chambre c WHERE c.typeC = :type AND c.bloc.foyer.universite.nomUniversite = :nomUniversite " +
            "AND NOT EXISTS (SELECT r FROM Reservation r WHERE r.chambre = c AND FUNCTION('YEAR', r.anneeUniversitaire) = FUNCTION('YEAR', :annee))")
    List<Chambre> findNonReservedByUniversiteAndTypeForAcademicYear(@Param("nomUniversite") String nomUniversite,
                                                                   @Param("type") TypeChambre type,
                                                                   @Param("annee") Date academicYearDate);
}
