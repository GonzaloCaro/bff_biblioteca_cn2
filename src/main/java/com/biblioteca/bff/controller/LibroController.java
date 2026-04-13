package com.biblioteca.bff.controller;

import com.biblioteca.bff.service.AzureFunctionOrchestratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*") // Habilita CORS por si luego le conectan un frontend
public class LibroController {

    @Value("${azure.functions.libros.crud.url}")
    private String functionUrl;

    private final AzureFunctionOrchestratorService orquestador;

    public LibroController(AzureFunctionOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerLibros() {
        return orquestador.callFunction(functionUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearLibro(@RequestBody Object libro) {
        return orquestador.callFunction(functionUrl, HttpMethod.POST, libro);
    }

    @PutMapping
    public ResponseEntity<String> actualizarLibro(@RequestBody Object libro) {
        return orquestador.callFunction(functionUrl, HttpMethod.PUT, libro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLibro(@PathVariable String id) {
        // El BFF orquesta: traduce la ruta REST al formato que la Function necesita
        // (?id=X)
        String urlConParametro = functionUrl + "?id=" + id;
        return orquestador.callFunction(urlConParametro, HttpMethod.DELETE, null);
    }
}