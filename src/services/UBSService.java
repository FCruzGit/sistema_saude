//src/services/UBSService.java

package services;

import models.UBS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UBSService {
    private Map<String, Object> dados;

    public UBSService(Map<String, Object> dados) {
        this.dados = dados;
    }

    @SuppressWarnings("unchecked")
    public List<UBS> getUBS() {
        List<UBS> ubsList = new ArrayList<>();
        if (!dados.containsKey("ubs")) {
            dados.put("ubs", new ArrayList<Map<String, Object>>());
        }
        List<Map<String, Object>> arr = (List<Map<String, Object>>) dados.get("ubs");
        for (Map<String, Object> obj : arr) {
            String imagem = obj.containsKey("imagem") ? (String) obj.get("imagem") : "";
            String logradouro = obj.containsKey("logradouro") ? (String) obj.get("logradouro") : "";
            String numero = obj.containsKey("numero") ? (String) obj.get("numero") : "";
            String bairro = obj.containsKey("bairro") ? (String) obj.get("bairro") : "";
            String estado = obj.containsKey("estado") ? (String) obj.get("estado") : "";
            String cep = obj.containsKey("cep") ? (String) obj.get("cep") : "";
            
            String endereco = "";
            if (!logradouro.isEmpty() || !numero.isEmpty() || !bairro.isEmpty()) {
                endereco = logradouro + ", " + numero + " - " + bairro;
            } else if (obj.containsKey("endereco")) {
                endereco = (String) obj.get("endereco");
            }
            
            ubsList.add(new UBS(
                ((Number) obj.get("id")).intValue(),
                (String) obj.get("nome"),
                endereco, imagem, logradouro, numero, bairro, estado, cep
            ));
        }
        return ubsList;
    }

    @SuppressWarnings("unchecked")
    public void cadastrarUBS(String nome, String imagem, String logradouro, String numero, String bairro, String estado, String cep) {
        List<Map<String, Object>> ubs = (List<Map<String, Object>>) dados.get("ubs");
        int novoId = ubs.size() + 1;
        
        Map<String, Object> novaUbs = new HashMap<>();
        novaUbs.put("id", novoId);
        novaUbs.put("nome", nome);
        novaUbs.put("imagem", imagem != null ? imagem : "");
        novaUbs.put("logradouro", logradouro != null ? logradouro : "");
        novaUbs.put("numero", numero != null ? numero : "");
        novaUbs.put("bairro", bairro != null ? bairro : "");
        novaUbs.put("estado", estado != null ? estado : "");
        novaUbs.put("cep", cep != null ? cep : "");
        ubs.add(novaUbs);
    }

    @SuppressWarnings("unchecked")
    public void atualizarUBS(int id, String nome, String imagem, String logradouro, String numero, String bairro, String estado, String cep) {
        List<Map<String, Object>> ubs = (List<Map<String, Object>>) dados.get("ubs");
        for (Map<String, Object> obj : ubs) {
            if (((Number) obj.get("id")).intValue() == id) {
                obj.put("nome", nome);
                obj.put("imagem", imagem != null ? imagem : "");
                obj.put("logradouro", logradouro != null ? logradouro : "");
                obj.put("numero", numero != null ? numero : "");
                obj.put("bairro", bairro != null ? bairro : "");
                obj.put("estado", estado != null ? estado : "");
                obj.put("cep", cep != null ? cep : "");
                obj.remove("endereco");
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void deletarUBS(int id) {
        List<Map<String, Object>> ubs = (List<Map<String, Object>>) dados.get("ubs");
        ubs.removeIf(obj -> ((Number) obj.get("id")).intValue() == id);
    }
}
