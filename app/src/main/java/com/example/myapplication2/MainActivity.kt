package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication2.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= Firebase.auth

        binding.btnregistrar.setOnClickListener(){
            haceRegistrar()
        }
        binding.btnlogin.setOnClickListener(){
            haceLogin()
        }





    }

    private fun haceRegistrar() {
        var email = binding.txtcorreo.text.toString()
        var contrasena=binding.txtcontraseA.text.toString()


        //se hace registro
        auth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Log.d("creando usuario","registrado")
                val user= auth.currentUser
                actualiza(user)
            }else{
                Log.d("creando usuario","fallo")
                Toast.makeText(baseContext, "fallo en la creacion del usuario",Toast.LENGTH_LONG)
                actualiza(null)
            }
        }
    }

    private fun haceLogin() {
        val email=binding.txtcorreo.text.toString()
        val clave= binding.txtcontraseA.text.toString()

        auth.signInWithEmailAndPassword(email,clave).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                Log.d("autenticado","autenticados")
                val user=auth.currentUser
                actualiza(user)
            }else{
                Log.d("autenticado fallo","autenticado fallido")
                Toast.makeText(baseContext,"fallo",Toast.LENGTH_LONG)
                actualiza(null)
            }
        }
    }

    private fun actualiza(user: FirebaseUser?) {
        if(user != null){
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        }

    }

    //esto hara que una vez autenticado
    public override fun onStart() {
        super.onStart()
        val usuario= auth.currentUser
        actualiza(usuario)
    }




}