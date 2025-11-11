package tn.esprit.TpGFMajdBougatef.services.ServiceImp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.TpGFMajdBougatef.entities.Bloc;
import tn.esprit.TpGFMajdBougatef.entities.Chambre;
import tn.esprit.TpGFMajdBougatef.repositories.BlocRepository;
import tn.esprit.TpGFMajdBougatef.repositories.ChambreRepository;
import tn.esprit.TpGFMajdBougatef.services.ServiceInterfaces.BlocServiceInterfaces;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlocServiceImp implements BlocServiceInterfaces {

    private final BlocRepository blocRepository;
    private final ChambreRepository chambreRepository;

    @Override
    public List<Bloc> retrieveBlocs() { return blocRepository.findAll(); }

    @Override
    public Bloc updateBloc(Bloc bloc) { return blocRepository.save(bloc); }

    @Override
    public Bloc addBloc(Bloc bloc) { return blocRepository.save(bloc); }

    @Override
    public Bloc retrieveBloc(long idBloc) { return blocRepository.findById(idBloc).orElse(null); }

    @Override
    public void removeBloc(long idBloc) { blocRepository.deleteById(idBloc); }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc).orElse(null);
        if (bloc == null) return null;

        List<Chambre> chambres = chambreRepository.findByNumeroChambreIn(numChambre);
        for (Chambre ch : chambres) {
            ch.setBloc(bloc); // set owning side
        }
        chambreRepository.saveAll(chambres);
        return bloc;
    }
}
