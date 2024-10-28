package com.concussion.controller;

import com.concussion.dto.AthleteOverview;
import com.concussion.model.User;
import com.concussion.service.SeverityRating;
import com.concussion.service.SymptomService;
import com.concussion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {
    private final SymptomService symptomService;
    private final UserService userService;

    @GetMapping("/athletes")
    public String listAthletes(Model model) {
        List<User> athletes = userService.findAllAthletes();
        List<AthleteOverview> athleteOverviews = athletes.stream()
            .map(athlete -> {
                SeverityRating rating = symptomService.calculateSeverityRating(athlete.getUsername());
                return new AthleteOverview(athlete, rating);
            })
            .collect(Collectors.toList());
        
        model.addAttribute("athletes", athleteOverviews);
        return "coach/athlete-list";
    }

    @GetMapping("/athlete/{athleteId}/summary")
    public String viewAthleteSummary(@PathVariable String athleteId, Model model) {
        User athlete = userService.findById(athleteId).orElseThrow();
        model.addAttribute("symptoms", 
            symptomService.getLastFiveGameSymptoms(athlete.getUsername()));
        model.addAttribute("severityRating", 
            symptomService.calculateSeverityRating(athlete.getUsername()));
        model.addAttribute("athlete", athlete);
        return "coach/athlete-summary";
    }
}
