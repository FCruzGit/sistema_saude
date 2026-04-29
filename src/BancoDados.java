import java.sql.*;

public class BancoDados {
    private static final String URL = "jdbc:sqlite:farmacia.db";

    public static Connection conectar() {
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Conexão estabelecida com sucesso!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite não encontrado! Verifique se o sqlite-jdbc.jar está no classpath.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
            return null;
        }
    }

    public static void inicializarBanco() {
        try (Connection conn = conectar()) {
            if (conn == null) {
                System.err.println("Não foi possível conectar ao banco de dados!");
                return;
            }
            criarTabelas(conn);
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco:");
            e.printStackTrace();
        }
    }

    private static void criarTabelas(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            System.out.println("Criando tabelas...");
            stmt.execute("CREATE TABLE IF NOT EXISTS remedios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "preco REAL NOT NULL," +
                    "estoque INTEGER NOT NULL," +
                    "precisa_receita INTEGER NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "usuario_id INTEGER NOT NULL," +
                    "status TEXT NOT NULL," +
                    "FOREIGN KEY(usuario_id) REFERENCES usuarios(id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS pedido_remedios (" +
                    "pedido_id INTEGER NOT NULL," +
                    "remedio_id INTEGER NOT NULL," +
                    "FOREIGN KEY(pedido_id) REFERENCES pedidos(id)," +
                    "FOREIGN KEY(remedio_id) REFERENCES remedios(id))");

            stmt.execute("CREATE TABLE IF NOT EXISTS receitas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome_cliente TEXT NOT NULL," +
                    "arquivo TEXT NOT NULL," +
                    "validada INTEGER NOT NULL)");
            System.out.println("Tabelas criadas com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas:");
            e.printStackTrace();
        }
    }
}
