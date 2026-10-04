package com.example.demo.features.ticket.model.converter;

import com.example.demo.features.ticket.model.response.TicketResponse;
import com.example.demo.features.ticket.repository.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class ListAllTicketsConverter {

    public TicketResponse convert(Ticket ticket) {
        return TicketResponse
            .builder()
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
}
