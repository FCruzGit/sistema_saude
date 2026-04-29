# Project Structure

## Directory Organization

```
sistema_saude/
├── src/                    # Source code files
│   ├── Main.java          # Application entry point
│   ├── BancoDados.java    # Database entity model
│   ├── GerenciadorJSON.java # JSON database layer
│   ├── SistemaFarmacia.java # Business logic singleton
│   ├── Usuario.java       # User entity
│   ├── UBS.java          # Health unit entity
│   ├── Remedio.java      # Medication entity
│   ├── Pedido.java       # Order entity
│   ├── Receita.java      # Prescription entity
│   ├── TelaInicial.java  # Login/registration screen
│   ├── TelaSelecaoUBS.java # UBS selection screen
│   ├── TelaCliente.java  # Client medication browsing screen
│   └── TelaAdmin.java    # Administrative panel
├── assets/                # Static resources
│   ├── drug_data/        # Medication images
│   ├── ubs_data/         # Health unit images
│   ├── user_data/        # User-uploaded files
│   │   └── receitas/     # Medical prescriptions
│   └── *.png             # UI icons and logos
├── .amazonq/             # Amazon Q configuration
│   └── rules/
│       └── memory-bank/  # Project documentation
├── dados.json            # JSON database file
└── README.md             # Project documentation
```

## Core Components

### Entity Layer
- **BancoDados**: Container class holding all system entities (users, UBS, medications, orders, prescriptions)
- **Usuario**: User accounts with authentication, balance, and role management
- **UBS**: Health unit information including location and identification
- **Remedio**: Medication details with pricing, stock, and prescription requirements
- **Pedido**: Order records linking users, medications, quantities, and status
- **Receita**: Prescription records with file paths and approval status

### Business Logic Layer
- **SistemaFarmacia**: Singleton managing all business operations
  - User authentication and registration
  - UBS and medication CRUD operations
  - Order processing and validation
  - Balance and stock management
  - Prescription approval workflow

### Persistence Layer
- **GerenciadorJSON**: Handles serialization/deserialization of BancoDados to/from JSON file
  - Automatic file creation on first run
  - Real-time data database
  - Thread-safe read/write operations

### Presentation Layer
- **TelaInicial**: Entry point for authentication and registration
- **TelaSelecaoUBS**: UBS browsing and selection interface
- **TelaCliente**: Medication catalog and shopping cart
- **TelaAdmin**: Administrative dashboard with management panels
- **Main**: Application launcher

## Architectural Patterns

### Singleton Pattern
- **SistemaFarmacia** implements singleton to ensure single source of truth for business logic
- Centralized state management across all UI screens

### MVC-Inspired Architecture
- **Model**: Entity classes (Usuario, UBS, Remedio, Pedido, Receita, BancoDados)
- **View**: Tela* classes (Swing UI components)
- **Controller**: SistemaFarmacia (business logic and state management)

### Data Access Pattern
- **GerenciadorJSON** acts as a simple DAO (Data Access Object)
- Abstracts JSON file operations from business logic
- Provides clean separation between database and domain logic

## Component Relationships

```
Main → TelaInicial
         ↓
    SistemaFarmacia (Singleton)
         ↓
    GerenciadorJSON
         ↓
    BancoDados (JSON File)

TelaInicial → TelaSelecaoUBS → TelaCliente
           → TelaAdmin

All Tela* classes ← SistemaFarmacia → BancoDados entities
```

## Data Flow

1. **Startup**: Main → TelaInicial → SistemaFarmacia.getInstance() → GerenciadorJSON.carregarDados()
2. **User Action**: UI Screen → SistemaFarmacia method → Entity manipulation → GerenciadorJSON.salvarDados()
3. **Navigation**: Current Screen → dispose() → New Screen → setVisible(true)

## Key Design Decisions

- **JSON Storage**: Simple file-based database suitable for desktop application without external database dependencies
- **Swing UI**: Native Java GUI framework for cross-platform desktop compatibility
- **Singleton Business Logic**: Ensures consistent state across multiple UI screens
- **Entity-Based Model**: Clear separation of concerns with dedicated classes for each domain concept
- **Image Management**: Timestamped filenames prevent collisions, organized by entity type
- **Real-time Persistence**: Every state change immediately saved to JSON file
