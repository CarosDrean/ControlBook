package xyz.drean.controlbook.pojo;

public class Asistant {
    private String id;
    private String name;
    private String lastname;
    private String dni;
    private String user;
    private String password;

    public Asistant() {
    }

    public Asistant(String id, String name, String lastname, String dni, String user, String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.user = user;
        this.password = password;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
