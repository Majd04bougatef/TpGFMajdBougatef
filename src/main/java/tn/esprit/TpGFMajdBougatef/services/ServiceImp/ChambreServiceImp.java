package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ChambreServiceInterfaces;

import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChambreServiceImp implements ChambreServiceInterfaces {

    private final ChambreRepository chambreRepository;

    @Override
    public List<Chambre> retrieveAllChambres() { return chambreRepository.findAll(); }

    @Override
    public Chambre addChambre(Chambre c) { return chambreRepository.save(c); }

    @Override
    public Chambre updateChambre(Chambre c) { return chambreRepository.save(c); }

    @Override
    public Chambre retrieveChambre(long idChambre) { return chambreRepository.findById(idChambre).orElse(null); }

    // Partie 5
    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        return chambreRepository.findByUniversiteNom(nomUniversite);
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, tn.esprit.TpGFMajdBougatef.entities.TypeChambre typeC) {
        // Use derived keywords implementation by default
        return chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, typeC);
    }

    // Alternative JPQL approach (not part of interface, but available for controller demo)
    public List<Chambre> getChambresParBlocEtTypeJPQL(long idBloc, tn.esprit.TpGFMajdBougatef.entities.TypeChambre typeC) {
        return chambreRepository.findByBlocAndType_JPQL(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, tn.esprit.TpGFMajdBougatef.entities.TypeChambre type) {
        Date currentYearDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return chambreRepository.findNonReservedByUniversiteAndTypeForAcademicYear(nomUniversite, type, currentYearDate);
    }
}
