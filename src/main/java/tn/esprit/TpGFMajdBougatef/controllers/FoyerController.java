package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Foyer;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.FoyerServiceInterfaces;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foyers")
@Tag(name = "Foyer", description = "Endpoints de gestion des foyers universitaires")
public class FoyerController {

    private final FoyerServiceInterfaces foyerService;

    @GetMapping
    @Operation(summary = "Lister tous les foyers", description = "Récupère la liste de l'ensemble des foyers")
    public List<Foyer> retrieveAllFoyers(){ return foyerService.retrieveAllFoyers(); }

    @PostMapping
    @Operation(summary = "Créer un foyer", description = "Ajoute un nouveau foyer")
    public Foyer addFoyer(@RequestBody Foyer f){ return foyerService.addFoyer(f); }

    @PostMapping("/universite/{idUniversite}")
    @Operation(summary = "Créer un foyer et l'affecter à une université", description = "Crée un foyer avec sa liste de blocs et l'associe à l'université donnée")
    public Foyer ajouterFoyerEtAffecterAUniversite(@RequestBody Foyer foyer, @PathVariable long idUniversite){
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
    }

    @PutMapping
    @Operation(summary = "Modifier un foyer", description = "Met à jour un foyer existant")
    public Foyer updateFoyer(@RequestBody Foyer f){ return foyerService.updateFoyer(f); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un foyer", description = "Récupère un foyer par identifiant")
    public Foyer retrieveFoyer(@PathVariable("id") long idFoyer){ return foyerService.retrieveFoyer(idFoyer); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un foyer", description = "Supprime un foyer par identifiant")
    public void removeFoyer(@PathVariable("id") long idFoyer){ foyerService.removeFoyer(idFoyer); }
}
