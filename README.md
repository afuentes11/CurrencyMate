
# CurrencyMate

Convertidor de monedas desarrollado en Java 8 usando programacion orientada a objetos y JavaFX para la interfaz de usuario.

El proyecto es el resultado del desarrollo del sprint 1 del programa ONE de Oracle + Alura LATAM.

De forma general, la calculadora fue construida usando el patron de diseño Modelo Vista Controlador (MVC) que tiene como objetivo separar los datos, la interfaz de usuario y la lógica de control en componentes distintos, con el fin de mejorar la modularidad, la reutilización del código y la mantenibilidad del proyecto.

Para obtener acceso a la informacion, fue usada la API [currencyapi](https://currencyapi.com/) que proporciona un listado detallado de un gran numero de divisas, donde incluye su código, su simbolo, y el valor actual en el mercado. Para poder manejar la informacion traida por la API de manera eficiente se usó la librería [JSON-java](https://github.com/stleary/JSON-java), que ofrece clases para poder trabajar con archivos JSON de forma rapida.

Por ultimo, la interfaz fue construida con [JavaFX](https://openjfx.io/), un conjunto de librerias y bibliotecas que permiten desarrollar GUI con diseños atractivos. JavaFX permite desarrollar interfaces de manera muy similar a como se realiza una pagina Web, tenemos un archivo .FXML donde esta toda la estructura de la pagina, similar a HTML, por otro lado nos permite dar estilo a nuestro diseño mediante CSS y por ultimo agregamos la funcionalidad de la misma mediante Java.


## Autor

- [@afuentes11](https://github.com/afuentes11)


## Demo

<div align="center">
   <img src="https://github.com/afuentes11/CurrencyMate/blob/master/GIF.gif" alt="destop gif" />
</div>


## Environment Variables

Para ejecutar este proyecto, deberá añadir las siguientes variables de entorno

`API_KEY`

Para obtener una API_KEY es necesario registrarse en [currencyapi](https://currencyapi.com/). 



## Tech Stack

 - Java 
 - Poo 
 - JavaFX
 - Css3
 - MVC
 - Manejo de API's

