# Política Cero Retrocompatibilidad: AMD Radeon™ RX 9000 Series Flagship (Vulkan 1.4.357 + Java 26)

> [!CAUTION]
> **CERO RETROCOMPATIBILIDAD**: Tracecraft ha eliminado por completo todo soporte hacia atrás para arquitecturas de hardware, versiones antiguas de Java, versiones obsoletas de Vulkan, versiones anteriores de Minecraft y versiones desactualizadas de Fabric Loader / Fabric API. Si se intenta ejecutar en un entorno que no sea el ecosistema insignia más reciente de última generación, **el mod rechazará la ejecución**.

---

## ⛔ Matriz de Eliminación Estricta de Retrocompatibilidad

| Componente del Sistema | Versión Mínima Requerida | Estado de Versiones Anteriores | Justificación Técnica |
| :--- | :--- | :--- | :--- |
| **Entorno Java (JDK)** | **Java 26 (OpenJDK 26.0.2)** | **RECHAZADO E INCOMPATIBLE** | Uso de primitivas intrínsecas de rendimiento de Java 26 y APIs `sun.misc.Unsafe` de acceso directo a memoria. |
| **API Gráfica Vulkan** | **Vulkan 1.4.357 Core Bleeding Edge** | **RECHAZADO E INCOMPATIBLE** | Requiere extensiones Vulkan 1.4 (`Synchronization2`, `Dynamic Rendering`, `Push Descriptors`, `Buffer Device Address`). |
| **Arquitectura de GPU** | **AMD Radeon™ RX 9000 Series Flagship (RDNA4)** | **RECHAZADO E INCOMPATIBLE** | Optimizado con instrucciones `VK_AMD_wave_limits` y acceso sin copia a la caché L3/L4 Infinity Cache. |
| **Plataforma Minecraft** | **Minecraft 26.2** | **RECHAZADO E INCOMPATIBLE** | Pipeline de renderizado de vanguardia integrado directamente sobre la arquitectura de desofuscación nativa 26.2. |
| **Cargador Fabric Loader** | **Fabric Loader 0.18.3+** | **RECHAZADO E INCOMPATIBLE** | Dependencia obligatoria de la última versión del ecosistema de carga de Fabric. |
| **Biblioteca Fabric API** | **Fabric API 0.119.4+ (`fabric-api`)** | **RECHAZADO E INCOMPATIBLE** | Dependencia estricta del conjunto de APIs oficiales de la última revisión. |

---

## ⚡ Especificaciones Exclusivas del Entorno Insignia

1. **Java 26 Direct Memory Access**:
   Ejecución forzada sobre OpenJDK 26 (`>=26`). Desactiva la verificación de bytecode antiguo y maximiza el rendimiento con optimizaciones JIT de última generación.

2. **Vulkan 1.4.357 Core Specification**:
   Elimina la compatibilidad con Vulkan 1.0, 1.1, 1.2 y 1.3. Cero fallback por software o pipelines heredados (`VkRenderPass`/`VkFramebuffer` obsoletos eliminados).

3. **Insignia AMD RDNA4 Flagship Tier**:
   Instrucciones de Ray Tracing directas para CUs RDNA4 en Wave64 sin sobrecarga de emulación.
