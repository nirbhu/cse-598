package com.concussion.service;

import com.concussion.model.Symptom;
import com.concussion.model.SymptomType;
import com.concussion.repository.SymptomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SymptomServiceTest {

    @Mock
    private SymptomRepository symptomRepository;

    private SymptomService symptomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        symptomService = new SymptomService(symptomRepository);
    }

    @Test
    void calculateSeverityRating_WhenNoDifference() {
        Symptom current = createSymptom(2);
        Symptom previous = createSymptom(2);
        
        when(symptomRepository.findLastFiveGamesByAthleteId(anyString()))
            .thenReturn(Arrays.asList(current, previous));

        SeverityRating rating = symptomService.calculateSeverityRating("athlete1");
        assertEquals(SeverityRating.NO_DIFFERENCE, rating);
    }

    @Test
    void calculateSeverityRating_WhenVeryDifferent() {
        Symptom current = createSymptom(5);
        Symptom previous = createSymptom(1);
        
        when(symptomRepository.findLastFiveGamesByAthleteId(anyString()))
            .thenReturn(Arrays.asList(current, previous));

        SeverityRating rating = symptomService.calculateSeverityRating("athlete1");
        assertEquals(SeverityRating.VERY_DIFFERENT, rating);
    }

    private Symptom createSymptom(int severityScore) {
        Symptom symptom = new Symptom();
        symptom.setId("test-id");
        symptom.setAthleteId("athlete1");
        symptom.setType(SymptomType.HEADACHE);
        symptom.setSeverityScore(severityScore);
        symptom.setRecordedAt(LocalDateTime.now());
        return symptom;
    }
}
