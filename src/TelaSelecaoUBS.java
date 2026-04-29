import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class TelaSelecaoUBS extends JFrame {
    private static final Color COR_AZUL_SUS = new Color(0, 94, 184);
    private static final Color COR_FUNDO = new Color(232, 244, 248);
    private static final Color COR_BRANCO = Color.WHITE;
    
    private SistemaFarmacia sistema = SistemaFarmacia.getInstance();
    private Usuario usuarioAtual;

    public TelaSelecaoUBS(Usuario usuario) {
        this.usuarioAtual = usuario;
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
        headerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 50, 15, 50));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COR_BRANCO);
        header.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
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

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setBackground(COR_BRANCO);

        JLabel lblUsuario = new JLabel("Olá, " + usuarioAtual.getNome() + " ");
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

        JLabel titulo = new JLabel("Selecione a unidade", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(COR_AZUL_SUS);

        try {
            File cartFile = new File("assets/cart.png");
            if (cartFile.exists()) {
                Image img = ImageIO.read(cartFile);
                Image scaledImg = img.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                JLabel btnCarrinho = new JLabel(new ImageIcon(scaledImg));
                btnCarrinho.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tituloPanel.add(btnCarrinho, BorderLayout.EAST);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar cart.png: " + e.getMessage());
        }

        tituloPanel.add(btnVoltar, BorderLayout.WEST);
        tituloPanel.add(titulo, BorderLayout.CENTER);

        container.add(tituloPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new BorderLayout(20, 0));
        mainContentPanel.setBackground(COR_BRANCO);

        JPanel painelUBSWrapper = new JPanel(new BorderLayout());
        painelUBSWrapper.setBackground(COR_BRANCO);

        JPanel painelPesquisa = new JPanel(new BorderLayout(10, 0));
        painelPesquisa.setBackground(COR_BRANCO);
        painelPesquisa.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10));

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

        JPanel painelUBSScroll = new JPanel(new BorderLayout());
        painelUBSScroll.setBackground(COR_BRANCO);
        painelUBSScroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel painelUBS = new JPanel();
        painelUBS.setLayout(new BoxLayout(painelUBS, BoxLayout.Y_AXIS));
        painelUBS.setBackground(COR_BRANCO);

        int numero = 1;
        UBS[] ubsArray = sistema.getUBS().toArray(new UBS[0]);
        UBS[] ubsSelecionada = {ubsArray.length > 0 ? ubsArray[0] : null};
        
        for (UBS ubs : ubsArray) {
            JPanel cardUBS = criarCardUBS(ubs, numero++, ubsSelecionada, wrapper);
            painelUBS.add(cardUBS);
            painelUBS.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        txtPesquisa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            
            public void filtrar() {
                String termo = txtPesquisa.getText().toLowerCase();
                painelUBS.removeAll();
                
                int num = 1;
                for (UBS ubs : ubsArray) {
                    if (ubs.getNome().toLowerCase().contains(termo) || 
                        ubs.getEndereco().toLowerCase().contains(termo)) {
                        JPanel cardUBS = criarCardUBS(ubs, num++, ubsSelecionada, wrapper);
                        painelUBS.add(cardUBS);
                        painelUBS.add(Box.createRigidArea(new Dimension(0, 15)));
                    }
                }
                
                painelUBS.revalidate();
                painelUBS.repaint();
            }
        });

        painelUBSScroll.add(painelUBS, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(painelUBSScroll);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(600, 0));

        painelUBSWrapper.add(painelPesquisa, BorderLayout.NORTH);
        painelUBSWrapper.add(scroll, BorderLayout.CENTER);

        JPanel painelResumo = criarPainelResumo(ubsSelecionada[0]);
        painelResumo.setPreferredSize(new Dimension(400, 0));

        mainContentPanel.add(painelUBSWrapper, BorderLayout.CENTER);
        mainContentPanel.add(painelResumo, BorderLayout.EAST);

        container.add(mainContentPanel, BorderLayout.CENTER);
        wrapper.add(container, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel painelResumoAtual;

    private JPanel criarCardUBS(UBS ubs, int numero, UBS[] ubsSelecionada, JPanel wrapper) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(COR_BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(COR_BRANCO);

        JLabel lblIcone = null;
        try {
            File homeFile = new File("assets/home.png");
            if (homeFile.exists()) {
                Image img = ImageIO.read(homeFile);
                Image scaledImg = img.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
                lblIcone = new JLabel(new ImageIcon(scaledImg));
            } else {
                lblIcone = new JLabel(String.valueOf(numero));
                lblIcone.setFont(new Font("Arial", Font.BOLD, 28));
                lblIcone.setForeground(COR_AZUL_SUS);
            }
        } catch (Exception e) {
            lblIcone = new JLabel(String.valueOf(numero));
            lblIcone.setFont(new Font("Arial", Font.BOLD, 28));
            lblIcone.setForeground(COR_AZUL_SUS);
        }

        JLabel lblNome = new JLabel(ubs.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 22));
        lblNome.setForeground(COR_AZUL_SUS);

        String nomeExibicao = ubs.getNome();
        if (nomeExibicao.length() > 30) {
            nomeExibicao = nomeExibicao.substring(0, 30) + "...";
        }
        lblNome.setText(nomeExibicao);

        String enderecoExibicao = "";
        if (ubs.getLogradouro() != null && !ubs.getLogradouro().isEmpty()) {
            enderecoExibicao = ubs.getLogradouro() + ", " + ubs.getNumero() + " - " + ubs.getBairro() + ", " + ubs.getEstado();
        } else {
            enderecoExibicao = ubs.getEndereco();
        }
        
        if (enderecoExibicao.length() > 50) {
            enderecoExibicao = enderecoExibicao.substring(0, 50) + "...";
        }

        JLabel lblEndereco = new JLabel(enderecoExibicao);
        lblEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEndereco.setForeground(Color.GRAY);

        infoPanel.add(lblNome);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(lblEndereco);

        card.add(lblIcone, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ubsSelecionada[0] = ubs;
                atualizarResumo(ubs);
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

    private JPanel criarPainelResumo(UBS ubs) {
        JPanel resumoWrapper = new JPanel(new BorderLayout());
        resumoWrapper.setBackground(COR_BRANCO);
        resumoWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_AZUL_SUS, 2, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        painelResumoAtual = new JPanel(new BorderLayout(0, 20));
        painelResumoAtual.setBackground(COR_BRANCO);

        if (ubs != null) {
            JLabel lblTitulo = new JLabel(ubs.getNome());
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
            lblTitulo.setForeground(COR_AZUL_SUS);

            JPanel topPanel = new JPanel(new BorderLayout(15, 0));
            topPanel.setBackground(COR_BRANCO);

            JLabel lblImagem = null;
            try {
                String caminhoImagem = ubs.getImagem() != null && !ubs.getImagem().isEmpty() 
                    ? "assets/ubs_data/" + ubs.getImagem() 
                    : "assets/local.png";
                File imgFile = new File(caminhoImagem);
                if (imgFile.exists()) {
                    Image img = ImageIO.read(imgFile);
                    Image scaledImg = img.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                    lblImagem = new JLabel(new ImageIcon(scaledImg));
                    lblImagem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem da UBS: " + e.getMessage());
            }

            JPanel dadosPanel = new JPanel();
            dadosPanel.setLayout(new BoxLayout(dadosPanel, BoxLayout.Y_AXIS));
            dadosPanel.setBackground(COR_BRANCO);

            JLabel lblEndereco = new JLabel("<html><b>Endereço:</b><br>" + ubs.getEndereco() + "</html>");
            lblEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
            lblEndereco.setForeground(Color.DARK_GRAY);
            lblEndereco.setAlignmentX(Component.LEFT_ALIGNMENT);

            dadosPanel.add(lblEndereco);

            topPanel.add(dadosPanel, BorderLayout.CENTER);
            if (lblImagem != null) {
                topPanel.add(lblImagem, BorderLayout.EAST);
            }

            JButton btnSelecionar = new JButton("Selecionar Unidade");
            btnSelecionar.setFont(new Font("Arial", Font.BOLD, 16));
            btnSelecionar.setForeground(Color.WHITE);
            btnSelecionar.setBackground(COR_AZUL_SUS);
            btnSelecionar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
            btnSelecionar.setFocusPainted(false);
            btnSelecionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSelecionar.addActionListener(e -> {
                new TelaCliente(usuarioAtual, ubs.getId());
                dispose();
            });

            JPanel wrapperPanel = new JPanel(new BorderLayout(0, 20));
            wrapperPanel.setBackground(COR_BRANCO);
            wrapperPanel.add(lblTitulo, BorderLayout.NORTH);
            wrapperPanel.add(topPanel, BorderLayout.CENTER);
            wrapperPanel.add(btnSelecionar, BorderLayout.SOUTH);

            painelResumoAtual.add(wrapperPanel, BorderLayout.NORTH);
        }

        resumoWrapper.add(painelResumoAtual, BorderLayout.NORTH);
        return resumoWrapper;
    }

    private void atualizarResumo(UBS ubs) {
        painelResumoAtual.removeAll();

        JLabel lblTitulo = new JLabel(ubs.getNome());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(COR_AZUL_SUS);

        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.setBackground(COR_BRANCO);

        JLabel lblImagem = null;
        try {
            String caminhoImagem = ubs.getImagem() != null && !ubs.getImagem().isEmpty() 
                ? "assets/ubs_data/" + ubs.getImagem() 
                : "assets/local.png";
            File imgFile = new File(caminhoImagem);
            if (imgFile.exists()) {
                Image img = ImageIO.read(imgFile);
                Image scaledImg = img.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                lblImagem = new JLabel(new ImageIcon(scaledImg));
                lblImagem.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem da UBS: " + e.getMessage());
        }

        JPanel dadosPanel = new JPanel();
        dadosPanel.setLayout(new BoxLayout(dadosPanel, BoxLayout.Y_AXIS));
        dadosPanel.setBackground(COR_BRANCO);

        JLabel lblEndereco = new JLabel("<html><b>Endereço:</b><br>" + ubs.getEndereco() + "</html>");
        lblEndereco.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEndereco.setForeground(Color.DARK_GRAY);
        lblEndereco.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEndereco.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        lblEndereco.setPreferredSize(new Dimension(200, lblEndereco.getPreferredSize().height));
        lblEndereco.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
        lblEndereco.setPreferredSize(new Dimension(200, lblEndereco.getPreferredSize().height));

        dadosPanel.add(lblEndereco);

        topPanel.add(dadosPanel, BorderLayout.CENTER);
        if (lblImagem != null) {
            topPanel.add(lblImagem, BorderLayout.EAST);
        }

        JButton btnSelecionar = new JButton("Selecionar Unidade");
        btnSelecionar.setFont(new Font("Arial", Font.BOLD, 16));
        btnSelecionar.setForeground(Color.WHITE);
        btnSelecionar.setBackground(COR_AZUL_SUS);
        btnSelecionar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btnSelecionar.setFocusPainted(false);
        btnSelecionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSelecionar.addActionListener(e -> {
            new TelaCliente(usuarioAtual, ubs.getId());
            dispose();
        });

        JPanel wrapperPanel = new JPanel(new BorderLayout(0, 20));
        wrapperPanel.setBackground(COR_BRANCO);
        wrapperPanel.add(lblTitulo, BorderLayout.NORTH);
        wrapperPanel.add(topPanel, BorderLayout.CENTER);
        wrapperPanel.add(btnSelecionar, BorderLayout.SOUTH);

        painelResumoAtual.add(wrapperPanel, BorderLayout.NORTH);

        painelResumoAtual.revalidate();
        painelResumoAtual.repaint();
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
