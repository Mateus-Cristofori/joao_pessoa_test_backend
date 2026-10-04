package com.example.demo.features.ticket.service.impl;

import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.model.converter.ListAllTicketsConverter;
import com.example.demo.features.ticket.model.request.CreateTicketRequest;
import com.example.demo.features.ticket.model.request.UpdateStatusRequest;
import com.example.demo.features.ticket.model.request.UpdateTicketRequest;
import com.example.demo.features.ticket.model.response.TicketResponse;
import com.example.demo.features.ticket.repository.TicketRepository;
import com.example.demo.features.ticket.repository.entity.Ticket;
import com.example.demo.features.ticket.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ListAllTicketsConverter listAllTicketsConverter;

    @Override
    public TicketResponse createTicket(UUID userId, CreateTicketRequest createTicketRequest) {
        log.info("Usuário de id: {} criando um novo ticket!", userId);

        long totalCount = ticketRepository.count() + 1;
        String generatedCode = String.format("SOL-%04d", totalCount);

        Ticket ticket = ticketRepository.save(
            Ticket
                .builder()
                .userId(userId)
                .code(generatedCode)
                .title(createTicketRequest.getTitle())
                .description(createTicketRequest.getDescription())
                .category(createTicketRequest.getCategory())
                .status(TicketStatusEnum.OPEN)
                .createdAt(LocalDateTime.now())
                .build()
        );

        log.info("Ticket criado!");
        return mapToResponse(ticket);
    }

    @Override
    public List<TicketResponse> listAllTickets() {
        log.info("Listing all tickets for frontend filtering");
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets
            .stream()
            .map(listAllTicketsConverter::convert)
            .toList();
    }

    @Override
    public TicketResponse retrieveById(UUID ticketId) {
        return mapToResponse(retrieveTicketById(ticketId));
    }

    @Override
    public TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest updateTicketRequest) {
        log.info("Updating ticket with ID: {}", ticketId);
        Ticket ticket = retrieveTicketById(ticketId);

        ticket.updateData(updateTicketRequest);
        Ticket updatedTicket = ticketRepository.save(ticket);

        log.info("Ticket updated successfully!");
        return mapToResponse(updatedTicket);
    }

    @Override
    public TicketResponse updateStatus(UUID ticketId, UpdateStatusRequest updateStatusRequest) {
        log.info("Updating status for ticket ID: {} to {}", ticketId, updateStatusRequest.getStatus());

        Ticket ticket = retrieveTicketById(ticketId);
        ticket.updateStatus(updateStatusRequest.getStatus());
        Ticket updatedTicket = ticketRepository.save(ticket);

        log.info("Ticket status updated successfully!");
        return mapToResponse(updatedTicket);
    }

    @Override
    public void deleteTicket(UUID ticketId) {
        log.info("Deleting ticket with ID: {}", ticketId);
        Ticket ticket = retrieveTicketById(ticketId);

        ticket.validateDeletable();

        ticketRepository.delete(ticket);
        log.info("Ticket deleted successfully!");
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
            .id(ticket.getId())
            .userId(ticket.getUserId())
            .code(ticket.getCode())
            .title(ticket.getTitle())
            .description(ticket.getDescription())
            .category(ticket.getCategory())
            .status(ticket.getStatus())
            .createdAt(ticket.getCreatedAt())
            .build();
    }

    private Ticket retrieveTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found with ID: " + ticketId));
    }
}
