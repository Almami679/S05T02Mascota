# Mascota Tasca S5.02 
### (niveles 1, 2 y 3)

### Albert Marin



## Descripción


_Este proyecto consiste en una API desarrollada con Spring Boot y presentado con un frontend en React,
diseñado para gestionar una flota de aviones de usuarios. Con swguridad JWT gestionada con tokens
**Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar)**
y a mas a mas operaciones del propio modelo de negocio de un tipico gestor
**Proporciona operaciones de negocio:**_
>Creación de usuarios con wallet.
>Compra y gestión de aviones.
>Estados de mejora y nivel de combustible.
>Batallas entre aviones de diferentes usuarios.
>Venta de aviones no deseados.
>Mensajes internos entre jugadores.
>Interfaz visual en React con un hangar dinámico mostrando ciclos de día, noche y clima aleatorio.
>Manejo de los datos de Users, Aviones, Hangares y accesorios se hace a través de una API REST. (Mediante MySQL) 
>Manejo de los datos de Battle se hace a través de una API REST. (MongoDB) _


## Estructura del Proyecto
```
S5T2Mascota(AIRCRAFT_FLEET)
├───main
│   ├───java
│   │   └───ItAcademyJavaSpringBoot
│   │       └───AircraftFleet
│   │           │   AircraftFleetApplication.java
│   │           │
│   │           ├───config
│   │           │       CacheConfig.java
│   │           │       SwaggerConfig.java
│   │           │
│   │           ├───controllers
│   │           │       BattleController.java
│   │           │       HangarController.java
│   │           │       StoreController.java
│   │           │
│   │           ├───DTO
│   │           │       BattlePlayerDTO.java
│   │           │       BattleResultDTO.java
│   │           │       HangarDTO.java
│   │           │       PlaneDTO.java
│   │           │       RankingDTO.java
│   │           │       UserInfoDTO.java
│   │           │
│   │           ├───exceptions
│   │           │       AccessoryNotFoundException.java
│   │           │       BattleNotFoundException.java
│   │           │       GlobalExceptionHandler.java
│   │           │       HealthPlaneIsFullException.java
│   │           │       InsufficientCreditsException.java
│   │           │       NoAccessoryFoundException.java
│   │           │       NoHavePermissionsException.java
│   │           │       NoPlaneFoundException.java
│   │           │       NotPlayerAvailableException.java
│   │           │       PasswordWithInvalidFormatException.java
│   │           │       PlaneHasFullFuelException.java
│   │           │       PlaneNotFoundException.java
│   │           │       PlayerHasNoPlanesException.java
│   │           │       UsernameIsInUseException.java
│   │           │       UserNotFoundException.java
│   │           │
│   │           ├───model
│   │           │   │   PlaneBuilder.java
│   │           │   │
│   │           │   ├───entitiesEnums
│   │           │   │       AccessoryType.java
│   │           │   │       PlaneAccessoryModel.java
│   │           │   │       PlaneAction.java
│   │           │   │       PlaneModel.java
│   │           │   │       Role.java
│   │           │   │       StoreAction.java
│   │           │   │       TimeOfDay.java
│   │           │   │       Weather.java
│   │           │   │
│   │           │   ├───mongoDB
│   │           │   │       Battle.java
│   │           │   │
│   │           │   └───sql
│   │           │           Hangar.java
│   │           │           Plane.java
│   │           │           PlaneAccessory.java
│   │           │           User.java
│   │           │
│   │           ├───repository
│   │           │       BattleRepository.java
│   │           │       HangarRepository.java
│   │           │       PlaneAccessoriesRepository.java
│   │           │       PlaneRepository.java
│   │           │       UserRepository.java
│   │           │
│   │           ├───security
│   │           │   ├───DTO
│   │           │   │       AuthRequestDTO.java
│   │           │   │       AuthResponseDTO.java
│   │           │   │
│   │           │   ├───securityConfig
│   │           │   │       SecurityConfig.java
│   │           │   │
│   │           │   ├───securityController
│   │           │   │       AuthenticationController.java
│   │           │   │
│   │           │   └───securityService
│   │           │           CustomUserDetailsService.java
│   │           │           JwtFilter.java
│   │           │           JwtService.java
│   │           │
│   │           └───Services
│   │               │   DTOConstructors.java
│   │               │   DTOConstructorsInterface.java
│   │               │
│   │               ├───battleService
│   │               │   │   BattleServiceInterface.java
│   │               │   │
│   │               │   └───battleServiceImpl
│   │               │           BattleService.java
│   │               │
│   │               ├───hangarService
│   │               │   │   HangarServiceInterface.java
│   │               │   │
│   │               │   └───hangarServiceImpl
│   │               │           HangarService.java
│   │               │
│   │               ├───planeService
│   │               │   │   PlaneServiceInterface.java
│   │               │   │
│   │               │   └───planeServiceImpl
│   │               │           PlaneService.java
│   │               │
│   │               ├───storeService
│   │               │   │   StoreServiceInterface.java
│   │               │   │
│   │               │   └───storeServiceImpl
│   │               │           StoreService.java
│   │               │
│   │               └───userService
│   │                   │   UserServiceInterface.java
│   │                   │
│   │                   └───userServiceImpl
│   │                           UserService.java
│   │
│   └───resources
│       │   application.properties
│       │   data.sql
│       │   schema.sql
│       │
│       ├───static
│       └───templates
└───test
    ├───java
    │   └───ItAcademyJavaSpringBoot
    │       └───Macota
    │           └───IntegrationTests
    │               ├───JWT
    │               │       LoginAndTokenTest.java
    │               │       LoginAndTokenTestADMIN.java
    │               │       RegistrationTest.java
    │               │
    │               └───PlanesService
    │                       PlaneServiceTestsActions.java
    │                       PlaneServiceTestsAddAccessory.java
    │                       PlanesServiceTestsBuyPlane.java
    │
    └───resources
            data.sql
            
```

