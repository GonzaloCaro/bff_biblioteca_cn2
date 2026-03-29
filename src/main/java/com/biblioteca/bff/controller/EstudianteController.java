package com.biblioteca.bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.bff.service.LambdaOrchestratorService;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    @Value("${aws.lambda.estudiantes.url}")
    private String lambdaUrl;

    private final LambdaOrchestratorService orquestador;

    public EstudianteController(LambdaOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerEstudiantes() {
        return orquestador.callLambda(lambdaUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearEstudiante(@RequestBody Object estudiante) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.POST, estudiante);
    }

    @PutMapping
    public ResponseEntity<String> actualizarEstudiante(@RequestBody Object estudiante) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.PUT, estudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable String id) {
        // El BFF orquesta: traduce la ruta REST al formato que la Lambda necesita
        // (?id=X)
        String urlConParametro = lambdaUrl + "?id=" + id;
        return orquestador.callLambda(urlConParametro, HttpMethod.DELETE, null);
    }
}
