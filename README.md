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
- **MySQL rodando** (seja via XAMPP ou instalação standalone do MySQL Server)
- **Driver JDBC** MySQL (`mysql-connector-j`) — já incluso na pasta `lib/`

### 2. Criar o banco de dados

Escolha **uma** das opções abaixo para criar o banco de dados e importar a tabela com as cartas:

#### Opção A: Pela interface web do XAMPP (phpMyAdmin) - *Recomendado*
1. Certifique-se de que os módulos **Apache** e **MySQL** estejam iniciados no XAMPP Control Panel.
2. Abra o seu navegador e acesse: **http://localhost/phpmyadmin**
3. No menu superior, clique na aba **Importar** (Import).
4. Clique em **Escolher arquivo** e selecione o arquivo `sql/script.sql` localizado na pasta do projeto.
5. Role a página até o final e clique no botão **Importar** (ou *Go*). O banco `super_trunfo` será criado e populado automaticamente!

#### Opção B: Pelo terminal do XAMPP
1. Abra o PowerShell ou prompt de comando e execute:
   ```powershell
   & "C:\xampp\mysql\bin\mysql.exe" -u root -e "source sql/script.sql"
   ```

#### Opção C: Pelo terminal do MySQL Server (Instalação standalone antiga)
1. Abra o PowerShell e execute o comando de login (substitua `SUASENHA` pela senha real caso utilize):
   ```powershell
   & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
   ```
2. Após fazer o login, execute o script:
   ```sql
   SOURCE C:/caminho/do/projeto/super-trunfo/sql/script.sql;
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

## Como visualizar e gerenciar o banco de dados MySQL

Você pode visualizar a tabela e validar a estrutura do banco de dados de três maneiras diferentes:

### Opção A: Pela interface visual web (phpMyAdmin - XAMPP) - *Recomendado*
1. Acesse o painel **[http://localhost/phpmyadmin](http://localhost/phpmyadmin)** no seu navegador.
2. Na coluna à esquerda, clique na base de dados **`super_trunfo`** e selecione a tabela **`cartas`**.
3. Na aba **Visualizar** (Browse), você verá a planilha interativa com as 11 cartas cadastradas.
4. Na aba **Estrutura** (Structure), você verá todos os campos criados (nomes dos atributos, tipos numéricos e chaves).

### Opção B: Pelo terminal interativo (Console MySQL)
Abra o PowerShell ou Prompt de Comando e execute (ajuste para o caminho do seu MySQL, ex: XAMPP ou standalone):
```powershell
# Exemplo usando XAMPP:
& "C:\xampp\mysql\bin\mysql.exe" -u root

# Exemplo usando MySQL Server avulso:
& "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```
Após o login, execute os seguintes comandos SQL:
```sql
-- Selecionar o banco
USE super_trunfo;

-- Ver todas as 11 cartas com seus atributos
SELECT * FROM cartas;

-- Mostrar a quantidade total de cartas
SELECT COUNT(*) AS total_cartas FROM cartas;

-- Mostrar a estrutura da tabela
DESCRIBE cartas;
```
Para sair, digite `exit`.

### Opção C: Comandos rápidos diretos via terminal
Execute no PowerShell para rodar consultas sem entrar no prompt interativo do banco (ajuste o caminho do executável conforme seu ambiente):
```powershell
# Ver todas as cartas
& "C:\xampp\mysql\bin\mysql.exe" -u root -e "USE super_trunfo; SELECT * FROM cartas;"

# Contar as cartas
& "C:\xampp\mysql\bin\mysql.exe" -u root -e "USE super_trunfo; SELECT COUNT(*) AS total_cartas FROM cartas;"

# Estrutura da tabela
& "C:\xampp\mysql\bin\mysql.exe" -u root -e "USE super_trunfo; DESCRIBE cartas;"
```

### O que cada validação demonstra no projeto
| Comando / Ação | O que é validado |
|---|---|
| `SELECT * FROM cartas` | As cartas estão de fato salvas e persistidas no banco MySQL (não fixas no código Java). |
| `SELECT COUNT(*)` | Existem exatamente 11 cartas no banco (atendendo ao requisito de número ímpar). |
| `DESCRIBE cartas` | A tabela possui a estrutura MVC correta com os 5 atributos numéricos comparáveis + nome e código. |

---

## Driver JDBC
O driver já está incluído em `lib/mysql-connector-j-8.3.0.jar`.
Caso precise baixar novamente:
- Maven: `com.mysql:mysql-connector-j:8.3.0`
- Download: https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.3.0/mysql-connector-j-8.3.0.jar
