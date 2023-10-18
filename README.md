# DesafioLello

Criar um projeto em java utilizando o frameworkdo Spring Boot para realizar operações básicas de armazenamento e recuperação do dado como buscar(GET), gravar(POST) e atualizar(Update).
| 💾 Project | Vinicius Serrano    |
| -------------  | --- |
| :sparkles: Nome        | **DesafioLello BackEnd**
| :label: Tecnologias | Java, SpringBoot, H2DataBase, Maven, "Jpa"
| :rocket: URL         | [Java / Desafio / POO](https://github.com/viniciusserrano/DesafioLello)
| :fire: Desafio     | Aplicação em Java com Spring que permite gerenciar informações de usuários por meio de uma API de serviço com operações de registro, busca, listagem paginada e atualização de dados de usuário. Inclui validações e testes unitários.

<!-- Inserir imagem com a #vitrinedev ao final do link -->
GetPorPaginacao
![](getPaginacao.png#vitrinedev)
<br>
Get por ID
![](getPorId.png#vitrinedev)
<br>
POST
![](post.png#vitrinedev)
<br>
PUT
![](put.png#vitrinedev)

## Detalhes do projeto
Arquitetura:
O projeto segue uma arquitetura em camadas típica de aplicações Spring, com controladores (controllers), serviços (services), repositórios (repositories), DTOs (Data Transfer Objects) e entidades.

Versionamento de Endpoints:
Na classe de recursos (Resource), introduzi o conceito de versionamento de endpoints, como exemplificado em "usuarios/v1". 
Essa abordagem permite adicionar novos endpoints de forma estruturada no futuro. 
Caso seja necessário, podemos usar a anotação @Deprecated e herança para criar subclasses que estendam os endpoints existentes, proporcionando maior flexibilidade.

Paginação para Listagem de Clientes:
Implementei a funcionalidade de paginação ao buscar todos os clientes. Essa é uma prática importante, especialmente quando se lida com um grande volume de dados no banco de dados. 
A paginação ajuda a otimizar as requisições, melhorando o desempenho e a experiência do usuário.
"/usuarios/v1/pageable?page=0&size=5"

Tratativas de Exceptions Personalizadas:
Introduzi exceções personalizadas para lidar com erros de forma mais precisa. Isso garante que o cliente receba mensagens de erro claras e significativas, facilitando a depuração e o entendimento dos problemas.

Utilização de Instant e Timestamp:
Optei por utilizar o tipo Instant e timestamps para registrar o momento exato em que um usuário é cadastrado ou tem seus dados alterados. 
Essa é uma prática comum em APIs e fornece informações precisas sobre as ações dos usuários.

Testes Unitários com JUnit e Mockito:
Desenvolvi testes unitários abrangentes usando as bibliotecas JUnit e Mockito. 
Através da função verify, assegurei que os métodos estão sendo utilizados corretamente, garantindo a qualidade do código e a funcionalidade correta.


Essas melhorias visam aprimorar a qualidade, flexibilidade do projeto, tornando-o mais adequado para futuras extensões e mantendo boas práticas de desenvolvimento de software.
