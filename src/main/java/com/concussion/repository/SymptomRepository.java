package com.concussion.repository;

import com.concussion.model.Symptom;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.Comparator;

@Repository
public class SymptomRepository {
    private final Map<String, Symptom> symptoms = new ConcurrentHashMap<>();

    public Symptom save(Symptom symptom) {
        if (symptom.getId() == null) {
            symptom.setId(UUID.randomUUID().toString());
        }
        if (symptom.getRecordedAt() == null) {
            symptom.setRecordedAt(LocalDateTime.now());
        }
        symptoms.put(symptom.getId(), symptom);
        return symptom;
    }

    public List<Symptom> findByAthleteId(String athleteId) {
        return symptoms.values().stream()
                .filter(symptom -> symptom.getAthleteId().equals(athleteId))
                .sorted(Comparator.comparing(Symptom::getRecordedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Symptom> findLastFiveGamesByAthleteId(String athleteId) {
        return symptoms.values().stream()
                .filter(symptom -> symptom.getAthleteId().equals(athleteId))
                .sorted(Comparator.comparing(Symptom::getRecordedAt).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
