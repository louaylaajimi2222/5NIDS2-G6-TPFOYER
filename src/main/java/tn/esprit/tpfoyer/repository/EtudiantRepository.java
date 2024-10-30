package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.util.Date;
import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    List<Etudiant> findByNomEtudiant(String nomEtudiant);

    List<Etudiant> findByDateNaissanceBefore(Date date);

    List<Etudiant> findByNomEtudiantContainingIgnoreCaseOrPrenomEtudiantContainingIgnoreCase(String nomEtudiant, String prenomEtudiant);

    Etudiant findEtudiantByCinEtudiant(long cinEtudiant);
}
