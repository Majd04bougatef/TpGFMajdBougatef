package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.BlocServiceInterfaces;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blocs")
@Tag(name = "Bloc", description = "Endpoints de gestion des blocs")
public class BlocController {

    private final BlocServiceInterfaces blocService;

    @GetMapping
    @Operation(summary = "Lister tous les blocs", description = "Récupère la liste de l'ensemble des blocs")
    public List<Bloc> retrieveBlocs(){ return blocService.retrieveBlocs(); }

    @PostMapping
    @Operation(summary = "Créer un bloc", description = "Ajoute un nouveau bloc")
    public Bloc addBloc(@RequestBody Bloc bloc){ return blocService.addBloc(bloc); }

    @PutMapping
    @Operation(summary = "Modifier un bloc", description = "Met à jour un bloc existant")
    public Bloc updateBloc(@RequestBody Bloc bloc){ return blocService.updateBloc(bloc); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un bloc", description = "Récupère un bloc par identifiant")
    public Bloc retrieveBloc(@PathVariable("id") long idBloc){ return blocService.retrieveBloc(idBloc); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un bloc", description = "Supprime un bloc par identifiant")
    public void removeBloc(@PathVariable("id") long idBloc){ blocService.removeBloc(idBloc); }

    @PutMapping("/{id}/chambres")
    @Operation(summary = "Affecter des chambres à un bloc", description = "Associe une liste de numéros de chambre au bloc donné")
    public Bloc affecterChambresABloc(@PathVariable("id") long idBloc, @RequestBody List<Long> numeros){
        return blocService.affecterChambresABloc(numeros, idBloc);
    }

    @GetMapping("/byUniversite/{nomUniversite}")
    public List<Bloc> getBlocsByUniversite(@PathVariable String nomUniversite) {
        return blocService.getBlocsByNomUniversite(nomUniversite);
    }
}
