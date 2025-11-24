package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.TpGFMajdBougatef.entities.Universite;
import tn.esprit.TpGFMajdBougatef.entities.Foyer;
import tn.esprit.TpGFMajdBougatef.repositories.UniversiteRepository;
import tn.esprit.TpGFMajdBougatef.repositories.FoyerRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.UniversiteServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversiteServiceImp implements UniversiteServiceInterfaces {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() { return universiteRepository.findAll(); }

    @Override
    public Universite addUniversite(Universite u) { return universiteRepository.save(u); }

    @Override
    public Universite updateUniversite(Universite u) { return universiteRepository.save(u); }

    @Override
    public Universite retrieveUniversite(long idUniversite) { return universiteRepository.findById(idUniversite).orElse(null); }

    @Override
    @Transactional
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foyer introuvable: id=" + idFoyer));

        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Université introuvable: nom=" + nomUniversite);
        }

        if (foyer.getUniversite() != null && foyer.getUniversite().getIdUniversite() != universite.getIdUniversite()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le foyer " + idFoyer + " est déjà associé à une autre université");
        }

        Foyer foyerAssocie = universite.getFoyer();
        if (foyerAssocie != null && foyerAssocie.getIdFoyer() == foyer.getIdFoyer()) {
            return universite;
        }

        if (foyerAssocie != null && foyerAssocie.getIdFoyer() != foyer.getIdFoyer()) {
            foyerAssocie.setUniversite(null);
            foyerRepository.save(foyerAssocie);
        }

        universite.setFoyer(foyer);
        foyer.setUniversite(universite);
        foyerRepository.save(foyer);

        return universiteRepository.save(universite);
    }

    @Override
    @Transactional
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Université introuvable: id=" + idUniversite));

        Foyer foyer = universite.getFoyer();
        universite.setFoyer(null);

        if (foyer != null) {
            foyer.setUniversite(null);
            foyerRepository.save(foyer);
        }

        return universiteRepository.save(universite);
    }
}
