package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Reservation;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ReservationServiceInterfaces;

import java.util.List;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@Tag(name = "Reservation", description = "Endpoints de gestion des réservations")
public class ReservationController {

    private final ReservationServiceInterfaces reservationService;

    @GetMapping
    @Operation(summary = "Lister toutes les réservations", description = "Récupère la liste de l'ensemble des réservations")
    public List<Reservation> retrieveAllReservation(){ return reservationService.retrieveAllReservation(); }

    @PutMapping
    @Operation(summary = "Modifier une réservation", description = "Met à jour une réservation existante")
    public Reservation updateReservation(@RequestBody Reservation res){ return reservationService.updateReservation(res); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation", description = "Récupère une réservation par identifiant")
    public Reservation retrieveReservation(@PathVariable("id") long idReservation){ return reservationService.retrieveReservation(idReservation); }

    @GetMapping("/par-annee-et-universite")
    @Operation(summary = "Réservations par année et université", description = "Retourne les réservations effectuées durant une année universitaire donnée et pour une université (nom unique)")
    public List<Reservation> reservationsParAnneeEtUniversite(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date anneeUniversite,
            @RequestParam String nomUniversite){
        return reservationService.getReservationParAnneeUniversitaireEtNomUniversite(anneeUniversite, nomUniversite);
    }

    @GetMapping("/get/{id}")
    public Reservation getReservation(@PathVariable("id") long idReservation) {
        return reservationService.retrieveReservation(idReservation);
    }

    @PostMapping("/ajouterReservation/{idChambre}/{cinEtudiant}")
    public ResponseEntity<?> ajouterReservation(
            @PathVariable long idChambre,
            @PathVariable long cinEtudiant) {
        try {
            Reservation reservation = reservationService.ajouterReservation(idChambre, cinEtudiant);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
