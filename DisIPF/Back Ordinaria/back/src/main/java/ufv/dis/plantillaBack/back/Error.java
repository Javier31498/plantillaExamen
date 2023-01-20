package ufv.dis.plantillaBack.back;

public class Error {
    private int id;
    private String mensaje_error;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje_error() {
        return mensaje_error;
    }

    public void setMensaje_error(String mensaje_error) {
        this.mensaje_error = mensaje_error;
    }

    public Error(int id, String mensaje_error) {
        this.id = id;
        this.mensaje_error = mensaje_error;
    }

    public Error() {
    }

    @Override
    public String toString() {
        return "Error{" +
                "id=" + id +
                ", mensaje_error='" + mensaje_error + '\'' +
                '}';
    }
}
