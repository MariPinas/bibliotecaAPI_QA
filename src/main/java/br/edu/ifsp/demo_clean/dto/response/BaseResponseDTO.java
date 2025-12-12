package br.edu.ifsp.demo_clean.dto.response;

public class BaseResponseDTO<T> {
    public boolean success;
    public String message;
    public T data;

    public BaseResponseDTO(T data) {
        this.success = true;
        this.data = data;
    }

    public BaseResponseDTO(String message) {
        this.success = false;
        this.message = message;
    }
}
