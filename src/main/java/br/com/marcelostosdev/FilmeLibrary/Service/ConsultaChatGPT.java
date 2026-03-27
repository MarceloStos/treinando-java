package br.com.marcelostosdev.FilmeLibrary.Service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;

public class ConsultaChatGPT {

    @Value("${OPENAI_APIKEY}")
    private static String TOKEN;

    public static String obterTraducao(String texto) {
        OpenAiService service = new OpenAiService(TOKEN);

        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduza para o português o texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}