//src/models/Remedio.java

package models;

public class Remedio {
    private int id;
    private String nome;
    private double preco;
    private int estoque;
    private boolean precisaReceita;
    private int ubsId;
    private String imagem;
    private String descricao;
    private String tipo;
    private String gramatura;

    public Remedio(int id, String nome, double preco, int estoque, boolean precisaReceita) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precisaReceita = precisaReceita;
        this.ubsId = 1;
        this.imagem = "";
        this.descricao = "";
        this.tipo = "";
        this.gramatura = "";
    }

    public Remedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precisaReceita = precisaReceita;
        this.ubsId = ubsId;
        this.imagem = "";
        this.descricao = "";
        this.tipo = "";
        this.gramatura = "";
    }

    public Remedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precisaReceita = precisaReceita;
        this.ubsId = ubsId;
        this.imagem = imagem != null ? imagem : "";
        this.descricao = "";
        this.tipo = "";
        this.gramatura = "";
    }

    public Remedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem, String descricao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precisaReceita = precisaReceita;
        this.ubsId = ubsId;
        this.imagem = imagem != null ? imagem : "";
        this.descricao = descricao != null ? descricao : "";
        this.tipo = "";
        this.gramatura = "";
    }

    public Remedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem, String descricao, String tipo, String gramatura) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.precisaReceita = precisaReceita;
        this.ubsId = ubsId;
        this.imagem = imagem != null ? imagem : "";
        this.descricao = descricao != null ? descricao : "";
        this.tipo = tipo != null ? tipo : "";
        this.gramatura = gramatura != null ? gramatura : "";
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }
    public boolean isPrecisaReceita() { return precisaReceita; }
    public int getUbsId() { return ubsId; }
    public String getImagem() { return imagem; }
    public String getDescricao() { return descricao; }
    public String getTipo() { return tipo; }
    public String getGramatura() { return gramatura; }
    
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public void setPrecisaReceita(boolean precisaReceita) { this.precisaReceita = precisaReceita; }
    public void setUbsId(int ubsId) { this.ubsId = ubsId; }
    public void setImagem(String imagem) { this.imagem = imagem; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setGramatura(String gramatura) { this.gramatura = gramatura; }
}
