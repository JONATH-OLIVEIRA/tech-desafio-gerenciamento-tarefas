package com.techtest.gerenciador_tarefas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techtest.gerenciador_tarefas.DTO.CriarTarefaDTO;
import com.techtest.gerenciador_tarefas.DTO.TarefaDTO;
import com.techtest.gerenciador_tarefas.globalExceptionHandler.OperacaoInvalidaException;
import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.StatusTarefa;
import com.techtest.gerenciador_tarefas.model.Tarefa;
import com.techtest.gerenciador_tarefas.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

	@Mock
	private TarefaRepository tarefaRepository;

	@InjectMocks
	private TarefaService tarefaService;

	@Test
	void criarTarefa_ComPrioridade_DeveCriarComSucesso() {
		// Arrange
		CriarTarefaDTO dto = new CriarTarefaDTO();
		dto.setTitulo("Teste Unitário");
		dto.setDescricao("Descrição do teste");
		dto.setPrioridade(PrioridadeTarefa.ALTA);

		Tarefa tarefaSalva = new Tarefa();
		tarefaSalva.setId(UUID.randomUUID());
		tarefaSalva.setTitulo("Teste Unitário");
		tarefaSalva.setDescricao("Descrição do teste");
		tarefaSalva.setPrioridade(PrioridadeTarefa.ALTA);
		tarefaSalva.setStatus(StatusTarefa.PENDENTE);

		when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);
	
		TarefaDTO resultado = tarefaService.criar(dto);
		
		assertNotNull(resultado);
		assertEquals("Teste Unitário", resultado.getTitulo());
		assertEquals(PrioridadeTarefa.ALTA, resultado.getPrioridade());
		verify(tarefaRepository, times(1)).save(any(Tarefa.class));
	}

	@Test
	void iniciarTarefa_TarefaPendente_DeveMudarStatusParaEmAndamento() {
	
		UUID id = UUID.randomUUID();
		Tarefa tarefa = new Tarefa();
		tarefa.setId(id);
		tarefa.setTitulo("Tarefa Teste");
		tarefa.setStatus(StatusTarefa.PENDENTE);
		tarefa.setPrioridade(PrioridadeTarefa.MEDIA);

		when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
	
		TarefaDTO resultado = tarefaService.iniciarTarefa(id);

		assertEquals(StatusTarefa.EM_ANDAMENTO, tarefa.getStatus());
		assertNotNull(resultado);
		verify(tarefaRepository, times(1)).findById(id);
	}

	@Test
	void iniciarTarefa_TarefaConcluida_DeveLancarExcecao() {
		
		UUID id = UUID.randomUUID();
		Tarefa tarefa = new Tarefa();
		tarefa.setId(id);
		tarefa.setStatus(StatusTarefa.CONCLUIDA);

		when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));


		assertThrows(OperacaoInvalidaException.class, () -> {
			tarefaService.iniciarTarefa(id);
		});
		verify(tarefaRepository, times(1)).findById(id);
	}
	
	@Test
	void concluirTarefa_TarefaPendente_DeveConcluirERegistrarData() {
	    // Arrange
	    UUID id = UUID.randomUUID();
	    Tarefa tarefa = new Tarefa();
	    tarefa.setId(id);
	    tarefa.setStatus(StatusTarefa.PENDENTE);
	    
	    when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
	    
	    // Act
	    TarefaDTO resultado = tarefaService.concluirTarefa(id);
	    
	    // Assert
	    assertEquals(StatusTarefa.CONCLUIDA, tarefa.getStatus());
	    assertNotNull(tarefa.getDataConclusao());
	    assertNotNull(resultado);
	}

	@Test
	void concluirTarefa_TarefaJaConcluida_DeveLancarExcecao() {
	    // Arrange
	    UUID id = UUID.randomUUID();
	    Tarefa tarefa = new Tarefa();
	    tarefa.setId(id);
	    tarefa.setStatus(StatusTarefa.CONCLUIDA);
	    tarefa.setDataConclusao(java.time.LocalDateTime.now());
	    
	    when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefa));
	    
	    // Act & Assert
	    assertThrows(OperacaoInvalidaException.class, () -> {
	        tarefaService.concluirTarefa(id);
	    });
	}
}