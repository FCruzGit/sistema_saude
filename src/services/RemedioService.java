package services;

import models.*;
import java.util.*;

public class RemedioService {
    private Map<String, Object> dados;

    public RemedioService(Map<String, Object> dados) {
        this.dados = dados;
    }

    @SuppressWarnings("unchecked")
    public List<Remedio> getRemedios() {
        List<Remedio> remedios = new ArrayList<>();
        List<Map<String, Object>> arr = (List<Map<String, Object>>) dados.get("remedios");
        for (Map<String, Object> obj : arr) {
            int ubsId = obj.containsKey("ubsId") ? ((Number) obj.get("ubsId")).intValue() : 1;
            String imagem = obj.containsKey("imagem") ? (String) obj.get("imagem") : "";
            String descricao = obj.containsKey("descricao") ? (String) obj.get("descricao") : "";
            String tipo = obj.containsKey("tipo") ? (String) obj.get("tipo") : "";
            String gramatura = obj.containsKey("gramatura") ? (String) obj.get("gramatura") : "";
            
            remedios.add(new Remedio(
                ((Number) obj.get("id")).intValue(),
                (String) obj.get("nome"),
                ((Number) obj.get("preco")).doubleValue(),
                ((Number) obj.get("estoque")).intValue(),
                (Boolean) obj.get("precisaReceita"),
                ubsId, imagem, descricao, tipo, gramatura
            ));
        }
        return remedios;
    }

    public List<Remedio> getRemediosPorUBS(int ubsId) {
        List<Remedio> todos = getRemedios();
        List<Remedio> filtrados = new ArrayList<>();
        for (Remedio r : todos) {
            if (r.getUbsId() == ubsId) {
                filtrados.add(r);
            }
        }
        return filtrados;
    }

    @SuppressWarnings("unchecked")
    public void cadastrarRemedio(String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        int novoId = 1;
        for (Map<String, Object> r : remedios) {
            int id = ((Number) r.get("id")).intValue();
            if (id >= novoId) novoId = id + 1;
        }
        
        Map<String, Object> remedio = new HashMap<>();
        remedio.put("id", novoId);
        remedio.put("nome", nome);
        remedio.put("preco", preco);
        remedio.put("estoque", estoque);
        remedio.put("precisaReceita", precisaReceita);
        remedio.put("ubsId", ubsId);
        remedio.put("imagem", imagem != null ? imagem : "");
        remedios.add(remedio);
    }

    @SuppressWarnings("unchecked")
    public void atualizarRemedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        for (Map<String, Object> obj : remedios) {
            if (((Number) obj.get("id")).intValue() == id) {
                obj.put("nome", nome);
                obj.put("preco", preco);
                obj.put("estoque", estoque);
                obj.put("precisaReceita", precisaReceita);
                obj.put("ubsId", ubsId);
                obj.put("imagem", imagem != null ? imagem : "");
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void deletarRemedio(int id) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        remedios.removeIf(obj -> ((Number) obj.get("id")).intValue() == id);
    }

    @SuppressWarnings("unchecked")
    public void diminuirEstoque(int remedioId, int quantidade) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        for (Map<String, Object> obj : remedios) {
            if (((Number) obj.get("id")).intValue() == remedioId) {
                int estoqueAtual = ((Number) obj.get("estoque")).intValue();
                int novoEstoque = Math.max(0, estoqueAtual - quantidade);
                obj.put("estoque", novoEstoque);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void aumentarEstoque(int remedioId, int quantidade) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        for (Map<String, Object> obj : remedios) {
            if (((Number) obj.get("id")).intValue() == remedioId) {
                int estoqueAtual = ((Number) obj.get("estoque")).intValue();
                obj.put("estoque", estoqueAtual + quantidade);
                break;
            }
        }
    }
}
