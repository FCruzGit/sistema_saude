# Technology Stack

## Programming Languages
- **Java**: Primary language (JDK 8 or higher required)
- **JSON**: Data database format

## Core Technologies

### UI Framework
- **Java Swing**: Desktop GUI framework
  - JFrame for window management
  - JPanel for layout containers
  - JButton, JTextField, JPasswordField for user input
  - JTable for data display
  - JScrollPane for scrollable content
  - Custom painting and styling

### Data Persistence
- **JSON**: File-based storage using `dados.json`
- **Java I/O**: File operations for images and prescriptions
- **Serialization**: Manual JSON serialization/deserialization

### Image Handling
- **ImageIO**: Reading and writing image files
- **BufferedImage**: Image manipulation and display
- **Supported formats**: PNG, JPG, JPEG, PDF (for prescriptions)

## Dependencies
- **Java Standard Library**: No external dependencies required
- **JDK Built-in Libraries**:
  - `javax.swing.*` - GUI components
  - `java.awt.*` - Graphics and layout
  - `java.io.*` - File I/O operations
  - `java.util.*` - Collections and utilities
  - `javax.imageio.*` - Image processing

## Build System
- **Manual Compilation**: Standard javac compilation
- **No build tool**: Direct Java compilation and execution
- **No dependency management**: Pure JDK implementation

## Development Commands

### Compilation
```bash
# Compile all source files
javac -d bin src/*.java

# Compile specific file
javac -d bin src/Main.java
```

### Execution
```bash
# Run from compiled classes
java -cp bin Main

# Run directly (if in src directory)
java Main
```

### Project Setup
```bash
# Create necessary directories
mkdir -p assets/drug_data
mkdir -p assets/ubs_data
mkdir -p assets/user_data/receitas

# No additional setup required - application creates dados.json automatically
```

## System Requirements

### Minimum Requirements
- **Java Runtime**: JDK 8 or higher
- **Operating System**: Windows, Linux, or macOS
- **Display**: 1200x700 pixels minimum resolution
- **Storage**: ~50MB for application and data
- **Memory**: 256MB RAM minimum

### Recommended Requirements
- **Java Runtime**: JDK 11 or higher
- **Display**: 1920x1080 pixels
- **Memory**: 512MB RAM

## File Structure Requirements

### Required Directories
- `assets/` - Static resources (icons, logos)
- `assets/drug_data/` - Medication images
- `assets/ubs_data/` - Health unit images
- `assets/user_data/receitas/` - Uploaded prescriptions

### Generated Files
- `dados.json` - Database file (auto-created on first run)
- `assets/drug_data/drug_*.png` - Uploaded medication images
- `assets/ubs_data/ubs_*.png` - Uploaded UBS images
- `assets/user_data/receitas/receita_*.{pdf,jpg,jpeg,png}` - Uploaded prescriptions

## Color Scheme (SUS Branding)
- **Primary Blue**: RGB(0, 94, 184) - #005EB8
- **Background**: RGB(232, 244, 248) - #E8F4F8
- **White**: RGB(255, 255, 255) - #FFFFFF
- **Success Green**: RGB(0, 150, 0) - #009600

## Development Environment
- **IDE**: Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans, VS Code)
- **Version Control**: Git
- **Encoding**: UTF-8 for source files
- **Line Endings**: Platform-specific (CRLF on Windows, LF on Unix)

## Deployment
- **Distribution**: JAR file or compiled classes
- **Installation**: Copy application files and assets directory
- **Configuration**: No external configuration files needed
- **Updates**: Replace JAR/classes and preserve dados.json

## Testing
- **Manual Testing**: UI-based testing through application
- **No Unit Tests**: Current implementation lacks automated tests
- **Test Data**: Default admin account (CPF: "administrador", Password: "123")

## Performance Considerations
- **JSON Loading**: Entire database loaded into memory on startup
- **Image Caching**: Images loaded on-demand from disk
- **UI Responsiveness**: Swing EDT for UI updates
- **File I/O**: Synchronous operations (blocking)

## Security Features
- **Password Storage**: Plain text (educational project - not production-ready)
- **Input Validation**: CPF format, password strength, file type validation
- **Account Lockout**: 3 failed login attempts
- **File Upload Validation**: Extension and size checks
