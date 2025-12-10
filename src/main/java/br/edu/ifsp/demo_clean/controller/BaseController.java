package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public abstract class BaseController {
    protected <T> ResponseEntity<BaseResponseDTO<T>> handleRequest(Supplier<T> supplier) {
        try {
            return ResponseEntity.ok(new BaseResponseDTO<T>(supplier.get()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponseDTO<T>(e.getMessage()));
        }
    }
}
