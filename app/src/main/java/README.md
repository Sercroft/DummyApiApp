# DummyAppASD

Este proyecto es una aplicación de muestra desarrollada en Kotlin que consume datos de la API JSONPlaceholder y muestra imágenes aleatorias obtenidas de la API Random Dog.

## Requisitos

- Android Studio 4.1 o superior
- Gradle 6.5 o superior
- Conexión a Internet para consumir las APIs
- Java 17
- Kotlin 1.8.0

## Instalación

1. Clona el repositorio en tu máquina local:
   ```bash
   git clone https://github.com/tu-usuario/dummyappasd.git
2. Abre Android Studio y selecciona "Open an existing project".
3. Navega a la carpeta donde clonaste el repositorio y ábrelo.
4. Android Studio debería sincronizar el proyecto automáticamente. Si no lo hace, selecciona File > Sync Project with Gradle Files.

## Ejecución
1. Conecta un dispositivo Android o inicia un emulador.
2. Haz clic en el botón Run o selecciona Run > Run 'app'.

## Estructura Principal del Proyecto
1. MainFragment: Fragmento principal que muestra una lista de posts y una imagen aleatoria de la API Random Dog.
2. DetailFragment: Fragmento de detalle que muestra la información completa de un post y una imagen aleatoria de la API Random Dog.
3. PostAdapter: Adaptador para el RecyclerView que muestra los posts y las imágenes aleatorias.
4. PostViewModel: ViewModel para manejar la lógica de negocio y la comunicación con las APIs.

## Decisiones de Diseño
1. Uso de Retrofit: Retrofit se utiliza para consumir las APIs de JSONPlaceholder y Random Dog debido a su facilidad de uso y capacidad de manejar peticiones HTTP de manera eficiente.
2. Glide para manejo de imágenes: Glide se utiliza para cargar y mostrar imágenes debido a su capacidad de manejar la carga de imágenes y su almacenamiento en caché de manera eficiente. Además, de que es un herramienta eficiente que cuenta con caracaterísticas de optimización como gurdar imágenes en cache para optimizar recursos y rendimiento en la app a nivel general.
3. Dagger Hilt para inyección de dependencias: Hilt se utiliza para facilitar la inyección de dependencias, lo que mejora la mantenibilidad y escalabilidad del proyecto.
4. Uso de ViewModel y LiveData: Se utiliza ViewModel y LiveData para manejar la lógica de negocio y la UI de manera reactiva, lo que permite una mejor separación de responsabilidades y una UI más receptiva.
5. Patrón MVVM: Se adopta el patrón MVVM (Model-View-ViewModel) para estructurar el código, lo que ayuda a mantener una separación clara entre la lógica de negocio, la UI y los datos. Este patrón mejora la testabilidad y la mantenibilidad del código al mantener las responsabilidades bien definidas.