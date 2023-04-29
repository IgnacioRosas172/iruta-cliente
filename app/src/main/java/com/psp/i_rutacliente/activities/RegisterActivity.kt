package com.psp.i_rutacliente.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.psp.i_rutacliente.databinding.ActivityRegisterBinding
import com.psp.i_rutacliente.models.Client
import com.psp.i_rutacliente.providers.AuthProvider
import com.psp.i_rutacliente.providers.ClientProvider

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authProvider = AuthProvider()
    private val clientProvider = ClientProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding.btnGoToLogin.setOnClickListener { goToLogin() }
        binding.btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        val name = binding.textFieldName.text.toString()
        val lastname = binding.textFieldLastName.text.toString()
        val phone = binding.textFieldPhone.text.toString()
        val email = binding.textFieldEmail.text.toString()
        val password = binding.textFieldPassword.text.toString()
        val confirmPassword = binding.textFieldConfirmPassword.text.toString()

        if (isValidForm(name, lastname, phone, email, password, confirmPassword)) {
           authProvider.register(email, password).addOnCompleteListener {
               if (it.isSuccessful) {
                   val client = Client(
                       id = authProvider.getId(),
                       name = name,
                       lastname = lastname,
                       phone = phone,
                       email = email
                   )
                   clientProvider.crate(client).addOnCompleteListener {
                       if (it.isSuccessful) {
                           Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                           goToMap()
                       }else {
                           Toast.makeText(this@RegisterActivity, "Hubo un error almacenando los datos del usuario ${it.exception.toString()}", Toast.LENGTH_SHORT).show()
                           Log.d("FIREBASE", "Error: ${it.exception.toString()}")
                       }
                   }
               } else {
                   Toast.makeText(this@RegisterActivity, "Registro fallido ${it.exception.toString()}", Toast.LENGTH_LONG).show()
                   Log.d("FIREBASE", "Error: ${it.exception.toString()}")
               }
           }
        }

    }

    private fun goToMap() {
        val i = Intent(this, MapActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    private fun isValidForm(name: String, lastname: String, phone: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()){
            Toast.makeText(this,"Debes de ingresar tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lastname.isEmpty()){
            Toast.makeText(this,"Debes de ingresar tu apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.isEmpty()){
            Toast.makeText(this,"Debes de ingresar tu número de celular", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()){
            Toast.makeText(this,"Debes de ingresar tu email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this,"Debes de ingresar tu contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (confirmPassword.isEmpty()){
            Toast.makeText(this,"Debes de confirmar tu contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length <= 6) {
            Toast.makeText(this,"Las contraseñas dede de tener al menos 6 digitos", Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}