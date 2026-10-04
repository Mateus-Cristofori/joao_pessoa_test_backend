package com.example.demo.features.dashboard.service.impl;

import com.example.demo.features.dashboard.model.response.DashboardSummaryResponse;
import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import com.example.demo.features.ticket.repository.TicketRepository;
import com.example.demo.features.ticket.repository.entity.Ticket;
import com.example.demo.features.user.repository.UserRepository;
import com.example.demo.features.user.repository.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Test
    @DisplayName("Deve retornar o resumo do dashboard com métricas e tickets recentes calculados corretamente")
    void shouldReturnDashboardSummarySuccessfully() {
        UUID userId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();

        User mockUser = User.builder()
            .id(userId)
            .name("Carlos Silva")
            .email("carlos@email.com")
            .build();

        Ticket mockTicket = Ticket.builder()
            .id(ticketId)
            .code("TICK-001")
            .title("Erro no sistema")
            .description("Não consigo acessar")
            .category(TicketCategoryEnum.IT)
            .status(TicketStatusEnum.OPEN)
            .userId(userId)
            .createdAt(LocalDateTime.now())
            .build();

        when(ticketRepository.count()).thenReturn(10L);
        when(ticketRepository.countByStatus(TicketStatusEnum.OPEN)).thenReturn(4L);
        when(ticketRepository.countByStatus(TicketStatusEnum.IN_PROGRESS)).thenReturn(3L);
        when(ticketRepository.countByStatus(TicketStatusEnum.COMPLETED)).thenReturn(3L);
        when(ticketRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(any(), any()))
            .thenReturn(List.of(mockTicket));
        when(userRepository.findAllById(any(Set.class)))
            .thenReturn(List.of(mockUser));
        when(ticketRepository.countGroupedByCategory()).thenReturn(List.of());

        DashboardSummaryResponse response = dashboardService.getDashboardSummary();

        assertNotNull(response);
        assertEquals(10L, response.getTotal());
        assertEquals(4L, response.getOpen());
        assertEquals(3L, response.getInProgress());
        assertEquals(3L, response.getCompleted());
        assertEquals(30.0, response.getCompletionRate());

        assertFalse(response.getRecentToday().isEmpty());
        assertEquals("TICK-001", response.getRecentToday().getFirst().getCode());
        assertEquals("Carlos Silva", response.getRecentToday().getFirst().getRequester());

        verify(userRepository, times(1)).findAllById(any(Set.class));
    }
}