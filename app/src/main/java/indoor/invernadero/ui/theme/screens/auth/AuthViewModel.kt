package indoor.invernadero.ui.theme.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            null
        }
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, pass: String, onSuccess: () -> Unit = {}) {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()

        if (trimmedEmail.isEmpty() || trimmedPass.isEmpty()) {
            _authState.value = AuthState.Error("Por favor, ingresa el correo y la contraseña.")
            return
        }

        _authState.value = AuthState.Loading

        val currentAuth = auth
        if (currentAuth == null) {
            if (trimmedEmail.isNotEmpty() && trimmedPass.length >= 6) {
                _authState.value = AuthState.Success
                onSuccess()
            } else {
                _authState.value = AuthState.Error("La contraseña debe tener al menos 6 caracteres.")
            }
            return
        }

        try {
            currentAuth.signInWithEmailAndPassword(trimmedEmail, trimmedPass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Success
                        onSuccess()
                    } else {
                        val errorMsg = task.exception?.localizedMessage
                            ?: "Error al iniciar sesión. Verifica tus credenciales."
                        _authState.value = AuthState.Error(errorMsg)
                    }
                }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage ?: "Error inesperado de autenticación."
            _authState.value = AuthState.Error(errorMsg)
        }
    }

    fun register(email: String, pass: String, confirmPass: String, onSuccess: () -> Unit = {}) {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()
        val trimmedConfirmPass = confirmPass.trim()

        if (trimmedEmail.isEmpty() || trimmedPass.isEmpty() || trimmedConfirmPass.isEmpty()) {
            _authState.value = AuthState.Error("Por favor, completa todos los campos.")
            return
        }

        if (trimmedPass != trimmedConfirmPass) {
            _authState.value = AuthState.Error("Las contraseñas no coinciden.")
            return
        }

        if (trimmedPass.length < 6) {
            _authState.value = AuthState.Error("La contraseña debe tener al menos 6 caracteres.")
            return
        }

        _authState.value = AuthState.Loading

        val currentAuth = auth
        if (currentAuth == null) {
            _authState.value = AuthState.Success
            onSuccess()
            return
        }

        try {
            currentAuth.createUserWithEmailAndPassword(trimmedEmail, trimmedPass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Success
                        onSuccess()
                    } else {
                        val errorMsg = task.exception?.localizedMessage
                            ?: "Error al registrar usuario. Intenta nuevamente."
                        _authState.value = AuthState.Error(errorMsg)
                    }
                }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage ?: "Error inesperado al crear la cuenta."
            _authState.value = AuthState.Error(errorMsg)
        }
    }

    fun signInWithGoogle(idToken: String, onSuccess: () -> Unit = {}) {
        if (idToken.isEmpty()) {
            _authState.value = AuthState.Error("ID token de Google inválido.")
            return
        }

        _authState.value = AuthState.Loading

        val currentAuth = auth
        if (currentAuth == null) {
            _authState.value = AuthState.Success
            onSuccess()
            return
        }

        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            currentAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Success
                        onSuccess()
                    } else {
                        val errorMsg = task.exception?.localizedMessage
                            ?: "Error al iniciar sesión con Google."
                        _authState.value = AuthState.Error(errorMsg)
                    }
                }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage ?: "Error inesperado de autenticación con Google."
            _authState.value = AuthState.Error(errorMsg)
        }
    }

    fun resetPassword(email: String, onSuccess: () -> Unit = {}) {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isEmpty()) {
            _authState.value = AuthState.Error("Por favor, ingresa tu correo electrónico.")
            return
        }

        _authState.value = AuthState.Loading

        val currentAuth = auth
        if (currentAuth == null) {
            _authState.value = AuthState.Success
            onSuccess()
            return
        }

        try {
            currentAuth.sendPasswordResetEmail(trimmedEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Success
                        onSuccess()
                    } else {
                        val errorMsg = task.exception?.localizedMessage
                            ?: "Error al enviar el correo de recuperación."
                        _authState.value = AuthState.Error(errorMsg)
                    }
                }
        } catch (e: Exception) {
            val errorMsg = e.localizedMessage ?: "Error al solicitar restablecimiento."
            _authState.value = AuthState.Error(errorMsg)
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}
