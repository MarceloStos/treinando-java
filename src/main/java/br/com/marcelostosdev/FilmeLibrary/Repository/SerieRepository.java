package br.com.marcelostosdev.FilmeLibrary.Repository;

import br.com.marcelostosdev.FilmeLibrary.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
