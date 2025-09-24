package br.edu.ifsp.demo_clean.interfaces;

import java.util.List;

public interface DTOAdapter<T, E> {
    public T toDTO(E value);
    public List<T> toDTOs(List<E> values);
}
