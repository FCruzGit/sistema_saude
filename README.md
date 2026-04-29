# Sistema SUS - Farmácia Popular

Sistema de gerenciamento de farmácias populares do SUS desenvolvido em Java com interface gráfica Swing.

## 📋 Descrição
5
O Sistema SUS - Farmácia Popular é uma aplicação desktop que permite aos usuários consultar e adquirir medicamentos disponíveis nas Unidades Básicas de Saúde (UBS). O sistema oferece funcionalidades completas de gerenciamento para administradores e uma interface intuitiva para os clientes.

## 🚀 Funcionalidades

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

### Para Administradores

#### 1. Gerenciamento de UBS
- **Adicionar UBS**: Cadastro de novas unidades
  - Nome
  - Endereço completo (logradouro, número, bairro, estado, CEP)
  - Imagem da unidade
- **Atualizar UBS**: Edição de dados existentes
- **Remover UBS**: Exclusão de unidades
- **Listar UBS**: Visualização completa com todos os campos

#### 2. Gerenciamento de Medicamentos
- **Cadastrar Remédio**: Adição de novos medicamentos
  - Nome
  - Preço
  - Estoque
  - Necessidade de receita
  - UBS vinculada
  - Imagem
  - Descrição
  - Tipo
  - Gramatura
- **Atualizar Remédio**: Edição de dados existentes
- **Deletar Remédio**: Remoção de medicamentos
- **Listar Estoque**: Visualização de todos os medicamentos

#### 3. Gerenciamento de Usuários
- **Adicionar Fundos**: Crédito de saldo para usuários
- **Desbloquear Usuários**: Resetar tentativas de login
- **Listar Usuários Bloqueados**: Visualização de contas bloqueadas

#### 4. Gerenciamento de Pedidos
- **Revisar Pedidos**: Aprovação de pedidos com receita médica
  - Visualização da receita enviada
  - Aprovação ou rejeição
- **Consultar Pedidos**: Visualização de todos os pedidos realizados
  - Informações do usuário
  - Lista de medicamentos
  - Valor total
  - Status

## 🏗️ Arquitetura do Sistema

### Estrutura de Classes

#### Entidades
- **Usuario**: Representa usuários do sistema (clientes e administradores)
- **UBS**: Representa as Unidades Básicas de Saúde
- **Remedio**: Representa os medicamentos disponíveis
- **Pedido**: Representa os pedidos realizados
- **Receita**: Representa as receitas médicas enviadas

#### Gerenciamento
- **SistemaFarmacia**: Singleton que gerencia toda a lógica de negócio
- **GerenciadorJSON**: Responsável pela persistência de dados em JSON

#### Interface Gráfica
- **TelaInicial**: Tela de login e cadastro
- **TelaSelecaoUBS**: Tela de seleção de UBS
- **TelaCliente**: Tela de consulta de medicamentos e carrinho
- **TelaAdmin**: Painel administrativo

### Persistência de Dados

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

## 🎨 Interface do Usuário

### Paleta de Cores
- **Azul SUS**: RGB(0, 94, 184) - Cor principal do sistema
- **Fundo**: RGB(232, 244, 248) - Azul claro para o fundo
- **Branco**: RGB(255, 255, 255) - Containers e cards
- **Verde**: RGB(0, 150, 0) - Preços e valores positivos

### Componentes Visuais
- **Cards com bordas arredondadas**: Para UBS, medicamentos e pedidos
- **Campos de busca**: Com ícone de lupa
- **Botões de ação**: Com cores diferenciadas por função
- **Painéis de resumo**: Com informações detalhadas
- **Scroll suave**: Para listas longas

## 📁 Estrutura de Diretórios

