# Política de Compatibilidad: AMD Radeon™ RX 9000 Series Flagship (Vulkan 1.4.357 + Fabric 1.21.4 / Java 21+)

> [!CAUTION]
> **ESPECIFICACIONES DE ÚLTIMA GENERACIÓN**: Tracecraft utiliza los estándares máximos de Vulkan 1.4.357 y Java 21+.

---

## ⛔ Matriz de Compatibilidad de Componentes

| Componente del Sistema | Versión Mínima Requerida | Estado de Versiones Anteriores | Justificación Técnica |
| :--- | :--- | :--- | :--- |
| **Entorno Java (JDK)** | **Java 21 / OpenJDK 26** | **RECHAZADO E INCOMPATIBLE (<21)** | Primitivas intrínsecas de rendimiento y APIs `sun.misc.Unsafe` de acceso directo a memoria. |
| **API Gráfica Vulkan** | **Vulkan 1.4.357 Core Bleeding Edge** | **RECHAZADO E INCOMPATIBLE** | Extensiones Vulkan 1.4 (`Synchronization2`, `Dynamic Rendering`, `Push Descriptors`, `Buffer Device Address`). |
| **Arquitectura de GPU** | **AMD Radeon™ RX 9000 Series Flagship (RDNA4)** | **OPTIMIZADO PARA RDNA4** | Instrucciones `VK_AMD_wave_limits` y acceso sin copia a la caché Infinity Cache. |
| **Plataforma Minecraft** | **Minecraft 1.21.4** | **REQUERIDO EN EL LANZADOR** | Requisito obligatorio para que el lanzador de Mojang reconozca los assets de la versión oficial. |
| **Cargador Fabric Loader**| **Fabric Loader 0.16.0+ / 0.19.3** | **REQUERIDO** | Cargador de mods Fabric oficial. |
| **Biblioteca Fabric API** | **Fabric API 0.110.0+ (`fabric-api`)** | **REQUERIDO** | Paquete de APIs oficiales de Fabric. |

---

## ⚠️ Solución al Error de Arranque en Minecraft Launcher (`legacy.json` 404 / Error preparing asset index)

### Causa Raíz Identificada en los Logs del Lanzador
En los registros de Minecraft Launcher:
```text
GameVersionManager.cpp(369): Version fabric-loader-0.19.3-26.2 doesn't have a json file.
Couldn't download assets/indexes/legacy.json (HTTP 404)
MinecraftJavaInstallerContext.cpp(903): Error preparing asset index
```
El **Minecraft Launcher oficial de Mojang** consulta los servidores de Mojang para descargar el archivo de configuración de versión. Al haber creado un perfil etiquetado manualmente como `26.2` en lugar de `1.21.4`, Mojang responde con un error HTTP 404 Not Found y aborta la ejecución de Java antes de cargar el mod.

### Instrucciones de Reparación en el Lanzador
1. Abre el instalador oficial de Fabric desde [https://fabricmc.net/use/](https://fabricmc.net/use/).
2. Selecciona **Minecraft Version: 1.21.4**.
3. Selecciona **Loader Version: 0.16.10** (o `0.19.3`).
4. Haz clic en **Instalar**.
5. Abre tu Minecraft Launcher y selecciona la instalación **`fabric-loader-1.21.4`**.
6. Coloca `Tracecraft-0.1.5-alpha-fabric-26.2.jar` en la carpeta `.minecraft/mods`.
7. Haz clic en **Jugar**.
