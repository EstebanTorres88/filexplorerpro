Diagrama de clases (Primer avance)

## 1. Núcleo del árbol de directorios

Estas clases representan la estructura principal del sistema de archivos y son las que se usan cuando se explica cómo se guardan carpetas y archivos.

### `FileSystemNode` (abstracta)
- Propósito: clase base para cualquier elemento del árbol.
- Responsabilidad: definir lo común entre carpetas y archivos: nombre, fecha de creación y referencia al padre.
- Se usa para explicar la idea general de un nodo del árbol y para construir rutas como `/documents/readme.txt`.

### `FolderNode` (extiende `FileSystemNode`)
- Propósito: representa una carpeta dentro del sistema.
- Responsabilidad: guardar hijos mediante una lista enlazada propia (`ChildNode`), añadir o eliminar elementos y localizar una subcarpeta o archivo por nombre.
- Se usa para explicar cómo funciona la jerarquía de directorios y cómo se organizan los contenidos dentro de una carpeta.

### `FileNode` (extiende `FileSystemNode`)
- Propósito: representa un archivo terminal del árbol.
- Responsabilidad: almacenar extensión, tamaño y contenido del archivo.
- Se usa para explicar cómo se modelan los archivos que no tienen hijos, por ejemplo cuando se trabaja con `touch`.

### `ChildNode`
- Propósito: nodo interno de la lista enlazada de hijos de una carpeta.
- Responsabilidad: enlazar cada elemento dentro de `FolderNode`.
- Se usa para explicar cómo la carpeta mantiene sus hijos sin usar estructuras de Java predefinidas.

---

## 2. Navegación, rutas y directorio actual

Estas clases explican cómo el usuario se mueve entre carpetas y cómo se obtiene la ruta actual del sistema.

### `FileSystem`
- Propósito: controlador principal del sistema de archivos.
- Responsabilidad: gestionar el directorio actual, crear carpetas y archivos, cambiar de ubicación con `cd`, mostrar contenido con `ls`, y devolver la ruta actual con `pwd`.
- Ejemplo de explicación: si se habla de cómo funciona `cd`, aquí están las operaciones principales que hacen que la navegación cambie de carpeta.

### `FolderNode` (de nuevo, como parte de la navegación)
- Propósito: también participa en la navegación porque permite localizar un hijo por nombre.
- Responsabilidad: al entrar a una carpeta, se consulta si existe esa ruta y se devuelve el nodo correspondiente.
- Ejemplo: cuando se usa `cd documents`, `FileSystem` pide a `FolderNode` si existe ese hijo.

### `FileSystemNode`
- Propósito: aporta la base para construir rutas.
- Responsabilidad: permite que cada nodo conozca su padre y construya su propia ruta dentro del árbol.
- Ejemplo: al mostrar la ruta actual, `FileSystem` usa esta información para representar la ubicación actual del usuario.

---

## 3. Historial de navegación (atrás y adelante)

Estas clases explican cómo el sistema recuerda dónde estuvo el usuario y permite regresar o avanzar entre carpetas.

### `FolderStack`
- Propósito: pila propia para el historial de navegación.
- Responsabilidad: guardar carpetas visitadas para poder volver atrás y avanzar hacia delante.
- Se usa para explicar `goBack()` y `goForward()` como una pila LIFO.

### `FolderStackNode`
- Propósito: nodo de la pila.
- Responsabilidad: enlazar cada carpeta dentro de `FolderStack`.
- Se usa para explicar la estructura interna del historial.

### `FileSystem`
- Propósito: coordina el uso del historial.
- Responsabilidad: al ejecutar `cd("..")`, `cd("/")` o los métodos de navegación, actualiza la pila de atrás y adelante.
- Ejemplo: si el usuario entra a una carpeta y luego quiere volver, `FileSystem` usa `FolderStack` para recordar la ubicación anterior.

---

## 4. Operaciones por lote

Estas clases explican cómo el proyecto puede guardar operaciones pendientes y ejecutarlas más adelante.

### `Operation`
- Propósito: describe una acción que se quiere ejecutar.
- Responsabilidad: guarda el tipo de operación, el objetivo y los datos extra necesarios.
- Se usa para explicar cómo se modela una instrucción como “crear una carpeta” o “crear un archivo”.

### `OperationQueue`
- Propósito: cola FIFO para tareas pendientes.
- Responsabilidad: almacenar operaciones en orden de llegada y extraer la primera cuando se desea ejecutar.
- Se usa para explicar el concepto de procesamiento por lotes.

### `OperationNode`
- Propósito: nodo interno de la cola.
- Responsabilidad: enlazar cada operación dentro de `OperationQueue`.

### `FileSystem`
- Propósito: ejecuta la cola de tareas.
- Responsabilidad: ofrece `enqueueOperation()` y `executeNextOperation()` para registrar y procesar operaciones pendientes.

---

## 5. Entrada de comandos del usuario

Esta parte explica cómo el usuario interactúa con el sistema mediante texto.

### `CommandProcessor`
- Propósito: recibir comandos escritos por el usuario.
- Responsabilidad: interpretar instrucciones como `mkdir`, `touch`, `cd`, `ls`, `pwd`, `back`, `forward`, `queue` y `run`, y delegarlas en `FileSystem`.
- Se usa para explicar cómo el sistema pasa de texto a acciones reales dentro del simulador.

### `FileSystem`
- Propósito: ejecutar las acciones indicadas por el procesador.
- Responsabilidad: realizar las operaciones reales sobre el árbol y sobre el historial.

---

## 6. Resumen de responsabilidad por parte del proyecto

- Árbol y nodos: `FileSystemNode`, `FolderNode`, `FileNode`, `ChildNode`
- Navegación y rutas: `FileSystem`, `FileSystemNode`, `FolderNode`
- Historial: `FolderStack`, `FolderStackNode`, `FileSystem`
- Operaciones por lote: `Operation`, `OperationQueue`, `OperationNode`, `FileSystem`
- Procesador de comandos: `CommandProcessor`, `FileSystem`
