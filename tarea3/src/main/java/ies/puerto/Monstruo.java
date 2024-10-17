package ies.puerto;

public class Monstruo extends Personaje implements Runnable{
    private boolean cazado = false;
    private static final long TIEMPOVIDA = 12000;

    public boolean isCazado() {
        return this.cazado;
    }

    public void setCazado(boolean cazado) {
        this.cazado = cazado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monstruo)) {
            return false;
        }
        Monstruo monstruo = (Monstruo) o;
        return cazado == monstruo.cazado;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
            " cazado='" + isCazado() + "'" +
            "}";
    }

    public Monstruo() {
    }

    public Monstruo(String nombre) {
        super(nombre);
    }

    public Monstruo(String nombre, Mapa mapa) {
        super(nombre, mapa);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int[] posicion = null;
        while(!isCazado() && (System.currentTimeMillis() - startTime < TIEMPOVIDA)){
            // Si en esa posición ya hay alguien, vuelve a moverse a una posición aleatoria
            posicion = getMapa().generarUbicacionAleatoria();
            getMapa().moverMonstruo(this, posicion);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getMapa().getUbicaciones()[getPosicion()[0]][getPosicion()[1]] = null;
        }
    }
}
