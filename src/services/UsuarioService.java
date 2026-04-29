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
            double saldo = obj.containsKey("saldo") ? ((Number) obj.get("saldo")).doubleValue() : 0.0;
            String respostaSeguranca = obj.containsKey("respostaSeguranca") ? (String) obj.get("respostaSeguranca") : "";
            int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
            usuarios.add(new Usuario(
                (String) obj.get("cpf"),
                (String) obj.get("nome"),
                (String) obj.get("email"),
                (String) obj.get("senha"),
                obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false,
                saldo, respostaSeguranca, tentativasErradas
            ));
        }
        return usuarios;
    }

    @SuppressWarnings("unchecked")
    public Usuario buscarPorCpf(String cpf) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").toString().toLowerCase().equals(cpf.toLowerCase())) {
                double saldo = obj.containsKey("saldo") ? ((Number) obj.get("saldo")).doubleValue() : 0.0;
                String respostaSeguranca = obj.containsKey("respostaSeguranca") ? (String) obj.get("respostaSeguranca") : "";
                int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
                return new Usuario(
                    (String) obj.get("cpf"),
                    (String) obj.get("nome"),
                    (String) obj.get("email"),
                    (String) obj.get("senha"),
                    obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false,
                    saldo, respostaSeguranca, tentativasErradas
                );
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void adicionarFundos(String cpf, double valor) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                double saldoAtual = obj.containsKey("saldo") ? ((Number) obj.get("saldo")).doubleValue() : 0.0;
                obj.put("saldo", saldoAtual + valor);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void diminuirSaldo(String cpf, double valor) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                double saldoAtual = obj.containsKey("saldo") ? ((Number) obj.get("saldo")).doubleValue() : 0.0;
                obj.put("saldo", Math.max(0, saldoAtual - valor));
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
