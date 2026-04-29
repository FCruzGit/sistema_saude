import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GerenciadorJSON {
    private static final String ARQUIVO = "dados.json";
    
    public static Map<String, Object> carregarDados() {
        try {
            File file = new File(ARQUIVO);
            if (!file.exists()) {
                return criarArquivoInicial();
            }
            String conteudo = new String(Files.readAllBytes(Paths.get(ARQUIVO)));
            return parseJSON(conteudo);
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return criarArquivoInicial();
        }
    }
    
    private static Map<String, Object> criarArquivoInicial() {
        Map<String, Object> dados = new HashMap<>();
        List<Map<String, Object>> usuarios = new ArrayList<>();
        List<Map<String, Object>> remedios = new ArrayList<>();
        List<Map<String, Object>> pedidos = new ArrayList<>();
        List<Map<String, Object>> receitas = new ArrayList<>();
        List<Map<String, Object>> ubs = new ArrayList<>();
        
        // Admin padrão
        Map<String, Object> admin = new HashMap<>();
        admin.put("cpf", "administrador");
        admin.put("senha", "123");
        admin.put("nome", "Administrador");
        admin.put("email", "admin@sus.gov.br");
        admin.put("isAdmin", true);
        usuarios.add(admin);
        
        // UBS
        ubs.add(criarUBS(1, "UBS Jordanópolis", "Viela Jangada Nova", "75", "Jardim Pres.", "SP", "04830-200"));
        ubs.add(criarUBS(2, "UBS Vila Mariana", "Rua das Flores", "123", "Vila Mariana", "SP", "04567-890"));
        ubs.add(criarUBS(3, "UBS Mooca", "Av. Paes de Barros", "456", "Mooca", "SP", "03115-000"));
        
        // Remédios vinculados às UBS
        remedios.add(criarRemedio(1, "Paracetamol", 10.50, 100, false, 1, "Analgésico e antitérmico", "Comprimido", "500mg"));
        remedios.add(criarRemedio(2, "Ibuprofeno", 15.00, 50, false, 1, "Anti-inflamatório não esteroidal", "Comprimido", "600mg"));
        remedios.add(criarRemedio(3, "Amoxicilina", 25.00, 30, true, 1, "Antibiótico de amplo espectro", "Cápsula", "500mg"));
        remedios.add(criarRemedio(4, "Dipirona", 8.00, 80, false, 2, "Analgésico e antitérmico potente", "Comprimido", "1g"));
        remedios.add(criarRemedio(5, "Losartana", 12.00, 60, false, 2, "Anti-hipertensivo", "Comprimido", "50mg"));
        remedios.add(criarRemedio(6, "Omeprazol", 18.00, 40, false, 3, "Inibidor da bomba de prótons", "Cápsula", "20mg"));
        remedios.add(criarRemedio(7, "Metformina", 14.00, 70, false, 1, "Antidiabético oral", "Comprimido", "850mg"));
        remedios.add(criarRemedio(8, "Atenolol", 11.00, 55, false, 2, "Beta-bloqueador cardioseletivo", "Comprimido", "25mg"));
        remedios.add(criarRemedio(9, "Sinvastatina", 16.00, 45, false, 3, "Redutor de colesterol", "Comprimido", "20mg"));
        remedios.add(criarRemedio(10, "Captopril", 9.00, 90, false, 1, "Inibidor da ECA", "Comprimido", "25mg"));
        remedios.add(criarRemedio(11, "Azitromicina", 28.00, 25, true, 2, "Antibiótico macrolídeo", "Comprimido", "500mg"));
        remedios.add(criarRemedio(12, "Dexametasona", 13.00, 35, true, 3, "Corticosteroide anti-inflamatório", "Comprimido", "4mg"));
        
        dados.put("usuarios", usuarios);
        dados.put("remedios", remedios);
        dados.put("pedidos", pedidos);
        dados.put("receitas", receitas);
        dados.put("ubs", ubs);
        
        salvarDados(dados);
        return dados;
    }
    
    private static Map<String, Object> criarUBS(int id, String nome, String logradouro, String numero, String bairro, String estado, String cep) {
        Map<String, Object> ubs = new HashMap<>();
        ubs.put("id", id);
        ubs.put("nome", nome);
        ubs.put("logradouro", logradouro);
        ubs.put("numero", numero);
        ubs.put("bairro", bairro);
        ubs.put("estado", estado);
        ubs.put("cep", cep);
        ubs.put("imagem", "");
        return ubs;
    }
    
    private static Map<String, Object> criarRemedio(int id, String nome, double preco, int estoque, boolean precisaReceita, int ubsId, String descricao, String tipo, String gramatura) {
        Map<String, Object> remedio = new HashMap<>();
        remedio.put("id", id);
        remedio.put("nome", nome);
        remedio.put("preco", preco);
        remedio.put("estoque", estoque);
        remedio.put("precisaReceita", precisaReceita);
        remedio.put("ubsId", ubsId);
        remedio.put("imagem", "");
        remedio.put("descricao", descricao);
        remedio.put("tipo", tipo);
        remedio.put("gramatura", gramatura);
        return remedio;
    }
    
    public static void salvarDados(Map<String, Object> dados) {
        try {
            String json = toJSON(dados);
            Files.write(Paths.get(ARQUIVO), json.getBytes());
            System.out.println("Dados salvos com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String toJSON(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof String) return "\"" + obj.toString().replace("\"", "\\\"") + "\"";
        if (obj instanceof Number || obj instanceof Boolean) return obj.toString();
        
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if (!first) sb.append(",");
                sb.append("\"").append(entry.getKey()).append("\":").append(toJSON(entry.getValue()));
                first = false;
            }
            sb.append("}");
            return sb.toString();
        }
        
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object item : list) {
                if (!first) sb.append(",");
                sb.append(toJSON(item));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        }
        
        return "\"" + obj.toString() + "\"";
    }
    
    private static Map<String, Object> parseJSON(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseObject(json);
        }
        return new HashMap<>();
    }
    
    private static Map<String, Object> parseObject(String json) {
        Map<String, Object> map = new HashMap<>();
        json = json.substring(1, json.length() - 1).trim();
        
        if (json.isEmpty()) return map;
        
        List<String> tokens = tokenize(json);
        for (int i = 0; i < tokens.size(); i += 2) {
            String key = tokens.get(i).replace("\"", "");
            if (i + 1 < tokens.size()) {
                map.put(key, parseValue(tokens.get(i + 1)));
            }
        }
        
        return map;
    }
    
    private static List<String> tokenize(String json) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inString = false;
        int depth = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
                current.append(c);
            } else if (!inString && (c == '{' || c == '[')) {
                depth++;
                current.append(c);
            } else if (!inString && (c == '}' || c == ']')) {
                depth--;
                current.append(c);
            } else if (!inString && c == ':' && depth == 0) {
                tokens.add(current.toString().trim());
                current = new StringBuilder();
            } else if (!inString && c == ',' && depth == 0) {
                tokens.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        if (current.length() > 0) {
            tokens.add(current.toString().trim());
        }
        
        return tokens;
    }
    
    private static Object parseValue(String value) {
        value = value.trim();
        
        if (value.equals("null")) return null;
        if (value.equals("true")) return true;
        if (value.equals("false")) return false;
        
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        
        if (value.startsWith("[")) {
            return parseArray(value);
        }
        
        if (value.startsWith("{")) {
            return parseObject(value);
        }
        
        try {
            if (value.contains(".")) {
                return Double.parseDouble(value);
            }
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
    
    private static List<Object> parseArray(String json) {
        List<Object> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim();
        
        if (json.isEmpty()) return list;
        
        StringBuilder current = new StringBuilder();
        boolean inString = false;
        int depth = 0;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
                current.append(c);
            } else if (!inString && (c == '{' || c == '[')) {
                depth++;
                current.append(c);
            } else if (!inString && (c == '}' || c == ']')) {
                depth--;
                current.append(c);
            } else if (!inString && c == ',' && depth == 0) {
                list.add(parseValue(current.toString().trim()));
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        if (current.length() > 0) {
            list.add(parseValue(current.toString().trim()));
        }
        
        return list;
    }
}
