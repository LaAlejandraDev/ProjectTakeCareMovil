import com.google.gson.annotations.SerializedName

data class ChatAllDataModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("idPsicologo")
    val idPsychologist: Int,

    @SerializedName("idPaciente")
    val idPatient: Int,

    @SerializedName("creadoEn")
    val createdDate: String,

    @SerializedName("ultimoMensajeEn")
    val lastMessageDate: String?,

    @SerializedName("nombrePsicologo")
    val psychologistName: String?,

    @SerializedName("especialidad")
    val specialty: String?,

    @SerializedName("universidadEgreso")
    val university: String?,

    @SerializedName("nombrePaciente")
    val patientName: String?,

    @SerializedName("apellidosPaciente")
    val patientLastName: String?
)