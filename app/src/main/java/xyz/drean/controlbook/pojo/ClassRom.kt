package xyz.drean.controlbook.pojo

class ClassRom {
    var id: String? = null
    var name: String? = null
    var seccion: String? = null
    var grado: String? = null
    var turno: String? = null

    constructor() {}

    constructor(id: String, name: String, seccion: String, grado: String, turno: String) {
        this.id = id
        this.name = name
        this.seccion = seccion
        this.grado = grado
        this.turno = turno
    }
}
