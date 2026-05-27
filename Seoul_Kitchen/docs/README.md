TERCERA ENTREGA - PROYECTO FINAL - THE SEOUL KITCHEN

Programa hecho en Java para ayudar a manejar un restaurante de comida coreana. El sistema se llama The Seoul Kitchen y tiene todo lo que se ocupa para administrar el negocio.

Lo que el sistema puede hacer:

Tiene una pantalla de login y registro para que solo entren los administradores.

Hay un dashboard principal donde se ven las estadisticas y si falta algo en el inventario.

Se pueden registrar, ver y editar los platillos del menu con sus precios.

El modulo de ordenes permite anotar los pedidos de los clientes y ver si ya estan listos o si se cancelaron.

Tambien hay una lista de clientes con su historial de lo que han comprado.

En el inventario se pueden ver los ingredientes y te avisa cuando se estan acabando.

Como esta organizado el codigo:

Acomode todo en carpetas para que no estuviera revuelto. En la carpeta de java puse los modelos, los controladores y todas las ventanas del diseño. En la carpeta de resources guarde las fotos y los iconos que use para que los botones se vean mejor.

Detalles de la programacion:

Use Java Swing para hacer todas las interfaces. Para que el programa no abra mil ventanas use CardLayout, asi todo cambia en la misma pantalla de forma mas limpia. Tambien me asegure de usar el comando dispose cuando cierras sesion o cancelas algo para que no se queden procesos abiertos gastando memoria.

Esta version ya cuenta con conexiones a la base de datos y funcionalidad completa en todos los modulos:

Dashboard: muestra estadísticas en tiempo real como total de ventas y pedidos pendientes.

Menú: permite agregar, editar y eliminar platillos.

Pedidos: registro de nuevos pedidos, gestión del estado (pendiente, preparado, entregado, cancelado) y visualización del historial.

Clientes: registro, edición y visualización del historial de pedidos.

Inventario: gestión de ingredientes con alertas automáticas cuando los niveles son bajos.

Para que funcione:

Solo se necesita tener instalado Java. La navegacion es sencilla y puedes moverte por todos los botones del menu lateral para ver los diferentes modulos.