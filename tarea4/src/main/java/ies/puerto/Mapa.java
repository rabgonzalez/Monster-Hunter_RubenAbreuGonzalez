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

    public boolean atacar(Monstruo monstruo, Cazador cazador, int[] posicion){
        Random random = new Random();
        int probabilidad = random.nextInt(10) + 1;

        if(probabilidad > getPROBABILIDAD()){
            return false;
        }
        monstruo.setVida(monstruo.getVida()-cazador.getDamage());
        if(monstruo.getVida() <= 0){
            getUbicaciones()[posicion[0]][posicion[1]] = null;
        }
        return true;
    }

    public synchronized void generarCueva(Cueva cueva){
        if(getUbicaciones()[cueva.getPosicion()[0]][cueva.getPosicion()[1]]!= null){
            return;
        }

        getUbicaciones()[cueva.getPosicion()[0]][cueva.getPosicion()[1]] = cueva;
        cueva.setPosicion(cueva.getPosicion());
    }

    public synchronized void moverCazador(Cazador cazador, int[] nuevaPosicion){
        // Si el cazador ya tiene posicion, la eliminamos.
        if(cazador.getPosicion() != null){
            int[] posicionAnterior = cazador.getPosicion();
            getUbicaciones()[posicionAnterior[0]][posicionAnterior[1]] = null;
        }

        // Si en esa posición hay un monstruo y este muere, el cazador obtiene una kill y el monstruo es cazado.
        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Monstruo){
            Monstruo monstruo = (Monstruo) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            
            if(atacar(monstruo, cazador, nuevaPosicion)){
                monstruo.setCazado(true);
                Cazador.kills++;
            }
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

        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Cueva){
            Cueva cueva = (Cueva) getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]];
            try{
                if(cueva.semaphore.tryAcquire(0, TimeUnit.SECONDS)){
                    refugiarse(cueva, monstruo);
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        if(getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] instanceof Personaje){
            moverMonstruo(monstruo, generarUbicacionAleatoria());
            return;
        }

        getUbicaciones()[nuevaPosicion[0]][nuevaPosicion[1]] = monstruo;
        monstruo.setPosicion(nuevaPosicion);
    }

    public synchronized void refugiarse(Cueva cueva, Monstruo monstuo){
        try{
            cueva.semaphore.acquire();
            System.out.println("un monstruo se ha escondido en la cueva");

            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            cueva.semaphore.release();
        }
    }

    public static void pintarMapa(Mapa mapa) throws InterruptedException{
        // Limpia la consola usando secuencias de escape ANSI
        //System.out.print("\033[H\033[2J");
        //System.out.flush();  // Asegura que se limpie inmediatamente

        Personaje[][] ubicaciones = mapa.getUbicaciones();
        for(int i = 0; i < ubicaciones.length; i++){
            for(int j = 0; j <= ubicaciones.length-1; j++){
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