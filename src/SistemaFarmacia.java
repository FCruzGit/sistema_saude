import java.util.*;

public class SistemaFarmacia {
    private static SistemaFarmacia instance;
    private Map<String, Object> dados;

    private SistemaFarmacia() {
        System.out.println("Inicializando SistemaFarmacia...");
        dados = GerenciadorJSON.carregarDados();
        System.out.println("SistemaFarmacia inicializado!");
    }

    public static SistemaFarmacia getInstance() {
        if (instance == null) instance = new SistemaFarmacia();
        return instance;
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
                ubsId,
                imagem,
                descricao,
                tipo,
                gramatura
            ));
        }
        return remedios;
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
            
            // Construir endereco a partir dos campos separados
            String endereco = "";
            if (!logradouro.isEmpty() || !numero.isEmpty() || !bairro.isEmpty()) {
                endereco = logradouro + ", " + numero + " - " + bairro;
            } else if (obj.containsKey("endereco")) {
                endereco = (String) obj.get("endereco");
            }
            
            ubsList.add(new UBS(
                ((Number) obj.get("id")).intValue(),
                (String) obj.get("nome"),
                endereco,
                imagem,
                logradouro,
                numero,
                bairro,
                estado,
                cep
            ));
        }
        return ubsList;
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
                saldo,
                respostaSeguranca
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
    public List<Receita> getReceitas() {
        List<Receita> receitas = new ArrayList<>();
        List<Map<String, Object>> arr = (List<Map<String, Object>>) dados.get("receitas");
        for (Map<String, Object> obj : arr) {
            Receita receita = new Receita(
                ((Number) obj.get("id")).intValue(),
                (String) obj.get("nomeCliente"),
                (String) obj.get("arquivo")
            );
            receita.setValidada((Boolean) obj.get("validada"));
            receitas.add(receita);
        }
        return receitas;
    }

    @SuppressWarnings("unchecked")
    public Usuario autenticar(String cpf, String senha) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        for (Map<String, Object> obj : usuarios) {
            if (obj.get("cpf").toString().toLowerCase().equals(cpf.toLowerCase())) {
                int tentativasErradas = obj.containsKey("tentativasErradas") ? ((Number) obj.get("tentativasErradas")).intValue() : 0;
                
                // Verificar se está bloqueado
                if (tentativasErradas >= 3) {
                    return null; // Retorna null para indicar bloqueio
                }
                
                if (obj.get("senha").equals(senha)) {
                    // Senha correta - resetar tentativas
                    obj.put("tentativasErradas", 0);
                    GerenciadorJSON.salvarDados(dados);
                    
                    double saldo = obj.containsKey("saldo") ? ((Number) obj.get("saldo")).doubleValue() : 0.0;
                    String respostaSeguranca = obj.containsKey("respostaSeguranca") ? (String) obj.get("respostaSeguranca") : "";
                    return new Usuario(
                        (String) obj.get("cpf"),
                        (String) obj.get("nome"),
                        (String) obj.get("email"),
                        (String) obj.get("senha"),
                        obj.containsKey("isAdmin") ? (Boolean) obj.get("isAdmin") : false,
                        saldo,
                        respostaSeguranca,
                        0
                    );
                } else {
                    // Senha incorreta - incrementar tentativas
                    obj.put("tentativasErradas", tentativasErradas + 1);
                    GerenciadorJSON.salvarDados(dados);
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void cadastrarUsuario(String cpf, String nome, String email, String senha, String respostaSeguranca) {
        List<Map<String, Object>> usuarios = (List<Map<String, Object>>) dados.get("usuarios");
        
        // Validar CPF
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos!");
        }
        if (!cpf.matches("[0-9]+")) {
            throw new IllegalArgumentException("CPF deve conter apenas números!");
        }

        // Validar senha
        if (senha.length() < 9) {
            throw new IllegalArgumentException("A senha é muito fraca");
        }
        if (senha.matches("[0-9]+")) {
            throw new IllegalArgumentException("A senha não atende aos requisitos minimos de complexidade");
        }


        // Validar se CPF já existe
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
                obj.remove("endereco"); // Remove campo antigo se existir
                break;
            }
        }
        GerenciadorJSON.salvarDados(dados);
    }

    @SuppressWarnings("unchecked")
    public void deletarUBS(int id) {
        List<Map<String, Object>> ubs = (List<Map<String, Object>>) dados.get("ubs");
        ubs.removeIf(obj -> ((Number) obj.get("id")).intValue() == id);
        GerenciadorJSON.salvarDados(dados);
    }

    @SuppressWarnings("unchecked")
    public void deletarRemedio(int id) {
        List<Map<String, Object>> remedios = (List<Map<String, Object>>) dados.get("remedios");
        remedios.removeIf(obj -> ((Number) obj.get("id")).intValue() == id);
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
    }

    @SuppressWarnings("unchecked")
    public void enviarReceita(String nomeCliente, String arquivo) {
        List<Map<String, Object>> receitas = (List<Map<String, Object>>) dados.get("receitas");
        int novoId = receitas.size() + 1;
        
        Map<String, Object> receita = new HashMap<>();
        receita.put("id", novoId);
        receita.put("nomeCliente", nomeCliente);
        receita.put("arquivo", arquivo);
        receita.put("validada", false);
        
        receitas.add(receita);
        GerenciadorJSON.salvarDados(dados);
    }

    @SuppressWarnings("unchecked")
    public void validarReceita(int id) {
        List<Map<String, Object>> receitas = (List<Map<String, Object>>) dados.get("receitas");
        for (Map<String, Object> obj : receitas) {
            if (((Number) obj.get("id")).intValue() == id) {
                obj.put("validada", true);
                break;
            }
        }
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
                saldo,
                respostaSeguranca,
                tentativasErradas
            ));
        }
        return usuarios;
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
                    saldo,
                    respostaSeguranca,
                    tentativasErradas
                );
            }
        }
        return null;
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
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
        GerenciadorJSON.salvarDados(dados);
    }
}
