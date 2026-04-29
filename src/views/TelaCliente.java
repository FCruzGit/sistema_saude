//src/views/TelaCliente.java

package views;

import core.SistemaFarmacia;
import models.Pedido;
import models.Remedio;
import models.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelaCliente extends JFrame {
    private static final Color COR_AZUL_SUS = new Color(0, 94, 184);
    private static final Color COR_FUNDO = new Color(232, 244, 248);
    private static final Color COR_BRANCO = Color.WHITE;
    private static final Color COR_VERDE = new Color(0, 150, 0);
    
    private SistemaFarmacia sistema = SistemaFarmacia.getInstance();
    private ArrayList<Remedio> carrinho = new ArrayList<>();
    private Usuario usuarioAtual;
    private int ubsId;

    public TelaCliente(Usuario usuario, int ubsId) {
        this.usuarioAtual = usuario;
        this.ubsId = ubsId;
        setTitle("Sistema SUS - Farmácia Popular");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COR_FUNDO);

        JPanel header = criarHeader();
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel conteudo = criarConteudo();
        mainPanel.add(conteudo, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel criarHeader() {
        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setBackground(COR_FUNDO);
        headerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 50, 0, 50));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COR_BRANCO);
        header.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setBackground(COR_BRANCO);

        try {
            File logoFile = new File("assets/logo.png");
            if (logoFile.exists()) {
                Image img = ImageIO.read(logoFile);
                Image scaledImg = img.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
                JLabel logo = new JLabel(new ImageIcon(scaledImg));
                leftPanel.add(logo);
            } else {
                JLabel lblSus = new JLabel("SUS");
                lblSus.setFont(new Font("Arial", Font.BOLD, 24));
                lblSus.setForeground(COR_AZUL_SUS);
                leftPanel.add(lblSus);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar logo: " + e.getMessage());
        }

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(COR_BRANCO);

        JLabel lblUsuario = new JLabel("Olá, " + (usuarioAtual != null ? usuarioAtual.getNome() : "Usuário") + " ");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsuario.setForeground(COR_AZUL_SUS);

        JLabel lblSaldo = new JLabel(String.format("Saldo: R$ %.2f", usuarioAtual != null ? usuarioAtual.getSaldo() : 0.0));
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 18));
        lblSaldo.setForeground(new Color(0, 150, 0));

        rightPanel.add(lblUsuario);
        rightPanel.add(lblSaldo);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        headerWrapper.add(header, BorderLayout.CENTER);

        return headerWrapper;
    }

    private JPanel criarConteudo() {
        JPanel conteudo = new JPanel(new BorderLayout(30, 30));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel containerBranco = new JPanel(new BorderLayout());
        containerBranco.setBackground(COR_BRANCO);
        containerBranco.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel headerRemedios = new JPanel(new BorderLayout());
        headerRemedios.setBackground(COR_BRANCO);
        headerRemedios.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 24));
        btnVoltar.setForeground(COR_AZUL_SUS);
        btnVoltar.setBackground(COR_BRANCO);
        btnVoltar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            new TelaSelecaoUBS(usuarioAtual);
            dispose();
        });

        JLabel titulo = new JLabel("Remédios Disponíveis", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(COR_AZUL_SUS);

        JPanel carrinhoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        carrinhoPanel.setBackground(COR_BRANCO);
        carrinhoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblContador = new JLabel(String.valueOf(carrinho.size()));
        lblContador.setFont(new Font("Arial", Font.BOLD, 14));
        lblContador.setForeground(COR_AZUL_SUS);
        lblContador.setHorizontalAlignment(SwingConstants.CENTER);
        lblContador.setPreferredSize(new Dimension(28, 28));
        lblContador.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        lblContador.setOpaque(true);
        lblContador.setBackground(COR_BRANCO);

        try {
            File cartFile = new File("assets/cart.png");
            if (cartFile.exists()) {
                Image img = ImageIO.read(cartFile);
                Image scaledImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                JLabel iconCarrinho = new JLabel(new ImageIcon(scaledImg));
                carrinhoPanel.add(lblContador);
                carrinhoPanel.add(iconCarrinho);
            } else {
                JLabel lblCarrinho = new JLabel("🛒");
                lblCarrinho.setFont(new Font("Arial", Font.BOLD, 20));
                carrinhoPanel.add(lblContador);
                carrinhoPanel.add(lblCarrinho);
            }
        } catch (Exception ex) {
            JLabel lblCarrinho = new JLabel("🛒");
            lblCarrinho.setFont(new Font("Arial", Font.BOLD, 20));
            carrinhoPanel.add(lblContador);
            carrinhoPanel.add(lblCarrinho);
        }

        carrinhoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verCarrinho();
            }
        });

        JPanel leftHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftHeaderPanel.setBackground(COR_BRANCO);
        leftHeaderPanel.add(btnVoltar);

        headerRemedios.add(leftHeaderPanel, BorderLayout.WEST);
        headerRemedios.add(titulo, BorderLayout.CENTER);
        headerRemedios.add(carrinhoPanel, BorderLayout.EAST);

        JPanel painelPrincipal = new JPanel(new BorderLayout(20, 0));
        painelPrincipal.setBackground(COR_BRANCO);

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(COR_BRANCO);

        JPanel painelPesquisa = new JPanel(new BorderLayout(10, 0));
        painelPesquisa.setBackground(COR_BRANCO);
        painelPesquisa.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JTextField txtPesquisa = new JTextField();
        txtPesquisa.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPesquisa.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblLupa = new JLabel();
        try {
            File searchFile = new File("assets/search.png");
            if (searchFile.exists()) {
                Image img = ImageIO.read(searchFile);
                Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                lblLupa.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception ex) {
            lblLupa.setText("🔍");
            lblLupa.setFont(new Font("Arial", Font.PLAIN, 16));
        }
        lblLupa.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        painelPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(lblLupa, BorderLayout.EAST);

        JPanel painelRemedios = new JPanel();
        painelRemedios.setLayout(new BoxLayout(painelRemedios, BoxLayout.Y_AXIS));
        painelRemedios.setBackground(COR_BRANCO);
        painelRemedios.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JScrollPane scroll = new JScrollPane(painelRemedios);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(COR_BRANCO);

        java.util.List<Remedio> remedios = sistema.getRemediosPorUBS(ubsId);
        Remedio[] remedioSelecionado = {remedios.isEmpty() ? null : remedios.get(0)};
        JPanel[] painelResumo = {new JPanel()};

        for (Remedio r : remedios) {
            JPanel card = criarCardRemedioSimples(r, remedioSelecionado, painelResumo);
            painelRemedios.add(card);
            painelRemedios.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            
            public void filtrar() {
                String termo = txtPesquisa.getText().toLowerCase();
                painelRemedios.removeAll();
                
                for (Remedio r : remedios) {
                    if (r.getNome().toLowerCase().contains(termo)) {
                        JPanel card = criarCardRemedioSimples(r, remedioSelecionado, painelResumo);
                        painelRemedios.add(card);
                        painelRemedios.add(Box.createRigidArea(new Dimension(0, 10)));
                    }
                }
                
                painelRemedios.revalidate();
                painelRemedios.repaint();
            }
        });

        painelEsquerda.add(painelPesquisa, BorderLayout.NORTH);
        painelEsquerda.add(scroll, BorderLayout.CENTER);

        JPanel painelDireita = new JPanel(new BorderLayout());
        painelDireita.setBackground(COR_BRANCO);
        painelDireita.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        painelDireita.setPreferredSize(new Dimension(450, 0));

        painelResumo[0] = criarPainelResumo(remedioSelecionado[0]);
        painelDireita.add(painelResumo[0], BorderLayout.CENTER);

        painelPrincipal.add(painelEsquerda, BorderLayout.CENTER);
        painelPrincipal.add(painelDireita, BorderLayout.EAST);

        containerBranco.add(headerRemedios, BorderLayout.NORTH);
        containerBranco.add(painelPrincipal, BorderLayout.CENTER);

        conteudo.add(containerBranco, BorderLayout.CENTER);

        return conteudo;
    }

    private JPanel criarCardRemedioSimples(Remedio r, Remedio[] remedioSelecionado, JPanel[] painelResumo) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(COR_BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COR_BRANCO);

        JLabel lblNome = new JLabel(r.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        lblNome.setForeground(COR_AZUL_SUS);
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        String detalhes = "";
        if (r.getTipo() != null && !r.getTipo().isEmpty()) {
            detalhes += r.getTipo();
        }
        if (r.getGramatura() != null && !r.getGramatura().isEmpty()) {
            if (!detalhes.isEmpty()) detalhes += " - ";
            detalhes += r.getGramatura();
        }
        if (detalhes.isEmpty()) {
            detalhes = "Sem informações adicionais";
        }

        JLabel lblDetalhes = new JLabel(detalhes);
        lblDetalhes.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDetalhes.setForeground(Color.GRAY);
        lblDetalhes.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(lblNome);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(lblDetalhes);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setBackground(COR_BRANCO);

        JButton btnAdicionar = new JButton("+");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 24));
        btnAdicionar.setForeground(COR_AZUL_SUS);
        btnAdicionar.setBackground(COR_BRANCO);
        btnAdicionar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(e -> {
            carrinho.add(r);
            atualizarTela();
        });

        btnPanel.add(btnAdicionar);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnPanel, BorderLayout.EAST);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                remedioSelecionado[0] = r;
                JPanel novoPainelResumo = criarPainelResumo(r);
                Container parent = painelResumo[0].getParent();
                parent.remove(painelResumo[0]);
                painelResumo[0] = novoPainelResumo;
                parent.add(novoPainelResumo, BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });

        return card;
    }

    private JPanel criarPainelResumo(Remedio r) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(COR_BRANCO);

        if (r == null) {
            JLabel lblVazio = new JLabel("Nenhum remédio disponível");
            lblVazio.setFont(new Font("Arial", Font.PLAIN, 16));
            lblVazio.setForeground(Color.GRAY);
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalGlue());
            painel.add(lblVazio);
            painel.add(Box.createVerticalGlue());
            return painel;
        }

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COR_BRANCO);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel lblNome = new JLabel(r.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 24));
        lblNome.setForeground(COR_AZUL_SUS);

        headerPanel.add(lblNome, BorderLayout.WEST);

        painel.add(headerPanel);
        painel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel containerPrincipal = new JPanel(new BorderLayout(15, 0));
        containerPrincipal.setBackground(COR_BRANCO);
        containerPrincipal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JPanel painelEsquerdo = new JPanel();
        painelEsquerdo.setLayout(new BoxLayout(painelEsquerdo, BoxLayout.Y_AXIS));
        painelEsquerdo.setBackground(COR_BRANCO);

        JTextArea txtDescricao = new JTextArea(r.getDescricao() != null && !r.getDescricao().isEmpty() 
            ? r.getDescricao() 
            : "Sem descrição disponível");
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDescricao.setForeground(Color.DARK_GRAY);
        txtDescricao.setBackground(COR_BRANCO);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setEditable(false);
        txtDescricao.setFocusable(false);
        txtDescricao.setBorder(BorderFactory.createEmptyBorder());
        txtDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String detalhes = "";
        if (r.getTipo() != null && !r.getTipo().isEmpty()) {
            detalhes += r.getTipo();
        }
        if (r.getGramatura() != null && !r.getGramatura().isEmpty()) {
            if (!detalhes.isEmpty()) detalhes += " - ";
            detalhes += r.getGramatura();
        }
        
        painelEsquerdo.add(txtDescricao);
        
        if (!detalhes.isEmpty()) {
            JLabel lblDetalhes = new JLabel(detalhes);
            lblDetalhes.setFont(new Font("Arial", Font.BOLD, 12));
            lblDetalhes.setForeground(COR_AZUL_SUS);
            lblDetalhes.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelEsquerdo.add(Box.createRigidArea(new Dimension(0, 8)));
            painelEsquerdo.add(lblDetalhes);
        }

        JPanel painelDireito = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelDireito.setBackground(COR_BRANCO);

        try {
            String caminhoImagem = r.getImagem() != null && !r.getImagem().isEmpty() 
                ? "assets/drug_data/" + r.getImagem() 
                : "assets/drug.png";
            File imgFile = new File(caminhoImagem);
            if (imgFile.exists()) {
                Image img = ImageIO.read(imgFile);
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel lblImagem = new JLabel(new ImageIcon(scaledImg));
                lblImagem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                painelDireito.add(lblImagem);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }

        containerPrincipal.add(painelEsquerdo, BorderLayout.CENTER);
        containerPrincipal.add(painelDireito, BorderLayout.EAST);

        painel.add(containerPrincipal);
        painel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel painelInfo = new JPanel(new BorderLayout(20, 0));
        painelInfo.setBackground(COR_BRANCO);
        painelInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel painelEsquerdoInfo = new JPanel();
        painelEsquerdoInfo.setLayout(new BoxLayout(painelEsquerdoInfo, BoxLayout.Y_AXIS));
        painelEsquerdoInfo.setBackground(COR_BRANCO);

        if (r.isPrecisaReceita()) {
            JLabel lblReceita = new JLabel("Apenas sob prescrição");
            lblReceita.setFont(new Font("Arial", Font.PLAIN, 14));
            lblReceita.setForeground(Color.RED);
            lblReceita.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelEsquerdoInfo.add(lblReceita);
        }

        JPanel painelDireitoInfo = new JPanel();
        painelDireitoInfo.setLayout(new BoxLayout(painelDireitoInfo, BoxLayout.Y_AXIS));
        painelDireitoInfo.setBackground(COR_BRANCO);

        JLabel lblDe = new JLabel(String.format("De: R$ %.2f", r.getPreco() * 3));
        lblDe.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDe.setForeground(Color.GRAY);
        lblDe.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel lblPor = new JLabel(String.format("Por: R$ %.2f", r.getPreco()));
        lblPor.setFont(new Font("Arial", Font.BOLD, 22));
        lblPor.setForeground(COR_VERDE);
        lblPor.setAlignmentX(Component.RIGHT_ALIGNMENT);

        painelDireitoInfo.add(lblDe);
        painelDireitoInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelDireitoInfo.add(lblPor);

        painelInfo.add(painelEsquerdoInfo, BorderLayout.WEST);
        painelInfo.add(painelDireitoInfo, BorderLayout.EAST);

        painel.add(painelInfo);
        painel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel acaoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        acaoPanel.setBackground(COR_BRANCO);
        acaoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JSpinner spinnerQtd = new JSpinner(new SpinnerNumberModel(1, 1, r.getEstoque(), 1));
        spinnerQtd.setFont(new Font("Arial", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) spinnerQtd.getEditor()).getTextField().setColumns(3);

        JButton btnAdicionar = new JButton("+ Carrinho");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setBackground(COR_AZUL_SUS);
        btnAdicionar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(e -> {
            int quantidade = (Integer) spinnerQtd.getValue();
            for (int i = 0; i < quantidade; i++) {
                carrinho.add(r);
            }
            atualizarTela();
        });

        acaoPanel.add(spinnerQtd);
        acaoPanel.add(btnAdicionar);
        
        painel.add(acaoPanel);
        painel.add(Box.createVerticalGlue());

        return painel;
    }

    private JPanel criarCardRemedio(Remedio r) {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(COR_BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel lblImagem = null;
        try {
            String caminhoImagem = r.getImagem() != null && !r.getImagem().isEmpty() 
                ? "assets/drug_data/" + r.getImagem() 
                : "assets/drug.png";
            File imgFile = new File(caminhoImagem);
            if (imgFile.exists()) {
                Image img = ImageIO.read(imgFile);
                Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblImagem = new JLabel(new ImageIcon(scaledImg));
                lblImagem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem do remédio: " + e.getMessage());
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COR_BRANCO);

        JLabel lblNome = new JLabel(r.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 20));
        lblNome.setForeground(COR_AZUL_SUS);

        JLabel lblDetalhes = new JLabel(String.format("Estoque: %d | Receita: %s", 
            r.getEstoque(), r.isPrecisaReceita() ? "Sim" : "Não"));
        lblDetalhes.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDetalhes.setForeground(Color.GRAY);

        infoPanel.add(lblNome);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(lblDetalhes);

        JPanel leftPanel = new JPanel(new BorderLayout(15, 0));
        leftPanel.setBackground(COR_BRANCO);
        if (lblImagem != null) {
            leftPanel.add(lblImagem, BorderLayout.WEST);
        }
        leftPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setBackground(COR_BRANCO);

        JLabel lblPreco = new JLabel(String.format("R$ %.2f", r.getPreco()));
        lblPreco.setFont(new Font("Arial", Font.BOLD, 22));
        lblPreco.setForeground(COR_VERDE);

        JSpinner spinnerQtd = new JSpinner(new SpinnerNumberModel(1, 1, r.getEstoque(), 1));
        spinnerQtd.setFont(new Font("Arial", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) spinnerQtd.getEditor()).getTextField().setColumns(3);

        JButton btnAdicionar = new JButton("+");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 24));
        btnAdicionar.setForeground(COR_AZUL_SUS);
        btnAdicionar.setBackground(COR_BRANCO);
        btnAdicionar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(e -> {
            int quantidade = (Integer) spinnerQtd.getValue();
            for (int i = 0; i < quantidade; i++) {
                carrinho.add(r);
            }
            atualizarTela();
        });

        rightPanel.add(lblPreco);
        rightPanel.add(spinnerQtd);
        rightPanel.add(btnAdicionar);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }

    private JPanel criarPainelAcoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(COR_FUNDO);

        JButton btnReceita = criarBotaoAcao("Enviar Receita");
        btnReceita.addActionListener(e -> enviarReceita());

        JButton btnEfetuarPedido = criarBotaoAcao("Efetuar Pedido");
        btnEfetuarPedido.addActionListener(e -> verCarrinho());

        panel.add(btnReceita);
        panel.add(btnEfetuarPedido);

        return panel;
    }

    private JButton criarBotaoAcao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(COR_AZUL_SUS);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void atualizarTela() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.add(criarHeader(), BorderLayout.NORTH);
        mainPanel.add(criarConteudo(), BorderLayout.CENTER);
        add(mainPanel);
        revalidate();
        repaint();
    }

    private void verCarrinho() {
        JFrame frame = new JFrame("Carrinho");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(this);
        frame.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JPanel tituloContainer = new JPanel(new BorderLayout());
        tituloContainer.setBackground(COR_BRANCO);
        tituloContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel titulo = new JLabel("Carrinho de " + (usuarioAtual != null ? usuarioAtual.getNome() : "Usuário"), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(COR_AZUL_SUS);
        
        tituloContainer.add(titulo, BorderLayout.CENTER);
        mainPanel.add(tituloContainer, BorderLayout.NORTH);

        JPanel painelItens = new JPanel();
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelItens.setBackground(COR_FUNDO);

        double[] totalArray = {0};
        
        for (int i = 0; i < carrinho.size(); i++) {
            final int index = i;
            Remedio r = carrinho.get(i);
            
            JPanel itemCard = new JPanel(new BorderLayout(10, 0));
            itemCard.setBackground(COR_BRANCO);
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));
            itemCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            JLabel lblNome = new JLabel(r.getNome());
            lblNome.setFont(new Font("Arial", Font.BOLD, 18));
            lblNome.setForeground(COR_AZUL_SUS);

            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            rightPanel.setBackground(COR_BRANCO);

            JLabel lblPreco = new JLabel(String.format("R$ %.2f", r.getPreco()));
            lblPreco.setFont(new Font("Arial", Font.BOLD, 18));
            lblPreco.setForeground(COR_VERDE);

            JButton btnRemover = new JButton("X");
            btnRemover.setFont(new Font("Arial", Font.BOLD, 16));
            btnRemover.setForeground(Color.WHITE);
            btnRemover.setBackground(new Color(220, 53, 69));
            btnRemover.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btnRemover.setFocusPainted(false);
            btnRemover.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnRemover.addActionListener(ev -> {
                carrinho.remove(index);
                frame.dispose();
                verCarrinho();
            });

            rightPanel.add(lblPreco);
            rightPanel.add(btnRemover);

            itemCard.add(lblNome, BorderLayout.WEST);
            itemCard.add(rightPanel, BorderLayout.EAST);

            painelItens.add(itemCard);
            painelItens.add(Box.createRigidArea(new Dimension(0, 10)));
            totalArray[0] += r.getPreco();
        }

        JScrollPane scroll = new JScrollPane(painelItens);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scroll, BorderLayout.CENTER);

        JPanel resumo = new JPanel();
        resumo.setLayout(new BoxLayout(resumo, BoxLayout.Y_AXIS));
        resumo.setBackground(COR_BRANCO);
        resumo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel lblResumo = new JLabel("Resumo");
        lblResumo.setFont(new Font("Arial", Font.BOLD, 24));
        lblResumo.setForeground(COR_AZUL_SUS);

        JLabel lblTotal = new JLabel(String.format("Total: R$ %.2f", totalArray[0]));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 28));
        lblTotal.setForeground(COR_AZUL_SUS);

        resumo.add(lblResumo);
        resumo.add(Box.createRigidArea(new Dimension(0, 20)));
        resumo.add(lblTotal);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 15));
        bottomPanel.setBackground(COR_FUNDO);
        bottomPanel.add(resumo, BorderLayout.NORTH);

        JButton btnFinalizar = new JButton("Finalizar Pedido");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 18));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setBackground(COR_AZUL_SUS);
        btnFinalizar.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFinalizar.addActionListener(e -> {
            if (fazerPedido()) {
                frame.dispose();
            }
        });

        bottomPanel.add(btnFinalizar, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private boolean fazerPedido() {
        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Cadastre-se primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Calcular total
        double total = 0;
        for (Remedio r : carrinho) {
            total += r.getPreco();
        }

        // Verificar saldo
        if (usuarioAtual.getSaldo() < total) {
            JOptionPane.showMessageDialog(this, 
                String.format("Saldo insuficiente!\nSaldo atual: R$ %.2f\nTotal da compra: R$ %.2f", 
                    usuarioAtual.getSaldo(), total), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Verificar se há estoque suficiente
        Map<Integer, Integer> quantidades = new HashMap<>();
        for (Remedio r : carrinho) {
            quantidades.put(r.getId(), quantidades.getOrDefault(r.getId(), 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : quantidades.entrySet()) {
            int remedioId = entry.getKey();
            int qtdSolicitada = entry.getValue();
            
            // Buscar remédio atualizado do sistema
            Remedio remedioAtual = null;
            for (Remedio r : sistema.getRemedios()) {
                if (r.getId() == remedioId) {
                    remedioAtual = r;
                    break;
                }
            }
            
            if (remedioAtual == null || remedioAtual.getEstoque() < qtdSolicitada) {
                String nomeRemedio = remedioAtual != null ? remedioAtual.getNome() : "ID " + remedioId;
                JOptionPane.showMessageDialog(this, 
                    "Estoque insuficiente para " + nomeRemedio + "!\n" +
                    "Disponível: " + (remedioAtual != null ? remedioAtual.getEstoque() : 0) + 
                    " | Solicitado: " + qtdSolicitada, 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Criar pedido
        Pedido pedido = sistema.criarPedido(usuarioAtual);
        for (Remedio r : carrinho) {
            pedido.adicionarRemedio(r);
        }
        sistema.salvarRemediosPedido(pedido.getId(), pedido.getRemedios());

        // Se precisar de receita, solicita upload
        if (pedido.precisaReceita()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a receita médica (PDF ou Imagem)");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".pdf") || name.endsWith(".jpg") || 
                           name.endsWith(".jpeg") || name.endsWith(".png");
                }
                public String getDescription() {
                    return "Arquivos de Receita (*.pdf, *.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File arquivoSelecionado = fileChooser.getSelectedFile();
                
                // Criar diretório se não existir
                File dirReceitas = new File("assets/user_data/receitas");
                if (!dirReceitas.exists()) {
                    dirReceitas.mkdirs();
                }
                
                // Copiar arquivo com nome único
                String extensao = arquivoSelecionado.getName().substring(arquivoSelecionado.getName().lastIndexOf("."));
                String nomeArquivo = "receita_pedido_" + pedido.getId() + "_" + System.currentTimeMillis() + extensao;
                File destino = new File(dirReceitas, nomeArquivo);
                
                try {
                    Files.copy(arquivoSelecionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    String caminhoRelativo = "assets/user_data/receitas/" + nomeArquivo;
                    sistema.salvarReceitaPedido(pedido.getId(), caminhoRelativo);
                    pedido.setCaminhoReceita(caminhoRelativo);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Erro ao salvar receita: " + ex.getMessage(), 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Receita médica é obrigatória para este pedido!", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } else {
            // Se não precisar de receita, já aprova automaticamente
            sistema.aprovarPedido(pedido.getId());
        }

        // Diminuir estoque
        for (Map.Entry<Integer, Integer> entry : quantidades.entrySet()) {
            sistema.diminuirEstoque(entry.getKey(), entry.getValue());
        }

        // Diminuir saldo
        sistema.diminuirSaldo(usuarioAtual.getCpf(), total);
        usuarioAtual.setSaldo(usuarioAtual.getSaldo() - total);

        carrinho.clear();

        JOptionPane.showMessageDialog(this, 
            String.format("Pedido #%d realizado com sucesso!\nValor: R$ %.2f\nSaldo restante: R$ %.2f", 
                pedido.getId(), total, usuarioAtual.getSaldo()), 
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        atualizarTela();
        return true;
    }

    private void enviarReceita() {
        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Cadastre-se primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String arquivo = JOptionPane.showInputDialog(this, "Digite o caminho do arquivo:", "Enviar Receita", JOptionPane.QUESTION_MESSAGE);
        if (arquivo != null && !arquivo.isEmpty()) {
            sistema.enviarReceita(usuarioAtual.getNome(), arquivo);
            JOptionPane.showMessageDialog(this, "Receita enviada!");
        }
    }
}
