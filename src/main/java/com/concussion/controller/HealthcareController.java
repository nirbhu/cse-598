package com.concussion.controller;

import com.concussion.dto.AthleteOverview;
import com.concussion.model.User;
import com.concussion.service.SeverityRating;
import com.concussion.service.SymptomService;
import com.concussion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/healthcare")
@RequiredArgsConstructor
public class HealthcareController {
    private final SymptomService symptomService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> athletes = userService.findAllAthletes();
        List<AthleteOverview> athleteOverviews = athletes.stream()
            .map(athlete -> {
                SeverityRating rating = symptomService.calculateSeverityRating(athlete.getId());
                return new AthleteOverview(athlete, rating);
            })
            .collect(Collectors.toList());
        
        // Filter to show risky athletes first
        List<AthleteOverview> riskyAthletes = athleteOverviews.stream()
            .filter(a -> a.getSeverityRating() == SeverityRating.VERY_DIFFERENT)
            .collect(Collectors.toList());
            
        model.addAttribute("riskyAthletes", riskyAthletes);
        model.addAttribute("allAthletes", athleteOverviews);
        return "healthcare/dashboard";
    }

    @GetMapping("/athlete/{athleteId}")
    public String viewAthleteDetails(@PathVariable String athleteId, Model model) {
        model.addAttribute("athlete", userService.findById(athleteId).orElseThrow());
        model.addAttribute("symptoms", symptomService.getLastFiveGameSymptoms(athleteId));
        model.addAttribute("severityRating", symptomService.calculateSeverityRating(athleteId));
        return "healthcare/athlete-details";
    }

    @PostMapping("/athlete/{athleteId}/note")
    public String addNote(@PathVariable String athleteId, 
                         @RequestParam String note,
                         Model model) {
        // TODO: Implement note adding functionality
        return "redirect:/healthcare/athlete/" + athleteId;
    }
}
