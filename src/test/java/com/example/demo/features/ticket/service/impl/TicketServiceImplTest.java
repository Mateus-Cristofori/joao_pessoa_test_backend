package com.example.demo.features.ticket.service.impl;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.model.request.UpdateStatusRequest;
import com.example.demo.features.ticket.model.request.UpdateTicketRequest;
import com.example.demo.features.ticket.model.response.TicketResponse;
import com.example.demo.features.ticket.repository.TicketRepository;
import com.example.demo.features.ticket.repository.entity.Ticket;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.features.ticket.repository.entity.Ticket;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    @DisplayName("Should successfully update the ticket status.")
    void updateTicketStatusSuccess() {
        UUID ticketId = UUID.randomUUID();

        Ticket existingTicket = Ticket.builder()
            .id(ticketId)
            .title("Title")
            .description("Description")
            .category(TicketCategoryEnum.IT)
            .status(TicketStatusEnum.OPEN)
            .createdAt(LocalDateTime.now())
            .build();

        UpdateStatusRequest request = new UpdateStatusRequest(TicketStatusEnum.IN_PROGRESS);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        TicketResponse response = ticketService.updateStatus(ticketId, request);

        assertNotNull(response);

        assertNotEquals(TicketStatusEnum.OPEN, response.getStatus());
        assertEquals(TicketStatusEnum.IN_PROGRESS, response.getStatus());

        verify(ticketRepository, times(1)).save(existingTicket);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when trying update ticket with NULL status")
    void updateTicketStatusThrowIllegalArgumentExceptionWithNullStatus() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = Ticket.builder()
            .id(ticketId)
            .title("Title")
            .description("Description")
            .category(TicketCategoryEnum.IT)
            .status(TicketStatusEnum.OPEN)
            .createdAt(LocalDateTime.now())
            .build();

        UpdateStatusRequest request = new UpdateStatusRequest(null);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ticketService.updateStatus(ticketId, request)
        );
        assertEquals("New status cannot be null.", exception.getMessage());

        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(0)).save(any(Ticket.class));
    }

    @Test
    @DisplayName("Should update ticket successfully when status is OPEN")
    void updateTicketSuccess() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = Ticket.builder()
            .id(ticketId)
            .title("Old Title")
            .description("Old Description")
            .category(TicketCategoryEnum.IT)
            .status(TicketStatusEnum.OPEN)
            .createdAt(LocalDateTime.now())
            .build();

        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");
        request.setCategory(TicketCategoryEnum.FINANCE);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(existingTicket);

        TicketResponse response = ticketService.updateTicket(ticketId, request);

        assertNotNull(response);
        assertEquals("New Title", existingTicket.getTitle());
        assertEquals("New Description", existingTicket.getDescription());
        assertEquals(TicketCategoryEnum.FINANCE, existingTicket.getCategory());

        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).save(existingTicket);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to update ticket with status other than OPEN")
    void updateTicketThrowsExceptionWhenStatusNotOpen() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = Ticket.builder()
                .id(ticketId)
                .title("Title")
                .description("Description")
                .category(TicketCategoryEnum.IT)
                .status(TicketStatusEnum.IN_PROGRESS)
                .createdAt(LocalDateTime.now())
                .build();

        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");
        request.setCategory(TicketCategoryEnum.FINANCE);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            ticketService.updateTicket(ticketId, request)
        );

        assertEquals("Only tickets with OPEN status can be updated.", exception.getMessage());
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    @DisplayName("Should delete ticket successfully when status is OPEN")
    void deleteTicketSuccess() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = Ticket.builder()
            .id(ticketId)
            .status(TicketStatusEnum.OPEN)
            .build();

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        doNothing().when(ticketRepository).delete(existingTicket);

        ticketService.deleteTicket(ticketId);

        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).delete(existingTicket);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when trying to delete ticket with status other than OPEN")
    void deleteTicketThrowsExceptionWhenStatusNotOpen() {
        UUID ticketId = UUID.randomUUID();
        Ticket existingTicket = Ticket.builder()
            .id(ticketId)
            .status(TicketStatusEnum.COMPLETED)
            .build();

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
            ticketService.deleteTicket(ticketId)
        );

        assertEquals("Only tickets with OPEN status can be deleted.", exception.getMessage());
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, never()).delete(any(Ticket.class));
    }
}