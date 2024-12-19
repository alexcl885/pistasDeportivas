# Readme Alex

## Reservas
En el fragmento del **nav principal**, he añadido un apartado exclusivo para los usuarios con rol de **ADMIN**. Este apartado, llamado **Reservas**, permite a los administradores acceder a las reservas actuales y gestionar su contenido. Las funcionalidades incluyen:
- Visualización de todas las reservas disponibles.
- Edición de reservas existentes.
- Eliminación de reservas.

---

## Controlador: ControReserva
El controlador **ControReserva** permite gestionar las reservas y está diseñado exclusivamente para usuarios con rol de **ADMIN**. Las acciones permitidas incluyen:
- **Visualizar** todas las reservas existentes.
- **Editar** los detalles de una reserva.
- **Eliminar** una reserva del sistema.

### Rutas del Controlador ControReserva

| **Método HTTP** | **Ruta**                | **Descripción**                                                                             |
|------------------|-------------------------|--------------------------------------------------------------------------------------------|
| `GET`           | `/reservas`             | Obtiene una lista de todas las reservas.                                                   |
| `GET`           | `/reservas/add`         | Muestra el formulario para añadir una nueva reserva.                                       |
| `POST`          | `/reservas/add`         | Procesa la creación de una nueva reserva.                                                  |
| `GET`           | `/reservas/edit/{id}`   | Muestra el formulario para editar una reserva existente.                                   |
| `POST`          | `/reservas/edit/{id}`   | Procesa la edición de una reserva existente.                                               |
| `GET`           | `/reservas/del/{id}`    | Muestra el formulario para confirmar la eliminación de una reserva existente.              |
| `POST`          | `/reservas/del/{id}`    | Procesa la eliminación de una reserva existente.                                           |

---

## Plantillas Thymeleaf
He implementado dos plantillas principales mediante el motor de plantillas **Thymeleaf**:

1. **Reservas**: 
   - Muestra una lista con todas las reservas realizadas en el sistema.
   - Disponible únicamente para usuarios con rol de **ADMIN**.

2. **Add**:
   - Permite editar y borrar reservas existentes.
   - Diseñada con campos que se rellenan automáticamente según la reserva seleccionada.

---


