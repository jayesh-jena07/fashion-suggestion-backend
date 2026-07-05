package com.example.FashionRecommendationApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkinAnalysisResponse {
    private String undertone;
    private List<String> matchingColors;

    // Getters and Setters
    public String getUndertone() { return undertone; }
    public void setUndertone(String undertone) { this.undertone = undertone; }

    public List<String> getMatchingColors() { return matchingColors; }
    public void setMatchingColors(List<String> matchingColors) { this.matchingColors = matchingColors; }
}