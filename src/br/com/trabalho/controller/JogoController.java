package br.com.trabalho.controller;

import br.com.trabalho.dao.CartaDAO;
import br.com.trabalho.model.Carta;
import br.com.trabalho.model.Jogador;

import java.util.Collections;
import java.util.List;

/**
 * Controla o fluxo do jogo Super Trunfo.
 * Gerencia a distribuição de cartas, rodadas, comparação e pontuação.
 */
public class JogoController {

    private static final int TOTAL_ATRIBUTOS = 5;

    private CartaDAO cartaDAO;
    private Jogador jogador;
    private Jogador maquina;
    private Carta cartaAtualMaquina;
    private int rodadaAtual;
    private int totalRodadas;

    // Resultado da última rodada
    private boolean[] jogadorVenceuAtributo; // true = jogador venceu aquele atributo
    private int pontosRodadaJogador;
    private int pontosRodadaMaquina;

    public JogoController() {
        this.cartaDAO = new CartaDAO();
        this.jogador = new Jogador("Você");
        this.maquina = new Jogador("Máquina");
    }

    /**
     * Inicia o jogo: carrega cartas do banco, embaralha e distribui.
     * 11 cartas → 5 para jogador, 5 para máquina, 1 descartada.
     */
    public void iniciarJogo() {
        jogador.resetar();
        maquina.resetar();
        rodadaAtual = 0;
        cartaAtualMaquina = null;

        List<Carta> todasCartas = cartaDAO.listarTodas();
        Collections.shuffle(todasCartas);

        // Distribui: 5 para jogador, 5 para máquina, 1 sobra
        int metade = todasCartas.size() / 2;
        for (int i = 0; i < metade; i++) {
            jogador.adicionarCarta(todasCartas.get(i));
        }
        for (int i = metade; i < metade * 2; i++) {
            maquina.adicionarCarta(todasCartas.get(i));
        }

        totalRodadas = metade; // 5 rodadas
    }

    /**
     * Executa uma rodada: recebe a carta escolhida pelo jogador,
     * a máquina sorteia uma carta, compara todos os 5 atributos.
     *
     * @param cartaJogador carta escolhida pelo jogador na interface
     * @return true se a rodada foi executada com sucesso
     */
    public boolean jogarRodada(Carta cartaJogador) {
        // Máquina sorteia carta das suas disponíveis
        cartaAtualMaquina = maquina.sortearCarta();
        if (cartaAtualMaquina == null) {
            return false;
        }

        // Compara todos os atributos
        jogadorVenceuAtributo = new boolean[TOTAL_ATRIBUTOS];
        pontosRodadaJogador = 0;
        pontosRodadaMaquina = 0;

        for (int i = 0; i < TOTAL_ATRIBUTOS; i++) {
            double valorJogador = cartaJogador.getValorAtributo(i);
            double valorMaquina = cartaAtualMaquina.getValorAtributo(i);

            if (valorJogador > valorMaquina) {
                jogadorVenceuAtributo[i] = true;
                pontosRodadaJogador++;
            } else if (valorMaquina > valorJogador) {
                jogadorVenceuAtributo[i] = false;
                pontosRodadaMaquina++;
            } else {
                // Empate no atributo: ninguém pontua
                jogadorVenceuAtributo[i] = false;
            }
        }

        // Quem venceu mais atributos ganha a rodada
        if (pontosRodadaJogador > pontosRodadaMaquina) {
            jogador.adicionarPontoGeral();
        } else if (pontosRodadaMaquina > pontosRodadaJogador) {
            maquina.adicionarPontoGeral();
        }
        // Se empatou na rodada, ninguém ganha ponto geral

        // Marca cartas como usadas
        jogador.marcarCartaUsada(cartaJogador.getId());
        maquina.marcarCartaUsada(cartaAtualMaquina.getId());

        rodadaAtual++;
        return true;
    }

    /** Verifica se o jogo acabou (todas as rodadas jogadas) */
    public boolean isJogoAcabou() {
        return rodadaAtual >= totalRodadas;
    }

    /** Retorna o nome do vencedor final ou "Empate" */
    public String getVencedorFinal() {
        if (jogador.getPontosGeral() > maquina.getPontosGeral()) {
            return "Você venceu!";
        } else if (maquina.getPontosGeral() > jogador.getPontosGeral()) {
            return "Máquina venceu!";
        } else {
            return "Empate!";
        }
    }

    // ==================== Getters ====================

    public Jogador getJogador() { return jogador; }
    public Jogador getMaquina() { return maquina; }
    public Carta getCartaAtualMaquina() { return cartaAtualMaquina; }
    public boolean[] getJogadorVenceuAtributo() { return jogadorVenceuAtributo; }
    public int getPontosRodadaJogador() { return pontosRodadaJogador; }
    public int getPontosRodadaMaquina() { return pontosRodadaMaquina; }
    public int getRodadaAtual() { return rodadaAtual; }
    public int getTotalRodadas() { return totalRodadas; }
}
