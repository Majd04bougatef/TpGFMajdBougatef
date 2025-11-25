package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

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
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReservationServiceImp implements ReservationServiceInterfaces {

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final EtudiantRepository etudiantRepository;

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
        List<Reservation> reservations = reservationRepository.findReservationsByUniversite(nomUniversite);
        List<Reservation> filteredReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getAnneeUniversitaire() == null) {
                continue;
            }
            int reservationYear = reservation.getAnneeUniversitaire().toInstant().atZone(ZoneId.systemDefault()).getYear();
            if (reservationYear == targetYear) {
                filteredReservations.add(reservation);
            }
        }
        return filteredReservations;
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

    @Override
    @Transactional
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant)
                .orElseThrow(() -> new RuntimeException("etudiant non trouvé avec le CIN : " + cinEtudiant));

        List<Reservation> studentReservations = etudiant.getReservations();
        if (studentReservations == null || studentReservations.isEmpty()) {
            throw new RuntimeException("Aucune réservation active pour l'étudiant avec le CIN : " + cinEtudiant);
        }

        Reservation reservationToCancel = null;
        for (Reservation currentReservation : studentReservations) {
            if (currentReservation.isEstValide()) {
                reservationToCancel = currentReservation;
                break;
            }
        }
        if (reservationToCancel == null) {
            reservationToCancel = studentReservations.get(0);
        }

        reservationToCancel.setEstValide(false);

        List<Etudiant> reservationStudents = reservationToCancel.getEtudiants();
        if (reservationStudents != null) {
            for (int i = 0; i < reservationStudents.size(); i++) {
                Etudiant currentStudent = reservationStudents.get(i);
                if (currentStudent.getCin() == cinEtudiant) {
                    reservationStudents.remove(i);
                    i--;
                }
            }
        }

        for (int i = 0; i < studentReservations.size(); i++) {
            Reservation currentReservation = studentReservations.get(i);
            if (currentReservation.getIdReservation() == reservationToCancel.getIdReservation()) {
                studentReservations.remove(i);
                break;
            }
        }
        etudiantRepository.save(etudiant);

        List<Chambre> reservationRooms = chambreRepository.findByReservationsIdReservation(reservationToCancel.getIdReservation());
        for (Chambre chambre : reservationRooms) {
            List<Reservation> chambreReservations = chambre.getReservations();
            if (chambreReservations == null) {
                continue;
            }
            for (int i = 0; i < chambreReservations.size(); i++) {
                Reservation currentReservation = chambreReservations.get(i);
                if (currentReservation.getIdReservation() == reservationToCancel.getIdReservation()) {
                    chambreReservations.remove(i);
                    break;
                }
            }
            chambreRepository.save(chambre);
        }

        return reservationRepository.save(reservationToCancel);
    }
}
