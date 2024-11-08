package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationTest {


    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation("1");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
    }

    @Test
    void testRetrieveAllReservations() {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        // Act
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals("1", reservations.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        // Arrange
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.retrieveReservation("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).findById("1");
    }

    @Test
    void testAddReservation() {
        // Arrange
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testModifyReservation() {
        // Arrange
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.modifyReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRemoveReservation() {
        // Arrange
        doNothing().when(reservationRepository).deleteById("1");

        // Act
        reservationService.removeReservation("1");

        // Assert
        verify(reservationRepository, times(1)).deleteById("1");
    }

    @Test
    void testTrouverResSelonDateEtStatus() {
        // Arrange
        Date date = new Date();
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true)).thenReturn(Arrays.asList(reservation));

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, true);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, true);
    }
}