<p align="center">
  <h1 align="center">Tracecraft (by Voidfilio)</h1>
</p>

| English | [简体中文](https://github.com/Voidfilio/tracecraft/blob/main/README-CN.md) |

# Tracecraft

**Tracecraft** es un motor de renderizado Vulkan integrado a Minecraft, **construido EXCLUSIVAMENTE para la arquitectura AMD RDNA4**. Creado y mantenido por **Voidfilio**.

Haciendo uso intensivo de **FSR4** y estructuras de aceleración de hardware nativas de AMD (OBB, memory fetch específico), este proyecto busca entregar el *Ultimate Path Tracing* a tasas de cuadros inigualables. 

## La Filosofía "Portal RTX"

A diferencia de otros mods genéricos, Tracecraft no bloquea explícitamente a las tarjetas gráficas NVIDIA (RTX) o arquitecturas AMD antiguas. En su lugar, el pipeline está tan fuertemente optimizado para las estructuras de IA (FP8) y de jerarquía BVH de RDNA4, que cualquier otra arquitectura tendrá que emularlo por software. 

**Resultado esperado:** Si intentas correr esto en una RTX 5090, obtendrás menos de 5 FPS. *Así como nos excluyeron con RTX, hoy excluimos con procesamiento real.*

## Instalación

### Ventaja Open Source (RDNA / FSR4)
Gracias a la naturaleza abierta de la tecnología de AMD, **no necesitas descargar librerías DLL de terceros** ni aceptar licencias restrictivas. Todo el poder de FSR4 y los algoritmos de IA de reducción de ruido (denoising) vienen compilados **directamente dentro de los binarios del mod**.

1. Descarga el mod desde nuestras fuentes oficiales.
2. Ponlo en tu carpeta mods.
3. Disfruta de la dominación absoluta de RDNA4.

## Disclaimer

* NO AFILIADO A NVIDIA. **Tracecraft es una declaración a favor del Open Source y el hardware AMD.**
