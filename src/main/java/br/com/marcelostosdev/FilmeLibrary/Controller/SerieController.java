package br.com.marcelostosdev.FilmeLibrary.Controller;

import br.com.marcelostosdev.FilmeLibrary.DTO.SerieDTO;
import br.com.marcelostosdev.FilmeLibrary.Service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return serieService.obterSeries();
    }

    @GetMapping("/series/top5")
    public List<SerieDTO> obterTop5Series(){
        return serieService.obterTop5Series();
    }
}
