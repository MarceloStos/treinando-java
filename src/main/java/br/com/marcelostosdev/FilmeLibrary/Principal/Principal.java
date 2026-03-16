package br.com.marcelostosdev.FilmeLibrary.Principal;

import br.com.marcelostosdev.FilmeLibrary.Model.DadosEpisodio;
import br.com.marcelostosdev.FilmeLibrary.Model.DadosSerie;
import br.com.marcelostosdev.FilmeLibrary.Model.DadosTemporada;
import br.com.marcelostosdev.FilmeLibrary.Service.ConsumoApi;
import br.com.marcelostosdev.FilmeLibrary.Service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=ac1930c2";

    public void exibeMenu(){
        System.out.println("Digite o nome da serie para buscar");
        var nomeSerie = leitura.nextLine();
        var json = consumo.ObterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumo.ObterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + APIKEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
//		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

    }
}
