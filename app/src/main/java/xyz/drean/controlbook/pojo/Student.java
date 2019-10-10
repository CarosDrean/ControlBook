package xyz.drean.controlbook.pojo;

public class Student {

    private String id;
    private String name;
    private String lastname;
    private String dni;
    private String idClassRom;

    public Student() {
    }

    public Student(String id, String name, String lastname, String dni, String idClassRom) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.idClassRom = idClassRom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getIdClassRom() {
        return idClassRom;
    }

    public void setIdClassRom(String idClassRom) {
        this.idClassRom = idClassRom;
    }
}
