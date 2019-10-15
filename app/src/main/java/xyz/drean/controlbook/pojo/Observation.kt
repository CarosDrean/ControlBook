package xyz.drean.controlbook.pojo

class Observation {

    var id: String? = null
    var observation: String? = null
    var type: String? = null
    var date: String? = null
    var idStudent: String? = null
    var assistance: String? = null

    constructor() {}

    constructor(
        id: String?,
        observation: String?,
        type: String?,
        date: String?,
        idStudent: String?,
        assistance: String?
    ) {
        this.id = id
        this.observation = observation
        this.type = type
        this.date = date
        this.idStudent = idStudent
        this.assistance = assistance
    }


}