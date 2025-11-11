package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Etudiant;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.EtudiantServiceInterfaces;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/etudiants")
@Tag(name = "Etudiant", description = "Endpoints de gestion des étudiants")
public class EtudiantController {

    private final EtudiantServiceInterfaces etudiantService;

    @GetMapping
    @Operation(summary = "Lister tous les étudiants", description = "Récupère la liste de l'ensemble des étudiants")
    public List<Etudiant> retrieveAllEtudiants(){ return etudiantService.retrieveAllEtudiants(); }

    @PostMapping
    @Operation(summary = "Créer des étudiants", description = "Ajoute une liste d'étudiants")
    public List<Etudiant> addEtudiants(@RequestBody List<Etudiant> etudiants){ return etudiantService.addEtudiants(etudiants); }

    @PutMapping
    @Operation(summary = "Modifier un étudiant", description = "Met à jour un étudiant existant")
    public Etudiant updateEtudiant(@RequestBody Etudiant e){ return etudiantService.updateEtudiant(e); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un étudiant", description = "Récupère un étudiant par identifiant")
    public Etudiant retrieveEtudiant(@PathVariable("id") long idEtudiant){ return etudiantService.retrieveEtudiant(idEtudiant); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un étudiant", description = "Supprime un étudiant par identifiant")
    public void removeEtudiant(@PathVariable("id") long idEtudiant){ etudiantService.removeEtudiant(idEtudiant); }
}
