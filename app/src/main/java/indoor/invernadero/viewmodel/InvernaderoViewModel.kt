package indoor.invernadero.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.util.UUID
import javax.net.ssl.SSLSocketFactory

class InvernaderoViewModel : ViewModel() {

    private val _humedad = MutableStateFlow("--")
    val humedad: StateFlow<String> = _humedad.asStateFlow()

    private val _temperatura = MutableStateFlow("--")
    val temperatura: StateFlow<String> = _temperatura.asStateFlow()

    private val _ph = MutableStateFlow("--")
    val ph: StateFlow<String> = _ph.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _isRiegoActivo = MutableStateFlow(false)
    val isRiegoActivo: StateFlow<Boolean> = _isRiegoActivo.asStateFlow()

    private var mqttClient: MqttClient? = null

    init {
        connectMqtt()
    }

    fun connectMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // ISO 27400: Conexión segura cifrada con TLS en puerto SSL 8883
                val serverUri = "ssl://broker.hivemq.com:8883"
                val clientId = "InvernaderoClient_" + UUID.randomUUID().toString().take(8)
                val persistence = MemoryPersistence()

                val client = MqttClient(serverUri, clientId, persistence)
                mqttClient = client

                val options = MqttConnectOptions().apply {
                    isCleanSession = true
                    connectionTimeout = 10
                    keepAliveInterval = 30
                    socketFactory = SSLSocketFactory.getDefault()
                }

                client.setCallback(object : MqttCallback {
                    override fun connectionLost(cause: Throwable?) {
                        _isConnected.value = false
                    }

                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        val payload = message?.payload?.let { String(it) } ?: return
                        when (topic) {
                            "invernadero/sensores/humedad" -> _humedad.value = payload
                            "invernadero/sensores/temperatura" -> _temperatura.value = payload
                            "invernadero/sensores/ph" -> _ph.value = payload
                            "invernadero/actuadores/riego" -> _isRiegoActivo.value = (payload == "ON")
                        }
                    }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {}
                })

                client.connect(options)
                _isConnected.value = true

                // Subscripciones a sensores y actuadores
                client.subscribe("invernadero/sensores/humedad", 0)
                client.subscribe("invernadero/sensores/temperatura", 0)
                client.subscribe("invernadero/sensores/ph", 0)
                client.subscribe("invernadero/actuadores/riego", 0)

            } catch (e: Exception) {
                _isConnected.value = false
            }
        }
    }

    fun disconnectMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mqttClient?.disconnect()
                mqttClient?.close()
            } catch (_: Exception) {
            } finally {
                mqttClient = null
                _isConnected.value = false
            }
        }
    }

    fun publicarMensaje(topico: String, mensaje: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mqttClient?.publish(topico, MqttMessage(mensaje.toByteArray()))
                if (topico == "invernadero/actuadores/riego") {
                    _isRiegoActivo.value = (mensaje == "ON")
                }
            } catch (_: Exception) {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            mqttClient?.disconnect()
            mqttClient?.close()
        } catch (_: Exception) {}
    }
}
