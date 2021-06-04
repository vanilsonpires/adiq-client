# Integração com a adiq

### Apresentação do projeto

Faço aqui uma simples demonstração de uma arquitetura de microserviços de pagamentos.

O projeto é composto pelo *config*, nele colocamos as configurações dos microserviços para caso precisarmos alterar algo, seja apenas em um local e uma instância e não em várias.

O *eureka* é responsável por fazer as descobertas dos microserviços, ele registra e encaminha ao gateway quando for solicitado


O *gateway* é quem recebe as requisições externas, valida o token, pergunta ao eureka quem está disponível para atender aquela requisição e encaminha a instância, nele também é onde está configurado o spring security

O *auth* tem o papel de gerar tokens, autenticar usuários, atualizar tokens etc, nele é onde está a criptografia e a implementação do JWT

E finalmente, o *payment* é o microserviço que realmente nos interessa, ele é quem vai realizar as transações financeiras.

### Banco de dados

O projeto está configurado para executar com o banco de dados Postgresql em localhost:5432

Caso você precise alterar as configurações, vá em:

	config/src/main/resources/config/
	
Nesse local terá os arquivos de configurações dos microserviços, será necessário realizar alterações em:

	auth-dev.properties
	payments-dev.properties
	
Os demais não realizam chamadas direta a banco de dados.

Para manter uma independência dos microserviços, é recomendado que cada microserviço utilize seu banco de dados independente.

Por padrão, o banco de dados configurado está:

* payments para o microserviço de payments
* oauth para o microserviço de oauth

É necessário que seu servidor do banco de dados tenha esses dois bancos já criados.

### Autenticação

Na autenticação o projeto utiliza oAuth2 e o JWT para realizar a autenticação.
Nessa etapa também não inventei nada, isto é uma tecnologia extraordinária para autenticação e segurança que funciona muito bem com o spring, na verdade em que o spring se dá mal eim?

Não vou explicar como funciona o oAuth2 nem o JWT, isto já temos no grande google.com.

Mas, o que você precisa para conseguir se comunicar com nossa api é:

* Client Id
* Client Secret
* Username
* Password
* Token

Vamos aos passos...

Para ter sucesso na chamada dos endpoints, você precisa conseguir um token.

Para conseguir um token, é necessário enviar um post para:

	http://localhost:8989/auth/oauth/token
	
Este endpoint está protegido com Basic Auth, então você precisa enviar o clientid e clientsecret juntos separado por dois pontos (:) criptografados com base64 no header da requisição.

Ficando assim:

	myclientid:mysecretid
	
Ah, não esqueça de colocar em base64

O *myclientid* e *mysecretid* está em:

	config/src/main/resources/config/auth-dev.properties
	
O nome dos parâmetros são:

	security.user.name e security.user.password
	
Ou seja, se você não modificar, essas serão o clientid e secretid por padrão:

	ClientId: finnet
	SecretId: 123456
	
Claro, não coloque uma senha dessa em produção, está muito fácil.

Você precisará também enviar no body da requisição os dados de autenticação do usuário em json, ficando mais ou menos assim:

	{
		grant_type: password,
		username: finet,
		password: 123456
	}
	
