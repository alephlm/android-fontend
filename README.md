# android-fontend
Aplicativo android usado como frontend para a comunicacao com o servidor do projeto [web-android-spring-backend](https://github.com/alephlm/web-android-spring-backend)

## Modo de Inicialização
Depois de clonar este repositório vá ate o arquivo ServiceGenerator.java e mude a constante BASE_URL com o IP e PORTA do seu servidor.  
(neste caso o servidor instanciado do projeto de [backend](https://github.com/alephlm/web-android-spring-backend))
```shell
$ git clone https://github.com/alephlm/android-fontend.git
$ cd /src/main/java/br/gov/ce/sda/androidsda/rest/
```
```java
//ServiceGenerator.java
private static final String BASE_URL = "http://SERVER_IP:SERVER_PORT/";
```
Depois basta iniciar a aplicação no androidStudio. Usando emulador ou passando para um celular android.

## Tecnologias utilizadas no projeto.
* Codificação com [Android Studio](https://developer.android.com/studio/index.html)
* Biblioteca [retrofit](http://square.github.io/retrofit/) para a comunicação com o servidor rest.


