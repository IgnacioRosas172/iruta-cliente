package com.psp.i_rutacliente.providers

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.psp.i_rutacliente.models.Client

class ClientProvider {

    val db = Firebase.firestore.collection("Clients")

    fun crate(client: Client): Task<Void> {
        return db.document(client.id!!).set(client)
    }

    fun getClientById(id: String): Task<DocumentSnapshot> {
        return db.document(id).get()
    }

}