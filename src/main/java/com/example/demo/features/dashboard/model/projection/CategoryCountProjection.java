package com.example.demo.features.dashboard.model.projection;

import com.example.demo.features.ticket.model.Enum.TicketCategoryEnum;

public interface CategoryCountProjection {

    TicketCategoryEnum getCategory();
    Long getCount();
}
