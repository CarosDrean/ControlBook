package xyz.drean.controlbook.pojo

class Student {

    var id: String? = null
    var name: String? = null
    var lastname: String? = null
    var dni: String? = null
    var idClassRom: String? = null
    var idParent: String? = null

    constructor() {}

    constructor(id: String, name: String, lastname: String, dni: String, idClassRom: String, idParent: String) {
        this.id = id
        this.name = name
        this.lastname = lastname
        this.dni = dni
        this.idClassRom = idClassRom
        this.idParent = idParent
    }
}
