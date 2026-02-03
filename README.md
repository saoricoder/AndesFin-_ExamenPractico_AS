# AndesFin - Simulador de Inversiones

## Descripción del Proyecto
AndesFin es una aplicación backend desarrollada en Java con Spring Boot que permite a los usuarios simular inversiones financieras. El sistema evalúa el capital disponible de un usuario y selecciona una combinación óptima de productos financieros para maximizar el retorno de inversión, respetando las restricciones presupuestarias.

El núcleo de la aplicación utiliza un algoritmo de optimización para recomendar la mejor cartera de productos basada en el costo y el porcentaje de retorno esperado.

## Tecnologías Utilizadas
*   **Java 17**: Lenguaje de programación principal.
*   **Spring Boot 3**: Framework para el desarrollo de la aplicación web y gestión de dependencias.
*   **Spring Data JPA**: Para la persistencia de datos y mapeo objeto-relacional (ORM).
*   **MySQL**: Base de datos relacional para almacenar usuarios, productos y simulaciones.
*   **Lombok**: Librería para reducir el código boilerplate (getters, setters, constructores).
*   **Maven**: Herramienta de gestión de proyectos y construcción.

## Instrucciones de Instalación y Ejecución

### Prerrequisitos
1.  Tener instalado **Java JDK 17** o superior.
2.  Tener instalado **Maven**.
3.  Tener instalado y ejecutándose **MySQL Server**.

### Configuración de la Base de Datos
1.  Cree una base de datos en MySQL llamada `andesfin_db`:
    ```sql
    CREATE DATABASE andesfin_db;
    ```
2.  Verifique que las credenciales en `src/main/resources/application.properties` coincidan con su configuración local:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/andesfin_db
    spring.datasource.username=root
    spring.datasource.password=root
    ```

### Ejecución
1.  Clone el repositorio o descargue el código fuente.
2.  Navegue a la carpeta raíz del proyecto.
3.  Ejecute el siguiente comando para compilar y ejecutar la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```
    (En Windows utilice `mvnw.cmd spring-boot:run`)

La aplicación se iniciará en el puerto `8080` por defecto. Al iniciar, se cargarán automáticamente datos de prueba (5 usuarios y 8 productos) gracias al archivo `data.sql`.

## Explicación de los Endpoints

### 1. Realizar Simulación
*   **Método**: `POST`
*   **URL**: `/simulaciones`
*   **Descripción**: Recibe el ID de un usuario, su capital disponible y una lista de productos candidatos. Retorna la selección óptima de productos.
*   **Cuerpo de la Petición (JSON)**:
    ```json
    {
      "usuarioId": "uuid-del-usuario",
      "capitalDisponible": 10000.00,
      "productos": [
        {
          "nombre": "Fondo A",
          "costo": 1000.00,
          "porcentajeRetorno": 5.00
        },
        {
          "nombre": "Fondo B",
          "costo": 2000.00,
          "porcentajeRetorno": 8.50
        }
      ]
    }
    ```

### 2. Consultar Historial
*   **Método**: `GET`
*   **URL**: `/simulaciones/{usuarioId}`
*   **Descripción**: Obtiene el historial de simulaciones realizadas por un usuario específico.

## Ejemplos de Uso y Cálculos

### Ejemplo de Optimización
Supongamos que un usuario tiene un capital de **$3,000** y los siguientes productos disponibles:

| Producto | Costo | Retorno (%) | Ganancia Esperada |
| :--- | :--- | :--- | :--- |
| Fondo A | $1,000 | 5% | $50 |
| Fondo B | $2,000 | 10% | $200 |
| Fondo C | $1,500 | 8% | $120 |

**Lógica del Algoritmo:**
1.  El sistema prioriza los productos con mayor porcentaje de retorno.
2.  Orden: Fondo B (10%) -> Fondo C (8%) -> Fondo A (5%).
3.  Selección:
    *   Intenta agregar **Fondo B**: Costo $2,000. (Acumulado: $2,000). Restante: $1,000.
    *   Intenta agregar **Fondo C**: Costo $1,500. (Excede el restante de $1,000). No se selecciona.
    *   Intenta agregar **Fondo A**: Costo $1,000. (Acumulado: $3,000). Restante: $0.

**Resultado de la Simulación:**
*   **Productos Seleccionados**: Fondo B, Fondo A.
*   **Costo Total**: $3,000.
*   **Ganancia Total**: $200 (B) + $50 (A) = **$250**.