O usuário e senha fica no banco do auth, na tabela users
Não criamos um endpoint para cadastrar usuários, deixamos isso mais pra frente (:
Mas, criamos uma *Bean* que verifica no banco de dados se tem algum usuário, se a tabela estiver vazia ele insere um usuário padrão. O username e password desse usuário fica em:

	config/src/main/resources/config/auth-dev.properties
	
Dentro do arquivo, você verá a seguinte configuração:

	finet.admin.username=finnet
	finet.admin.password=123456
	
Você pode modifica-lo caso deseje que seu usuário padrão tenha outro username e outro password.
Mas lembre-se, ele somente insere esse usuário apenas se a tabela estiver vazia tudo bem? Foi apenas um facilitador que criei.

Sem mais delongas, faça um POST!

Se tiver feito tudo certinho, você terá uma resposta parecida com esta:

	{
	    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI4MzMyODAsInVzZXJfbmFtZSI6ImZpbmV0IiwianRpIjoiMWRlMWM1N2UtZGRhYS00ZjFjLWIxYmYtZGRkMGEwMTM1YTBjIiwiY2xpZW50X2lkIjoiZmlubmV0Iiwic2NvcGUiOlsiYWxsIl19.CvPR5C4QxRUSL1RnMoq-tlnFYWOiB1CB9JWrjjcjGz4",
	    "token_type": "bearer",
	    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI5MTg3ODAsInVzZXJfbmFtZSI6ImZpbmV0IiwianRpIjoiOTljYjNlNWUtNDk2ZS00NmYxLTk5OTItMDMwMTgzMTdlNmM3IiwiY2xpZW50X2lkIjoiZmlubmV0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjFkZTFjNTdlLWRkYWEtNGYxYy1iMWJmLWRkZDBhMDEzNWEwYyJ9.nGGcjs1tuzQ2O5aHdCqX0vPufM5rVEXR4edRT2SDo1A",
	    "expires_in": 899,
	    "scope": "all",
	    "jti": "1de1c57e-ddaa-4f1c-b1bf-ddd0a0135a0c"
	}
	
Finalmente, você tem um *access_token*, você deverá inserir ele nas das requisições à api adicionando no header com o nome *Authorization*

Ficando assim:

	Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI4MzMyODAsInVzZXJfbmFtZSI6ImZpbmV0IiwianRpIjoiMWRlMWM1N2UtZGRhYS00ZjFjLWIxYmYtZGRkMGEwMTM1YTBjIiwiY2xpZW50X2lkIjoiZmlubmV0Iiwic2NvcGUiOlsiYWxsIl19.CvPR5C4QxRUSL1RnMoq-tlnFYWOiB1CB9JWrjjcjGz4
	
Esse token não é pra sempre... por segurança ele expira e você deve renová-lo ou gerar outro.

Para renovar use o mesmo endpoint que você usou para criar o token, mas altere o body enviando o *refresh_token*, ficando assim:

	{
		grant_type: refresh_token,
		refresh_token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI5MTg3ODAsInVzZXJfbmFtZSI6ImZpbmV0IiwianRpIjoiOTljYjNlNWUtNDk2ZS00NmYxLTk5OTItMDMwMTgzMTdlNmM3IiwiY2xpZW50X2lkIjoiZmlubmV0Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjFkZTFjNTdlLWRkYWEtNGYxYy1iMWJmLWRkZDBhMDEzNWEwYyJ9.nGGcjs1tuzQ2O5aHdCqX0vPufM5rVEXR4edRT2SDo1A
	}
	
Não esqueça de enviar também no header o base64 do clientid e secretid, da mesma forma que no passo anterior de gerar os tokens.	

### Modo de funcionamento

Vamos focar no microserviço de *payment* já que o *config*, *eureka*, *gateway* e *oauth* é amplamente explicado e documentado na internet.

O objetivo do *payment* é realizar uma intermediação entre nós e a *adiq*, que é um gateway de pagamentos.

Seu modo de funcionamento é bem simples e fácil de entender, na documentação temos os detalhes sobre como deve ser feito as transações, para mais detalhes veja: *https://developers.adiq.io/manual/ecommerce*

No microserviço está exposto três endpoint, são:

* POST /payments para requisitar um pagamento
* GET /payments para consultar pagamentos
* PUT /payments/{payment} para cancelar pagamentos

Para mais detalhes sobre os parâmetros e respostas, veja a documentação em http://localhost:8989/swagger-ui.html

#### Composição do microserviço de payments

* Config: Onde deixo as configurações do projeto, inicialmente existe duas configurações, são:
	* SwaggerConfig, o swagger é uma ferramenta que usamos para documentar nossa API, é muito útil e amplamente usado no mercado.
	* WebClientConfig, é onde configuro nosso cliente web que será usando para a comunicação máquina com máquina por REST para a API da adiq. Este webclient do spring oferece suporte para auth2 em que faz todo o trabalho de aquisição de token, renovação e autenticação para nós. Precisaremos dele para manter as chamadas sempre com um token válido e funcional, caso contrálio teremos que implementar uma lógica parecida manualmente, mas porque reinventar a roda né? Vamos usar o que temos de melhor no mercado.
* Controllers: Onde expomos os métodos e deixamos acessíveis para a api, não há nada de especial aqui, são apenas rest's :)
* Dto: É a abreviação de Data Transfer Object, ou seja apenas um objeto para modelar a transferência de dados. É muito útil quando precisamos de uma modeladem de dados apenas para transferir por rest ou algo que seja necessário, onde não queremos ou não podemos enviar as entity's.
* Entity: São as entididades, classes que representa o mapeamento dos registros e a modelagem do projeto
* Enums: São as constantes
* Exceptions: São as exceções que ao serem lançadas enviam seu respectivo erro http na response.
* Feing: É onde fica nossos clientes http para acessar os outros microserviços da cloud, é muito útil quando precisamos se comunicar outro outros microserviços para invocar alguma ação, buscar dados ou enviar. É uma mão na roda para o mundo cloud pois sua semântica se assemelha muito com os repository.
* Repository: É onde fica definido nossas queridas DAO's, como o spring faz o maior trabalho para gente, apenas definimos os repository com sua assinatura confome na documentação que o mesmo cria nossas consultas no banco de dados junto com uma maozinha do hibernate.
* Service: São nossos serviços, é onde deixamos nossa lógica e regras de negócio
* Specifications: É onde coloco minhas consultas bem espefícicas, aquelas que ficou dificil pro spring data reproduzi-las por meio de seus repository. Nela podemos usar as técnicas do criteria Query e atender aquelas consultas bem específica do negócio. Neste projeto, estou usando para fazer pesquisa de registros.

Cada microserviço tem seu *bootstrap.properties*, nele adicionamos as configurações que são necessárias antes mesmo do microserviço buscar suas configurações no nosso microserviço *config*, aqui é importante deixamos apenas as configurações que realmente são necessárias antes de ocorrer essa comunicação. No caso aqui, estou usando para informar ao microserviço onde ele tem que buscar essas configurações, observe que isso realmente é necessário, pois como vou buscar minhas configurações sem saber onde devo ir não é mesmo? Ou seja, coloque aqui apenas informações desse nível, não vamos deixar que nosso *config* não sirva para nada, ele tem um papel muito importante e que será útil para manutenção da cloud.







