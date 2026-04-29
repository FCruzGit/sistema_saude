# Development Guidelines

## Code Quality Standards

### Naming Conventions
- **Classes**: PascalCase with descriptive Portuguese names (e.g., `TelaInicial`, `SistemaFarmacia`, `GerenciadorJSON`)
- **Methods**: camelCase with Portuguese verbs (e.g., `conectar()`, `adicionarRemedio()`, `isValidada()`)
- **Variables**: camelCase with descriptive Portuguese names (e.g., `nomeCliente`, `caminhoReceita`, `precisaReceita`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `URL`, though limited usage in codebase)
- **Boolean methods**: Prefix with `is` or `precisa` (e.g., `isValidada()`, `isPrecisaReceita()`, `precisaReceita()`)

### File Organization
- One public class per file
- File name matches class name exactly
- All source files in `src/` directory (flat structure, no packages)
- No package declarations - default package used throughout

### Code Formatting
- **Indentation**: 4 spaces (no tabs)
- **Braces**: Opening brace on same line, closing brace on new line
- **Line length**: No strict limit, but generally kept under 120 characters
- **Blank lines**: Single blank line between methods
- **Spacing**: Space after keywords (`if`, `for`, `while`), no space before parentheses in method calls

### Documentation
- **Minimal comments**: Code is self-documenting through descriptive names
- **Console logging**: Extensive use of `System.out.println()` for debugging and status messages
- **Error messages**: Portuguese error messages with `System.err.println()` and stack traces
- **No JavaDoc**: No formal documentation comments in codebase

## Structural Conventions

### Entity Classes Pattern
All entity classes follow a consistent structure:

```java
public class EntityName {
    // Private fields
    private int id;
    private String field1;
    private boolean field2;
    
    // Constructor(s) - multiple overloaded constructors for flexibility
    public EntityName(int id, String field1) {
        this.id = id;
        this.field1 = field1;
        this.field2 = false; // Default values
    }
    
    // Getters - one-line format
    public int getId() { return id; }
    public String getField1() { return field1; }
    public boolean isField2() { return field2; }
    
    // Setters - one-line format (only when needed)
    public void setField1(String field1) { this.field1 = field1; }
}
```

**Pattern frequency**: 5/5 entity classes (Usuario, UBS, Remedio, Pedido, Receita)

### Constructor Overloading
- Multiple constructors with increasing parameter counts
- Later constructors build upon earlier ones
- Default values assigned for optional fields (empty strings, false, null)
- Null-safe assignments using ternary operators: `field = value != null ? value : ""`

**Example from UBS.java**:
```java
public UBS(int id, String nome, String endereco) { /* minimal */ }
public UBS(int id, String nome, String endereco, String imagem) { /* + image */ }
public UBS(int id, String nome, String endereco, String imagem, String logradouro, ...) { /* full */ }
```

**Pattern frequency**: 3/5 entity classes use constructor overloading

### Getter/Setter Style
- **One-line format**: `public Type getField() { return field; }`
- **No validation**: Setters directly assign values
- **Boolean getters**: Use `is` prefix instead of `get`
- **Selective setters**: Only provided for mutable fields (id typically has no setter)

**Pattern frequency**: 5/5 entity classes

## Architectural Patterns

### Singleton Pattern
```java
public class SistemaFarmacia {
    private static SistemaFarmacia instancia;
    
    private SistemaFarmacia() {
        // Private constructor
    }
    
    public static SistemaFarmacia getInstance() {
        if (instancia == null) {
            instancia = new SistemaFarmacia();
        }
        return instancia;
    }
}
```

**Usage**: Central business logic class (SistemaFarmacia)

### Swing EDT Pattern
Always launch UI on Event Dispatch Thread:

```java
SwingUtilities.invokeLater(() -> {
    try {
        new TelaInicial();
    } catch (Exception e) {
        System.err.println("Erro fatal ao iniciar aplicação:");
        e.printStackTrace();
    }
});
```

**Pattern frequency**: Used in Main.java entry point

