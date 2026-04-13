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

import com.biblioteca.bff.service.AzureFunctionOrchestratorService;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    @Value("${azure.functions.estudiantes.crud.url}")
    private String functionUrl;

    private final AzureFunctionOrchestratorService orquestador;

    public EstudianteController(AzureFunctionOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerEstudiantes() {
        return orquestador.callFunction(functionUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearEstudiante(@RequestBody Object estudiante) {
        return orquestador.callFunction(functionUrl, HttpMethod.POST, estudiante);
    }

    @PutMapping
    public ResponseEntity<String> actualizarEstudiante(@RequestBody Object estudiante) {
        return orquestador.callFunction(functionUrl, HttpMethod.PUT, estudiante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable String id) {
        // El BFF orquesta: traduce la ruta REST al formato que la Function necesita
        // (?id=X)
        String urlConParametro = functionUrl + "?id=" + id;
        return orquestador.callFunction(urlConParametro, HttpMethod.DELETE, null);
    }
}
