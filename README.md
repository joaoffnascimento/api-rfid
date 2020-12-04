# API-RFID

## RESUMO:

### Proposta de um sistema destinado ao monitoramento e localização em tempo real de ativos e passivos dentro de uma instituição. Permite consultar o inventário de patrimônio de diferentes setores. A API disponibiliza as seguintes funções: 

* Controle do leitor: Cadastrar um leitor (Marca, modelo, IP e porta), ativar e desativar;

* Controle de tags rfid: leitura, cadastro, monitoramento das antenas;

* Cadastro de ativos e passivos; vinculo as tags rfid;

* Cadastro dos funcionarios que terão acesso ao sistema;

* Cadastro de departamentos da instituição;

* Possibilidade de realizar o inventário de departamentos;

* Verificar o histórico de entrada e saída dos ativos / passivos.

## Tecnologias utilizadas:

### API REST **Java** construida com o **Spring Boot 2.3.4**, utilizando o **Eclipse EE**, utilizando o banco de dados não relacional **MongoDB**, com sua solução em nuvem **MongoDB Atlas**.

### Bibliotecas utilizadas: 

* spring-boot-starter-actuator
* spring-boot-starter-data-mongodb
* spring-boot-starter-web
* spring-boot-devtools
* spring-boot-starter-test
* spring-boot-starter-validation
* spring-boot-starter-cache
* spring-boot-starter-hateoas
* lombok
* ltkjava 1.0.0.7
* ltkjava-generator 1.0.0.7
* jmxri
* jmxtools
* jms
* modelmapper
* jjwt
* spring-security-crypto
* spring-boot-starter-websocket