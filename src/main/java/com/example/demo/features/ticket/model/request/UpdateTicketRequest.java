package com.example.demo.features.ticket.model.request;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketRequest {

    private String title;
    private String description;
    private TicketCategoryEnum category;
    private TicketStatusEnum status;
}
