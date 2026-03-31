package br.com.marcelostosdev.FilmeLibrary.Repository;

import br.com.marcelostosdev.FilmeLibrary.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie>findByTituloContainingIgnoreCase(String nomeSerie);
}
