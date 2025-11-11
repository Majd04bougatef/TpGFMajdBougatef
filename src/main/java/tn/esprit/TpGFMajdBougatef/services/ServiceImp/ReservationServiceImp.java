package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;
import tn.esprit.TpGFMajdBougatef.repositories.ReservationRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ReservationServiceInterfaces;

import java.util.List;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservationServiceImp implements ReservationServiceInterfaces {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> retrieveAllReservation() { return reservationRepository.findAll(); }

    @Override
    public Reservation updateReservation(Reservation res) { return reservationRepository.save(res); }

    @Override
    public Reservation retrieveReservation(String idReservation) { return reservationRepository.findById(idReservation).orElse(null); }

    // Partie 5
    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite) {
        return reservationRepository.findByAcademicYearAndUniversite(anneeUniversite, nomUniversite);
    }
}
