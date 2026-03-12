package br.com.marcelostosdev.FilmeLibrary.Service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
