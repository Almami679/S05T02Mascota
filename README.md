# Mascota Tasca S5.02 
### (niveles 1, 2 y 3)

### Albert Marin



## Descripción


_Este proyecto consiste en una API desarrollada con Spring Boot y presentado con un frontend en React,
diseñado para gestionar una flota de aviones de usuarios. Con swguridad JWT gestionada con tokens
**Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar)**
y a mas a mas operaciones del propio modelo de negocio de un tipico gestor
**Proporciona operaciones de negocio:**_
-Creación de usuarios con wallet.
-Compra y gestión de aviones.
-Estados de mejora y nivel de combustible.
-Batallas entre aviones de diferentes usuarios.
-Venta de aviones no deseados.
-Mensajes internos entre jugadores.

Interfaz visual en React con un hangar dinámico, mostrando ciclos de día, noche y clima aleatorio.
- para manejar los datos de Users, Aviones, Hangares y accesorios se hace a través de una API REST. (Mediante MySQL) 
- para manejar los datos deBattle se hace a través de una API REST. (MongoDB) _


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


 **Endpoints**
Puedes ver la documentación completa con Swagger en:
```
http://localhost:8080/swagger-ui.html
```
Algunos endpoints clave:

POST /auth/register → Registra un nuevo usuario

POST /auth/login → Autentica y devuelve un JWT

GET /planes → Lista todos los aviones disponibles

POST /planes/buy → Comprar un avión

POST /battle → Iniciar una batalla

**Interfaz del Hangar**

_El hangar del usuario muestra sus aviones y se actualiza dinámicamente:
Ciclo de día y noche basado en una hora real = un día en el juego.
Clima aleatorio con efectos visuales._

_El hangar del Admin muestra todos los aviones de la base de datos
con nombre del dueño y posibilidades de eliminar, modificar estado 
y añadir accesorio_

**Excepciones Personalizadas**
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

**Integración con Base de Datos**

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
