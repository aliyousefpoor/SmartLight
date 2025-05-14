# 💡 SmartLight

SmartLight is an Android application that connects to an MQTT broker (such as [Mosquitto](https://mosquitto.org)) to control and monitor smart lighting devices. The app enables real-time light toggling and brightness control via MQTT topics.

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
  - MainScreen 
  - `SmartLightViewModel`  

- **Domain Layer**  
  - `SmartLightRepository` interface
  - ConnectUseCase
  - DisconnectUseCase
  - PublishBrightnessUseCase
  - ToggleLightUseCase

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

