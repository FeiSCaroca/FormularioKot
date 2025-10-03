package com.example.formulario.viewmodel

import androidx.lifecycle.ViewModel
import com.example.formulario.model.UsuarioErrores
import com.example.formulario.model.UsuarioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUIState())

    val estado: StateFlow<UsuarioUIState> = _estado

    fun onNombreChange (valor : String){
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange (valor: String){
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange (valor: String){
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccion (valor: String){
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange (valor: Boolean){
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean{

        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if(estadoActual.nombre.isBlank())"No puede estar vacio el nombre" else null,
            correo = if(!estadoActual.correo.contains("@"))"El correo tiene que tener @ (CORREO INVALIDO)" else null,
            clave = if(estadoActual.clave.length<8)"LA CLAVE TIENE QUE TENER ALMENOS 8 CARACTERES" else null,
            direccion = if(!estadoActual.direccion.isBlank())"La direccion no puede estar vacia" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()
        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }
}