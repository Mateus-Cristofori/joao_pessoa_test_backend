package com.example.demo.features.ticket.repository.entity;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.model.request.UpdateTicketRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    private UUID userId;

    @Column(unique = true)
    @NotNull
    private String code;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketCategoryEnum category;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TicketStatusEnum status;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void updateData(
       UpdateTicketRequest updateTicketRequest
    ) {
        if (this.status != TicketStatusEnum.OPEN) {
            throw new IllegalStateException("Only tickets with OPEN status can be updated.");
        }

        if(updateTicketRequest.getTitle() != null) this.title = updateTicketRequest.getTitle();
        if(updateTicketRequest.getDescription() != null) this.description = updateTicketRequest.getDescription();
        if(updateTicketRequest.getCategory() != null) this.category = updateTicketRequest.getCategory();
        if(updateTicketRequest.getStatus() != null) this.status = updateTicketRequest.getStatus();
        this.updatedAt = LocalDateTime.now();
    }

    public void validateDeletable() {
        if(this.status != TicketStatusEnum.OPEN) {
            throw new IllegalStateException("Only tickets with OPEN status can be deleted.");
        }
    }

    public void updateStatus(TicketStatusEnum newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null.");
        }
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
