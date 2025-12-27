package com.techtest.gerenciador_tarefas.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techtest.gerenciador_tarefas.DTO.CriarTarefaDTO;
import com.techtest.gerenciador_tarefas.globalExceptionHandler.OperacaoInvalidaException;
import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.Tarefa;
import com.techtest.gerenciador_tarefas.repository.TarefaRepository;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceValidationTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

  
    
    @Test
    void criarTarefa_SemPrioridade_DeveLancarExcecao() {
      
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("Tarefa sem prioridade");
        dto.setDescricao("Descrição");
        // prioridade não definida (null)
        
      
        assertThrows(OperacaoInvalidaException.class, () -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_SemTitulo_DeveLancarExcecao() {
       
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("");  // Título vazio
        dto.setDescricao("Descrição");
        dto.setPrioridade(PrioridadeTarefa.ALTA);
        
      
        assertThrows(OperacaoInvalidaException.class, () -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_TituloApenasEspacos_DeveLancarExcecao() {
      
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("   ");  // Apenas espaços
        dto.setDescricao("Descrição");
        dto.setPrioridade(PrioridadeTarefa.ALTA);
        
        
        assertThrows(OperacaoInvalidaException.class, () -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_TituloNull_DeveLancarExcecao() {
      
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo(null);  // Título nulo
        dto.setDescricao("Descrição");
        dto.setPrioridade(PrioridadeTarefa.ALTA);
        
       
        assertThrows(OperacaoInvalidaException.class, () -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_ComTodosCamposValidos_DeveCriarComSucesso() {
     
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("Título válido");
        dto.setDescricao("Descrição válida");
        dto.setPrioridade(PrioridadeTarefa.MEDIA);
        
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(UUID.randomUUID());
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);
        
        
        assertDoesNotThrow(() -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_DescricaoNull_DeveCriarComSucesso() {
        
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("Título válido");
        dto.setDescricao(null);  // Descrição pode ser null
        dto.setPrioridade(PrioridadeTarefa.BAIXA);
        
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(UUID.randomUUID());
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);
        
        assertDoesNotThrow(() -> {
            tarefaService.criar(dto);
        });
    }

    @Test
    void criarTarefa_DescricaoVazia_DeveCriarComSucesso() {
       
        CriarTarefaDTO dto = new CriarTarefaDTO();
        dto.setTitulo("Título válido");
        dto.setDescricao("");  // Descrição pode ser vazia
        dto.setPrioridade(PrioridadeTarefa.BAIXA);
        
        Tarefa tarefaSalva = new Tarefa();
        tarefaSalva.setId(UUID.randomUUID());
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);
        
     
        assertDoesNotThrow(() -> {
            tarefaService.criar(dto);
        });
    }

      
    @Test
    void iniciarTarefa_IdInexistente_DeveLancarExcecao() {
    
        UUID idInexistente = UUID.randomUUID();
        
       
        assertThrows(com.techtest.gerenciador_tarefas.globalExceptionHandler.TarefaNaoEncontradaException.class, () -> {
            tarefaService.iniciarTarefa(idInexistente);
        });
    }
        
    @Test
    void concluirTarefa_IdInexistente_DeveLancarExcecao() {
        
        UUID idInexistente = UUID.randomUUID();
        
        
        assertThrows(com.techtest.gerenciador_tarefas.globalExceptionHandler.TarefaNaoEncontradaException.class, () -> {
            tarefaService.concluirTarefa(idInexistente);
        });
    }

   
    
    @Test
    void buscarPorId_IdInexistente_DeveLancarExcecao() {
        
        UUID idInexistente = UUID.randomUUID();
        
       
        assertThrows(com.techtest.gerenciador_tarefas.globalExceptionHandler.TarefaNaoEncontradaException.class, () -> {
            tarefaService.buscarPorId(idInexistente);
        });
    }

       
    @Test
    void deletar_IdInexistente_DeveLancarExcecao() {
     
        UUID idInexistente = UUID.randomUUID();
        
        
        assertThrows(com.techtest.gerenciador_tarefas.globalExceptionHandler.TarefaNaoEncontradaException.class, () -> {
            tarefaService.deletar(idInexistente);
        });
    }

    
    
    @Test
    void criarTarefa_ComTodasPrioridades_DeveFuncionar() {
        // Testa todas as prioridades válidas
        PrioridadeTarefa[] prioridades = {
            PrioridadeTarefa.BAIXA,
            PrioridadeTarefa.MEDIA, 
            PrioridadeTarefa.ALTA
        };
        
        for (PrioridadeTarefa prioridade : prioridades) {
            
            CriarTarefaDTO dto = new CriarTarefaDTO();
            dto.setTitulo("Tarefa com prioridade " + prioridade);
            dto.setDescricao("Descrição");
            dto.setPrioridade(prioridade);
            
            Tarefa tarefaSalva = new Tarefa();
            tarefaSalva.setId(UUID.randomUUID());
            when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaSalva);
            
         
            assertDoesNotThrow(() -> {
                tarefaService.criar(dto);
            });
        }
    }
}