### Database Connection Pattern (Legacy)
Note: BancoDados.java contains SQLite code but is not used in current JSON-based implementation:

```java
public static Connection conectar() {
    try {
        System.out.println("Tentando conectar ao banco de dados...");
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        System.out.println("Conexão estabelecida com sucesso!");
        return conn;
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("Erro ao conectar ao banco de dados:");
        e.printStackTrace();
        return null;
    }
}
```

**Status**: Legacy code - not actively used (JSON database is current approach)

## Common Implementation Patterns

### Collection Initialization
```java
private List<Remedio> remedios;

public Pedido(int id, Usuario usuario) {
    this.remedios = new ArrayList<>();
}
```

**Pattern**: Initialize collections in constructor, not at declaration

### Business Logic Methods
```java
public boolean precisaReceita() {
    for (Remedio r : remedios) {
        if (r.isPrecisaReceita()) {
            return true;
        }
    }
    return false;
}
```

**Pattern**: Simple iteration with early return for boolean checks

### Error Handling
```java
try {
    // Operation
} catch (Exception e) {
    System.err.println("Erro ao [operação]:");
    e.printStackTrace();
    return null; // or appropriate default
}
```

**Pattern frequency**: Consistent across all file I/O and database operations

### Null Safety
```java
this.logradouro = logradouro != null ? logradouro : "";
```

**Pattern**: Ternary operator for null-safe assignments with empty string defaults

## Language and Localization

### Portuguese-First Approach
- All class names, method names, and variables in Portuguese
- Console messages in Portuguese
- UI text in Portuguese
- Comments (when present) in Portuguese

### String Literals
- Direct string literals in code (no resource bundles)
- Hardcoded UI text in Tela* classes
- No internationalization support

## Testing Practices

### Current State
- No unit tests present in codebase
- Manual testing through UI
- Console logging for debugging

### Debugging Approach
```java
System.out.println("=== INICIANDO SISTEMA DE FARMÁCIA ===");
System.out.println("Java Version: " + System.getProperty("java.version"));
System.out.println("Working Directory: " + System.getProperty("user.dir"));
```

**Pattern**: Verbose console output for tracking application state

## Best Practices Observed

1. **Immutable IDs**: Entity IDs have getters but no setters
2. **Encapsulation**: All fields private with public accessors
3. **Defensive copying**: Collections returned directly (not defensive - potential improvement area)
4. **Constructor chaining**: Not used - each constructor is independent
5. **Null handling**: Explicit null checks with ternary operators
6. **Exception handling**: Catch-and-log pattern with stack traces
7. **Resource management**: Try-with-resources for database connections (in legacy code)

## Code Idioms

### Field Declaration and Initialization
```java
private String campo = ""; // Default at declaration
// OR
private String campo; // Initialize in constructor
this.campo = valor != null ? valor : "";
```

### Method Chaining
Not used in this codebase - methods return void or values, not `this`

### Builder Pattern
Not used - constructor overloading preferred

### Factory Methods
Not used - direct constructor calls throughout

## Anti-Patterns to Avoid

Based on codebase analysis:

1. **No packages**: All classes in default package (acceptable for small projects, but limits scalability)
2. **Public fields**: Never used - good practice maintained
3. **God classes**: SistemaFarmacia may become large - monitor complexity
4. **Magic numbers**: Limited use of constants - some hardcoded values in UI code
5. **Deep nesting**: Generally avoided with early returns

## Recommended Practices for New Code

1. **Follow existing naming**: Use Portuguese names consistent with codebase
2. **Match formatting**: 4-space indentation, same-line braces
3. **Add console logging**: Use `System.out.println()` for important operations
4. **Handle exceptions**: Always catch and log with stack traces
5. **Use constructor overloading**: Provide multiple constructors for flexibility
6. **One-line accessors**: Keep getters/setters concise
7. **Initialize collections**: Always initialize in constructor
8. **Null-safe assignments**: Use ternary operator pattern
9. **Boolean naming**: Use `is` or `precisa` prefixes
10. **No JavaDoc**: Keep code self-documenting through clear names
