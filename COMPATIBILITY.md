# Especificación de Hardware: AMD Radeon™ RX 9000 Series Flagship (Vulkan 1.4.357)

> [!IMPORTANT]
> **Optimización Exclusiva para la Flagship AMD Radeon™ RX 9000 Series (RDNA4)**: Tracecraft ha sido diseñado, compilado y calibrado específicamente para exprimir el 100% de la capacidad de cómputo, pipelines de Ray Tracing BVH nativo y el subsistema de memoria coherente L3/L4 Infinity Cache de la tarjeta gráfica **AMD Radeon™ RX 9000 Series Flagship** sobre la API **Vulkan 1.4.357**.

---

## 1. Matriz de Compatibilidad de Arquitecturas y Niveles de Desempeño

| Arquitectura / Modelo de GPU | Nivel de Soporte | Modo de Ejecución | Ray Tracing BVH Nativo | Control SIMD Wave64 RDNA4 | Coherencia L3/L4 Directa |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **AMD Radeon™ RX 9000 Series Flagship (RDNA4 Flagship Tier)** | **Nativo Máximo (Ultimate Flagship)** | **Aceleración Hardware Total** | **Sí (Instrucciones Directas HW)** | **Sí (Wave64 RDNA4 Dynamic)** | **Sí (Zero-Copy Infinity Cache)** |
| **AMD Radeon™ RX 9000 Series (Mid-Range)** | Nativo Estándar | Aceleración por Hardware | Sí | Parcial | Parcial |
| **AMD RDNA3 (Radeon RX 7000 Series)** | No Soportado Nativo | Emulación por Software | No (Software Fallback) | No | No |
| **AMD RDNA2 (Radeon RX 6000 Series)** | No Soportado Nativo | Emulación por Software | No (Software Fallback) | No | No |
| **NVIDIA GeForce RTX (Serie 20/30/40/50)** | No Soportado Nativo | Emulación por Software | No (Software Fallback) | No | No |
| **Intel Arc (A-Series / B-Series)** | No Soportado Nativo | Emulación por Software | No (Software Fallback) | No | No |

---

## 2. Optimizaciones Específicas para la Flagship AMD RX 9000 Series

> [!TIP]
> Las siguientes características de arquitectura RDNA4 Flagship son aprovechadas al máximo por Tracecraft bajo **Vulkan 1.4.357**:

1. **Aceleración Hardware BVH4 & OBB Ray Tracing**:
   Los Compute Units (CUs) de la insignia RX 9000 ejecutan traversals de volumen orientado (OBB) y rebotes de luz en tiempo real sin sobrecargar los procesadores de sombreado.

2. **Ejecución Masiva Wave64 Dynamic (`VK_AMD_wave_limits`)**:
   El motor fuerza al planificador de la GPU a operar en paquetes Wave64 para maximizar el paralelismo SIMD del núcleo de la RX 9000 Flagship durante los pases de Ray Tracing y FSR4.

3. **Zero-Copy L3/L4 Coherent Transfer (`VK_AMD_device_coherent_memory`)**:
   La comunicación entre el motor del mod y la memoria de la GPU omite los búferes intermedios de RAM del sistema, utilizando transferencia directa a la caché Infinity Cache de la serie 9000 Flagship.

4. **Descriptores Directos (`VK_KHR_push_descriptor`) & Punteros 64-bit (`VK_KHR_buffer_device_address`)**:
   Directamente especificados bajo la revisión Vulkan 1.4.357 para cero latencia de despacho.

---

## 3. Requisitos del Sistema para la Flagship

* **GPU Objetivo**: **AMD Radeon™ RX 9000 Series Flagship** (RDNA4 Flagship Tier).
* **Driver Requerido**: AMD Software: Adrenalin Edition™ 26.1.1 o superior con soporte nativo de Vulkan 1.4.357.
* **Entorno Java**: OpenJDK 25 / OpenJDK 26 (64-bit).
* **API Gráfica**: Vulkan 1.4.357 API Specification.
