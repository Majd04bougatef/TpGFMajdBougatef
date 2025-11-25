package tn.esprit.TpGFMajdBougatef.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.entities.TypeChambre;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.ChambreServiceInterfaces;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chambres")
@Tag(name = "Chambre", description = "Endpoints de gestion des chambres et service avancé de disponibilité")
public class ChambreController {

    private final ChambreServiceInterfaces chambreService;

    @GetMapping
    @Operation(summary = "Lister toutes les chambres", description = "Récupère la liste de l'ensemble des chambres")
    public List<Chambre> retrieveAllChambres(){ return chambreService.retrieveAllChambres(); }

    @PostMapping
    @Operation(summary = "Créer une chambre", description = "Ajoute une nouvelle chambre")
    public Chambre addChambre(@RequestBody Chambre c){ return chambreService.addChambre(c); }

    @PutMapping
    @Operation(summary = "Modifier une chambre", description = "Met à jour une chambre existante")
    public Chambre updateChambre(@RequestBody Chambre c){ return chambreService.updateChambre(c); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une chambre", description = "Récupère une chambre par identifiant")
    public Chambre retrieveChambre(@PathVariable("id") long idChambre){ return chambreService.retrieveChambre(idChambre); }

    // Advanced endpoint (Partie 5 preview): non reserved rooms for university name and type in current academic year
    @GetMapping("/non-reservees")
    @Operation(summary = "Chambres non réservées", description = "Retourne les chambres d'un type donné et d'une université qui ne sont pas réservées durant l'année universitaire courante")
    public List<Chambre> chambresNonReservees(@RequestParam String nomUniversite, @RequestParam TypeChambre type){
        return chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);
    }

    @GetMapping("/universite")
    @Operation(summary = "Chambres par uni", description = "toutes les cham d'une uni ")
    public List<Chambre> chambresParUniversite(@RequestParam String nomUniversite){
        return chambreService.getChambresParNomUniversite(nomUniversite);
    }



















    @GetMapping("/bloc-type/keywords")
    @Operation(summary = "Chambres par bloc et type (Keywords)", description = "Filtre les chambres d'un bloc selon leur type - version Derived Keywords")
    public List<Chambre> chambresParBlocEtTypeKeywords(@RequestParam long idBloc, @RequestParam TypeChambre type){
        return chambreService.getChambresParBlocEtType(idBloc, type);
    }

    @GetMapping("/bloc-type/jpql")
    @Operation(summary = "Chambres par bloc et type (JPQL)", description = "Filtre les chambres d'un bloc selon leur type - version JPQL explicite")
    public List<Chambre> chambresParBlocEtTypeJPQL(@RequestParam long idBloc, @RequestParam TypeChambre type){
        // Using implementation method directly (cast to concrete class not required since we added a helper method) 
        if(chambreService instanceof tn.esprit.TpGFMajdBougatef.services.ServiceImp.ChambreServiceImp impl){
            return impl.getChambresParBlocEtTypeJPQL(idBloc, type);
        }
        return chambreService.getChambresParBlocEtType(idBloc, type);
    }
}


