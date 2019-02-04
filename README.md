# README

## Pré requisitos

   É necessário ter o [JDK](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) instalado na máquina e o [Maven](https://maven.apache.org/install.html)

## Instalação

   mvn clean install

## Execução

   mvn spring-boot:run

## Tecnologias e frameworks escolhidos

- **Java** - Escolhido por conta da familiaridade e também porque é utilizado na stack.

   - **Spring Boot** - Facilidade na execução e por ser um framework bem completo. Também elimina a necessidade de instalaçào de um servidor de aplicação; 

   - **Lombok** - Para facilitar o acesso as classes, não sendo necessário a criação de getters, setters, equals e hashcode;
   
   - **JUnit** - Para executar os testes unitarios;

- **Vue.js** - Escolhido pelo aprendizado, pois nunca havia trabalhado com ele, apenas com AngularJS.
   
   - **Axios** - Para fazer as chamadas a api rest de maneira simples

   - **Vuetify** - Material design para facilitar o desenvolvimento do front-end

## Arquitetura

## TODO

- [ ] Adicionar o projeto no [CloudForge](http://www.cloudforge.com) ou [Heroku](https://www.heroku.com) 
- [ ] Criar Dockerfile e Docker Compose
- [ ] Download da tabela em CSV
- [ ] Adicionar os dados em um banco de dados H2 (Apenas para fins didaticos)
- [ ] Criar função que calcula a melhor rota e disponibilizar para download para o usuário
