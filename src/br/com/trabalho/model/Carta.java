package br.com.trabalho.model;

/**
 * Representa uma carta do baralho Super Trunfo.
 * Cada carta é um instrumento musical com 5 atributos comparáveis.
 */
public class Carta {

    /** Nomes dos atributos para exibição na interface */
    public static final String[] NOMES_ATRIBUTOS = {
        "Ano de Cria\u00e7\u00e3o", "Peso (kg)", "Pre\u00e7o (R$)", "Popularidade", "Volume (dB)"
    };

    private int id;
    private String nome;
    private String codigo;
    private int anoCriacao;
    private double pesoKg;
    private double preco;
    private int popularidade;
    private int volumeDb;

    public Carta() {}

    public Carta(int id, String nome, String codigo, int anoCriacao,
                 double pesoKg, double preco, int popularidade, int volumeDb) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.anoCriacao = anoCriacao;
        this.pesoKg = pesoKg;
        this.preco = preco;
        this.popularidade = popularidade;
        this.volumeDb = volumeDb;
    }

    /**
     * Retorna o valor numérico de um atributo pelo índice (0–4).
     * Usado para comparação entre cartas.
     */
    public double getValorAtributo(int indice) {
        switch (indice) {
            case 0: return anoCriacao;
            case 1: return pesoKg;
            case 2: return preco;
            case 3: return popularidade;
            case 4: return volumeDb;
            default: throw new IllegalArgumentException("Índice inválido: " + indice);
        }
    }

    /**
     * Formata o valor de um atributo para exibição.
     */
    public String getValorFormatado(int indice) {
        switch (indice) {
            case 0: return String.valueOf(anoCriacao);
            case 1: return String.format("%.1f", pesoKg);
            case 2: return String.format("%.0f", preco);
            case 3: return String.valueOf(popularidade);
            case 4: return String.valueOf(volumeDb);
            default: return "";
        }
    }

    // ==================== Getters e Setters ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getAnoCriacao() { return anoCriacao; }
    public void setAnoCriacao(int anoCriacao) { this.anoCriacao = anoCriacao; }

    public double getPesoKg() { return pesoKg; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getPopularidade() { return popularidade; }
    public void setPopularidade(int popularidade) { this.popularidade = popularidade; }

    public int getVolumeDb() { return volumeDb; }
    public void setVolumeDb(int volumeDb) { this.volumeDb = volumeDb; }

    /** Retorna o nome do arquivo de imagem (ex: "I1.png") */
    public String getNomeImagem() {
        return codigo + ".png";
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
