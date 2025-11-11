package tn.esprit.TpGFMajdBougatef.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.TypeChambre;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

        // Chambres par nom université (pure derived)
        List<Chambre> findByBloc_Foyer_Universite_NomUniversite(String nomUniversite);

        // Chambres par université & type (pure derived)
        List<Chambre> findByTypeCAndBloc_Foyer_Universite_NomUniversite(TypeChambre typeC, String nomUniversite);

        // Derived Keywords: chambres par bloc & type
        List<Chambre> findByBloc_IdBlocAndTypeC(long idBloc, TypeChambre typeC);

        // Pure derived: traverse c.bloc.foyer.capaciteFoyer
        List<Chambre> findByTypeCAndBlocFoyerCapaciteFoyer(TypeChambre typeC, long capaciteFoyer);

        // Find chambres by their room numbers
        List<Chambre> findByNumeroChambreIn(List<Long> numeros);
}
