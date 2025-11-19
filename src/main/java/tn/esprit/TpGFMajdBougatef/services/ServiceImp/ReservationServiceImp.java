package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.Etudiant;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.repositories.EtudiantRepository;
import tn.esprit.TpGFMajdBougatef.repositories.ReservationRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ReservationServiceInterfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class ReservationServiceImp implements ReservationServiceInterfaces {

    ReservationRepository reservationRepository;
    ChambreRepository chambreRepository;
    EtudiantRepository etudiantRepository;

    @Override
    public List<Reservation> retrieveAllReservation() { return reservationRepository.findAll(); }

    @Override
    public Reservation updateReservation(Reservation res) { return reservationRepository.save(res); }

    @Override
    public Reservation retrieveReservation(long idReservation) { return reservationRepository.findById(idReservation).orElse(null); }

    // Partie 5
    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite) {
        // Without JPQL YEAR, fetch by university and filter by year in memory
        int targetYear = anneeUniversite.toInstant().atZone(ZoneId.systemDefault()).getYear();
        return reservationRepository.findByChambres_Bloc_Foyer_Universite_NomUniversite(nomUniversite)
                .stream()
                .filter(r -> r.getAnneeUniversitaire() != null &&
                        r.getAnneeUniversitaire().toInstant().atZone(ZoneId.systemDefault()).getYear() == targetYear)
                .toList();
    }

    @Override
    @Transactional
    public Reservation ajouterReservation(long idChambre, long cinEtudiant) {
        // Récupérer la chambre
        Chambre chambre = chambreRepository.findById(idChambre)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée avec ID : " + idChambre));

        // Récupérer l'étudiant par son CIN
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant)
                .orElseThrow(() -> new RuntimeException("etudiant non trouvé avec le CIN : " + cinEtudiant));

        // Créer une nouvelle réservation
        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        reservation.setEtudiants(new ArrayList<>());
        reservation.setChambres(new ArrayList<>());

        // Sauvegarder la réservation d'abord pour avoir un ID
        reservation = reservationRepository.save(reservation);

        // Ajouter l'étudiant à la réservation (côté étudiant qui a @JoinTable)
        if (etudiant.getReservations() == null) {
            etudiant.setReservations(new ArrayList<>());
        }
        etudiant.getReservations().add(reservation);
        etudiantRepository.save(etudiant);

        // Ajouter la chambre à la réservation (côté chambre qui a @JoinTable)
        if (chambre.getReservations() == null) {
            chambre.setReservations(new ArrayList<>());
        }
        chambre.getReservations().add(reservation);
        chambreRepository.save(chambre);

        // Retourner la réservation mise à jour
        return reservation;
    }
}
