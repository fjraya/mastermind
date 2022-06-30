# mastermind

Hay una suite de tests unitarios, de integración y funcionales hechos con junit.

Finalmente, los tests de endpoints requeridos los he hecho con un ParametrizedTest usando JUnit y TestRestTemplate.
Normalmente uso JMeter para los test funcionales. Esto facilita enormemente el aprovechar estos tests para
construir tests de rendimiento. Se puede ver el archivo .jmx en /test/kotlin/functional/.

He usado una base de datos H2 embebida para el desarrollo local y los test.
La construcción de tablas la realizo mediante Liquibase aplicando migrations.

En el directorio docker, tenéis el Dockerfile y el docker-compose.yml para construir la imagen del backend y para orquestar esta imagen y la bbdd.

## Despliegue

Para desplegar el sistema, he hecho en la raíz un Makefile (habrá que tener esta utilidad instalada en vuestro terminal),
los dos comandos son:

make build: compila, pasa los tests, y si los tests están ok, construye la imagen docker del backend

make deploy: usa docker-compose para desplegar esa imagen y la bbdd (uso postgres).

NO he puesto volúmen en la BBDD porque no sé que sistema operativo usais y no quería que diera problemas con las rutas.


