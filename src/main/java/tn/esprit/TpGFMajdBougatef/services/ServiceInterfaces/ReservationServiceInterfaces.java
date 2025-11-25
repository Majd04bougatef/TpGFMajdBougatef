package tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces;

import tn.esprit.TpGFMajdBougatef.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationServiceInterfaces {
    List<Reservation> retrieveAllReservation();
    Reservation updateReservation(Reservation res);
    Reservation retrieveReservation(long idReservation);
    Reservation ajouterReservation(long idChambre, long cinEtudiant);
    Reservation annulerReservation(long cinEtudiant);

    // Partie 5
    List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversite, String nomUniversite);
}
