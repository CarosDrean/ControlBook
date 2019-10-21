package xyz.drean.controlbook.pojo

class Meeting {

    var id: String? = null
    var text: String? = null
    var date: String? = null

    constructor() {}

    constructor(
        id: String,
        text: String,
        date: String
    ) {
        this.id = id
        this.text = text
        this.date = date
    }
}