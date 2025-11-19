package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import tn.esprit.TpGFMajdBougatef.entities.Foyer;
import tn.esprit.TpGFMajdBougatef.repositories.FoyerRepository;
import tn.esprit.TpGFMajdBougatef.repositories.UniversiteRepository;
import tn.esprit.TpGFMajdBougatef.entities.Universite;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.FoyerServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoyerServiceImp implements FoyerServiceInterfaces {

    private final FoyerRepository foyerRepository;
    private final UniversiteRepository universiteRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() { return foyerRepository.findAll(); }

    @Override
    public Foyer addFoyer(Foyer f) { return foyerRepository.save(f); }

    @Override
    public Foyer updateFoyer(Foyer f) { return foyerRepository.save(f); }

    @Override
    public Foyer retrieveFoyer(long idFoyer) { return foyerRepository.findById(idFoyer).orElse(null); }

    @Override
    public void removeFoyer(long idFoyer) { foyerRepository.deleteById(idFoyer); }

    @Override
    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Université introuvable: id=" + idUniversite));

        // Link foyer to universite (OneToOne owning side is Foyer)
        foyer.setUniversite(universite);
        universite.setFoyer(foyer);

        // Ensure blocs reference this foyer if provided inline
        if (foyer.getBlocs() != null) {
            for (Bloc b : foyer.getBlocs()) {
                b.setFoyer(foyer);
            }
        }

        Foyer foyerSauvegarde = foyerRepository.save(foyer);

        universite.setFoyer(foyerSauvegarde);
        universiteRepository.save(universite);

        return foyerRepository.findById(foyerSauvegarde.getIdFoyer())
                .orElseThrow(() -> new RuntimeException("⚠Erreur lors de la récupération du foyer créé"));

    }
}
