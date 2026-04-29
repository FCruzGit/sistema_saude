# Sistema SUS - Farmácia Popular

Sistema de gerenciamento de farmácias, produtos e usuarios do SUS desenvolvido em Java com interface gráfica Swing.

## Descrição

O Sistema SUS - Farmácia Popular é uma aplicação desktop que permite aos usuários consultar e adquirir medicamentos disponíveis nas Unidades Básicas de Saúde (UBS). O sistema oferece funcionalidades completas de gerenciamento para administradores e uma interface intuitiva para os clientes.

## Funcionalidades

### Para Usuários (Clientes)

#### 1. Autenticação e Cadastro
- **Login**: Acesso com CPF e senha (case-insensitive)
- **Cadastro**: Criação de conta com validações de segurança
  - CPF (11 dígitos numéricos)
  - Nome completo
  - Email
  - Senha (mínimo 9 caracteres, não pode ser apenas números)
  - Pergunta de segurança (nome da mãe)
- **Recuperação de Senha**: Sistema de recuperação via pergunta de segurança
- **Bloqueio de Conta**: Após 3 tentativas incorretas de login

#### 2. Seleção de UBS
- Visualização de todas as Unidades Básicas de Saúde disponíveis
- Busca por nome ou endereço
- Informações detalhadas de cada UBS:
  - Nome da unidade
  - Endereço completo (logradouro, número, bairro, estado, CEP)
  - Imagem da unidade
- Seleção da UBS para consultar medicamentos disponíveis

#### 3. Consulta de Medicamentos
- Listagem de medicamentos disponíveis na UBS selecionada
- Busca por nome do medicamento
- Informações detalhadas de cada remédio:
  - Nome
  - Descrição
  - Tipo (comprimido, cápsula, etc.)
  - Gramatura/dosagem
  - Preço (com desconto simulado)
  - Necessidade de receita médica
  - Imagem do medicamento
  - Estoque disponível

#### 4. Carrinho de Compras
- Adição de medicamentos ao carrinho
- Seleção de quantidade
- Visualização do total
- Remoção de itens
- Finalização do pedido

#### 5. Sistema de Pedidos
- Criação automática de pedidos
- Upload de receita médica (para medicamentos controlados)
  - Formatos aceitos: PDF, JPG, JPEG, PNG
- Validação de saldo
- Validação de estoque
- Desconto automático do saldo
- Atualização automática do estoque
- Status do pedido:
  - **Pendente**: Aguardando aprovação (medicamentos com receita)
  - **Aprovado**: Pedido aprovado e processado

### Sistema Gerenciador de dados

O sistema utiliza um arquivo JSON (`dados.json`) para armazenar todas as informações:

```json
{
  "usuarios": [...],
  "ubs": [...],
  "remedios": [...],
  "pedidos": [...],
  "receitas": [...]
}
```

#### Estrutura de UBS
```json
{
  "id": 1,
  "nome": "UBS Jordanópolis",
  "logradouro": "Viela Jangada Nova",
  "numero": "75",
  "bairro": "Jardim Pres.",
  "estado": "SP",
  "cep": "04830-200",
  "imagem": "ubs_1234567890.png"
}
```

#### Estrutura de Remédio
```json
{
  "id": 1,
  "nome": "Paracetamol",
  "preco": 10.50,
  "estoque": 100,
  "precisaReceita": false,
  "ubsId": 1,
  "imagem": "drug_1234567890.png",
  "descricao": "Analgésico e antitérmico",
  "tipo": "Comprimido",
  "gramatura": "500mg"
}
```

## 📁 Estrutura de Diretórios

```
sistema_saude/
├── src/
│   ├── core/                    # Facade - Interface unificada
│   │   └── SistemaFarmacia.java
│   ├── services/                # Lógica de negócio
│   │   ├── AutenticacaoService.java
│   │   ├── UsuarioService.java
│   │   ├── UBSService.java
│   │   ├── RemedioService.java
│   │   └── PedidoService.java
│   ├── models/                  # Entidades
│   │   ├── Usuario.java
│   │   ├── UBS.java
│   │   ├── Remedio.java
│   │   ├── Pedido.java
│   │   └── Receita.java
│   ├── views/                   # Interface gráfica
│   │   ├── TelaInicial.java
│   │   ├── TelaSelecaoUBS.java
│   │   ├── TelaCliente.java
│   │   └── TelaAdmin.java
│   ├── database/                # Persistência
│   │   └── GerenciadorJSON.java
│   └── Main.java                # Ponto de entrada
├── assets/
│   ├── logo.png
│   ├── tela_inicial.png
│   ├── cart.png
│   ├── search.png
│   ├── home.png
│   ├── local.png
│   ├── drug.png
│   ├── drug_data/               # Imagens dos medicamentos
│   ├── ubs_data/                # Imagens das UBS
│   └── user_data/
│       └── receitas/            # Receitas médicas enviadas
├── dados.json                   # Banco de dados em JSON
├── README.md
└── REFATORACAO.md               # Documentação da refatoração
```

## 🔐 Segurança

### Validações Implementadas
- **CPF**: Apenas números, 11 dígitos
- **Senha**: Mínimo 9 caracteres, não pode ser apenas números
- **Bloqueio de Conta**: Após 3 tentativas incorretas
- **Recuperação de Senha**: Via pergunta de segurança
- **Validação de Saldo**: Antes de finalizar pedido
- **Validação de Estoque**: Antes de processar pedido

### Controle de Acesso
- **Usuários Comuns**: Acesso apenas às funcionalidades de cliente
- **Administradores**: Acesso completo ao sistema

## 🚀 Como Executar

### Compilação

```bash
# Navegar até o diretório do projeto
cd sistema_saude

# Compilar todos os arquivos (Windows)
javac -d bin -sourcepath src src\Main.java

# Compilar todos os arquivos (Linux/Mac)
javac -d bin -sourcepath src src/Main.java
```

### Execução

```bash
# Executar o sistema (Windows)
java -cp bin Main

# Executar o sistema (Linux/Mac)
java -cp bin Main
```

### Primeira Execução

1. O sistema criará automaticamente o arquivo `dados.json` na primeira execução
2. Use as credenciais do administrador para acesso completo:
   - **CPF**: `administrador`
   - **Senha**: `123`
3. Ou crie uma conta de usuário comum para testar as funcionalidades de cliente

### Estrutura de Pacotes

O sistema utiliza a seguinte estrutura de pacotes:
- `core` - Facade do sistema
- `services` - Lógica de negócio
- `models` - Entidades
- `views` - Interface gráfica
- `database` - Persistência

## 📄 Licença

Este projeto é de uso educacional.
