package xyz.drean.controlbook.pojo

class Asistant {
    private var id: String? = null
    private var name: String? = null
    private var lastname: String? = null
    private var dni: String? = null
    private var user: String? = null
    private var password: String? = null

    constructor()

    constructor(
        id: String?,
        name: String?,
        lastname: String?,
        dni: String?,
        user: String?,
        password: String?
    ) {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.dni = dni
        this.user = user
        this.password = password
    }

}