package com.techtest.gerenciador_tarefas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techtest.gerenciador_tarefas.DTO.CriarTarefaDTO;
import com.techtest.gerenciador_tarefas.DTO.TarefaDTO;
import com.techtest.gerenciador_tarefas.globalExceptionHandler.OperacaoInvalidaException;
import com.techtest.gerenciador_tarefas.globalExceptionHandler.TarefaNaoEncontradaException;
import com.techtest.gerenciador_tarefas.mapper.TarefaMapper;
import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.StatusTarefa;
import com.techtest.gerenciador_tarefas.model.Tarefa;
import com.techtest.gerenciador_tarefas.repository.TarefaRepository;
import com.techtest.gerenciador_tarefas.repository.specification.TarefaSpecification;

@Service
@Transactional
public class TarefaService {

	private final TarefaRepository tarefaRepository;

	public TarefaService(TarefaRepository repository) {
		this.tarefaRepository = repository;
	}

	public TarefaDTO criar(CriarTarefaDTO dto) {
		if (dto.getPrioridade() == null) {
			throw new OperacaoInvalidaException("Prioridade e Obrigatoria");
		}
		if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
	        throw new OperacaoInvalidaException("Título é obrigatório");
	    }
		Tarefa tarefa = TarefaMapper.toEntity(dto);
		Tarefa salva = tarefaRepository.save(tarefa);
		return TarefaMapper.toDTO(salva);
	}

	public TarefaDTO iniciarTarefa(UUID id) {
	    Tarefa tarefa = buscarEntidadePorId(id);

	    if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
	        throw new OperacaoInvalidaException("Tarefa já finalizada para o ID: " + id);
	    }
	    
	    if (tarefa.getStatus() == StatusTarefa.EM_ANDAMENTO) {
	        throw new OperacaoInvalidaException("Tarefa já iniciada para o ID: " + id);
	    }	    
	    
	    tarefa.setStatus(StatusTarefa.EM_ANDAMENTO);
	    return TarefaMapper.toDTO(tarefa);
	}

	public TarefaDTO concluirTarefa(UUID id) {
		Tarefa tarefa = buscarEntidadePorId(id);

		if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
			throw new OperacaoInvalidaException("Tarefa já concluida para o Id : " + id);
		}

		tarefa.setStatus(StatusTarefa.CONCLUIDA);
		tarefa.setDataConclusao(LocalDateTime.now());

		return TarefaMapper.toDTO(tarefa);
	}

	@Transactional(readOnly = true)
	public TarefaDTO buscarPorId(UUID id) {
		return TarefaMapper.toDTO(buscarEntidadePorId(id));
	}

	public void deletar(UUID id) {
		Tarefa tarefa = buscarEntidadePorId(id);
		tarefaRepository.delete(tarefa);
	}

	@Transactional(readOnly = true)
	public List<TarefaDTO> listar(StatusTarefa status, PrioridadeTarefa prioridade, LocalDateTime dataInicio,
			LocalDateTime dataFim, String ordenarPor) {

		
		Specification<Tarefa> spec = null;

		if (status != null) {
			spec = TarefaSpecification.statusIgual(status);
		}

		if (prioridade != null) {
			spec = spec == null ? TarefaSpecification.prioridadeIgual(prioridade)
					: spec.and(TarefaSpecification.prioridadeIgual(prioridade));
		}

		if (dataInicio != null && dataFim != null) {
			spec = spec == null ? TarefaSpecification.dataCriacaoEntre(dataInicio, dataFim)
					: spec.and(TarefaSpecification.dataCriacaoEntre(dataInicio, dataFim));
		}

	
		if (spec == null) {
			spec = (root, query, cb) -> cb.conjunction(); 
		}

		Sort sort = Sort.by("dataCriacao");
		if ("prioridade".equalsIgnoreCase(ordenarPor)) {
			sort = Sort.by("prioridade");
		}

		return tarefaRepository.findAll(spec, sort).stream().map(TarefaMapper::toDTO).toList();
	}

	private Tarefa buscarEntidadePorId(UUID id) {
		return tarefaRepository.findById(id).orElseThrow(() -> new TarefaNaoEncontradaException(id));
	}

}
