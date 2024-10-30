package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTestMockito {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrieveAllEtudiants() {
        // Given
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null));
        etudiants.add(new Etudiant(2L, "Jane", "Smith", 87654321L, new Date(), null));

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        // When
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Then
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void retrieveEtudiant() {
        // Given
        Long etudiantId = 1L;
        Etudiant etudiant = new Etudiant(etudiantId, "John", "Doe", 12345678L, new Date(), null);
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        // When
        Etudiant result = etudiantService.retrieveEtudiant(etudiantId);

        // Then
        assertNotNull(result);
        assertEquals(etudiantId, result.getIdEtudiant());
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }

    @Test
    void addEtudiant() {
        // Given
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.addEtudiant(etudiant);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void modifyEtudiant() {
        // Given
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), null);
        when(etudiantRepository.existsById(etudiant.getIdEtudiant())).thenReturn(true);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.modifyEtudiant(etudiant);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).existsById(etudiant.getIdEtudiant());
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
    void removeEtudiant() {
        // Given
        Long etudiantId = 1L;
        when(etudiantRepository.existsById(etudiantId)).thenReturn(true);

        // When
        etudiantService.removeEtudiant(etudiantId);

        // Then
        verify(etudiantRepository, times(1)).existsById(etudiantId);
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }

    @Test
    void recupererEtudiantParCin() {
        // Given
        long cin = 12345678L;
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", cin, new Date(), null);
        when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

        // When
        Etudiant result = etudiantService.recupererEtudiantParCin(cin);

        // Then
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
    }
}