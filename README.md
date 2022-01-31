# INFORMAÇÕES DO TRABALHO 
<br />

## TURMA / DISCIPLINA / PROFESSOR
### 41SCJ / MICROSERVICES DEVELOPMENT / ANDRE PONTES SAMPAIO
<br />

## TIPO AVALIAÇÃO
### Avaliação Parcial da Disciplina
<br />

## TÍTULO
#### 41 SCJ - MICROSERVICES DEV - TRABALHO 2Cancel changes
<br />

## ALUNOS 
#### Emerson Dias - 341060  
#### Marco Antonio - 341785
<br />

### SOBRE O SISTEMA 
##### Desenvolvimento de um aplicativo para acompanhar o deslocamento em tempo real do filho, da residência para escola e da escola para a residência através do transporte escolar
<br />

### STACK TECNOLÓGICA
##### A arquitetura utilizada foi de microserviços, para sua implementação utilizamos as seguintes tecologias:
<ul>
  <li><strong>Spring Boot:</strong> Para termos uma maior agilidade no processo de desenvolvimento.</li>
  <li><strong>Spring Cloud Configuration:</strong> Para integrar os microserviços ao servidor de configuração (GitHub).</li>
  <li><strong>Spring Cloud OpenFeign:</strong> Para consumo entre os microserviços.</li>
  <li><strong>Eureka, Feign e Spring Cloud Load Balancer:</strong> Para localizar serviços com o objetivo de balanceamento de carga e failover de servidores.</li>
  <li><strong>Spring Cloud Load API Gateway:</strong> Para gerenciar e rotear as requisições aos serviços, a partir do nome dos microserviços, independente de quantidade de instâncias e de porta.</li>
  <li><strong>COMUNICAÇÃO ASSINCRONA EVENTOS com RabbitMQ: </strong> Para o envio de SMS, envio de código de confirmação para cadastro novo de usuário, esqueceu a senha, etc...
</ul>

##### Informações adicionais:
O design pattern escolhido para a transferencia de dados foi o DTO para tentar minimizar ao maximo o trafego de rede e a comunicacao do cliente com o banco de dados.<br />
Utilizamos tambem os recursos padrao da JPA e suas implementacoes providas pelo spring.<br /> 
Utilizamos em trechos do código o Lombok para diminuir a quantidade de codigo boiler plate na aplicacao.<br /> 
Utilizamos nesse projeto o swagger para a criaçao da documentacao<br />

<br />

## VISÃO GERAL DO SISTEMA

![image](https://user-images.githubusercontent.com/29930488/148454224-73df71ea-d315-4024-9df6-a8ba7cb220f1.png)

<br />

## Como executar o sistema

### Primeiro criar uma imagem no docker do RabbitMQ, com segue:

docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management

<br />

## Você pode ter as alterações abaixo em um respositório privado para não ficar exposto as configurações, ou se preferir alterar diretamente nos arquivos como abaixo:

### Alterar nas classe RabbitMQSenderConfig na package: 41scj-microservices-dev-be/notificacoes-service/src/main/java/br/com/fiap/microservices/configuracao/
@Value("${queue.sms.name}") = Alterar aqui para sua queue
<br /><br />

### Alterar nas classe SMSQueueConsumer na package: 41scj-microservices-dev-be/notificacoes-service/src/main/java/br/com/fiap/microservices/mq/
@Value("${queue.sms.name}") = Alterar aqui para sua queue
<br /><br />

### Alterar nas classe SlackService na package: 41scj-microservices-dev-be/notificacoes-service/src/main/java/br/com/fiap/microservices/services/
@Value("${slack.bot.token}") = Alterar para o seu token no slack
<br />
@Value("${slack.bot.channel}") = Alterar para o seu canal no slack
<br /><br />

### Alterar nas classe TwilioService na package: 41scj-microservices-dev-be/notificacoes-service/src/main/java/br/com/fiap/microservices/services/
@Value("${twilio.account.sid}") = Alterar para o seu id no twilio
<br />
	
	@Value("${twilio.auth.token}") = Alterar para o seu token no twilio
<br />
	
	@Value("${twilio.account.phone}") = Alterar para o seu phone no twilio
<br />
<br /><br />

## FEITO AS ALTERAÇÕES ACIMA
Na sua IDE de preferência, executar cada projeto/serviço "Run As -> Spring Boot App"



