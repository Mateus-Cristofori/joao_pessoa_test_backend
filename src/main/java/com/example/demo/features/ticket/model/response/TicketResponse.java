package com.example.demo.features.ticket.model.response;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TicketResponse {
    private UUID id;
    private UUID userId;
    private String code;
    private String title;
    private String description;
    private TicketCategoryEnum category;
    private TicketStatusEnum status;
    private LocalDateTime createdAt;
}