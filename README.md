# Requisitos

   Os requisitos para esse projeto podem ser encontrados em https://github.com/involvestecnologia/selecaoinvolves/blob/master/desafio-localizacao/README.md

# Pré requisitos

   É necessário ter o [JDK](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) e [Maven](https://maven.apache.org/install.html) instalados na máquina para poder executar o projeto.

# Instalação

   ```
   mvn clean install
   ```

# Execução

   ```
   mvn spring-boot:run
   ```

# Tecnologias e frameworks escolhidos

- **Java** - Escolhido por conta da familiaridade e também porque é utilizado na stack.

   - **Spring Boot** - Facilidade na execução e por ser um framework bem completo. Também elimina a necessidade de instalaçào de um servidor de aplicação; 

   - **Lombok** - Para facilitar o acesso as classes, não sendo necessário a criação de getters, setters, equals e hashcode;
   
   - **JUnit** - Para executar os testes unitarios;

- **Vue.js** - Escolhido pelo aprendizado, pois nunca havia trabalhado com ele, apenas com AngularJS.
   
   - **Axios** - Para fazer as chamadas a api rest de maneira simples

   - **Vuetify** - Material design para facilitar o desenvolvimento do front-end

# Arquitetura

``` 
.
├── README.md
├── pom.xml
├── prospectIndicator.iml
├── src
    ├── main
    │   ├── java
    │   │   └── br
    │   │       └── com
    │   │           └── involves
    │   │               └── prospectIndicator
    │   │                   ├── ProspectIndicatorApplication.java
    │   │                   ├── controller
    │   │                   │   ├── EmployeeController.java
    │   │                   │   └── ShopController.java
    │   │                   ├── dto
    │   │                   │   └── ShopDistanceDTO.java
    │   │                   ├── helper
    │   │                   │   └── GeoMathHelper.java
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

Todas as funções que se referem a calculo estão nas classes contidas nessa pasta. No caso existe apenas uma classe aqui dentro, que é a GeoMathHelper.java. Ela é responsável por aplicar a função de Haversine e retornar a distancia e também de calcular as lojas que estão dentro do raio especifico.

### Model

As entidades se encontram nessa parte. Foi escolhido ter uma classe GeoLocatedObject.java, pois ambos os dados passados contém os mesmos dados. Assim facilita a criação dos dados e o ajuda nas funções de calculo. No futuro, caso seja necessário mudar a forma que está os dados de geolocalização, não precisa mexer nas duas entidades, apenas na entidade abstrata.

### Reader

Os leitores compartilham a mesma maneira de ler o arquivo, mas cada um sabe como aplicar a regra de negócio para criar o seu próprio objeto. Por isso foi criada uma classe abstrata para leitura do arquivo e as classes filhas sabem como criar seus objetos.

# TODO

- [ ] Adicionar o projeto no [CloudForge](http://www.cloudforge.com) ou [Heroku](https://www.heroku.com) 
- [ ] Criar Dockerfile e Docker Compose
- [ ] Download da tabela em CSV
- [ ] Adicionar os dados em um banco de dados H2 (Apenas para fins didáticos)
- [ ] Criar função que calcula a melhor rota e disponibilizar para download para o usuário
