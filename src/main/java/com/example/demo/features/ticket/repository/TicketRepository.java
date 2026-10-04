package com.example.demo.features.ticket.repository;

import com.example.demo.features.dashboard.model.projection.CategoryCountProjection;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.repository.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    long countByStatus(TicketStatusEnum status);

    List<Ticket> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT t.category as category, COUNT(t) as count FROM Ticket t GROUP BY t.category")
    List<CategoryCountProjection> countGroupedByCategory();
}
