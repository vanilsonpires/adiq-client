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



### Modo de funcionamento

Vamos focar no microserviço de *payment* já que o *config*, *eureka*, *gateway* e *oauth* é amplamente explicado e documentado na internet.

O objetivo do *payment* é realizar uma intermediação entre nós e a *adiq*, que é um gateway de pagamentos.

Seu modo de funcionamento é bem simples e fácil de entender, na documentação temos os detalhes sobre como deve ser feito as transações, para mais detalhes veja: *https://developers.adiq.io/manual/ecommerce*

No microserviço está exposto três endpoint, são:

* POST /payments para requisitar um pagamento
* GET /payments para consultar pagamentos
* PUT /payments/{payment} para cancelar pagamentos

Para mais detalhes sobre os parâmetros e respostas, veja a documentação em http://localhost:8989/swagger-ui.html

Composição do microserviço:

* Controllers: Onde expomos os métodos e deixamos acessíveis para a api
