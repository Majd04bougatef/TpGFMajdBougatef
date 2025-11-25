package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.TypeChambre;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

        // Chambres par nom université (pure derived)
        List<Chambre> findByBlocFoyerUniversiteNomUniversite(String nomUniversite);

        // Chambres par université & type (pure derived)
        List<Chambre> findByTypeCAndBloc_Foyer_Universite_NomUniversite(TypeChambre typeC, String nomUniversite);

        // JPQL: chambres par bloc & type
        @Query("SELECT c FROM Chambre c WHERE c.bloc.idBloc = :idBloc AND c.typeC = :typeC")
        List<Chambre> findChambresByBlocAndType(@Param("idBloc") long idBloc, @Param("typeC") TypeChambre typeC);

        // Pure derived: traverse c.bloc.foyer.capaciteFoyer
        List<Chambre> findByTypeCAndBlocFoyerCapaciteFoyer(TypeChambre typeC, long capaciteFoyer);

        // Find chambres by their room numbers
        List<Chambre> findByNumeroChambreIn(List<Long> numeros);
}
