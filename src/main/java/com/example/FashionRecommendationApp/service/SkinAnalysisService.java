package com.example.FashionRecommendationApp.service;

import com.example.FashionRecommendationApp.dto.SkinAnalysisResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkinAnalysisService {
    private final tools.jackson.databind.ObjectMapper objectMapper = new tools.jackson.databind.ObjectMapper();
    public SkinAnalysisResponse analyzeSkin(String imagePath) {
        try {
           // 1. Dynamically locate where the application is running from
            String rootDir = System.getProperty("user.dir");
            File aiDir = new File(rootDir, "ai");

            // If you opened a parent folder in IntelliJ, let's look one level deeper
            if (!aiDir.exists()) {
                aiDir = new File(rootDir, "FashionRecommendationApp" + File.separator + "ai");
            }

            // Fallback: If it's still missing, find it relative to the class path location
            if (!aiDir.exists()) {
                String classPath = SkinAnalysisService.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                File classFile = new File(classPath);
                // Walk up to find project root
                while (classFile != null && !new File(classFile, "ai").exists()) {
                    classFile = classFile.getParentFile();
                }
                if (classFile != null) {
                    aiDir = new File(classFile, "ai");
                }
            }

            String workingDir = aiDir.getAbsolutePath();
            System.out.println("📂 Active AI Directory Found: " + workingDir);

            // 2. Locate the python executable inside this verified folder layout
            String pythonExe = workingDir + File.separator + "env" + File.separator + "Scripts" + File.separator + "python.exe";
            if (!new File(pythonExe).exists()) {
                pythonExe = workingDir + File.separator + "env" + File.separator + "bin" + File.separator + "python";
            }
            if (!new File(pythonExe).exists()) {
                System.out.println("⚠️ Local env executable missing. Attempting global system python fallback.");
                pythonExe = "python";
            }
            String scriptPath = workingDir + File.separator + "analyze_skin.py";

            // 3. Set up the terminal command execution
            List<String> command = new ArrayList<>();
            command.add(pythonExe);
            command.add(scriptPath);
            command.add(imagePath);

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File(workingDir));
            processBuilder.redirectErrorStream(true); // Merges error logs into standard output

            // 4. Start the process and read the terminal JSON response
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Python script failed with exit code: " + exitCode + ". Output: " + output);
            }

            // 5. Parse that beautiful JSON string straight into a Java Object!
            return objectMapper.readValue(output.toString().trim(), SkinAnalysisResponse.class);

        }catch (Exception e) {
            e.printStackTrace(); // This prints the full error stack trace to your console
            throw new RuntimeException("Failed to execute skin analysis: " + e.getMessage(), e);
        }
    }
}