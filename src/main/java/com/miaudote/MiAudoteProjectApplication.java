package com.miaudote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MiAudoteProjectApplication {

	public static void main(String[] args) {
        loadDotenv();
		SpringApplication.run(MiAudoteProjectApplication.class, args);
	}

    private static void loadDotenv() {
        try {
            Dotenv dotenv = Dotenv.load();
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
            System.out.println(".env carregado com sucesso.");
        } catch (io.github.cdimascio.dotenv.DotenvException e) {
            System.err.println("Atenção: Arquivo .env não encontrado ou erro ao carregar. Usando variáveis de ambiente existentes.");
        }
    }

    /*
    Só serve pra podermos rodar a aplicação, não vamos digitar nenhum código aqui
     */

}
