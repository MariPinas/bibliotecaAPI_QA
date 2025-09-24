package br.edu.ifsp.demo_clean.dto;

import java.util.function.Function;

public class Result<T> {
    public boolean success;
    public T data;
    public String message;

    private Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success(T value) {
        return new Result<T>(true, value, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, null, message);
    }

    public <U> Result<U> map(Function<T, U> callback) {
        if(!success) {
            return Result.failure(message);
        }

        return Result.success(callback.apply(data));
    }
}
