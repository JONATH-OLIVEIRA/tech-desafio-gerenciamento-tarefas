package com.techtest.gerenciador_tarefas.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tarefas")
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank
	@Column(nullable = false)
	private String titulo;

	@Column(length = 500)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusTarefa status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull
	private PrioridadeTarefa prioridade;

	@Column(nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	private LocalDateTime dataConclusao;

	public Tarefa() {
	}

	public Tarefa(@NotBlank String titulo, String descricao, @NotNull PrioridadeTarefa prioridade,
			LocalDateTime dataConclusao) {
		super();
		
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.dataConclusao = dataConclusao;
	}

	@PrePersist
	public void prePersist() {
		this.status = StatusTarefa.PENDENTE;
		this.dataCriacao = LocalDateTime.now();
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

	@Override
	public String toString() {
		return "Tarefa [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", status=" + status
				+ ", prioridade=" + prioridade + ", dataCriacao=" + dataCriacao + ", dataConclusao=" + dataConclusao
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(id, other.id);
	}

}