package com.example.demo.features.ticket.model.request;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private TicketCategoryEnum category;
}
