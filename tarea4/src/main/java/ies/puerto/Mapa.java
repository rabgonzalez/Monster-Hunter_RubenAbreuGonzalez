package ies.puerto;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Mapa {
    private int size;
    private Personaje[][] ubicaciones;
    private final int PROBABILIDAD = 7;

    public Mapa() {
    }

    public Mapa(int size) {
        this.size = size;
        this.ubicaciones = new Personaje[size][size];
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Personaje[][] getUbicaciones() {
        return this.ubicaciones;
    }

    public void setUbicaciones(Personaje[][] ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public int getPROBABILIDAD(){
        return this.PROBABILIDAD;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Mapa)) {
            return false;
        }
        Mapa mapa = (Mapa) o;
        return size == mapa.size && Objects.equals(ubicaciones, mapa.ubicaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, ubicaciones);
    }

    @Override
    public String toString() {
        return "{" +
            " size='" + getSize() + "'" +
            ", ubicaciones='" + getUbicaciones() + "'" +
            "}";
    }

    public int[] generarUbicacionAleatoria() {
        Random random = new Random();
        int x = random.nextInt(this.size);
        int y = random.nextInt(this.size);

        return new int[]{x,y};
    }

    public boolean atacar(){
        Random random = new Random();
        int probabilidad = random.nextInt(10) + 1;

        return (probabilidad > getPROBABILIDAD());
    }

    public synchronized void generarCueva(Cueva cueva){
        getUbicaciones()[cueva.getPosicion()[0]][cueva.getPosicion()[1]] = cueva;
        cueva.setPosicion(cueva.getPosicion());
    }

    public synchronized void generarBonificador(int[] posicion, Bonificador bonificador){
        if(getUbicaciones()[posicion[0]][posicion[1]] != null){
            generarBonificador(posicion, bonificador);
            return;
        }
        getUbicaciones()[posicion[0]][posicion[1]] = bonificador;
        bonificador.setPosicion(posicion);
    }

    public synchronized void moverCazador(Cazador cazador, int[] nuevaPosicion){
        // Si el cazador ya tiene posicion, la eliminamos.
        if(cazador.getPosicion() != null){
            int[] posicionAnterior = cazador.getPosicion();
            getUbicaciones()[posicionAnterior[0]][posicionAnterior[1]] = null;
        }

        // Si en esa posición hay un monstruo, el cazador le intenta atacar.
        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Monstruo){
            Monstruo monstruo = (Monstruo) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            
            if(atacar()){
                monstruo.setVida(monstruo.getVida() - cazador.getDamage());

                // si el monstruo muere, el cazador obtiene una eliminacion
                if(monstruo.getVida() <= 0){
                    getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = null;
                    monstruo.setCazado(true);
                    Cazador.kills++;
                }
            }
        }

        // Si un cazador obtiene una bonificación, ese cazador obtendrá 50 más de daño por disparo
        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Bonificador){
            Bonificador bonificador = (Bonificador) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            cazador.setDamage(cazador.getDamage()+bonificador.getBonificacion());

            getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = null;

            System.out.println("un cazador ha cogido un bonificador!");
        }

        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Personaje){
            moverCazador(cazador, generarUbicacionAleatoria());
            return;
        }

        getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = cazador;
        cazador.setPosicion(nuevaPosicion);
    }

    public synchronized void moverMonstruo(Monstruo monstruo, int[] nuevaPosicion){
        // Si el monstruo ya tiene posicion, la eliminamos.
        if(monstruo.getPosicion() != null){
            int[] posicionAnterior = monstruo.getPosicion();
            getUbicaciones()[posicionAnterior[0]][posicionAnterior[1]] = null;
        }

        // Si el monstruo cae en una cueva, este intentará refugiarse en ella
        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Cueva){
            Cueva cueva = (Cueva) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            try{
                if(cueva.semaphore.tryAcquire(0, TimeUnit.SECONDS)){
                    new Thread(cueva).start();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        // Si un monstruo coge un bonificador, este obtendrá 50 de vida
        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Bonificador){
            Bonificador bonificador = (Bonificador) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            monstruo.setVida(monstruo.getVida()+bonificador.getBonificacion());

            getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = null;

            System.out.println("un Monstruo ha cogido un bonificador!");
        }

        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Personaje){
            moverMonstruo(monstruo, generarUbicacionAleatoria());
            return;
        }

        getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = monstruo;
        monstruo.setPosicion(nuevaPosicion);
    }

    public static void pintarMapa(Mapa mapa) throws InterruptedException{
        // Limpia la consola usando secuencias de escape ANSI
        //System.out.print("\033[H\033[2J");
        //System.out.flush();  // Asegura que se limpie inmediatamente

        Personaje[][] ubicaciones = mapa.getUbicaciones();

        for(int i = 0; i < ubicaciones.length; i++){
            for(int j = 0; j < ubicaciones.length; j++){
                if(ubicaciones[i][j] == null){
                    System.out.print(". ");
                } else {
                    System.out.print(ubicaciones[i][j].getNombre() + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}