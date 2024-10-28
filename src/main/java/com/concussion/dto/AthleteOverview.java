package com.concussion.dto;

import com.concussion.model.User;
import com.concussion.service.SeverityRating;
import lombok.Data;

@Data
public class AthleteOverview {
    private final String id;
    private final String username;
    private final SeverityRating severityRating;

    public AthleteOverview(User athlete, SeverityRating rating) {
        this.id = athlete.getId();
        this.username = athlete.getUsername();
        this.severityRating = rating;
    }
}
