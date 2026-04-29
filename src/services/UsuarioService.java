//src/services/UsuarioService.java

package services;

import models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioService {
    private Map<String, Object> dados;

    public UsuarioService(Map<String, Object> dados) {
        this.dados = dados;
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Map<String, Object>> arr = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : arr) {
            int cotaMensal = obj.containsKey("cotaMensal") ? ((Number) obj.get("cotaMensal")).intValue() : 10;
            int cotaUtilizada = obj.containsKey("cotaUtilizada") ? ((Number) obj.get("cotaUtilizada")).intValue() : 0;
            String mesReferencia = obj.containsKey("mesReferencia") ? (String) obj.get("mesReferencia") : null;
            String respostaSeguranca = obj.containsKey("respostaSeguranca") ? (String) obj.get("respostaSeguranca") : "";
            int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
            usuarios.add(new Usuario(
                (String) obj.get("cpf"),
                (String) obj.get("nome"),
                (String) obj.get("email"),
                (String) obj.get("senha"),
                obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false,
                cotaMensal, cotaUtilizada, mesReferencia, respostaSeguranca, tentativasErradas
            ));
        }
        return usuarios;
    }

    @SuppressWarnings("unchecked")
    public Usuario buscarPorCpf(String cpf) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").toString().toLowerCase().equals(cpf.toLowerCase())) {
                int cotaMensal = obj.containsKey("cotaMensal") ? ((Number) obj.get("cotaMensal")).intValue() : 10;
                int cotaUtilizada = obj.containsKey("cotaUtilizada") ? ((Number) obj.get("cotaUtilizada")).intValue() : 0;
                String mesReferencia = obj.containsKey("mesReferencia") ? (String) obj.get("mesReferencia") : null;
                String respostaSeguranca = obj.containsKey("respostaSeguranca") ? (String) obj.get("respostaSeguranca") : "";
                int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
                return new Usuario(
                    (String) obj.get("cpf"),
                    (String) obj.get("nome"),
                    (String) obj.get("email"),
                    (String) obj.get("senha"),
                    obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false,
                    cotaMensal, cotaUtilizada, mesReferencia, respostaSeguranca, tentativasErradas
                );
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void adicionarCotas(String cpf, int quantidade) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                int cotaMensalAtual = obj.containsKey("cotaMensal") ? ((Number) obj.get("cotaMensal")).intValue() : 10;
                obj.put("cotaMensal", cotaMensalAtual + quantidade);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void consumirCota(String cpf, int quantidade) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                int cotaUtilizadaAtual = obj.containsKey("cotaUtilizada") ? ((Number) obj.get("cotaUtilizada")).intValue() : 0;
                obj.put("cotaUtilizada", cotaUtilizadaAtual + quantidade);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void devolverCota(String cpf, int quantidade) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                int cotaUtilizadaAtual = obj.containsKey("cotaUtilizada") ? ((Number) obj.get("cotaUtilizada")).intValue() : 0;
                int novoValor = Math.max(0, cotaUtilizadaAtual - quantidade);
                obj.put("cotaUtilizada", novoValor);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void resetarTentativas(String cpf) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                obj.put("tentativasErradas", 0);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void incrementarTentativasErradas(String cpf) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                int tentativas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
                obj.put("tentativasErradas", tentativas + 1);
                break;
            }
        }
    }
}
