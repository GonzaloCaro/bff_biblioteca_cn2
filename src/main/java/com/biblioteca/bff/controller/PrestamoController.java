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
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {
    @Value("${azure.functions.prestamos.crud.url}")
    private String functionUrl;

    private final AzureFunctionOrchestratorService orquestador;

    public PrestamoController(AzureFunctionOrchestratorService orquestador) {
        this.orquestador = orquestador;
    }

    @GetMapping
    public ResponseEntity<String> obtenerPrestamos() {
        return orquestador.callFunction(functionUrl, HttpMethod.GET, null);
    }

    @PostMapping
    public ResponseEntity<String> crearPrestamo(@RequestBody Object prestamo) {
        return orquestador.callFunction(functionUrl, HttpMethod.POST, prestamo);
    }

    @PutMapping
    public ResponseEntity<String> actualizarPrestamo(@RequestBody Object prestamo) {
        return orquestador.callFunction(functionUrl, HttpMethod.PUT, prestamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPrestamo(@PathVariable String id) {
        String urlConParametro = functionUrl + "?id=" + id;
        return orquestador.callFunction(urlConParametro, HttpMethod.DELETE, null);
    }
}
