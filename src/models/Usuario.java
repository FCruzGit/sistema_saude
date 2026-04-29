//src/models/Usuario.java

package models;

import java.time.YearMonth;

public class Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private boolean isAdmin;
    private int cotaMensal;
    private int cotaUtilizada;
    private String mesReferencia;
    private String respostaSeguranca;
    private int tentativasErradas;

    public Usuario(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = false;
        this.cotaMensal = 10;
        this.cotaUtilizada = 0;
        this.mesReferencia = YearMonth.now().toString();
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.cotaMensal = 10;
        this.cotaUtilizada = 0;
        this.mesReferencia = YearMonth.now().toString();
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, int cotaMensal, int cotaUtilizada, String mesReferencia) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.cotaMensal = cotaMensal > 0 ? cotaMensal : 10;
        this.cotaUtilizada = cotaUtilizada;
        this.mesReferencia = mesReferencia != null ? mesReferencia : YearMonth.now().toString();
        this.respostaSeguranca = "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, int cotaMensal, int cotaUtilizada, String mesReferencia, String respostaSeguranca) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.cotaMensal = cotaMensal > 0 ? cotaMensal : 10;
        this.cotaUtilizada = cotaUtilizada;
        this.mesReferencia = mesReferencia != null ? mesReferencia : YearMonth.now().toString();
        this.respostaSeguranca = respostaSeguranca != null ? respostaSeguranca : "";
        this.tentativasErradas = 0;
    }

    public Usuario(String cpf, String nome, String email, String senha, boolean isAdmin, int cotaMensal, int cotaUtilizada, String mesReferencia, String respostaSeguranca, int tentativasErradas) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.cotaMensal = cotaMensal > 0 ? cotaMensal : 10;
        this.cotaUtilizada = cotaUtilizada;
        this.mesReferencia = mesReferencia != null ? mesReferencia : YearMonth.now().toString();
        this.respostaSeguranca = respostaSeguranca != null ? respostaSeguranca : "";
        this.tentativasErradas = tentativasErradas;
    }

    private void verificarRenovacaoCota() {
        String mesAtual = YearMonth.now().toString();
        if (!mesAtual.equals(mesReferencia)) {
            cotaUtilizada = 0;
            mesReferencia = mesAtual;
        }
    }

    public boolean temCotaDisponivel(int quantidade) {
        verificarRenovacaoCota();
        return (cotaUtilizada + quantidade) <= cotaMensal;
    }

    public void consumirCota(int quantidade) {
        verificarRenovacaoCota();
        cotaUtilizada += quantidade;
    }

    public int getCotasDisponiveis() {
        verificarRenovacaoCota();
        return cotaMensal - cotaUtilizada;
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public boolean isAdmin() { return isAdmin; }
    public int getCotaMensal() { return cotaMensal; }
    public void setCotaMensal(int cotaMensal) { this.cotaMensal = cotaMensal; }
    public int getCotaUtilizada() { return cotaUtilizada; }
    public void setCotaUtilizada(int cotaUtilizada) { this.cotaUtilizada = cotaUtilizada; }
    public String getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(String mesReferencia) { this.mesReferencia = mesReferencia; }
    public String getRespostaSeguranca() { return respostaSeguranca; }
    public void setSenha(String senha) { this.senha = senha; }
    public int getTentativasErradas() { return tentativasErradas; }
    public void setTentativasErradas(int tentativasErradas) { this.tentativasErradas = tentativasErradas; }
    public boolean isBloqueado() { return tentativasErradas >= 3; }
}
