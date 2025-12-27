package com.techtest.gerenciador_tarefas.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.StatusTarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TarefaDTO {

	private UUID id;

	@NotBlank
	private String titulo;

	private String descricao;

	private StatusTarefa status;

	@NotNull
	private PrioridadeTarefa prioridade;

	private LocalDateTime dataCriacao;

	private LocalDateTime dataConclusao;

	public TarefaDTO() {
	}

	public TarefaDTO(UUID id, @NotBlank String titulo, String descricao, StatusTarefa status,
			@NotNull PrioridadeTarefa prioridade, LocalDateTime dataCriacao, LocalDateTime dataConclusao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.prioridade = prioridade;
		this.dataCriacao = dataCriacao;
		this.dataConclusao = dataConclusao;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

	public PrioridadeTarefa getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadeTarefa prioridade) {
		this.prioridade = prioridade;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(LocalDateTime dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
}