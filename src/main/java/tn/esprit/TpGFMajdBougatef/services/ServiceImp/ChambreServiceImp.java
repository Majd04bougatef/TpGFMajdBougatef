package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;
import tn.esprit.TpGFMajdBougatef.entities.TypeChambre;
import tn.esprit.TpGFMajdBougatef.repositories.BlocRepository;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ChambreServiceInterfaces;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ChambreServiceImp implements ChambreServiceInterfaces {

    private final ChambreRepository chambreRepository;
    private final BlocRepository blocRepository;

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
        return chambreRepository.findByBlocFoyerUniversiteNomUniversite(nomUniversite);
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreRepository.findChambresByBlocAndType(idBloc, typeC);
    }

    // Alternative JPQL approach (not part of interface, but available for controller demo)
    public List<Chambre> getChambresParBlocEtTypeJPQL(long idBloc, TypeChambre typeC) {
        return chambreRepository.findChambresByBlocAndType(idBloc, typeC);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, tn.esprit.TpGFMajdBougatef.entities.TypeChambre type) {
        int targetYear = LocalDate.now().getYear();
        // Fetch all chambres for this université and type, then filter out those having a reservation in the same year
        List<Chambre> candidates = chambreRepository.findByTypeCAndBlocFoyerUniversiteNomUniversite(type, nomUniversite);
        List<Chambre> availableChambres = new ArrayList<>();
        for (Chambre chambre : candidates) {
            boolean hasReservationThisYear = false;
            if (chambre.getReservations() != null) {
                for (Reservation reservation : chambre.getReservations()) {
                    if (reservation.getAnneeUniversitaire() == null) {
                        continue;
                    }
                    int reservationYear = reservation.getAnneeUniversitaire().toInstant().atZone(ZoneId.systemDefault()).getYear();
                    if (reservationYear == targetYear) {
                        hasReservationThisYear = true;
                        break;
                    }
                }
            }
            if (!hasReservationThisYear) {
                availableChambres.add(chambre);
            }
        }
        return availableChambres;
    }

    @Override
    public List<Chambre> affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec ID : " + idBloc));

        List<Chambre> chambres = chambreRepository.findByNumeroChambreIn(numChambre);

        // Affecter le bloc à chaque chambre
        for (Chambre chambre : chambres) {
            chambre.setBloc(bloc);
        }

        // Sauvegarder toutes les chambres
        return chambreRepository.saveAll(chambres);
    }

    @Override
    public List<Chambre> getChambresByTypeCAndBlocFoyerCapaciteFoyer(TypeChambre type, long c) {
        return chambreRepository.findByTypeCAndBlocFoyerCapaciteFoyer(type, c);
    }
}