```
sistema_saude/
├── src/
│   ├── TelaInicial.java
│   ├── TelaSelecaoUBS.java
│   ├── TelaCliente.java
│   ├── TelaAdmin.java
│   ├── SistemaFarmacia.java
│   ├── GerenciadorJSON.java
│   ├── Usuario.java
│   ├── UBS.java
│   ├── Remedio.java
│   ├── Pedido.java
│   └── Receita.java
├── assets/
│   ├── logo.png
│   ├── tela_inicial.png
│   ├── cart.png
│   ├── search.png
│   ├── home.png
│   ├── local.png
│   ├── drug.png
│   ├── drug_data/          # Imagens dos medicamentos
│   ├── ubs_data/           # Imagens das UBS
│   └── user_data/
│       └── receitas/       # Receitas médicas enviadas
├── dados.json              # Banco de dados em JSON
└── README.md
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

## 🚦 Fluxo de Uso

### Fluxo do Cliente

1. **Login/Cadastro**
   - Usuário faz login ou cria uma conta
   - Sistema valida credenciais

2. **Seleção de UBS**
   - Usuário visualiza UBS disponíveis
   - Pode buscar por nome ou endereço
   - Seleciona a UBS desejada

3. **Consulta de Medicamentos**
   - Visualiza medicamentos disponíveis na UBS
   - Pode buscar por nome
   - Visualiza detalhes do medicamento

4. **Adicionar ao Carrinho**
   - Seleciona quantidade desejada
   - Adiciona ao carrinho
   - Pode adicionar múltiplos itens

5. **Finalizar Pedido**
   - Visualiza resumo do carrinho
   - Se necessário, envia receita médica
   - Sistema valida saldo e estoque
   - Pedido é criado e processado

### Fluxo do Administrador

1. **Login**
   - Acesso com credenciais de administrador
   - Usuário padrão: `administrador` / Senha: `123`

2. **Painel Administrativo**
   - Acesso a todas as funcionalidades de gerenciamento

3. **Gerenciamento**
   - Adiciona/edita/remove UBS e medicamentos
   - Adiciona fundos aos usuários
   - Revisa e aprova pedidos
   - Desbloqueia usuários

## 💾 Dados Iniciais

### Usuário Administrador Padrão
- **CPF**: administrador
- **Senha**: 123

### UBS Cadastradas
1. **UBS Jordanópolis**
   - Viela Jangada Nova, 75 - Jardim Pres., SP
   - CEP: 04830-200

2. **UBS Vila Mariana**
   - Rua das Flores, 123 - Vila Mariana, SP
   - CEP: 04567-890

3. **UBS Mooca**
   - Av. Paes de Barros, 456 - Mooca, SP
   - CEP: 03115-000

### Medicamentos Cadastrados
- Paracetamol (500mg)
- Ibuprofeno (600mg)
- Amoxicilina (500mg) - Requer receita
- Dipirona (1g)
- Losartana (50mg)
- Omeprazol (20mg)
- Metformina (850mg)
- Atenolol (25mg)
- Sinvastatina (20mg)
- Captopril (25mg)
- Azitromicina (500mg) - Requer receita
- Dexametasona (4mg) - Requer receita

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem de programação
- **Swing**: Framework para interface gráfica
- **JSON**: Formato de persistência de dados
- **Padrão Singleton**: Para gerenciamento centralizado
- **MVC**: Separação de responsabilidades

## 📝 Requisitos do Sistema

- Java JDK 8 ou superior
- Sistema operacional: Windows, Linux ou macOS
- Resolução mínima: 1200x700 pixels

## 🚀 Como Executar

1. Compile todos os arquivos `.java` na pasta `src/`
2. Execute a classe `TelaInicial`
3. O sistema criará automaticamente o arquivo `dados.json` na primeira execução
4. Use as credenciais do administrador para acesso completo ou crie uma conta de usuário

## 📌 Observações

- As imagens dos medicamentos e UBS devem estar nas pastas correspondentes em `assets/`
- O sistema cria automaticamente as pastas necessárias para armazenar receitas
- Todos os dados são persistidos em tempo real no arquivo JSON
- O sistema suporta múltiplos usuários simultâneos (em execuções separadas)

## 🔄 Atualizações Futuras

- Integração com gov.br
- Relatórios de vendas
- Histórico de pedidos do usuário
- Notificações de estoque baixo
- Sistema de avaliação de medicamentos
- Agendamento de retirada

## 👥 Autores

Projeto desenvolvido como atividade de Programação Orientada a Objetos (POO).

## 📄 Licença

Este projeto é de uso educacional.