## Links

Enlace de github del codigo: [Almami BlackJack repository](https://github.com/Almami679/S05T02Mascota).

## Funcionalidades


### **Endpoints**
Puedes ver la documentación completa con Swagger en:
```
http://localhost:8080/swagger-ui.html
```
Algunos endpoints clave:

>POST /auth/register → Registra un nuevo usuario

>POST /auth/login → Autentica y devuelve un JWT

>GET /aircraft/hangar/planes → Lista todos los aviones disponibles (ADMIN)

>POST /aircraft/store/planes/buy → Comprar un avión

>POST /aircraft/battle → Iniciar una batalla

### **Logica de accesorios**

_Hay tres niveles disponibles de accesorios de GUN y ARMOR,
cada nivel otorga un suplemento tanto al ataque o a la vida en relacion
al nivel del accesorio.

Solo se puede tener un accesorio equipado por avion, si se equipa uno se desequipa 
el que tenia antes._

### **Logica de Store**

_Se tiene una wallet asignada a cada user, se pueden añadir fondos intercambiado el score
cada 1000 puntos de score se añaden 1000 creditos.
La tienda vende aviones que se crean mediante un builder.
Si se equipan accesorios se descuenta dinero de la wallet.
Si se vende el avion del hangar se elimina de la base de datos y se le añaden fondos al usuario
con el valor de la mitad del precio principal del avión._

### **Logica de batalla**

_El sistema selecciona un usuario aleatorio y un avion que le pertenezca,
te muestra el nombre y el avion y te da la elecion de aceptar batalla o retirarte,
se calcula una media de la vida y el ataque de tu avion y el oponente, y le da un 60%
de provavilidad de ganar a avion que tenga mayores stats.
El avion perdedor se detruye y elimina de la base de datos.
El avion ganador vuleve al hangar sin conbustible y con una vida aleatoria de entre el 
30% y el 60%_

Los dos jugadores reciben puntos de score por batalla en relacion al resultado

### **Interfaz del Hangar**

_El hangar del usuario muestra sus aviones y se actualiza dinámicamente:
Ciclo de día y noche basado en una hora real = un día en el juego.
Clima aleatorio con efectos visuales._

_El hangar del Admin muestra todos los aviones de la base de datos
con nombre del dueño y posibilidades de eliminar, modificar estado 
y añadir accesorio_

### **Excepciones Personalizadas**
_ Gestionadas desde el **GlobalEceptionHandler** _
>AccessoryNotFoundException.java
>BattleNotFoundException.java
>HealthPlaneIsFullException.java
>InsufficientCreditsException.java
>NoAccessoryFoundException.java
>NoHavePermissionsException.java
>NoPlaneFoundException.java
>NotPlayerAvailableException.java
>PasswordWithInvalidFormatException.java
>PlaneHasFullFuelException.java
>PlaneNotFoundException.java
>PlayerHasNoPlanesException.java
>UsernameIsInUseException.java
>UserNotFoundException.java
>
### **Tests de integracion**
>Se incluyen tests de integracion para endpoints de register y login,
tanto en user y admin y comprobacion de seguridad
>Test de aviones donde se comprueba la creacion del avion, la venta del mismo y
añadir accesorio y realizar accion.

### **Integración con Base de Datos**

>Ya que en mongo no tenemos forma de gestionar el Id en autoincrement, he aadido un servicio
>que nos devuelve el ultimo Id de la base de datos para gestionarlo en el constructor de game/new
>
>Es completamente funcional con wagger accediendo a la url local:
```
http://localhost:8080/swagger-ui/index.html
```


## Tables

| Requisitos Previos  | 
| ------------- |
| Java 17 o superior      | 
| Maven 3.8 o superior      | 
| MongoDB Compass     |


## Instalación

### Clona el repositorio:
```
git clone <repository-url>
```

### Navega al directorio del proyecto:
```
cd <project-directory>
```
### Compila el proyecto:
```
./mvnw clean install
```
### Se debera iniciar el Servicio mongoDB ubicado en:
```
C:\Program Files\MongoDB\Server\8.0\bin\mongod
```
