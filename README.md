# 🎟️ SportPass
Plataforma web de venta de entradas para eventos deportivos. Permite a los usuarios ver eventos, seleccionar asientos por zona, comprar entradas y recibir notificaciones ante cambios en los eventos adquiridos.

## 🛠️ Stack tecnológico
CapaTecnologíaBackendJava + Spring Boot 3.5SeguridadSpring Security + JWT (HttpOnly Cookies)ORMJPA + HibernateBase de datosPostgreSQL 16MigracionesFlywayFrontendAngular 20 + Angular MaterialPagosMercadoPago SandboxNotificacionesWebSockets + JavaMailSender

## 🚀 Cómo correr el proyecto
#### Prerrequisitos
- Java 17+
- Node.js 18+
- PostgreSQL 16
- Angular CLI ( ` npm install -g @angular/cli `)

### Backend
1. Cloná el repositorio
2. Creá la base de datos en PostgreSQL:

  ` CREATE DATABASE sportpass; `

3. ` Configurá src/main/resources/application.yml: `

  ```
    spring:
      datasource:
      url: jdbc:postgresql://localhost:5432/sportpass
      username: postgres
      password: tu_password
  ```

4. Levantá el backend:

```
 cd sportpass-backend
./mvnw spring-boot:run
```

El servidor corre en ` http://localhost:8080 `

### Frontend

```
cd sportpass-frontend
npm install
ng serve
```

La app corre en ` http://localhost:4200 `

## ✨ Funcionalidades
### 👤 Usuarios

- Registro y login con autenticación JWT via cookies HttpOnly
- Perfil con historial de compras
- Notificaciones ante cambios en eventos comprados

### 🏟️ Eventos

- Listado de eventos deportivos con filtros
- Detalle de evento con mapa visual de zonas y asientos
- Precios diferenciados por zona (VIP, Platea, Popular, etc.)

### 🎟️ Compra de entradas

- Selección de asientos por zona con disponibilidad en tiempo real
- Checkout integrado con MercadoPago
- Generación de código QR por entrada comprada

### 🔔 Notificaciones

- Email automático al completar una compra
- Notificación automática si un evento cambia de fecha, lugar o se cancela
- Alertas in-app en tiempo real via WebSockets

### 🛡️ Panel de administración

- Dashboard con métricas: eventos activos, entradas vendidas, ingresos
- CRUD completo de eventos y estadios
- Gestión de zonas y precios por evento
- Visualización de compradores por evento

## 👨‍💻 Autor
Hecho por: Vicente Moscuzza.

Mail: vicente.moscuzza@gmail.com

Desarrollado como proyecto personal para portfolio.
