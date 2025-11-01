package com.example.Pix.service;


import com.example.Pix.model.TransacaoEntidade;
import com.example.Pix.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class PixService {

    private final TransacaoRepository transacaoRepository;
    private final Executor executor = Executors.newFixedThreadPool(5);


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

    public void NovaFunc() {
        List<TransacaoEntidade> ListaTest = new ArrayList<>();


        CompletableFuture<List<TransacaoEntidade>> future = processarTransacaoAsync(ListaTest);


        List<TransacaoEntidade> resultado = future.join();


        resultado.parallelStream().forEach(transacao -> {
            System.out.println("Transação processada: " + transacao.getStatus());
        });}
}
