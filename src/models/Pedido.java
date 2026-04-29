//src/models/Pedido.java

package models;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Usuario usuario;
    private List<Remedio> remedios;
    private String status;
    private String caminhoReceita;

    public Pedido(int id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
        this.remedios = new ArrayList<>();
        this.status = "Pendente";
        this.caminhoReceita = null;
    }

    public Pedido(int id, Usuario usuario, String status) {
        this.id = id;
        this.usuario = usuario;
        this.remedios = new ArrayList<>();
        this.status = status;
        this.caminhoReceita = null;
    }

    public Pedido(int id, Usuario usuario, String status, String caminhoReceita) {
        this.id = id;
        this.usuario = usuario;
        this.remedios = new ArrayList<>();
        this.status = status;
        this.caminhoReceita = caminhoReceita;
    }

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public List<Remedio> getRemedios() { return remedios; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCaminhoReceita() { return caminhoReceita; }
    public void setCaminhoReceita(String caminhoReceita) { this.caminhoReceita = caminhoReceita; }
    
    public void adicionarRemedio(Remedio remedio) {
        remedios.add(remedio);
    }

    public boolean precisaReceita() {
        for (Remedio r : remedios) {
            if (r.isPrecisaReceita()) {
                return true;
            }
        }
        return false;
    }
}
