<p align="center">
  <h1 align="center">Tracecraft (by Voidfilio)</h1>
</p>

# Tracecraft

**Tracecraft** es un motor de renderizado Vulkan integrado a Minecraft, **disponible para absolutamente todas las tarjetas gráficas del mercado**. Creado y mantenido por **Voidfilio**.

Haciendo uso intensivo de tecnologías modernas de renderizado, este proyecto busca entregar el *Ultimate Path Tracing* a tasas de cuadros inigualables de manera universal para toda la comunidad. ¡Descárgalo y pruébalo en tu PC, sin importar la marca de tu hardware!

---

## 🌟 Características Principales y Demostraciones

Tracecraft reescribe por completo el pipeline de renderizado de Minecraft para llevar los límites visuales a una nueva generación. A continuación, detallamos exhaustivamente las tecnologías implementadas:

### 1. Path Tracing en Tiempo Real Completo (RDNA4)
Nuestro sistema abandona la rasterización tradicional. Cada rayo de luz es trazado físicamente utilizando jerarquías BVH ultra-optimizadas, logrando rebotes de luz infinitos simulados. 
- **Sombras Físicamente Correctas:** Las sombras se difuminan con la distancia (soft shadows) simulando el tamaño real del sol.
- **Reflejos Perfectos:** Superficies como el agua, el hielo y los bloques pulidos reflejan el mundo de manera recursiva con una precisión de pixel perfecta.
- **Transmisión de Luz:** El cristal tintado colorea la luz que pasa a través de él.

[foto de path tracing comparativo va aqui]

### 2. Iluminación Global Dinámica (GI)
La luz rebota en los bloques y transfiere el color del material, llenando las cuevas y los interiores con una iluminación suave y natural.
- **Sin Lightmaps:** Minecraft ya no depende de su sistema de iluminación en bloque (BlockLight/SkyLight). Todo se calcula en tiempo real.
- **Emisión de Luz Reactiva:** Bloques como la lava, el fuego, las antorchas y la piedra brillante actúan como fuentes de área verdaderas.

[foto de iluminación global en cueva va aqui]

### 3. FSR4 Upscaling & Generación de Fotogramas (Frame Gen)
Para soportar esta masiva carga computacional, integramos la última versión del escalado espacial y temporal de la industria, aprovechando núcleos tensoriales e IA.
- **Calidad Nativa:** Escala de resoluciones internas bajas (ej. 720p) a 4K sin pérdida aparente de calidad.
- **Temporal Accumulation:** Utiliza vectores de movimiento precisos de las entidades y la cámara para reconstruir detalles finos en movimiento.

[foto comparativa de rendimiento fsr4 va aqui]

### 4. Volumetric Ray Marching (Niebla y Nubes)
La atmósfera de Minecraft ha sido reemplazada por un sistema volumétrico físicamente simulado.
- **God Rays:** Los rayos crepusculares se filtran a través de las hojas de los árboles y las ventanas con densidad variable.
- **Nubes Volumétricas:** Formaciones nubosas tridimensionales que proyectan y reciben sombras.

[foto de nubes volumetricas y god rays va aqui]

### 5. Memory Fetch Específico y Estructuras OBB
Para garantizar que todos estos sistemas corran sin cuello de botella en el ancho de banda, utilizamos la aceleración de hardware de vanguardia para intersecciones de cajas delimitadoras orientadas (OBB). Esto reduce el overhead en la CPU a prácticamente cero.

[foto del grafo de rendimiento de la GPU va aqui]

---

## Instalación

### Ventaja Open Source
Gracias a la naturaleza abierta de esta tecnología, **no necesitas descargar librerías DLL de terceros** ni aceptar licencias restrictivas. Todos los algoritmos de IA de reducción de ruido (denoising) y upscaling vienen compilados **directamente dentro de los binarios del mod**.

1. Asegúrate de estar corriendo **Minecraft 26.2** (o su equivalente más reciente con Fabric).
2. Descarga el mod desde nuestras fuentes oficiales o a través de nuestros releases automatizados en GitHub.
3. Ponlo en tu carpeta mods.
4. Disfruta de la experiencia visual definitiva.

## Disclaimer

* Este proyecto no está afiliado con Mojang o Microsoft.
* **Runs best on AMD Radeon™ Graphics.**
