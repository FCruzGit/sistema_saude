//src/services/PedidoService.java

package services;

import models.Pedido;
import models.Remedio;
import models.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoService {
    private Map<String, Object> dados;

    public PedidoService(Map<String, Object> dados) {
        this.dados = dados;
    }

    @SuppressWarnings("unchecked")
    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        List<Map<String, Object>> arr = (List<Map<String, Object>>) dados.get("pedidos");
        for (Map<String, Object> obj : arr) {
            Map<String, Object> usuarioObj = (Map<String, Object>) obj.get("usuario");
            double saldo = usuarioObj.containsKey("saldo") ? ((Number) usuarioObj.get("saldo")).doubleValue() : 0.0;
            String cpf = usuarioObj.containsKey("cpf") ? (String) usuarioObj.get("cpf") : (String) usuarioObj.get("numeroSus");
            String respostaSeguranca = usuarioObj.containsKey("respostaSeguranca") ? (String) usuarioObj.get("respostaSeguranca") : "";
            Usuario usuario = new Usuario(
                cpf,
                (String) usuarioObj.get("nome"),
                (String) usuarioObj.get("email"),
                "",
                usuarioObj.containsKey("isAdmin") ? (Boolean) usuarioObj.get("isAdmin") : false,
                saldo, respostaSeguranca
            );
            String status = obj.containsKey("status") ? (String) obj.get("status") : "Pendente";
            String caminhoReceita = obj.containsKey("caminhoReceita") ? (String) obj.get("caminhoReceita") : null;
            Pedido pedido = new Pedido(((Number) obj.get("id")).intValue(), usuario, status, caminhoReceita);
            
            if (obj.containsKey("remedios")) {
                List<Map<String, Object>> remediosArr = (List<Map<String, Object>>) obj.get("remedios");
                for (Map<String, Object> remObj : remediosArr) {
                    Remedio remedio = new Remedio(
                        ((Number) remObj.get("id")).intValue(),
                        (String) remObj.get("nome"),
                        ((Number) remObj.get("preco")).doubleValue(),
                        remObj.containsKey("estoque") ? ((Number) remObj.get("estoque")).intValue() : 0,
                        (Boolean) remObj.get("precisaReceita"),
                        remObj.containsKey("ubsId") ? ((Number) remObj.get("ubsId")).intValue() : 1,
                        remObj.containsKey("imagem") ? (String) remObj.get("imagem") : ""
                    );
                    pedido.adicionarRemedio(remedio);
                }
            }
            pedidos.add(pedido);
        }
        return pedidos;
    }

    @SuppressWarnings("unchecked")
    public Pedido criarPedido(Usuario usuario) {
        List<Map<String, Object>> pedidos = (List<Map<String, Object>>) dados.get("pedidos");
        int novoId = pedidos.size() + 1;
        
        Map<String, Object> usuarioObj = new HashMap<>();
        usuarioObj.put("cpf", usuario.getCpf());
        usuarioObj.put("nome", usuario.getNome());
        usuarioObj.put("email", usuario.getEmail());
        
        Map<String, Object> pedido = new HashMap<>();
        pedido.put("id", novoId);
        pedido.put("usuario", usuarioObj);
        pedido.put("status", "Pendente");
        pedido.put("remedios", new ArrayList<Map<String, Object>>());
        pedido.put("caminhoReceita", null);
        pedidos.add(pedido);
        return new Pedido(novoId, usuario);
    }

    @SuppressWarnings("unchecked")
    public void salvarRemediosPedido(int pedidoId, List<Remedio> remedios) {
        List<Map<String, Object>> pedidos = (List<Map<String, Object>>) dados.get("pedidos");
        for (Map<String, Object> obj : pedidos) {
            if (((Number) obj.get("id")).intValue() == pedidoId) {
                List<Map<String, Object>> remediosArr = new ArrayList<>();
                for (Remedio r : remedios) {
                    Map<String, Object> remObj = new HashMap<>();
                    remObj.put("id", r.getId());
                    remObj.put("nome", r.getNome());
                    remObj.put("preco", r.getPreco());
                    remObj.put("precisaReceita", r.isPrecisaReceita());
                    remediosArr.add(remObj);
                }
                obj.put("remedios", remediosArr);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void salvarReceitaPedido(int pedidoId, String caminhoReceita) {
        List<Map<String, Object>> pedidos = (List<Map<String, Object>>) dados.get("pedidos");
        for (Map<String, Object> obj : pedidos) {
            if (((Number) obj.get("id")).intValue() == pedidoId) {
                obj.put("caminhoReceita", caminhoReceita);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void aprovarPedido(int id) {
        List<Map<String, Object>> pedidos = (List<Map<String, Object>>) dados.get("pedidos");
        for (Map<String, Object> obj : pedidos) {
            if (((Number) obj.get("id")).intValue() == id) {
                obj.put("status", "Aprovado");
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void cancelarPedido(int id) {
        List<Map<String, Object>> pedidos = (List<Map<String, Object>>) dados.get("pedidos");
        for (Map<String, Object> obj : pedidos) {
            if (((Number) obj.get("id")).intValue() == id) {
                obj.put("status", "Cancelado");
                break;
            }
        }
    }
}
