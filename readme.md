## EventVs

Projeto da disciplina de engenharia de Software 2. <br />
O EventVs consiste num projeto onde é possível criar e divulgar eventos de qualquer natureza.

A seguir virão alguns pontos importante a respeito do desenvolvimento do projeto.

### Como executar o projeto
1. É necessário ter o java na versão 11 instalado.
1. É necessário possuir algum servidor local na máquina.
1. É necessário iniciar o serviço apache e o mysql na máquina.
1. É preciso baixar as dependências do projeto utilizando o Maven ``` mvn clean install ```
1. Após esses passos basta executar a classe ``` EventvsApplication.java ``` ou executar no terminal `mvn spring-boot:run`

### Recomendações
1. Utilizar o servidor local ``` XAMP ```
1. Utilizar o IDE ``` Intellij Community ```

Apesar das recomendações, fique a vontade para utilizar o servidor local e IDE da sua preferência.

### Estrutura do código

No pacote ```domain``` ficam todos os arquivos responsáveis pelas regras de domínio do negócio. <br />
O pacote ``` domain/model``` contém as classes de entidade. <br />
O pacote ``` domain/repository``` contém todos os acessos ao banco de dados. <br />
O pacote ``` domain/controller``` contém todos os controladores que orquestram os casos de uso. <br />
O pacote ``` domain/enums``` contém todos os enumeradores que serão utilizados no domínio. <br />
O pacote ``` domain/exception``` contém as classes que representam os Tipos de erros encontrados no domínio. <br />
No pacote ``` api``` ficam todos os arquivos responsáveis pelo funcionamento da API. <br />
O pacote ``` api/resources``` contém todos os arquivos responsáveis pelo atendimento às requisições. HTTP <br />
O pacote ``` api/util``` contém todos os arquivos que terão alguma utilidade, mas não se encaixam em outro pacote. <br />
O pacote ``` api/dto``` contém todos os DTO's (Data Transfers Objects) utilizados para comunicação. <br />
O pacote ``` api/dto/requests``` contém todos os DTO's especializados nas ***requests***.  <br />
O pactote ``` api/dto/responses``` contém todos os DTO'S especializados nas ***responses***. <br />
O pactote ``` api/exceptionhandler``` contém as classes que lidam com o tratamento de exceções encontrados na camada de domínio. <br />




### Fluxo de desenvolvimento

1. Criar uma branch
1. Criar a query no repository, quando necessário.
1. Criar o enum quando for necessário.
1. Criar e Documentar o método no controlador.
1. Criar o DTO de request, caso a sua requisição envie parâmetros no corpo.
1. Criar o DTO de response, caso a sua resposta não seja exatamente uma entidade.
1. Criar o path da requisição, quando este não existir.
1. Criar e Documentar o método no seu respectivo resource.
1. Testar localmente.
1. Realizar o commit na sua branch
1. Por fim submeter um merge request para a branch master.
1. OBS: A documentação citada anteriormente se refere a criação do javadoc. Para criar o javadoc basta inserir acima do método
o seguinte trecho: <br />
   ```
   /**
    * Descreva em breves palavras o que seu método faz
    *
    * @param adicione aqui os parametros do seu método
    * @return adicione aqui o retorno do seu método
    * */
    ```

### Features

- [x] Criar categoria de evento.
- [x] Listar todas as categorias de eventos.
- [x] Buscar categorias de evento pelo nome.
- [x] Criar evento.
- [ ] Publicar evento.
- [x] Editar evento.
- [ ] Excluir evento não publicado.
- [x] Cancelar evento.
- [x] Inscrever-se em um evento.
- [x] Visualizar inscrição
- [x] Visualizar inscrições de um participante
- [ ] Cancelar inscrição num evento.
- [x] Listar participantes de um evento.
- [x] Criar conta de participante.
- [x] Criar conta de produtor.
- [x] Login.

### Endpoints prontos

Função | Endpoint | Verbo | Statuscode
--------- | ------ | ----- | ---------  
Solicitar token de acesso  | /oauth/token | POST | 200 - 400
Cadastrar participante  | /criar/participantes | POST | 201 - 400
Cadastrar produtor   | /criar/produtores | POST |201 - 400
Cadastrar categoria | /categorias | POST | 201
Listar categorias  | /categorias | GET | 200
Buscar categoria que contenham um nome | /categorias/nome | GET | 200 - 404
Buscar categoria pela descrição ou parte dela | /categorias/descricao | GET | 200 - 404
Cadastrar inscricao | /inscricoes | POST | 200 - 400 - 404
Buscar inscrição | /inscricoes/{inscricaoId} | GET | 200 - 400 - 404
Listar inscrições do Usuário | /inscricoes | GET | 200 - 404
Visualizar inscrições de um Evento | /inscricoes/eventos/{eventoId} | GET | 200 - 400 - 404
Cadastrar Evento | /eventos | POST | 201 - 400
Editar Evento | /eventos/{eventoId} | PATCH | 200 - 400 - 404
Cancelar Evento | /eventos/{eventoId}/cancelar | PATCH | 200 - 400 - 404
Listar todos eventos publicados | /eventos/publicados | GET | 200
Listar todos eventos não publicados | /eventos/nao-publicados | GET | 200 - 400 -404
Listar todos eventos não publicados por categoria | /eventos/nao-publicados/categoria/{categoriaId} | GET | 200 - 400 - 404
Listar todos eventos não publicados por nome | /eventos/nao-publicados/nome | GET | 200 - 400 - 404
