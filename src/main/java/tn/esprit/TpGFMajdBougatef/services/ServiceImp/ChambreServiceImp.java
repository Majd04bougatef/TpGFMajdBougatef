package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.repositories.ReservationRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ChambreServiceInterfaces;

import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChambreServiceImp implements ChambreServiceInterfaces {

    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;

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
        return chambreRepository.findByBloc_Foyer_Universite_NomUniversite(nomUniversite);
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, tn.esprit.TpGFMajdBougatef.entities.TypeChambre typeC) {
        // Use derived keywords implementation by default
        return chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, typeC);
    }

    // Alternative JPQL approach (not part of interface, but available for controller demo)
    public List<Chambre> getChambresParBlocEtTypeJPQL(long idBloc, tn.esprit.TpGFMajdBougatef.entities.TypeChambre typeC) {
        // Without JPQL we simply delegate to the same derived method
        return chambreRepository.findByBloc_IdBlocAndTypeC(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, tn.esprit.TpGFMajdBougatef.entities.TypeChambre type) {
        int targetYear = LocalDate.now().getYear();
        // Fetch all chambres for this université and type, then filter out those having a reservation in the same year
        List<Chambre> candidates = chambreRepository.findByTypeCAndBloc_Foyer_Universite_NomUniversite(type, nomUniversite);
        return candidates.stream()
                .filter(c -> c.getReservations() == null || c.getReservations().stream()
                        .noneMatch(r -> r.getAnneeUniversitaire() != null &&
                                r.getAnneeUniversitaire().toInstant().atZone(ZoneId.systemDefault()).getYear() == targetYear))
                .toList();
    }
}
