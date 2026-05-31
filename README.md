# Super Trunfo — Instrumentos Musicais

Jogo estilo Super Trunfo desenvolvido em **Java SE** com padrão **MVC** e persistência em **MySQL**.

## Tema
**Instrumentos Musicais** — 11 cartas com 5 atributos comparáveis:
Ano de Criação, Peso (kg), Preço (R$), Popularidade e Volume (dB).

## Requisitos atendidos
- Java SE com interface Swing
- Arquitetura MVC (model / view / controller / dao / util)
- Banco de dados MySQL para armazenar as cartas
- Jogo humano × máquina
- A máquina nunca sorteia a mesma carta do jogador
- Cartas usadas ficam desabilitadas
- Número ímpar de cartas (11)
- Pontuação por rodada e pontuação geral

## Estrutura do projeto
```
super-trunfo/
├── sql/script.sql                              → criação do banco e inserts
├── resources/images/                           → imagens dos instrumentos
├── lib/mysql-connector-j-8.3.0.jar             → driver JDBC MySQL
├── src/br/com/trabalho/
│   ├── Main.java                               → ponto de entrada
│   ├── model/Carta.java                        → entidade carta
│   ├── model/Jogador.java                      → entidade jogador
│   ├── dao/CartaDAO.java                       → acesso ao MySQL
│   ├── controller/JogoController.java          → controle do jogo
│   ├── view/TelaJogo.java                      → interface Swing
│   └── util/ConnectionFactory.java             → conexão com banco
└── README.md
```

---

## Passo a passo completo

### 1. Pré-requisitos
- **JDK** instalado (versão 17 ou superior)
- **MySQL Server** instalado e rodando
- **Driver JDBC** MySQL (`mysql-connector-j`) — já incluso na pasta `lib/`

### 2. Criar o banco de dados
Abra o **PowerShell** e execute:
```powershell
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```
Digite a senha do MySQL quando solicitado. Depois, dentro do MySQL:
```sql
SOURCE C:/caminho/do/projeto/super-trunfo/sql/script.sql;
```
Ou, de forma direta no PowerShell (substitua `SUASENHA` pela senha real):
```powershell
Get-Content "sql\script.sql" -Encoding UTF8 | & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pSUASENHA
```

### 3. Ajustar credenciais (se necessário)
Abra o arquivo `src/br/com/trabalho/util/ConnectionFactory.java` e altere:
```java
private static final String USUARIO = "root";
private static final String SENHA = "";  // ← coloque a sua senha do MySQL aqui (o padrão do XAMPP é vazio "")
```

### 4. Compilar o projeto
No PowerShell, navegue até a pasta do projeto e execute:
```powershell
& "C:\Program Files\Java\jdk-26.0.1\bin\javac.exe" -encoding UTF-8 -d bin -cp "lib\mysql-connector-j-8.3.0.jar" -sourcepath src src\br\com\trabalho\Main.java src\br\com\trabalho\model\Carta.java src\br\com\trabalho\model\Jogador.java src\br\com\trabalho\util\ConnectionFactory.java src\br\com\trabalho\dao\CartaDAO.java src\br\com\trabalho\controller\JogoController.java src\br\com\trabalho\view\TelaJogo.java
```
> **Nota:** Se o `javac` estiver no PATH do sistema, basta trocar o caminho completo por `javac`.

### 5. Executar o jogo
```powershell
& "C:\Program Files\Java\jdk-26.0.1\bin\java.exe" -cp "bin;lib\mysql-connector-j-8.3.0.jar" br.com.trabalho.Main
```
> **Nota:** Se o `java` estiver no PATH do sistema, basta trocar o caminho completo por `java`.

---

## Como jogar
1. Clique em uma das suas cartas (miniaturas no topo esquerdo).
2. Clique no botão **JOGAR**.
3. A máquina sorteia uma carta e todos os 5 atributos são comparados.
4. Cada atributo vencido vale 1 ponto na **Pontuação da Rodada**.
5. Quem vencer mais atributos ganha +1 na **Pontuação Geral**.
6. Após 5 rodadas, o **vencedor** é declarado na tela.

---

## Como demonstrar o banco MySQL ao professor

### Opção A: Modo interativo (recomendado para apresentação)
```powershell
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```
Após digitar a senha, execute dentro do MySQL:
```sql
-- Selecionar o banco
USE super_trunfo;

-- Ver todas as 11 cartas com seus atributos
SELECT * FROM cartas;

-- Mostrar que são 11 cartas (número ímpar)
SELECT COUNT(*) AS total_cartas FROM cartas;

-- Mostrar a estrutura da tabela
DESCRIBE cartas;
```
Para sair, digite `exit`.

### Opção B: Comandos diretos (mais rápido)
```powershell
# Ver todas as cartas
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pSUASENHA -e "USE super_trunfo; SELECT * FROM cartas;"

# Contar as cartas
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pSUASENHA -e "USE super_trunfo; SELECT COUNT(*) AS total_cartas FROM cartas;"

# Estrutura da tabela
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -pSUASENHA -e "USE super_trunfo; DESCRIBE cartas;"
```

### O que cada comando demonstra ao professor
| Comando | O que prova |
|---------|-------------|
| `SELECT * FROM cartas` | As cartas são armazenadas no MySQL (não estão fixas no código) |
| `SELECT COUNT(*)` | São exatamente 11 cartas (número ímpar, como exigido) |
| `DESCRIBE cartas` | A tabela possui os 5 atributos numéricos + nome + código |

---

## Driver JDBC
O driver já está incluído em `lib/mysql-connector-j-8.3.0.jar`.
Caso precise baixar novamente:
- Maven: `com.mysql:mysql-connector-j:8.3.0`
- Download: https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar
