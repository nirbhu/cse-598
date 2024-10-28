package com.concussion.service;

import com.concussion.model.Symptom;
import com.concussion.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public Symptom recordSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public List<Symptom> getLastFiveGameSymptoms(String athleteId) {
        return symptomRepository.findLastFiveGamesByAthleteId(athleteId);
    }

    public SeverityRating calculateSeverityRating(String athleteId) {
        List<Symptom> lastTwoGames = symptomRepository.findLastFiveGamesByAthleteId(athleteId)
                .stream()
                .limit(2)
                .toList();

        if (lastTwoGames.size() < 2) {
            return SeverityRating.UNSURE;
        }

        int totalSymptomDifference = calculateSymptomDifference(lastTwoGames.get(0), lastTwoGames.get(1));
        int severityScoreDifference = Math.abs(
            calculateTotalSeverity(lastTwoGames.get(0)) - calculateTotalSeverity(lastTwoGames.get(1))
        );

        if (totalSymptomDifference < 3 && severityScoreDifference < 10) {
            return SeverityRating.NO_DIFFERENCE;
        } else if (totalSymptomDifference < 3 && severityScoreDifference >= 10) {
            return SeverityRating.UNSURE;
        } else {
            return SeverityRating.VERY_DIFFERENT;
        }
    }

    private int calculateSymptomDifference(Symptom current, Symptom previous) {
        // Implementation for calculating symptom difference
        return Math.abs(current.getSeverityScore() - previous.getSeverityScore());
    }

    private int calculateTotalSeverity(Symptom symptom) {
        return symptom.getSeverityScore();
    }
}
