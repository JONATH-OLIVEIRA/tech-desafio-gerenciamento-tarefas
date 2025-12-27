package com.techtest.gerenciador_tarefas.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

//import io.swagger.v3.oas.annotations.parameters.RequestBody; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.techtest.gerenciador_tarefas.DTO.CriarTarefaDTO;
import com.techtest.gerenciador_tarefas.DTO.TarefaDTO;
import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.StatusTarefa;
import com.techtest.gerenciador_tarefas.service.TarefaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	private final TarefaService tarefaService;

	public TarefaController(TarefaService tarefaService) {
		this.tarefaService = tarefaService;
	}

	@PostMapping
	public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody CriarTarefaDTO dto) {
		TarefaDTO criada = tarefaService.criar(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(criada);
	}

	@PutMapping("/{id}/iniciar")
	public ResponseEntity<TarefaDTO> iniciarTarefa(@PathVariable UUID id) {
		TarefaDTO tarefaAtualizada = tarefaService.iniciarTarefa(id);
		return ResponseEntity.ok(tarefaAtualizada);
	}

	@PutMapping("/{id}/concluir")
	public ResponseEntity<TarefaDTO> concluirTarefa(@PathVariable UUID id) {
		TarefaDTO tarefaAtualizada = tarefaService.concluirTarefa(id);
		return ResponseEntity.ok(tarefaAtualizada);
	}

	@GetMapping
	public ResponseEntity<List<TarefaDTO>> listarTarefas(@RequestParam(required = false) StatusTarefa status,
			@RequestParam(required = false) PrioridadeTarefa prioridade,
			@RequestParam(required = false) String dataInicio, @RequestParam(required = false) String dataFim,
			@RequestParam(defaultValue = "dataCriacao") String ordenarPor) {

		LocalDateTime inicio = null;
		LocalDateTime fim = null;

		try {
			if (dataInicio != null && !dataInicio.isEmpty()) {
				inicio = LocalDate.parse(dataInicio, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
			}
			if (dataFim != null && !dataFim.isEmpty()) {
				fim = LocalDate.parse(dataFim, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atTime(23, 59, 59);
			}
		} catch (DateTimeParseException e) {

			try {
				if (dataInicio != null && !dataInicio.isEmpty()) {
					inicio = LocalDate.parse(dataInicio).atStartOfDay();
				}
				if (dataFim != null && !dataFim.isEmpty()) {
					fim = LocalDate.parse(dataFim).atTime(23, 59, 59);
				}
			} catch (DateTimeParseException e2) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Formato de data inválido. Use dd/MM/yyyy ou yyyy-MM-dd");
			}
		}

		List<TarefaDTO> tarefas = tarefaService.listar(status, prioridade, inicio, fim, ordenarPor);
		return ResponseEntity.ok(tarefas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable UUID id) {
		TarefaDTO dto = tarefaService.buscarPorId(id);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarTarefa(@PathVariable UUID id) {
		tarefaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
