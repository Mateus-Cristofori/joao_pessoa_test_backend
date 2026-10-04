package com.example.demo.features.dashboard.model.response;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;
import com.example.demo.features.ticket.model.Enum.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private long total;
    private long open;
    private long inProgress;
    private long completed;
    private double completionRate;
    private List<TicketSummary> recentToday;
    private List<CategoryCount> byCategory;

    @Data
    @Builder
    public static class TicketSummary {
        private UUID id;
        private String code;
        private String title;
        private TicketCategoryEnum category;
        private String description;
        private String requester;
        private TicketStatusEnum status;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    public static class CategoryCount {
        private TicketCategoryEnum category;
        private Long count;
    }
}