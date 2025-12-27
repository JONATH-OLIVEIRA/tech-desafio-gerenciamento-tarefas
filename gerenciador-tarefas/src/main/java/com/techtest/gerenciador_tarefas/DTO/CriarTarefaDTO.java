package com.techtest.gerenciador_tarefas.DTO;

import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CriarTarefaDTO {

	@NotBlank(message = "Título é obrigatório")
	private String titulo;

	private String descricao;

	@NotNull(message = "Prioridade é obrigatória")
	private PrioridadeTarefa prioridade;

	// Construtores
	public CriarTarefaDTO() {
	}

	public CriarTarefaDTO(String titulo, String descricao, PrioridadeTarefa prioridade) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
	}

	// Getters e Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public PrioridadeTarefa getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadeTarefa prioridade) {
		this.prioridade = prioridade;
	}
}