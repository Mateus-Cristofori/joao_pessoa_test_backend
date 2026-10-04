package com.example.demo.features.ticket.controller;

import com.example.demo.annotations.CurrentUserId;
import com.example.demo.features.ticket.model.request.CreateTicketRequest;
import com.example.demo.features.ticket.model.request.UpdateStatusRequest;
import com.example.demo.features.ticket.model.request.UpdateTicketRequest;
import com.example.demo.features.ticket.model.response.TicketResponse;
import com.example.demo.features.ticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ticket")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public TicketResponse createTicket(
        @CurrentUserId UUID userId,
        @RequestBody @Valid CreateTicketRequest createTicketRequest
    ) {
        return ticketService.createTicket(userId, createTicketRequest);
    }

    @GetMapping("/list-all")
    public List<TicketResponse> listAllTickets() {
        return ticketService.listAllTickets();
    }

    @GetMapping("/retrieve-ticket/{ticketId}")
    public Object retrieveById(@PathVariable UUID ticketId) {
        return ticketService.retrieveById(ticketId);
    }

    @PutMapping("/update/{ticketId}")
    public TicketResponse updateTicket(
        @PathVariable UUID ticketId,
        @RequestBody @Valid UpdateTicketRequest updateTicketRequest
    ) {
        return ticketService.updateTicket(ticketId, updateTicketRequest);
    }

    @PatchMapping("/{ticketId}/status")
    public TicketResponse updateStatus(
        @PathVariable UUID ticketId,
        @RequestBody @Valid UpdateStatusRequest updateStatusRequest
    ) {
        return ticketService.updateStatus(ticketId, updateStatusRequest);
    }

    @DeleteMapping("/{ticketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable UUID ticketId) {
        ticketService.deleteTicket(ticketId);
    }
}
