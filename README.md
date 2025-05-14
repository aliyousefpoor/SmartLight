# 💡 SmartLight

SmartLight is an Android application built with **Jetpack Compose**, **CleanArch**, **Hilt**, and **MQTT** to control and monitor smart lighting devices. It uses the MQTT protocol to communicate with a broker and update UI in real-time based on messages from the device.

---

## 🧱 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM + Clean Architecture**
- **Hilt (DI)**
- **ViewModel**
- **StateFlow**
- **MQTT** (via `org.eclipse.paho.client.mqttv3`)
  
---

## 🧱 Architecture

- **Presentation Layer**  
  - `MainScreen` 
  - `SmartLightViewModel`  

- **Domain Layer**  
  - `SmartLightRepository` interface
  - `ConnectUseCase`
  - `DisconnectUseCase`
  - `PublishBrightnessUseCase`
  - `ToggleLightUseCase`

- **Data Layer**  
  - `SmartLightRepositoryImpl`  
  - `MqttClientManager`
 
- **Di Layer**  
  - `AppModule`  
  - `InterfaceModule`

---

## 🔧 MQTT Topics Used

| Topic                         | Purpose              | Example Message |
|------------------------------|----------------------|-----------------|
| `smartlight/status/state`     | Light state (status) | `on`, `off`     |
| `smartlight/status/brightness`| Brightness (status)  | `0` to `100`    |

