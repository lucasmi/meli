# Como subir o projeto e acessar o Swagger UI

1. **Pré-requisitos:**
   - Java 21 ou superior instalado
   - Maven instalado

2. **Compilar o projeto:**
   ```sh
   mvn clean install

3. **Rodar o projeto:**
   ```sh
   mvn spring-boot:run

4. **Interagindo com o projeto**
   - Abra o browser e acesse
   ```sh
   http://localhost:8080/swagger-ui/index.html

5. **Sonar**
  - Esse comando funciona somente na maquina onde foi desenvolvido. 
  - A imagem do relatório está no arquivo relatiroSonar.png
  
  ![Relatório Sonar](https://github.com/lucasmi/meli/blob/main/relatorioSonar.png)

  ```sh
  mvn clean install sonar:sonar -Dsonar.login=squ_2e3e9853d89bc907cc002b333f6c7eabe9e67031 -Dsonar.host.url=http://localhost:9000