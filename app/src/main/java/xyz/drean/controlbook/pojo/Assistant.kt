package xyz.drean.controlbook.pojo

class Assistant {
    var id: String? = null
    var name: String? = null
    var lastname: String? = null
    var dni: String? = null
    var user: String? = null
    var password: String? = null

    constructor() {}

    constructor(
        id: String,
        name: String,
        lastname: String,
        dni: String,
        user: String,
        password: String
    ) {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.dni = dni
        this.user = user
        this.password = password
    }
}
