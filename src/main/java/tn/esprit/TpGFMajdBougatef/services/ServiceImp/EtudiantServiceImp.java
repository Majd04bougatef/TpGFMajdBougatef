package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Etudiant;
import tn.esprit.TpGFMajdBougatef.repositories.EtudiantRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.EtudiantServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImp implements EtudiantServiceInterfaces {

    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() { return etudiantRepository.findAll(); }

    @Override
    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) { return etudiantRepository.saveAll(etudiants); }

    @Override
    public Etudiant updateEtudiant(Etudiant e) { return etudiantRepository.save(e); }

    @Override
    public Etudiant retrieveEtudiant(long idEtudiant) { return etudiantRepository.findById(idEtudiant).orElse(null); }

    @Override
    public void removeEtudiant(long idEtudiant) { etudiantRepository.deleteById(idEtudiant); }
}
