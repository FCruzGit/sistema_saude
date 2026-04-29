public class Receita {
    private int id;
    private String nomeCliente;
    private String arquivo;
    private boolean validada;

    public Receita(int id, String nomeCliente, String arquivo) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.arquivo = arquivo;
        this.validada = false;
    }

    public int getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public String getArquivo() { return arquivo; }
    public boolean isValidada() { return validada; }
    public void setValidada(boolean validada) { this.validada = validada; }
}
