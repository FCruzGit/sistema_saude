public class Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private boolean isAdmin;
    private double saldo;
    private String respostaSeguranca;
    private int tentativasErradas;

    public Usuario(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
        this.saldo = 0.0;
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.saldo = 0.0;
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, double saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.saldo = saldo;
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, double saldo, String respostaSeguranca) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.saldo = saldo;
        this.respostaSeguranca = respostaSeguranca != null ? respostaSeguranca : "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, double saldo, String respostaSeguranca, int tentativasErradas) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.saldo = saldo;
        this.respostaSeguranca = respostaSeguranca != null ? respostaSeguranca : "";
        this.tentativasErradas = tentativasErradas;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public boolean isAdmin() { return isAdmin; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getRespostaSeguranca() { return respostaSeguranca; }
    public void setSenha(String senha) { this.senha = senha; }
    public int getTentativasErradas() { return tentativasErradas; }
    public void setTentativasErradas(int tentativasErradas) { this.tentativasErradas = tentativasErradas; }
    public boolean isBloqueado() { return tentativasErradas >= 3; }
}
