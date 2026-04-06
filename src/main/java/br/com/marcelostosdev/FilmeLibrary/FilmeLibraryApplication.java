package br.com.marcelostosdev.FilmeLibrary;

import br.com.marcelostosdev.FilmeLibrary.Principal.Principal;
import br.com.marcelostosdev.FilmeLibrary.Repository.SerieRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmeLibraryApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
		SpringApplication.run(FilmeLibraryApplication.class, args);
	}

}
