public class UBS {
    private int id;
    private String nome;
    private String endereco;
    private String imagem;
    private String logradouro;
    private String numero;
    private String bairro;
    private String estado;
    private String cep;

    public UBS(int id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.imagem = "";
        this.logradouro = "";
        this.numero = "";
        this.bairro = "";
        this.estado = "";
        this.cep = "";
    }

    public UBS(int id, String nome, String endereco, String imagem) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.imagem = imagem;
        this.logradouro = "";
        this.numero = "";
        this.bairro = "";
        this.estado = "";
        this.cep = "";
    }

    public UBS(int id, String nome, String endereco, String imagem, String logradouro, String numero, String bairro, String estado, String cep) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.imagem = imagem;
        this.logradouro = logradouro != null ? logradouro : "";
        this.numero = numero != null ? numero : "";
        this.bairro = bairro != null ? bairro : "";
        this.estado = estado != null ? estado : "";
        this.cep = cep != null ? cep : "";
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getImagem() { return imagem; }
    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }
    
    public void setImagem(String imagem) { this.imagem = imagem; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCep(String cep) { this.cep = cep; }
}
