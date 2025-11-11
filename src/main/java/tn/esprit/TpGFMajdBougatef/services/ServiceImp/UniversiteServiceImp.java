package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        if (foyer == null) return null;

        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) return null;

        foyer.setUniversite(universite);
        universite.setFoyer(foyer);

        universiteRepository.save(universite);
        return universite;
    }
}
