//src/views/TelaAdmin.java

package views;

import core.SistemaFarmacia;
import models.Pedido;
import models.Remedio;
import models.UBS;
import models.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaAdmin extends JFrame {
    private static final Color COR_AZUL_SUS = new Color(0, 94, 184);
    private static final Color COR_FUNDO = new Color(232, 244, 248);
    private static final Color COR_BRANCO = Color.WHITE;
    
    private SistemaFarmacia sistema = SistemaFarmacia.getInstance();

    public TelaAdmin() {
        setTitle("Sistema SUS - Área Administrativa");
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
        headerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 50, 15, 50));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COR_BRANCO);
        header.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

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
                lblSus.setFont(new Font("Arial", Font.BOLD, 20));
                lblSus.setForeground(COR_AZUL_SUS);
                leftPanel.add(lblSus);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar logo: " + e.getMessage());
        }
        
        try {
            Image icon = ImageIO.read(new File("assets/icon.png"));
            setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Erro ao carregar ícone: " + e.getMessage());
        }

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(COR_BRANCO);

        JLabel lblUsuario = new JLabel("Olá, Administrador ");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 20));
        lblUsuario.setForeground(COR_AZUL_SUS);

        rightPanel.add(lblUsuario);

        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);

        headerWrapper.add(header, BorderLayout.CENTER);

        return headerWrapper;
    }

    private JPanel criarConteudo() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_FUNDO);
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 50, 30, 50));

        JPanel container = new JPanel(new BorderLayout(30, 30));
        container.setBackground(COR_BRANCO);
        container.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JPanel tituloPanel = new JPanel(new BorderLayout());
        tituloPanel.setBackground(COR_BRANCO);

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 28));
        btnVoltar.setForeground(COR_AZUL_SUS);
        btnVoltar.setBackground(COR_BRANCO);
        btnVoltar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.addActionListener(e -> {
            new TelaInicial();
            dispose();
        });

        JLabel titulo = new JLabel("Painel Administrativo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(COR_AZUL_SUS);

        tituloPanel.add(btnVoltar, BorderLayout.WEST);
        tituloPanel.add(titulo, BorderLayout.CENTER);

        container.add(tituloPanel, BorderLayout.NORTH);

        JPanel painelOpcoesWrapper = new JPanel(new BorderLayout());
        painelOpcoesWrapper.setBackground(COR_BRANCO);
        painelOpcoesWrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel painelOpcoes = new JPanel(new GridLayout(0, 2, 30, 20));
        painelOpcoes.setBackground(COR_BRANCO);

        painelOpcoes.add(criarCardOpcao("1", "Gerenciar UBS", "Adicionar, editar ou remover unidades", () -> gerenciarUBS()));
        painelOpcoes.add(criarCardOpcao("2", "Gerenciar Remédios", "Cadastrar, atualizar ou deletar remédios", () -> gerenciarRemedios()));
        painelOpcoes.add(criarCardOpcao("3", "Listar Estoque", "Visualizar informações de estoque", () -> listarEstoque()));
        painelOpcoes.add(criarCardOpcao("4", "Adicionar Fundos", "Adicionar saldo aos usuários", () -> adicionarFundos()));
        painelOpcoes.add(criarCardOpcao("5", "Revisar Pedidos", "Aprovar pedidos que precisam de receita", () -> revisarPedidos()));
        painelOpcoes.add(criarCardOpcao("6", "Consultar Pedidos", "Visualizar todos os pedidos realizados", () -> consultarPedidos()));
        painelOpcoes.add(criarCardOpcao("7", "Usuários Bloqueados", "Desbloquear usuários com tentativas excedidas", () -> gerenciarBloqueados()));

        painelOpcoesWrapper.add(painelOpcoes, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(painelOpcoesWrapper);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        container.add(scroll, BorderLayout.CENTER);
        wrapper.add(container, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel criarCardOpcao(String numero, String titulo, String descricao, Runnable acao) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(COR_BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblNumero = new JLabel(numero);
        lblNumero.setFont(new Font("Arial", Font.BOLD, 28));
        lblNumero.setForeground(COR_AZUL_SUS);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COR_BRANCO);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COR_AZUL_SUS);

        JLabel lblDescricao = new JLabel("<html>" + descricao + "</html>");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescricao.setForeground(Color.GRAY);

        infoPanel.add(lblTitulo);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(lblDescricao);

        card.add(lblNumero, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acao.run();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(240, 248, 255));
                infoPanel.setBackground(new Color(240, 248, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(COR_BRANCO);
                infoPanel.setBackground(COR_BRANCO);
            }
        });

        return card;
    }

    private void gerenciarUBS() {
        JDialog dialog = new JDialog(this, "Gerenciar UBS", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Unidades de Saúde", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (UBS ubs : sistema.getUBS()) {
            StringBuilder info = new StringBuilder();
            info.append(String.format("ID: %d | %s", ubs.getId(), ubs.getNome()));
            
            if (ubs.getLogradouro() != null && !ubs.getLogradouro().isEmpty()) {
                info.append(" | ").append(ubs.getLogradouro());
                if (ubs.getNumero() != null && !ubs.getNumero().isEmpty()) {
                    info.append(", ").append(ubs.getNumero());
                }
                if (ubs.getBairro() != null && !ubs.getBairro().isEmpty()) {
                    info.append(" - ").append(ubs.getBairro());
                }
                if (ubs.getEstado() != null && !ubs.getEstado().isEmpty()) {
                    info.append(", ").append(ubs.getEstado());
                }
                if (ubs.getCep() != null && !ubs.getCep().isEmpty()) {
                    info.append(" - CEP: ").append(ubs.getCep());
                }
            } else if (ubs.getEndereco() != null && !ubs.getEndereco().isEmpty()) {
                info.append(" | ").append(ubs.getEndereco());
            }
            
            model.addElement(info.toString());
        }

        JList<String> lista = new JList<>(model);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botoesPanel.setBackground(COR_FUNDO);

        JButton btnAdicionar = criarBotaoAcao("Adicionar UBS");
        btnAdicionar.addActionListener(e -> {
            adicionarUBS();
            dialog.dispose();
            gerenciarUBS();
        });

        JButton btnAtualizar = criarBotaoAcao("Atualizar UBS");
        btnAtualizar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                int id = Integer.parseInt(sel.split("ID: ")[1].split(" ")[0]);
                UBS ubsSelecionada = null;
                for (UBS u : sistema.getUBS()) {
                    if (u.getId() == id) {
                        ubsSelecionada = u;
                        break;
                    }
                }
                if (ubsSelecionada != null) {
                    atualizarUBS(ubsSelecionada);
                    dialog.dispose();
                    gerenciarUBS();
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione uma UBS para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnRemover = criarBotaoAcao("Remover UBS");
        btnRemover.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                int id = Integer.parseInt(sel.split("ID: ")[1].split(" ")[0]);
                sistema.deletarUBS(id);
                JOptionPane.showMessageDialog(dialog, "UBS removida!");
                dialog.dispose();
                gerenciarUBS();
            }
        });

        botoesPanel.add(btnAdicionar);
        botoesPanel.add(btnAtualizar);
        botoesPanel.add(btnRemover);

        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void adicionarUBS() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField txtNome = new JTextField();
        JTextField txtLogradouro = new JTextField();
        JTextField txtNumero = new JTextField();
        JTextField txtBairro = new JTextField();
        JTextField txtEstado = new JTextField();
        JTextField txtCep = new JTextField();
        JButton btnImagem = new JButton("Selecionar Imagem");
        JLabel lblImagemSelecionada = new JLabel("Nenhuma imagem selecionada");
        final String[] caminhoImagemSelecionada = {null};

        btnImagem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a imagem da UBS");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
                }
                public String getDescription() {
                    return "Imagens (*.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                caminhoImagemSelecionada[0] = fileChooser.getSelectedFile().getAbsolutePath();
                lblImagemSelecionada.setText(fileChooser.getSelectedFile().getName());
            }
        });

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Logradouro:"));
        panel.add(txtLogradouro);
        panel.add(new JLabel("Número:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Bairro:"));
        panel.add(txtBairro);
        panel.add(new JLabel("Estado:"));
        panel.add(txtEstado);
        panel.add(new JLabel("CEP:"));
        panel.add(txtCep);
        panel.add(btnImagem);
        panel.add(lblImagemSelecionada);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar UBS", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nomeImagem = "";
            
            if (caminhoImagemSelecionada[0] != null) {
                try {
                    java.io.File dirUbsData = new java.io.File("assets/ubs_data");
                    if (!dirUbsData.exists()) {
                        dirUbsData.mkdirs();
                    }
                    
                    java.io.File arquivoOrigem = new java.io.File(caminhoImagemSelecionada[0]);
                    String extensao = arquivoOrigem.getName().substring(arquivoOrigem.getName().lastIndexOf("."));
                    nomeImagem = "ubs_" + System.currentTimeMillis() + extensao;
                    java.io.File destino = new java.io.File(dirUbsData, nomeImagem);
                    
                    java.nio.file.Files.copy(arquivoOrigem.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            sistema.cadastrarUBS(txtNome.getText(), nomeImagem, txtLogradouro.getText(), 
                txtNumero.getText(), txtBairro.getText(), txtEstado.getText(), txtCep.getText());
            JOptionPane.showMessageDialog(this, "UBS cadastrada!");
        }
    }

    private void atualizarUBS(UBS ubs) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        JTextField txtId = new JTextField(String.valueOf(ubs.getId()));
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        
        JTextField txtNome = new JTextField(ubs.getNome() != null ? ubs.getNome() : "");
        JTextField txtLogradouro = new JTextField(ubs.getLogradouro() != null ? ubs.getLogradouro() : "");
        JTextField txtNumero = new JTextField(ubs.getNumero() != null ? ubs.getNumero() : "");
        JTextField txtBairro = new JTextField(ubs.getBairro() != null ? ubs.getBairro() : "");
        JTextField txtEstado = new JTextField(ubs.getEstado() != null ? ubs.getEstado() : "");
        JTextField txtCep = new JTextField(ubs.getCep() != null ? ubs.getCep() : "");
        JButton btnImagem = new JButton("Alterar Imagem");
        JLabel lblImagemSelecionada = new JLabel(ubs.getImagem() != null && !ubs.getImagem().isEmpty() ? ubs.getImagem() : "Nenhuma imagem");
        final String[] caminhoImagemSelecionada = {null};

        btnImagem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a imagem da UBS");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
                }
                public String getDescription() {
                    return "Imagens (*.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                caminhoImagemSelecionada[0] = fileChooser.getSelectedFile().getAbsolutePath();
                lblImagemSelecionada.setText(fileChooser.getSelectedFile().getName());
            }
        });

        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Logradouro:"));
        panel.add(txtLogradouro);
        panel.add(new JLabel("Número:"));
        panel.add(txtNumero);
        panel.add(new JLabel("Bairro:"));
        panel.add(txtBairro);
        panel.add(new JLabel("Estado:"));
        panel.add(txtEstado);
        panel.add(new JLabel("CEP:"));
        panel.add(txtCep);
        panel.add(btnImagem);
        panel.add(lblImagemSelecionada);

        int result = JOptionPane.showConfirmDialog(this, panel, "Atualizar UBS", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nomeImagem = ubs.getImagem();
            
            if (caminhoImagemSelecionada[0] != null) {
                try {
                    java.io.File dirUbsData = new java.io.File("assets/ubs_data");
                    if (!dirUbsData.exists()) {
                        dirUbsData.mkdirs();
                    }
                    
                    java.io.File arquivoOrigem = new java.io.File(caminhoImagemSelecionada[0]);
                    String extensao = arquivoOrigem.getName().substring(arquivoOrigem.getName().lastIndexOf("."));
                    nomeImagem = "ubs_" + System.currentTimeMillis() + extensao;
                    java.io.File destino = new java.io.File(dirUbsData, nomeImagem);
                    
                    java.nio.file.Files.copy(arquivoOrigem.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            sistema.atualizarUBS(ubs.getId(), txtNome.getText(), nomeImagem, 
                txtLogradouro.getText(), txtNumero.getText(), txtBairro.getText(), 
                txtEstado.getText(), txtCep.getText());
            JOptionPane.showMessageDialog(this, "UBS atualizada com sucesso!");
        }
    }

    private void gerenciarRemedios() {
        JDialog dialog = new JDialog(this, "Gerenciar Remédios", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Remédios Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Remedio r : sistema.getRemedios()) {
            model.addElement(String.format("ID: %d | %s - R$ %.2f | Estoque: %d", 
                r.getId(), r.getNome(), r.getPreco(), r.getEstoque()));
        }

        JList<String> lista = new JList<>(model);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botoesPanel.setBackground(COR_FUNDO);

        JButton btnCadastrar = criarBotaoAcao("Cadastrar");
        btnCadastrar.addActionListener(e -> {
            cadastrarRemedio();
            dialog.dispose();
            gerenciarRemedios();
        });

        JButton btnAtualizar = criarBotaoAcao("Atualizar");
        btnAtualizar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                int id = Integer.parseInt(sel.split("ID: ")[1].split(" ")[0]);
                // Buscar remédio pelo ID
                Remedio remedioSelecionado = null;
                for (Remedio r : sistema.getRemedios()) {
                    if (r.getId() == id) {
                        remedioSelecionado = r;
                        break;
                    }
                }
                if (remedioSelecionado != null) {
                    atualizarRemedio(remedioSelecionado);
                    dialog.dispose();
                    gerenciarRemedios();
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um remédio para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton btnDeletar = criarBotaoAcao("Deletar");
        btnDeletar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                int id = Integer.parseInt(sel.split("ID: ")[1].split(" ")[0]);
                sistema.deletarRemedio(id);
                JOptionPane.showMessageDialog(dialog, "Remédio deletado!");
                dialog.dispose();
                gerenciarRemedios();
            }
        });

        botoesPanel.add(btnCadastrar);
        botoesPanel.add(btnAtualizar);
        botoesPanel.add(btnDeletar);

        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void cadastrarRemedio() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        JTextField txtNome = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtEstoque = new JTextField();
        JCheckBox chkReceita = new JCheckBox();
        JTextField txtUbsId = new JTextField();
        JButton btnImagem = new JButton("Selecionar Imagem");
        JLabel lblImagemSelecionada = new JLabel("Nenhuma imagem selecionada");
        final String[] caminhoImagemSelecionada = {null};

        btnImagem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a imagem do remédio");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
                }
                public String getDescription() {
                    return "Imagens (*.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                caminhoImagemSelecionada[0] = fileChooser.getSelectedFile().getAbsolutePath();
                lblImagemSelecionada.setText(fileChooser.getSelectedFile().getName());
            }
        });

        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Preço:"));
        panel.add(txtPreco);
        panel.add(new JLabel("Estoque:"));
        panel.add(txtEstoque);
        panel.add(new JLabel("Precisa Receita:"));
        panel.add(chkReceita);
        panel.add(new JLabel("ID da UBS:"));
        panel.add(txtUbsId);
        panel.add(btnImagem);
        panel.add(lblImagemSelecionada);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cadastrar Remédio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nomeImagem = "";
                
                if (caminhoImagemSelecionada[0] != null) {
                    try {
                        java.io.File dirDrugData = new java.io.File("assets/drug_data");
                        if (!dirDrugData.exists()) {
                            dirDrugData.mkdirs();
                        }
                        
                        java.io.File arquivoOrigem = new java.io.File(caminhoImagemSelecionada[0]);
                        String extensao = arquivoOrigem.getName().substring(arquivoOrigem.getName().lastIndexOf("."));
                        nomeImagem = "drug_" + System.currentTimeMillis() + extensao;
                        java.io.File destino = new java.io.File(dirDrugData, nomeImagem);
                        
                        java.nio.file.Files.copy(arquivoOrigem.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                sistema.cadastrarRemedio(
                    txtNome.getText(), 
                    Double.parseDouble(txtPreco.getText()),
                    Integer.parseInt(txtEstoque.getText()), 
                    chkReceita.isSelected(), 
                    Integer.parseInt(txtUbsId.getText()),
                    nomeImagem
                );
                JOptionPane.showMessageDialog(this, "Remédio cadastrado com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarRemedio(Remedio remedio) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField txtId = new JTextField(String.valueOf(remedio.getId()));
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        
        JTextField txtNome = new JTextField(remedio.getNome());
        JTextField txtPreco = new JTextField(String.valueOf(remedio.getPreco()));
        JTextField txtEstoque = new JTextField(String.valueOf(remedio.getEstoque()));
        JCheckBox chkReceita = new JCheckBox();
        chkReceita.setSelected(remedio.isPrecisaReceita());
        JTextField txtUbsId = new JTextField(String.valueOf(remedio.getUbsId()));
        JButton btnImagem = new JButton("Alterar Imagem");
        JLabel lblImagemSelecionada = new JLabel(remedio.getImagem() != null && !remedio.getImagem().isEmpty() ? remedio.getImagem() : "Nenhuma imagem");
        final String[] caminhoImagemSelecionada = {null};

        btnImagem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecione a imagem do remédio");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(java.io.File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
                }
                public String getDescription() {
                    return "Imagens (*.jpg, *.jpeg, *.png)";
                }
            });
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                caminhoImagemSelecionada[0] = fileChooser.getSelectedFile().getAbsolutePath();
                lblImagemSelecionada.setText(fileChooser.getSelectedFile().getName());
            }
        });

        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("Preço:"));
        panel.add(txtPreco);
        panel.add(new JLabel("Estoque:"));
        panel.add(txtEstoque);
        panel.add(new JLabel("Precisa Receita:"));
        panel.add(chkReceita);
        panel.add(new JLabel("ID da UBS:"));
        panel.add(txtUbsId);
        panel.add(btnImagem);
        panel.add(lblImagemSelecionada);

        int result = JOptionPane.showConfirmDialog(this, panel, "Atualizar Remédio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nomeImagem = remedio.getImagem();
                
                if (caminhoImagemSelecionada[0] != null) {
                    try {
                        java.io.File dirDrugData = new java.io.File("assets/drug_data");
                        if (!dirDrugData.exists()) {
                            dirDrugData.mkdirs();
                        }
                        
                        java.io.File arquivoOrigem = new java.io.File(caminhoImagemSelecionada[0]);
                        String extensao = arquivoOrigem.getName().substring(arquivoOrigem.getName().lastIndexOf("."));
                        nomeImagem = "drug_" + System.currentTimeMillis() + extensao;
                        java.io.File destino = new java.io.File(dirDrugData, nomeImagem);
                        
                        java.nio.file.Files.copy(arquivoOrigem.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                sistema.atualizarRemedio(
                    Integer.parseInt(txtId.getText()), 
                    txtNome.getText(),
                    Double.parseDouble(txtPreco.getText()), 
                    Integer.parseInt(txtEstoque.getText()),
                    chkReceita.isSelected(), 
                    Integer.parseInt(txtUbsId.getText()),
                    nomeImagem
                );
                JOptionPane.showMessageDialog(this, "Remédio atualizado com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarEstoque() {
        JDialog dialog = new JDialog(this, "Estoque", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Informações de Estoque", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Remedio r : sistema.getRemedios()) {
            model.addElement(String.format("ID: %d | %s | Estoque: %d | Preço: R$ %.2f",
                    r.getId(), r.getNome(), r.getEstoque(), r.getPreco()));
        }

        JList<String> lista = new JList<>(model);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);

        mainPanel.add(scroll, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void consultarPedidos() {
        JDialog dialog = new JDialog(this, "Consultar Pedidos", true);
        dialog.setSize(1100, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Todos os Pedidos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel painelPedidos = new JPanel();
        painelPedidos.setLayout(new BoxLayout(painelPedidos, BoxLayout.Y_AXIS));
        painelPedidos.setBackground(COR_FUNDO);

        for (Pedido p : sistema.getPedidos()) {
            JPanel cardPedido = new JPanel(new BorderLayout(15, 0));
            cardPedido.setBackground(COR_BRANCO);
            cardPedido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
            ));
            cardPedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(COR_BRANCO);

            JLabel lblPedido = new JLabel("Pedido #" + p.getId());
            lblPedido.setFont(new Font("Arial", Font.BOLD, 20));
            lblPedido.setForeground(COR_AZUL_SUS);

            JLabel lblUsuario = new JLabel("Usuário: " + p.getUsuario().getNome() + " (" + p.getUsuario().getCpf() + ")");
            lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
            lblUsuario.setForeground(Color.GRAY);

            double total = 0;
            for (Remedio r : p.getRemedios()) {
                total += r.getPreco();
            }

            JLabel lblRemedios = new JLabel(String.format("Remédios: %d itens | Subtotal: R$ %.2f", p.getRemedios().size(), total));
            lblRemedios.setFont(new Font("Arial", Font.PLAIN, 14));
            lblRemedios.setForeground(Color.DARK_GRAY);

            JLabel lblTotal = new JLabel(String.format("Total: R$ %.2f | Status: %s", total, p.getStatus()));
            lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
            lblTotal.setForeground(new Color(0, 150, 0));

            infoPanel.add(lblPedido);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            infoPanel.add(lblUsuario);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            infoPanel.add(lblRemedios);
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            infoPanel.add(lblTotal);

            cardPedido.add(infoPanel, BorderLayout.CENTER);

            if (p.getCaminhoReceita() != null) {
                JButton btnVerReceita = new JButton("Ver Receita");
                btnVerReceita.setFont(new Font("Arial", Font.BOLD, 14));
                btnVerReceita.setForeground(Color.WHITE);
                btnVerReceita.setBackground(new Color(0, 123, 255));
                btnVerReceita.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
                btnVerReceita.setFocusPainted(false);
                btnVerReceita.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnVerReceita.addActionListener(e -> {
                    try {
                        File receita = new File(p.getCaminhoReceita());
                        if (receita.exists()) {
                            Desktop.getDesktop().open(receita);
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Arquivo de receita não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Erro ao abrir receita: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                });
                cardPedido.add(btnVerReceita, BorderLayout.EAST);
            }

            painelPedidos.add(cardPedido);
            painelPedidos.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scroll = new JScrollPane(painelPedidos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scroll, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
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

    private void adicionarFundos() {
        JDialog dialog = new JDialog(this, "Adicionar Fundos", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gerenciar Fundos dos Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Usuario u : sistema.getUsuarios()) {
            if (!u.isAdmin()) {
                model.addElement(String.format("CPF: %s | %s | Saldo: R$ %.2f", 
                    u.getCpf(), u.getNome(), u.getSaldo()));
            }
        }

        JList<String> lista = new JList<>(model);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botoesPanel.setBackground(COR_FUNDO);

        JButton btnAdicionar = criarBotaoAcao("Adicionar Fundos");
        btnAdicionar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                String cpf = sel.split("CPF: ")[1].split(" ")[0];
                String valorStr = JOptionPane.showInputDialog(dialog, "Digite o valor a adicionar:", "Adicionar Fundos", JOptionPane.QUESTION_MESSAGE);
                if (valorStr != null && !valorStr.isEmpty()) {
                    try {
                        double valor = Double.parseDouble(valorStr);
                        if (valor > 0) {
                            sistema.adicionarFundos(cpf, valor);
                            JOptionPane.showMessageDialog(dialog, "Fundos adicionados com sucesso!");
                            dialog.dispose();
                            adicionarFundos();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Valor deve ser positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        botoesPanel.add(btnAdicionar);

        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void revisarPedidos() {
        JDialog dialog = new JDialog(this, "Revisar Pedidos", true);
        dialog.setSize(1000, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Pedidos Pendentes de Aprovação", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        JPanel painelPedidos = new JPanel();
        painelPedidos.setLayout(new BoxLayout(painelPedidos, BoxLayout.Y_AXIS));
        painelPedidos.setBackground(COR_FUNDO);

        for (Pedido p : sistema.getPedidos()) {
            if (p.getStatus().equals("Pendente") && p.precisaReceita()) {
                JPanel cardPedido = new JPanel(new BorderLayout(15, 0));
                cardPedido.setBackground(COR_BRANCO);
                cardPedido.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
                    BorderFactory.createEmptyBorder(20, 25, 20, 25)
                ));
                cardPedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
                infoPanel.setBackground(COR_BRANCO);

                JLabel lblPedido = new JLabel("Pedido #" + p.getId());
                lblPedido.setFont(new Font("Arial", Font.BOLD, 20));
                lblPedido.setForeground(COR_AZUL_SUS);

                JLabel lblUsuario = new JLabel("Usuário: " + p.getUsuario().getNome() + " (" + p.getUsuario().getCpf() + ")");
                lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
                lblUsuario.setForeground(Color.GRAY);

                StringBuilder remediosStr = new StringBuilder("Remédios: ");
                double total = 0;
                for (Remedio r : p.getRemedios()) {
                    total += r.getPreco();
                }

                JLabel lblRemedios = new JLabel(String.format("Remédios: %d itens | Subtotal: R$ %.2f", p.getRemedios().size(), total));
                lblRemedios.setFont(new Font("Arial", Font.PLAIN, 14));
                lblRemedios.setForeground(Color.DARK_GRAY);

                JLabel lblTotal = new JLabel(String.format("Total: R$ %.2f", total));
                lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
                lblTotal.setForeground(new Color(0, 150, 0));

                infoPanel.add(lblPedido);
                infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                infoPanel.add(lblUsuario);
                infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                infoPanel.add(lblRemedios);
                infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                infoPanel.add(lblTotal);

                JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
                botoesPanel.setBackground(COR_BRANCO);

                if (p.getCaminhoReceita() != null) {
                    JButton btnVerReceita = new JButton("Ver Receita");
                    btnVerReceita.setFont(new Font("Arial", Font.PLAIN, 14));
                    btnVerReceita.setForeground(Color.WHITE);
                    btnVerReceita.setBackground(new Color(0, 123, 255));
                    btnVerReceita.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                    btnVerReceita.setFocusPainted(false);
                    btnVerReceita.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btnVerReceita.addActionListener(e -> {
                        try {
                            File receita = new File(p.getCaminhoReceita());
                            if (receita.exists()) {
                                Desktop.getDesktop().open(receita);
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Arquivo de receita não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(dialog, "Erro ao abrir receita: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    botoesPanel.add(btnVerReceita);
                }

                JButton btnAprovar = new JButton("Aprovar");
                btnAprovar.setFont(new Font("Arial", Font.BOLD, 16));
                btnAprovar.setForeground(Color.WHITE);
                btnAprovar.setBackground(new Color(0, 150, 0));
                btnAprovar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
                btnAprovar.setFocusPainted(false);
                btnAprovar.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnAprovar.addActionListener(e -> {
                    sistema.aprovarPedido(p.getId());
                    JOptionPane.showMessageDialog(dialog, "Pedido #" + p.getId() + " aprovado!");
                    dialog.dispose();
                    revisarPedidos();
                });
                botoesPanel.add(btnAprovar);

                cardPedido.add(infoPanel, BorderLayout.CENTER);
                cardPedido.add(botoesPanel, BorderLayout.EAST);

                painelPedidos.add(cardPedido);
                painelPedidos.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scroll = new JScrollPane(painelPedidos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scroll, BorderLayout.CENTER);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void gerenciarBloqueados() {
        JDialog dialog = new JDialog(this, "Usuários Bloqueados", true);
        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(COR_FUNDO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Usuários Bloqueados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        mainPanel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Usuario u : sistema.getUsuarios()) {
            if (!u.isAdmin() && u.isBloqueado()) {
                model.addElement(String.format("CPF: %s | %s | Tentativas: %d", 
                    u.getCpf(), u.getNome(), u.getTentativasErradas()));
            }
        }

        JList<String> lista = new JList<>(model);
        lista.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botoesPanel.setBackground(COR_FUNDO);

        JButton btnDesbloquear = criarBotaoAcao("Desbloquear Usuário");
        btnDesbloquear.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel != null) {
                String cpf = sel.split("CPF: ")[1].split(" ")[0];
                sistema.resetarTentativas(cpf);
                JOptionPane.showMessageDialog(dialog, "Usuário desbloqueado com sucesso!");
                dialog.dispose();
                gerenciarBloqueados();
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecione um usuário!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        botoesPanel.add(btnDesbloquear);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}
