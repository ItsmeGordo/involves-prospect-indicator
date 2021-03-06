# Requisitos

   Os requisitos para esse projeto podem ser encontrados em https://github.com/involvestecnologia/selecaoinvolves/blob/master/desafio-localizacao/README.md

# Pré requisitos

   É necessário ter o [JDK](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) e [Maven](https://maven.apache.org/install.html) instalados na máquina para poder executar o projeto.

# Instalação e execução
### Heroku

Caso queira testar a aplicação de uma maneira mais simples, pode acessar através de https://prospect-indicator.herokuapp.com

### Local

Para instalar os pacotes necessarios e gerar o pacote para executar, é preciso executar os seguintes comandos.

   ```
   mvn clean install
   ```

Para executar o projeto localmente, na pasta raiz do projeto executar o comando

   ```
   mvn spring-boot:run
   ```

### Docker

O projeto também está pronto para o Docker. Na pasta raiz do projeto é preciso executar o comando abaixo para gerar o JAR do projeto, que sera utilizado para montar a imagem do Docker e logo depois gerar a imagem que será utilizada no Docker.

   ```
   mvn clean package
   docker build -t itsmegordo/prospect-indicator .
   ```

Em seguida, dentro de src/main/resources/docker tem o docker-compose para facilitar a inicialização.

   ```
   docker-compose up -d
   ```
   
# Tecnologias e frameworks escolhidos

- **Java** - Escolhido por conta da familiaridade e também porque é utilizado na stack.

   - **Spring Boot** - Facilidade na execução e por ser um framework bem completo. Também elimina a necessidade de instalaçào de um servidor de aplicação; 

   - **Lombok** - Para facilitar o acesso as classes, não sendo necessário a criação de getters, setters, equals e hashcode;
   
   - **JUnit** - Para executar os testes unitarios;
   
   - **Log4j** - Biblioteca de log amplamente utilizada nas aplicações Java;

- **Vue.js** - Escolhido pelo aprendizado, pois nunca havia trabalhado com ele, apenas com AngularJS.
   
   - **Axios** - Para fazer as chamadas a api rest de maneira simples;

   - **Vuetify** - Material design para facilitar o desenvolvimento do front-end;

# Arquitetura

A arquitetura do sistema foi decidida dessa maneira monolitica, pois ficaria mais simples para configurar o Docker.
Sendo dessa maneira, é necessario configurar apenas um Dockerfile para gerar o build e também não precisa ter que subir e instalar dois projetos.
Porém, da maneira que está, não seria trabalhoso separar em dois projetos em projetos distintos, um para o frontend e um para o backend.

``` 
.
├── Dockerfile
├── README.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── prospectIndicator.iml
└── src
    ├── main
    │   ├── java
    │   │   └── br
    │   │       └── com
    │   │           └── involves
    │   │               └── prospectIndicator
    │   │                   ├── ProspectIndicatorApplication.java
    │   │                   ├── controller
    │   │                   │   ├── employee
    │   │                   │   │   └── EmployeeController.java
    │   │                   │   └── shop
    │   │                   │       ├── ShopController.java
    │   │                   │       └── ShopVMI.java
    │   │                   ├── dto
    │   │                   │   ├── BestRouteDTO.java
    │   │                   │   └── ShopDistanceDTO.java
    │   │                   ├── facade
    │   │                   │   ├── employee
    │   │                   │   │   └── EmployeeFacade.java
    │   │                   │   └── shop
    │   │                   │       └── ShopFacade.java
    │   │                   ├── helper
    │   │                   │   ├── CsvHelper.java
    │   │                   │   ├── GeoMathHelper.java
    │   │                   │   └── TravellerSalesmanHelper.java
    │   │                   ├── model
    │   │                   │   ├── Employee.java
    │   │                   │   ├── GeoLocatedObject.java
    │   │                   │   └── Shop.java
    │   │                   └── reader
    │   │                       ├── AbstractCSVReader.java
    │   │                       ├── EmployeeCSVReader.java
    │   │                       └── ShopCSVReader.java
    │   └── resources
    │       ├── application.properties
    │       ├── docker
    │       │   └── docker-compose.yml
    │       ├── files
    │       │   ├── funcionarios.csv
    │       │   └── lojas.csv
    │       └── static
    │           ├── css
    │           │   └── style.css
    │           ├── index.html
    │           └── js
    │               └── index.js
    └── test
        └── java
            └── br
                └── com
                    └── involves
                        └── prospectIndicator
                            ├── ProspectIndicatorApplicationTests.java
                            ├── helper
                            │   └── GeoMathHelperTest.java
                            └── reader
                                ├── EmployeeCSVReaderTest.java
                                └── ShopCSVReaderTest.java

```

### Controllers

Foram criados dois controllers, que contém os endpoints da API. O endpoint da classe EmployeeController.java trata de retornar os dados referentes aos funcionários. Já o ShopController.java trata dos dados das lojas.

Para eles ficarem melhores e estarem preparados para uma aplicação do mundo real, seria interessante tratar exceções e controlar os retornos. Hoje, caso der algum problema na aplicação ele irá retornar um exception como resposta, o que não é interessante.

### DTO

Foi criado um DTO para poder mostrar a distancia no frontend, já que os dados inicialmente não tem ligação direta.

### Helper

Todas as funções que se referem a calculo e funções de ajuda estão nas classes contidas nessa pasta. 
Existe hoje, a classe GeoMathHelper.java, que faz o calculo de distancia dos pontos com a função de Haversine, também retorna as lojas dentro de um raio especifico a partir de um ponto. E também existe a TravellerSalesmanHelper.java, que calcula a melhor rota para o usuário dentro das lojas que ele precisa visitar.

### Model

As entidades se encontram nessa parte. Foi escolhido ter uma classe GeoLocatedObject.java, pois ambos os dados passados contém os mesmos dados. Assim facilita a criação dos dados e o ajuda nas funções de calculo. No futuro, caso seja necessário mudar a forma que está os dados de geolocalização, não precisa mexer nas duas entidades, apenas na entidade abstrata.

### Reader

Os leitores compartilham a mesma maneira de ler o arquivo, mas cada um sabe como aplicar a regra de negócio para criar o seu próprio objeto. Por isso foi criada uma classe abstrata para leitura do arquivo e as classes filhas sabem como criar seus objetos.

# TODO

- [x] Adicionar o projeto no [CloudForge](http://www.cloudforge.com) ou [Heroku](https://www.heroku.com) 
- [x] Criar Dockerfile e Docker Compose
- [x] Download da tabela em CSV
- [x] Criar função que calcula a melhor rota e disponibilizar para download para o usuário
- [ ] Criar testes para os endpoints
- [ ] Adicionar os dados em um banco de dados H2 (Apenas para fins didáticos)
- [ ] Melhorar a função para busca das lojas que serão atendidas para não repetir a mesma loja para demais usuários
- [ ] Adicionar paginação no backend (Como não existe registro de novos dados, não quis me preocupar com isso)