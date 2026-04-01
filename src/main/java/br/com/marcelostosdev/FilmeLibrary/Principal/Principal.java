package br.com.marcelostosdev.FilmeLibrary.Principal;

import br.com.marcelostosdev.FilmeLibrary.Model.*;
import br.com.marcelostosdev.FilmeLibrary.Repository.SerieRepository;
import br.com.marcelostosdev.FilmeLibrary.Service.ConsumoApi;
import br.com.marcelostosdev.FilmeLibrary.Service.ConverteDados;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ac1930c2";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBusca;


    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Listar séries pelo titulo
                    5 - Buscar séries por ator
                    6 - Buscar séries por gênero
                    7 - Buscar séries por temporada e avaliação
                    8 - Buscar episódios por trecho
                    9 - Buscar episódios a partir da data de lançamento
                    10 - Top 5 séries
                    11 - Top 5 episódios por série 
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarSeriePorGenero();
                    break;
                case 7:
                    buscarSeriePorTemporadaEAvaliacao();
                    break;
                case 8:
                    buscarEpisodioPorTrecho();
                    break;
                case 9:
                    buscarEpisodiosPorData();
                    break;
                case 10:
                    buscarTop5Series();
                    break;
                case 11:
                    top5EpisodiosPorSerie();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        Serie serie = new Serie(getDadosSerie());
        repositorio.save(serie);
        System.out.println(serie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para buscar");
        var nomeSerie = leitura.nextLine();
        var json = consumo.ObterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.ObterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo(){
        System.out.println("Escolha uma série pelo nome:");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()){
            System.out.println("Dados da Série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriePorAtor(){
        System.out.println("Qual o nome do ator ou atriz que deseja buscar?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Qual o valor da avaliação minima que deseja ver? ");
        var avaliacao = leitura.nextDouble();

        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Ator ou Atriz escolhido: " + nomeAtor);
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));

    }

    private void buscarTop5Series(){
        List<Serie> topSeries =  repositorio.findTop5ByOrderByAvaliacaoDesc();
        topSeries.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorGenero(){
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var genero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(genero);
        List<Serie> seriesPorGenero = repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria: " + genero);
        seriesPorGenero.forEach(System.out::println);
    }

    private void buscarSeriePorTemporadaEAvaliacao(){
        System.out.println("Qual o limite máximo de temporadas que uma série deve ter? ");
        var limiteTemporada = leitura.nextInt();
        System.out.println("Qual a avaliação mínima que uma série deve ter?");
        var limiteAvaliacao = leitura.nextDouble();

        List<Serie> seriesFiltradas = repositorio.seriesPorTemporadaEAvaliacao(limiteTemporada, limiteAvaliacao);
        System.out.println("Séries com o limite de " + limiteTemporada + " e com avaliação igual ou superior a " + limiteAvaliacao);
        seriesFiltradas.forEach(System.out::println);
    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Qual o nome do episódio que deseja buscar?");
        var nomeEpisodio = leitura.nextLine();

        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(nomeEpisodio);
        System.out.println("Episódios com: " + nomeEpisodio);
        episodiosEncontrados.forEach(System.out::println);
        episodiosEncontrados.forEach(e ->
                System.out.println(e.getTitulo() + " Série: " + e.getSerie().getTitulo() + " Temporada: " + e.getTemporada()));
    }

    private void top5EpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios =  repositorio.top5EpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.println(e.getTitulo() + " Série: " + e.getSerie().getTitulo() + " Temporada: " + e.getTemporada() + " Avaliação: " + e.getAvaliacao()));
        }
    }

    private void buscarEpisodiosPorData(){
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            System.out.println("Digite o ano minimo de lançamento");
            var anoLancamento = leitura.nextInt();

            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEAno(serie, anoLancamento);
            System.out.println("Estes são os episódios lançados a partir do ano: " + anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}
