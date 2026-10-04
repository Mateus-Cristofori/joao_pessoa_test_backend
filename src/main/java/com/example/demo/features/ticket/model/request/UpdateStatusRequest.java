package com.example.demo.features.ticket.model.request;

import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {

    @NotNull(message = "Status is mandatory")
    private TicketStatusEnum status;
}