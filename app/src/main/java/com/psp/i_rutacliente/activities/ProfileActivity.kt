package com.psp.i_rutacliente.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.psp.i_rutacliente.databinding.ActivityProfileBinding
import com.psp.i_rutacliente.models.Client
import com.psp.i_rutacliente.providers.AuthProvider
import com.psp.i_rutacliente.providers.ClientProvider
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    val clientProvider = ClientProvider()
    val authProvider = AuthProvider()

    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        getClient()
        binding.imageViewBack.setOnClickListener { finish() }

    }




    private fun getClient() {
        clientProvider.getClientById(authProvider.getId()).addOnSuccessListener { document ->
            if (document.exists()) {
                val client = document.toObject(Client::class.java)
                binding.textViewEmail.text = client?.email
                binding.textFieldName.setText(client?.name)
                binding.textFieldLastname.setText(client?.lastname)
                binding.textFieldPhone.setText(client?.phone)

            }
        }
    }





}