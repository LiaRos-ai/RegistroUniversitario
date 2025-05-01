# 📘 Descripción de Funcionalidades Implementadas – Materia & Unidad Temática

## 🧩 Ejercicio 1 – Relación One-to-Many (Materia → UnidadTematica)
**Autores:** Enrique Fernandez y Samantha Gironda

### 🔧 Funcionalidades implementadas:
- Se creó una relación One-to-Many entre Materia y UnidadTematica.
- Se usaron las anotaciones `@JsonManagedReference` y `@JsonBackReference` para evitar ciclos infinitos al devolver datos en JSON.
- Se implementó un endpoint para listar todas las unidades temáticas asociadas a una materia específica, accediendo a través del ID de la materia.

---

## 🎁 Extra 1 – Listar todas las materias con sus unidades temáticas
**Autores:** Luz Chavez y Nohemy Mamani

### 🔧 Funcionalidades implementadas:
- Se agregó un DTO especial que incluye tanto los datos de la Materia como su lista de UnidadTematica.
- Se implementó un endpoint que permite obtener todas las materias, incluyendo dentro de cada una sus unidades temáticas asociadas.
- La estructura de la respuesta es clara y jerárquica para facilitar el consumo desde el frontend.

---

## 🔄 Ejercicio 2 – Reemplazo en cascada de unidades temáticas
**Autores:** Cristhian Alvarez, Oscar Choque y Josue Lopez

### 🔧 Funcionalidades implementadas:
- Se implementó un endpoint `PUT` para reemplazar completamente las unidades temáticas de una materia específica.
- Se configuró la entidad Materia con `cascade = CascadeType.ALL` y `orphanRemoval = true` en su relación con UnidadTematica.
- Esto permite que las unidades anteriores se eliminen automáticamente cuando se actualiza la lista.

---

## 🎁 Extra 2 – Validación de duplicados en unidades temáticas
**Autores:** Cristhian Alvarez, Oscar Choque y Josue Lopez

### 🔧 Funcionalidades implementadas:
- Se agregó una validación que evita insertar unidades duplicadas con el mismo `nombre_unidad` dentro del mismo JSON enviado.
- Si se detectan duplicados, se lanza un error con código `400` y un mensaje claro indicando la causa del rechazo.

---

## 🌐 Endpoints Implementados

| Método | Ruta                         | Descripción |
|--------|------------------------------|-------------|
| GET    | `api/materias/{id}/unidades` | Lista todas las unidades temáticas de una materia específica. |
| GET    | `api/materias/unidades`      | Lista todas las materias con sus unidades temáticas incluidas. |
| PUT    | `api/materias/{id}/unidades` | Reemplaza completamente las unidades temáticas de una materia. |

---

## 📦 Ejemplo de respuesta del endpoint `api/materias/unidades`

```json
{
  "id": 1,
  "nombreMateria": "Calculo I",
  "codigoUnico": "MAT101",
  "creditos": 4,
  "unidades": [
    {
      "id": 1,
      "nombre_unidad": "Límites y Continuidad",
      "descripcion": "Introducción a límites y funciones continuas",
      "contenido": "Conceptos de límite y continuidad"
    },
    {
      "id": 2,
      "nombre_unidad": "Derivadas",
      "descripcion": "Definición y propiedades de la derivada",
      "contenido": "Reglas de derivación"
    }
  ]
}