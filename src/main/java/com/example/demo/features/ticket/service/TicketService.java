package com.example.demo.features.ticket.service;

import com.example.demo.features.ticket.model.request.CreateTicketRequest;
import com.example.demo.features.ticket.model.request.UpdateStatusRequest;
import com.example.demo.features.ticket.model.request.UpdateTicketRequest;
import com.example.demo.features.ticket.model.response.TicketResponse;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketResponse createTicket(UUID userId, CreateTicketRequest createTicketRequest);
    List<TicketResponse> listAllTickets();
    Object retrieveById(UUID ticketId);
    TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest updateTicketRequest);
    TicketResponse updateStatus(UUID ticketId, UpdateStatusRequest updateStatusRequest);
    void deleteTicket(UUID ticketId);
}
