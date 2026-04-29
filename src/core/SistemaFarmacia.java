//src/core/SistemaFarmacia.java

package core;

import database.GerenciadorJSON;
import models.Pedido;
import models.Remedio;
import models.UBS;
import models.Usuario;
import services.*;

import java.util.List;
import java.util.Map;

public class SistemaFarmacia {
    private static SistemaFarmacia instance;
    private Map<String, Object> dados;
    private GerenciadorJSON gerenciador;
    
    private AutenticacaoService autenticacaoService;
    private UsuarioService usuarioService;
    private UBSService ubsService;
    private RemedioService remedioService;
    private PedidoService pedidoService;

    private SistemaFarmacia() {
        System.out.println("Inicializando SistemaFarmacia...");
        gerenciador = new GerenciadorJSON();
        dados = gerenciador.carregarDados();
        
        autenticacaoService = new AutenticacaoService(dados);
        usuarioService = new UsuarioService(dados);
        ubsService = new UBSService(dados);
        remedioService = new RemedioService(dados);
        pedidoService = new PedidoService(dados);
        
        System.out.println("SistemaFarmacia inicializado!");
    }

    public static SistemaFarmacia getInstance() {
        if (instance == null) instance = new SistemaFarmacia();
        return instance;
    }

    private void salvar() {
        gerenciador.salvarDados(dados);
    }

    public Usuario autenticar(String cpf, String senha) {
        Usuario usuario = autenticacaoService.autenticar(cpf, senha);
        salvar();
        return usuario;
    }

    public void cadastrarUsuario(String cpf, String nome, String email, String senha, String respostaSeguranca) {
        autenticacaoService.cadastrarUsuario(cpf, nome, email, senha, respostaSeguranca);
        salvar();
    }

    public void alterarSenha(String cpf, String novaSenha) {
        autenticacaoService.alterarSenha(cpf, novaSenha);
        salvar();
    }

    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    public Usuario buscarPorCpf(String cpf) {
        return usuarioService.buscarPorCpf(cpf);
    }

    public void adicionarFundos(String cpf, double valor) {
        usuarioService.adicionarFundos(cpf, valor);
        salvar();
    }

    public void diminuirSaldo(String cpf, double valor) {
        usuarioService.diminuirSaldo(cpf, valor);
        salvar();
    }

    public void resetarTentativas(String cpf) {
        usuarioService.resetarTentativas(cpf);
        salvar();
    }

    public void incrementarTentativasErradas(String cpf) {
        usuarioService.incrementarTentativasErradas(cpf);
        salvar();
    }

    public List<UBS> getUBS() {
        return ubsService.getUBS();
    }

    public void cadastrarUBS(String nome, String imagem, String logradouro, String numero, String bairro, String estado, String cep) {
        ubsService.cadastrarUBS(nome, imagem, logradouro, numero, bairro, estado, cep);
        salvar();
    }

    public void atualizarUBS(int id, String nome, String imagem, String logradouro, String numero, String bairro, String estado, String cep) {
        ubsService.atualizarUBS(id, nome, imagem, logradouro, numero, bairro, estado, cep);
        salvar();
    }

    public void deletarUBS(int id) {
        ubsService.deletarUBS(id);
        salvar();
    }

    public List<Remedio> getRemedios() {
        return remedioService.getRemedios();
    }

    public List<Remedio> getRemediosPorUBS(int ubsId) {
        return remedioService.getRemediosPorUBS(ubsId);
    }

    public void cadastrarRemedio(String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem) {
        remedioService.cadastrarRemedio(nome, preco, estoque, precisaReceita, ubsId, imagem);
        salvar();
    }

    public void atualizarRemedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String imagem) {
        remedioService.atualizarRemedio(id, nome, preco, estoque, precisaReceita, ubsId, imagem);
        salvar();
    }

    public void deletarRemedio(int id) {
        remedioService.deletarRemedio(id);
        salvar();
    }

    public void diminuirEstoque(int remedioId, int quantidade) {
        remedioService.diminuirEstoque(remedioId, quantidade);
        salvar();
    }

    public List<Pedido> getPedidos() {
        return pedidoService.getPedidos();
    }

    public Pedido criarPedido(Usuario usuario) {
        Pedido pedido = pedidoService.criarPedido(usuario);
        salvar();
        return pedido;
    }

    public void salvarRemediosPedido(int pedidoId, List<Remedio> remedios) {
        pedidoService.salvarRemediosPedido(pedidoId, remedios);
        salvar();
    }

    public void salvarReceitaPedido(int pedidoId, String caminhoReceita) {
        pedidoService.salvarReceitaPedido(pedidoId, caminhoReceita);
        salvar();
    }

    public void aprovarPedido(int id) {
        pedidoService.aprovarPedido(id);
        salvar();
    }

    public void cancelarPedido(int id) {
        pedidoService.cancelarPedido(id);
        salvar();
    }

    public void enviarReceita(String nomeCliente, String arquivo) {}
}
