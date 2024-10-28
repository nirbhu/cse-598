package com.concussion.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Symptom {
    private String id;
    
    @NotNull(message = "Athlete ID is required")
    private String athleteId;
    
    private LocalDateTime recordedAt;
    
    @NotNull(message = "Symptom type is required")
    private SymptomType type;
    
    @Min(value = 0, message = "Severity score must be between 0 and 6")
    @Max(value = 6, message = "Severity score must be between 0 and 6")
    private int severityScore;
}
