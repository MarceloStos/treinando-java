package br.com.marcelostosdev.FilmeLibrary.Service;

import br.com.marcelostosdev.FilmeLibrary.DTO.SerieDTO;
import br.com.marcelostosdev.FilmeLibrary.Model.Serie;
import br.com.marcelostosdev.FilmeLibrary.Repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;

    @GetMapping("/series")
    public List<SerieDTO> obterSeries() {
        return repositorio.findAll()
                .stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse(),
                        s.getAvaliacao()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterTop5Series() {
        return repositorio.findTop5ByOrderByAvaliacaoDesc()
                .stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse(),
                        s.getAvaliacao()))
                .collect(Collectors.toList());
    }
}
