package br.com.criciumadevs.llm.controller;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.criciumadevs.llm.dto.MyQuestion;
import br.com.criciumadevs.llm.dto.MyStructureTemplate;
import br.com.criciumadevs.llm.dto.MyStructureTemplate.PromptDeReceita;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.ollama.OllamaChatModel.OllamaChatModelBuilder;
import dev.langchain4j.model.openai.OpenAiImageModel.OpenAiImageModelBuilder;

@RestController
public class OpenAIController {
	
	@Autowired
	private ChatLanguageModel chatModel;
	
	@Value("${OPENAI_KEY_PREMIUM}")
	private String openAIKey;
	
	@PostMapping("/chat")
	public String chatWithOpenAI(@RequestBody MyQuestion myQuestion) {
		return chatModel.generate(myQuestion.question());
	}
	
	@PostMapping("/image")
	public String generateImage(@RequestBody MyQuestion myQuestion) {
		try {
			ImageModel imageModel = new OpenAiImageModelBuilder()
					                     .apiKey(openAIKey)
					                     .modelName("dall-e")
					                     .build();
			return imageModel.generate(myQuestion.question()).content().url().toURL().toString();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/receita")
	public String facaUmaReceita() {
		MyStructureTemplate template = new MyStructureTemplate();
		PromptDeReceita prompt = new PromptDeReceita();
		prompt.prato = "Assado";
		prompt.ingredientes = Arrays.asList("Maminha","tomate","cebola", "pimentao", "batata");
		Prompt meuPrompt = StructuredPromptProcessor.toPrompt(prompt);
		return chatModel.generate(meuPrompt.text());
	}
	
	@PostMapping("/chatwithollama")
	public String chatWithOllama(@RequestBody MyQuestion myQuestion) {
		ChatLanguageModel ollamaChat = new OllamaChatModelBuilder()
										   .baseUrl("http://localhost:11434")
										   .modelName("phi3")
										   .timeout(Duration.ofSeconds(300))
										   .build();
		return ollamaChat.generate(myQuestion.question());
	}
	

}
