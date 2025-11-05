package com.example.Pix.service;


import com.example.Pix.dto.TransacaoDTORequisicao;
import com.example.Pix.dto.TransacaoDTOResposta;
import com.example.Pix.model.TransacaoEntidade;
import com.example.Pix.model.UsuarioEntidade;
import com.example.Pix.repository.TransacaoRepository;
import com.example.Pix.repository.UsuarioRepository;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PixService {

    private final TransacaoRepository transacaoRepository;
    private final Executor executor = Executors.newFixedThreadPool(5);
    private final UsuarioRepository usuarioRepository;

    public TransacaoDTOResposta processarPix(TransacaoDTORequisicao dto){


        TransacaoEntidade transacao = new TransacaoEntidade();
        transacao.setChaveOrigem(dto.getChaveOrigem());
        transacao.setChaveDestino(dto.getChaveDestino());
        transacao.setValor(BigDecimal.valueOf(dto.getValor()));
        transacao.setStatus("PROCESSANDO");


        transacaoRepository.save(transacao);

        try{
            realizarTransferencia(transacao);
        }catch (Exception e){

            transacao.setStatus("Falha");

            System.out.println("Erro na transação: " + e.getMessage());
        }
        transacaoRepository.save(transacao);
        return new TransacaoDTOResposta(transacao);
    }

    public List<TransacaoDTOResposta> listarTransacoes(){

        List<TransacaoEntidade> listaEntidades = transacaoRepository.findAll();

        return listaEntidades.stream()
                .map(entidade -> new TransacaoDTOResposta(entidade))
                .collect(Collectors.toList());
    }


    @Async
    public CompletableFuture<List<TransacaoEntidade>> processarTransacaoAsync(List<TransacaoEntidade> transacoes){
        return CompletableFuture.supplyAsync(()->{
           try {
               transacoes.forEach(t -> t.setStatus("PROCESSANDO"));
               transacaoRepository.saveAll(transacoes);


               Thread.sleep((long) (Math.random() *2000 +500));

               transacoes.forEach(t -> t.setStatus("CONCLUÍDO"));
               transacaoRepository.saveAll(transacoes);
           }catch (Exception e){
               transacoes.forEach(t -> t.setStatus("FALHA"));
               transacaoRepository.saveAll(transacoes);
           }
           return transacoes;
        }, executor);
    }
    @Transactional
    public void realizarTransferencia(TransacaoEntidade transacao)throws Exception{
        UsuarioEntidade origem = usuarioRepository.findByChavePix(transacao.getChaveOrigem())
                .orElseThrow(()-> new Exception("Usuário de origem não encontrado"));

        UsuarioEntidade destino = usuarioRepository.findByChavePix(transacao.getChaveDestino())
                .orElseThrow(()-> new Exception("Usuário de destino não encontrado"));

        if (origem.getSaldo().compareTo(transacao.getValor())<0){
            throw new Exception("Saldo insuficiente");
        }

        origem.setSaldo(origem.getSaldo().subtract(transacao.getValor()));
        origem.setSaldo(destino.getSaldo().add(transacao.getValor()));

        usuarioRepository.saveAll(Arrays.asList(origem,destino));

        transacao.setStatus("Aprovado");

    }

    public void NovaFunc() {
        List<TransacaoEntidade> ListaTest = new ArrayList<>();


        CompletableFuture<List<TransacaoEntidade>> future = processarTransacaoAsync(ListaTest);


        List<TransacaoEntidade> resultado = future.join();


        resultado.parallelStream().forEach(transacao -> {
            System.out.println("Transação processada: " + transacao.getStatus());
        });}
}
