package br.com.marcelostosdev.FilmeLibrary.Controller;

import br.com.marcelostosdev.FilmeLibrary.DTO.EpisodioDTO;
import br.com.marcelostosdev.FilmeLibrary.DTO.SerieDTO;
import br.com.marcelostosdev.FilmeLibrary.Model.Episodio;
import br.com.marcelostosdev.FilmeLibrary.Service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return serieService.obterSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return serieService.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas (@PathVariable Long id){
        return  serieService.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero (@PathVariable Long id, @PathVariable Long numero){
        return  serieService.obterTemporadasPorNumero(id, numero);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obterSeriesPorCategoria (@PathVariable String genero) {
        return serieService.obterSeriesPorCategoria(genero);
    }
}
