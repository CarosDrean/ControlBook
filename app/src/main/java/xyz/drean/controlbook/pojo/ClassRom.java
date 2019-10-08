package xyz.drean.controlbook.pojo;

public class ClassRom {
    private String id;
    private String name;
    private String seccion;
    private String grado;
    private String turno;

    public ClassRom() {
    }

    public ClassRom(String id, String name, String seccion, String grado, String turno) {
        this.id = id;
        this.name = name;
        this.seccion = seccion;
        this.grado = grado;
        this.turno = turno;
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
