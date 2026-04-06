package br.com.marcelostosdev.FilmeLibrary.DTO;

import br.com.marcelostosdev.FilmeLibrary.Model.Categoria;
import jakarta.persistence.*;

import java.util.List;

public record SerieDTO( Long id,
                        String titulo,
                        Integer totalTemporadas,
                        Categoria genero,
                        String atores,
                        String poster,
                        String sinopse,
                        Double avaliacao) {

}
