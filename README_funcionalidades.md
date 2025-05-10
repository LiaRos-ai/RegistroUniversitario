# 📚 Registro Universitario – Gestión de Materias y Unidades Temáticas

Este documento describe la implementación de la funcionalidad solicitada en los Ejercicios 1 y 2 del módulo de relaciones y persistencia con Spring Boot.

---

## ✅ Funcionalidades desarrolladas

### 🧩 Parte 1: Relaciones (One-to-Many) + Referencias circulares

#### Ejercicio 1 – Relación Materia → UnidadTematica

- Se creó la entidad `UnidadTematica`.
- Se vinculó con la entidad `Materia` mediante una relación **One-to-Many**.
- Una `Materia` puede tener muchas `UnidadTematica`.

✔️ Se utilizó:

- `@OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)` en `Materia`.
- `@ManyToOne` en `UnidadTematica`.
- `@JsonManagedReference` y `@JsonBackReference` para evitar referencias cíclicas en JSON.

---

## 🔄 Parte 2: Actualización en Cascada

#### Ejercicio 2 – Reemplazo completo de unidades temáticas

- Se implementó un endpoint que reemplaza **todas las unidades temáticas** de una materia.
- Las unidades anteriores se eliminan automáticamente gracias a:

✔️ `cascade = CascadeType.ALL`  
✔️ `orphanRemoval = true`

#### Extra: Validación de duplicados

- Se agregó una validación para **evitar títulos duplicados** en las unidades temáticas dentro del mismo request.

---

## 🌐 Endpoints principales

Estos son los endpoints clave desarrollados para resolver los ejercicios:

| Método | Endpoint                            | Descripción                                                                 |
|--------|-------------------------------------|-----------------------------------------------------------------------------|
| GET    | `/api/materias/{id}/unidades`       | 📌 Lista las unidades temáticas de una materia.                             |
| GET    | `/api/materias/con-unidades`        | 📌 Extra del ejercicio 1: lista todas las materias con sus unidades.        |
| PUT    | `/api/materias/{id}/unidades`       | 📌 Reemplaza completamente las unidades de una materia (ejercicio 2).       |
|        |                                     | 🚫 Elimina las anteriores y guarda nuevas con cascade + orphanRemoval.     |
|        |                                     | ⚠️ Extra del ejercicio 2: valida que no haya títulos duplicados.           |

---

## 🧪 Cómo probar

1. Crear una materia: `POST /api/materias`
2. Reemplazar sus unidades: `PUT /api/materias/{id}/unidades`
3. Verificar con: `GET /api/materias/{id}/unidades`
4. Intentar enviar títulos duplicados para ver el mensaje de error

---

## 🔖 Anotaciones clave usadas

| Anotación                    | Función                                                               |
|-----------------------------|------------------------------------------------------------------------|
| `@OneToMany`                | Establece la relación de Materia a muchas UnidadesTematicas            |
| `@ManyToOne`                | Relación inversa de UnidadTematica hacia Materia                      |
| `@JsonManagedReference`     | Previene bucle infinito al serializar el lado "padre" de la relación  |
| `@JsonBackReference`        | Complemento de la anterior, ignora serialización del hijo              |
| `@Transactional`            | Asegura integridad en operaciones como reemplazo de listas             |
| `@RestController` + `@RequestMapping` | Define el controlador REST de Materia                              |
