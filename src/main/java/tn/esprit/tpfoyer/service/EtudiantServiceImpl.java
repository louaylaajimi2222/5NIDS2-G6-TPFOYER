package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant retrieveEtudiant(Long etudiantId) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(etudiantId);
        if (etudiant.isPresent()) {
            return etudiant.get();
        } else {
            throw new RuntimeException("Etudiant not found with ID: " + etudiantId);
        }
    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant modifyEtudiant(Etudiant etudiant) {
        if (etudiantRepository.existsById(etudiant.getIdEtudiant())) {
            return etudiantRepository.save(etudiant);
        } else {
            throw new RuntimeException("Etudiant not found with ID: " + etudiant.getIdEtudiant());
        }
    }

    @Override
    public void removeEtudiant(Long etudiantId) {
        if (etudiantRepository.existsById(etudiantId)) {
            etudiantRepository.deleteById(etudiantId);
        } else {
            throw new RuntimeException("Etudiant not found with ID: " + etudiantId);
        }
    }

    @Override
    public Etudiant recupererEtudiantParCin(long cin) {
        return etudiantRepository.findEtudiantByCinEtudiant(cin);
    }

    // Advanced Functionality 1: Retrieve students by school and birthdate

}