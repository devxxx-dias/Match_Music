package br.mauricio.matchmusic.service;

public interface IConverterDados {
    <T> T obterDados(String json, Class<T> classe);
}
