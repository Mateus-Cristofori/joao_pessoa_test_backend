package com.example.demo.features.dashboard.service.impl;

import com.example.demo.features.dashboard.model.response.DashboardSummaryResponse;
import com.example.demo.features.dashboard.service.DashboardService;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.repository.TicketRepository;
import com.example.demo.features.ticket.repository.entity.Ticket;
import com.example.demo.features.user.repository.UserRepository;
import com.example.demo.features.user.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public DashboardSummaryResponse getDashboardSummary() {
        long total = ticketRepository.count();
        long open = ticketRepository.countByStatus(TicketStatusEnum.OPEN);
        long inProgress = ticketRepository.countByStatus(TicketStatusEnum.IN_PROGRESS);
        long completed = ticketRepository.countByStatus(TicketStatusEnum.COMPLETED);

        double ticketCompletedRate = total > 0 ? ((double) completed / total) * 100 : 0.0;

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<DashboardSummaryResponse.TicketSummary> recentToday = buildRecentToDayTickets(startOfDay, endOfDay);

        List<DashboardSummaryResponse.CategoryCount> ticketsByCategory = ticketRepository.countGroupedByCategory()
            .stream()
            .map(
                projection ->
                    DashboardSummaryResponse.CategoryCount.builder()
                        .category(projection.getCategory())
                        .count(projection.getCount())
                        .build()
            )
            .toList();

        return new DashboardSummaryResponse(
            total,
            open,
            inProgress,
            completed,
            ticketCompletedRate,
            recentToday,
            ticketsByCategory
        );
    }

    private List<DashboardSummaryResponse.TicketSummary> buildRecentToDayTickets(
        LocalDateTime startOfDay,
        LocalDateTime endOfDay
    ) {
        List<Ticket> tickets = ticketRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startOfDay, endOfDay);

        Set<UUID> userIds = tickets.stream()
            .map(Ticket::getUserId)
            .collect(Collectors.toSet());

        Map<UUID, String> userNamesMap = userRepository.findAllById(userIds).stream()
            .collect(Collectors.toMap(User::getId, User::getName));

        return tickets.stream()
            .map(ticket -> DashboardSummaryResponse.TicketSummary.builder()
                .id(ticket.getId())
                .code(ticket.getCode())
                .title(ticket.getTitle())
                .category(ticket.getCategory())
                .description(ticket.getDescription())
                .requester(userNamesMap.getOrDefault(ticket.getUserId(), "Usuário Desconhecido"))
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .build()
            ).toList();
    }
}
