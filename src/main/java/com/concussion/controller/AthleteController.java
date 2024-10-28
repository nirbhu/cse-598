package com.concussion.controller;

import com.concussion.model.Symptom;
import com.concussion.model.SymptomType;
import com.concussion.service.SymptomService;
import com.concussion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/athlete")
@RequiredArgsConstructor
public class AthleteController {
    private final SymptomService symptomService;
    private final UserService userService;

    @GetMapping("/symptoms")
    public String symptomForm(Model model) {
        if (!model.containsAttribute("symptom")) {
            model.addAttribute("symptom", new Symptom());
        }
        model.addAttribute("symptomTypes", SymptomType.values());
        return "athlete/symptom-form";
    }

    @PostMapping("/symptoms")
    public String recordSymptoms(@ModelAttribute Symptom symptom,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        try {
            symptom.setAthleteId(userDetails.getUsername());
            symptomService.recordSymptom(symptom);
            redirectAttributes.addFlashAttribute("success", "Symptoms recorded successfully");
            return "redirect:/athlete/summary";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to record symptoms: " + e.getMessage());
            return "redirect:/athlete/symptoms";
        }
    }

    @GetMapping("/summary")
    public String viewSummary(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("symptoms", 
            symptomService.getLastFiveGameSymptoms(userDetails.getUsername()));
        model.addAttribute("severityRating", 
            symptomService.calculateSeverityRating(userDetails.getUsername()));
        return "athlete/summary";
    }
}
