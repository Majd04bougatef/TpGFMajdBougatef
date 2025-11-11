package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Universite;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.UniversiteServiceInterfaces;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/universites")
@Tag(name = "Universite", description = "Endpoints de gestion des universités")
public class UniversiteController {

    private final UniversiteServiceInterfaces universiteService;

    @GetMapping
    @Operation(summary = "Lister toutes les universités", description = "Récupère la liste de l'ensemble des universités")
    public List<Universite> retrieveAllUniversities(){ return universiteService.retrieveAllUniversities(); }

    @PostMapping
    @Operation(summary = "Créer une université", description = "Ajoute une nouvelle université")
    public Universite addUniversite(@RequestBody Universite u){ return universiteService.addUniversite(u); }

    @PutMapping
    @Operation(summary = "Modifier une université", description = "Met à jour une université existante")
    public Universite updateUniversite(@RequestBody Universite u){ return universiteService.updateUniversite(u); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une université", description = "Récupère une université par identifiant")
    public Universite retrieveUniversite(@PathVariable("id") long idUniversite){ return universiteService.retrieveUniversite(idUniversite); }
}
