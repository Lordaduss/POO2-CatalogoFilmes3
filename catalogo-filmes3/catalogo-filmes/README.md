# Catálogo de Filmes (JavaFX + PostgreSQL)

Trabalho II: MVC + DAO evoluído com Service Layer e os padrões Observer, Builder e Command (desfazer).

1. Rode o `database.sql` no PostgreSQL.
2. Ajuste usuário e senha em `src/main/java/util/ConexaoDB.java`.
3. Execute com Java 17+ e Maven: `mvn javafx:run`

Fluxo: Controller -> Service -> DAO -> PostgreSQL

Testes (JUnit 5): `mvn test`, ou no IntelliJ botão direito em `src/test/java` > Run 'All Tests'
