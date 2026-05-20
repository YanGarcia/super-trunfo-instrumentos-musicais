package br.com.trabalho.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Representa um jogador (humano ou máquina).
 * Mantém a mão de cartas, cartas usadas e pontuação geral.
 */
public class Jogador {

    private String nome;
    private int pontosGeral;
    private List<Carta> cartas;
    private Set<Integer> cartasUsadasIds;
    private Random random;

    public Jogador(String nome) {
        this.nome = nome;
        this.pontosGeral = 0;
        this.cartas = new ArrayList<>();
        this.cartasUsadasIds = new HashSet<>();
        this.random = new Random();
    }

    /** Adiciona uma carta à mão do jogador */
    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }

    /** Incrementa 1 ponto na pontuação geral */
    public void adicionarPontoGeral() {
        pontosGeral++;
    }

    /** Marca uma carta como usada pelo seu ID */
    public void marcarCartaUsada(int cartaId) {
        cartasUsadasIds.add(cartaId);
    }

    /** Verifica se uma carta já foi usada */
    public boolean isCartaUsada(int cartaId) {
        return cartasUsadasIds.contains(cartaId);
    }

    /** Retorna as cartas que ainda não foram usadas */
    public List<Carta> getCartasDisponiveis() {
        List<Carta> disponiveis = new ArrayList<>();
        for (Carta c : cartas) {
            if (!cartasUsadasIds.contains(c.getId())) {
                disponiveis.add(c);
            }
        }
        return disponiveis;
    }

    /** Sorteia aleatoriamente uma carta entre as disponíveis */
    public Carta sortearCarta() {
        List<Carta> disponiveis = getCartasDisponiveis();
        if (disponiveis.isEmpty()) {
            return null;
        }
        return disponiveis.get(random.nextInt(disponiveis.size()));
    }

    /** Verifica se o jogador ainda tem cartas disponíveis */
    public boolean temCartasDisponiveis() {
        return !getCartasDisponiveis().isEmpty();
    }

    // ==================== Getters e Setters ====================

    public String getNome() { return nome; }
    public int getPontosGeral() { return pontosGeral; }
    public List<Carta> getCartas() { return cartas; }

    public void resetar() {
        pontosGeral = 0;
        cartasUsadasIds.clear();
        cartas.clear();
    }
}
