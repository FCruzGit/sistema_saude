//src/views/TelaInicial.java

package views;

import core.SistemaFarmacia;
import models.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaInicial extends JFrame {
    private static final Color COR_AZUL_SUS = new Color(0, 94, 184);
    private static final Color COR_FUNDO = new Color(232, 244, 248);
    private static final Color COR_BRANCO = Color.WHITE;
    private SistemaFarmacia sistema = SistemaFarmacia.getInstance();

    public TelaInicial() {
        try {
            System.out.println("Iniciando TelaInicial...");
            setTitle("Sistema SUS - Farmácia Popular");
            setSize(1200, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            getContentPane().setBackground(COR_FUNDO);

            JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
            mainPanel.setBackground(COR_FUNDO);

            JPanel painelEsquerdo = criarPainelLogin();
            JPanel painelDireito = criarPainelImagem();

            mainPanel.add(painelEsquerdo);
            mainPanel.add(painelDireito);

            add(mainPanel);
            setVisible(true);
            System.out.println("TelaInicial carregada com sucesso");
        }
        catch (Exception e) {
            System.err.println("Erro ao criar TelaInicial:");
            e.printStackTrace();
        }
    }

    private JPanel criarPainelLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(COR_FUNDO);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COR_BRANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));

        JLabel titulo = new JLabel("Entrar");
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(COR_AZUL_SUS);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCpf.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCpf.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        JTextField txtCpf = new JTextField();
        txtCpf.setPreferredSize(new Dimension(450, 50));
        txtCpf.setMaximumSize(new Dimension(450, 50));
        txtCpf.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCpf.setBackground(new Color(240, 240, 240));
        txtCpf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        txtCpf.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSenha.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(450, 50));
        txtSenha.setMaximumSize(new Dimension(450, 50));
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSenha.setBackground(new Color(240, 240, 240));
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        txtSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtSenha.addActionListener(e -> realizarLogin(txtCpf, txtSenha));
        
        // Adicionar listener de Enter no campo CPF também
        txtCpf.addActionListener(e -> realizarLogin(txtCpf, txtSenha));

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(450, 45));
        btnEntrar.setMaximumSize(new Dimension(450, 45));
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setBackground(COR_AZUL_SUS);
        btnEntrar.setBorder(BorderFactory.createEmptyBorder());
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEntrar.addActionListener(e -> realizarLogin(txtCpf, txtSenha));

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setPreferredSize(new Dimension(450, 45));
        btnCadastrar.setMaximumSize(new Dimension(450, 45));
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(100, 100, 100));
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCadastrar.addActionListener(e -> abrirTelaCadastro());

        JButton btnGovBr = new JButton("Entrar com gov.br");
        btnGovBr.setPreferredSize(new Dimension(450, 40));
        btnGovBr.setMaximumSize(new Dimension(450, 40));
        btnGovBr.setFont(new Font("Arial", Font.PLAIN, 13));
        btnGovBr.setForeground(Color.WHITE);
        btnGovBr.setBackground(COR_AZUL_SUS);
        btnGovBr.setBorder(BorderFactory.createEmptyBorder());
        btnGovBr.setFocusPainted(false);
        btnGovBr.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGovBr.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGovBr.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Integração com gov.br em desenvolvimento.\nEm breve você poderá fazer login com sua conta gov.br!", 
                "Em Desenvolvimento", JOptionPane.INFORMATION_MESSAGE);
        });

        card.add(titulo);
        card.add(lblCpf);
        card.add(txtCpf);
        card.add(lblSenha);
        card.add(txtSenha);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(btnEntrar);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(btnCadastrar);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        
        JButton btnEsqueciSenha = new JButton("Esqueci minha senha");
        btnEsqueciSenha.setPreferredSize(new Dimension(450, 30));
        btnEsqueciSenha.setMaximumSize(new Dimension(450, 30));
        btnEsqueciSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        btnEsqueciSenha.setForeground(COR_AZUL_SUS);
        btnEsqueciSenha.setBackground(COR_BRANCO);
        btnEsqueciSenha.setBorder(BorderFactory.createEmptyBorder());
        btnEsqueciSenha.setFocusPainted(false);
        btnEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEsqueciSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEsqueciSenha.addActionListener(e -> recuperarSenha());
        
        card.add(btnEsqueciSenha);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(btnGovBr);

        panel.add(card);
        return panel;
    }

    private void realizarLogin(JTextField txtCpf, JPasswordField txtSenha) {
        String cpf = txtCpf.getText().toLowerCase();
        String senha = new String(txtSenha.getPassword());
        
        if (cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar se usuário está bloqueado
        Usuario usuarioCheck = sistema.buscarPorCpf(cpf);
        if (usuarioCheck != null && usuarioCheck.isBloqueado()) {
            JOptionPane.showMessageDialog(this, "Usuário bloqueado por excesso de tentativas!\nContate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuario = sistema.autenticar(cpf, senha);
        if (usuario != null) {
            if (usuario.isAdmin()) {
                new TelaAdmin();
            } else {
                new TelaSelecaoUBS(usuario);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "CPF ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaCadastro() {
        JDialog dialog = new JDialog(this, "Cadastrar-se", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COR_BRANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Criar Conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(COR_AZUL_SUS);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtCpf = criarCampoTexto("CPF");
        JTextField txtNome = criarCampoTexto("Nome Completo");
        JTextField txtEmail = criarCampoTexto("Email");
        JPasswordField txtSenha = criarCampoSenha("Senha");
        JTextField txtResposta = criarCampoTexto("Nome da sua mãe");

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setPreferredSize(new Dimension(400, 50));
        btnCadastrar.setMaximumSize(new Dimension(400, 50));
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(COR_AZUL_SUS);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.addActionListener(e -> {
            String cpf = txtCpf.getText();
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            String resposta = txtResposta.getText();

            if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || resposta.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                sistema.cadastrarUsuario(cpf, nome, email, senha, resposta);
                JOptionPane.showMessageDialog(dialog, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(txtCpf);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtNome);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtEmail);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtResposta);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(btnCadastrar);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private JTextField criarCampoTexto(String placeholder) {
        JTextField campo = new JTextField();
        campo.setPreferredSize(new Dimension(400, 45));
        campo.setMaximumSize(new Dimension(400, 45));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBackground(new Color(240, 240, 240));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(placeholder),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return campo;
    }

    private JPasswordField criarCampoSenha(String placeholder) {
        JPasswordField campo = new JPasswordField();
        campo.setPreferredSize(new Dimension(400, 45));
        campo.setMaximumSize(new Dimension(400, 45));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBackground(new Color(240, 240, 240));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(placeholder),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return campo;
    }

    private JPanel criarPainelImagem() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COR_FUNDO);

        try {
            File imgFile = new File("assets/tela_inicial.png");
            if (imgFile.exists()) {
                Image img = ImageIO.read(imgFile);
                Image scaledImg = img.getScaledInstance(600, 500, Image.SCALE_SMOOTH);
                JLabel lblImagem = new JLabel(new ImageIcon(scaledImg));
                panel.add(lblImagem);
            } else {
                JLabel lblTexto = new JLabel("SUS");
                lblTexto.setFont(new Font("Arial", Font.BOLD, 120));
                lblTexto.setForeground(COR_AZUL_SUS);
                panel.add(lblTexto);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
        }

        return panel;
    }

    private void recuperarSenha() {
        JDialog dialog = new JDialog(this, "Recuperar Senha", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(COR_FUNDO);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COR_BRANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Recuperar Senha");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(COR_AZUL_SUS);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtCpf = criarCampoTexto("CPF");
        JTextField txtResposta = criarCampoTexto("Nome da sua mãe");
        JPasswordField txtNovaSenha = criarCampoSenha("Nova Senha");

        JButton btnRecuperar = new JButton("Recuperar Senha");
        btnRecuperar.setPreferredSize(new Dimension(400, 50));
        btnRecuperar.setMaximumSize(new Dimension(400, 50));
        btnRecuperar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRecuperar.setForeground(Color.WHITE);
        btnRecuperar.setBackground(COR_AZUL_SUS);
        btnRecuperar.setBorder(BorderFactory.createEmptyBorder());
        btnRecuperar.setFocusPainted(false);
        btnRecuperar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecuperar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRecuperar.addActionListener(e -> {
            String cpf = txtCpf.getText();
            String resposta = txtResposta.getText();
            String novaSenha = new String(txtNovaSenha.getPassword());

            if (cpf.isEmpty() || resposta.isEmpty() || novaSenha.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (novaSenha.length() < 9) {
                JOptionPane.showMessageDialog(dialog, "A senha é muito fraca", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (novaSenha.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(dialog, "A senha não atende aos requisitos minimos de complexidade", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = sistema.buscarPorCpf(cpf);
            if (usuario == null) {
                JOptionPane.showMessageDialog(dialog, "CPF não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuario.isBloqueado()) {
                JOptionPane.showMessageDialog(dialog, "Usuário bloqueado por excesso de tentativas!\nContate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!usuario.getRespostaSeguranca().toLowerCase().equals(resposta.toLowerCase())) {
                sistema.incrementarTentativasErradas(cpf);
                JOptionPane.showMessageDialog(dialog, "Resposta de segurança incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sistema.alterarSenha(cpf, novaSenha);
            JOptionPane.showMessageDialog(dialog, "Senha alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(txtCpf);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtResposta);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(txtNovaSenha);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(btnRecuperar);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
