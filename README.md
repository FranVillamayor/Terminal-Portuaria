# Sistema de Gestión de Terminal Portuaria (TP Final - POO2 - UNQ)
Desarrolladores: Rodrigo Bornia - Emmanuel Menager - Franco Villamayor

Este proyecto es una aplicación robusta desarrollada en **Java** para la gestión integral de operaciones en una terminal portuaria. 
El sistema modela la complejidad de la logística marítima, desde el arribo de buques hasta la facturación de servicios especializados.

## 🚀 Características Principales
* **Gestión de Buques y Camiones:** Registro y control de flujos de transporte.
* **Administración de Contenedores:** Clasificación (Dry, Reefer, Tanque) y manejo de cargas.
* **Asignación de Muelles y Turnos:** Lógica para optimizar el atraque y la carga/descarga.
* **Módulo de Facturación:** Cálculo automático de tarifas de servicios (almacenaje, electricidad, pesaje) y penalizaciones.
* **Gestión de Circuitos:** Control de ingreso y egreso con validaciones de seguridad.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 8+
* **Arquitectura:** Programación Orientada a Objetos (POO).
* **Principios de Diseño:** Aplicación de **SOLID** para un código mantenible y escalable.
* **Testing:** Pruebas unitarias con **JUnit**.
* **Gestión de Dependencias:** Mockito.
* **Versionado:** Git / GitHub.

## 🏗️ Arquitectura y Patrones
En este proyecto hicimos hincapié en la calidad del software aplicando:
* **Patrón Strategy/State:** Para el manejo de los diferentes estados de los buques y tipos de servicios.
* **Inyección de Dependencias:** Para facilitar el testeo y reducir el acoplamiento.
* **Validaciones Robustas:** Asegurando que las reglas de negocio (ej. prioridad de atraque) se cumplan estrictamente.

## 📦 Instalación y Ejecución
1. Clona el repositorio:
   ```bash
   git clone [https://github.com/FranVillamayor/TP-Terminal-Portuaria.git](https://github.com/FranVillamayor/TP-Terminal-Portuaria.git)
