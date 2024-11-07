package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestFoyerImplementation {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @Test
    void testRetrieveAllFoyers() {
        // Arrange
        Foyer foyer1 = new Foyer();
        Foyer foyer2 = new Foyer();
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertEquals(2, foyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer foundFoyer = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(foundFoyer);
        assertEquals(1L, foundFoyer.getIdFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer addedFoyer = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(addedFoyer);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(modifiedFoyer);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        Long foyerId = 1L;
        doNothing().when(foyerRepository).deleteById(foyerId);

        // Act
        foyerService.removeFoyer(foyerId);

        // Assert
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
