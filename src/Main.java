//src/Main.java

import views.TelaInicial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE FARMÁCIA ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        
        SwingUtilities.invokeLater(() -> {
            try {
                new TelaInicial();
            }
            catch (Exception e) {
                System.err.println("Erro fatal ao iniciar aplicação:");
                e.printStackTrace();
            }
        });
    }
}