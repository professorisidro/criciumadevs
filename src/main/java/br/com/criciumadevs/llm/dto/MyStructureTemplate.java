package br.com.criciumadevs.llm.dto;

import java.util.List;

import dev.langchain4j.model.input.structured.StructuredPrompt;

public class MyStructureTemplate {
	@StructuredPrompt({
		"Crie uma receita de um {{prato}} que possa ser praparado usando os seguintes {{ingredientes}}",
		"Estruture a sua resposta da seguinte maneira:",
		"Nome da receita: ...",
		"Descriçao: ...",
		"Tempo de preparo: ...",
		"Lista de Ingredientes:",
		"- ...",
		"- ...",
		"Modo de preparo:",
		"- ...",
		"- ..."
	})
	public static class PromptDeReceita{
		public String prato;
		public List<String> ingredientes;
	}
}
