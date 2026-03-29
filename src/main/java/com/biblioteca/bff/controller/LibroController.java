package com.biblioteca.bff.controller;

import com.biblioteca.bff.service.LambdaOrchestratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*") // Habilita CORS por si luego le conectan un frontend
public class LibroController {

    @Value("${aws.lambda.libros.url}")
    private String lambdaUrl;

    private final LambdaOrchestratorService orquestador;

    public LibroController(LambdaOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerLibros() {
        return orquestador.callLambda(lambdaUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearLibro(@RequestBody Object libro) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.POST, libro);
    }

    @PutMapping
    public ResponseEntity<String> actualizarLibro(@RequestBody Object libro) {
        return orquestador.callLambda(lambdaUrl, HttpMethod.PUT, libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLibro(@PathVariable String id) {
        // El BFF orquesta: traduce la ruta REST al formato que la Lambda necesita
        // (?id=X)
        String urlConParametro = lambdaUrl + "?id=" + id;
        return orquestador.callLambda(urlConParametro, HttpMethod.DELETE, null);
    }
}