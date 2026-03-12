package br.com.marcelostosdev.FilmeLibrary;

import br.com.marcelostosdev.FilmeLibrary.Model.DadosSerie;
import br.com.marcelostosdev.FilmeLibrary.Service.ConsumoApi;
import br.com.marcelostosdev.FilmeLibrary.Service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmeLibraryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FilmeLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.ObterDados("https://www.omdbapi.com/?t=the+mentalist&apikey=ac1930c2");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
