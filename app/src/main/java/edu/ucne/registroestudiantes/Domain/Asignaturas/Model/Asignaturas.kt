package edu.ucne.registroestudiantes.Domain.Asignaturas.Model

data class Asignaturas(
    val asignaturaId: Int=0,
    val codigo: Int,
    val nombre: String,
    val aula: Int,
    val creditos: Int

) {
}
