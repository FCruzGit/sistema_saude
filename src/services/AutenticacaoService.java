//src/services/AutenticacaoService.java

package services;

import models.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutenticacaoService {
    private Map<String, Object> dados;

    public AutenticacaoService(Map<String, Object> dados) {
        this.dados = dados;
    }

    @SuppressWarnings("unchecked")
    public Usuario autenticar(String cpf, String senha) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").toString().toLowerCase().equals(cpf.toLowerCase())) {
                boolean isAdmin = obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false;
                int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
                
                // Administrador nunca é bloqueado
                if (!isAdmin && tentativasErradas >= 3) return null;
                
                if (obj.get("senha").equals(senha)) {
                    obj.put("tentativasErradas", 0);
                    return construirUsuario(obj);
                } else {
                    // Só incrementa tentativas se não for admin
                    if (!isAdmin) {
                        obj.put("tentativasErradas", tentativasErradas + 1);
                    }
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void cadastrarUsuario(String cpf, String nome, String email, String senha, String respostaSeguranca) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos!");
        }
        if (senha.length() < 9 || senha.matches("[0-9]+")) {
            throw new IllegalArgumentException("A senha não atende aos requisitos mínimos de complexidade");
        }
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                throw new IllegalArgumentException("CPF já cadastrado!");
            }
        }
        
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("cpf", cpf);
        usuario.put("nome", nome);
        usuario.put("email", email);
        usuario.put("senha", senha);
        usuario.put("isAdmin", false);
        usuario.put("saldo", 0.0);
        usuario.put("respostaSeguranca", respostaSeguranca);
        usuario.put("tentativasErradas", 0);
        usuarios.add(usuario);
    }

    @SuppressWarnings("unchecked")
    public void alterarSenha(String cpf, String novaSenha) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").equals(cpf)) {
                obj.put("senha", novaSenha);
                obj.put("tentativasErradas", 0);
                break;
            }
        }
    }

    private Usuario construirUsuario(Map<String, Object> obj) {
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
