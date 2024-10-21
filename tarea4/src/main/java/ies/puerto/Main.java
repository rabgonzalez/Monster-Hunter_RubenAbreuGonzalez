package ies.puerto;

public class Main {
    private static final long TIEMPOPARTIDA = 15000;

    public static void main(String[] args) throws InterruptedException{
        Mapa mapa = new Mapa(5); 
        int monstruos = 5;
        
        Cazador cazador1 = new Cazador("H", mapa);
        Cazador cazador2 = new Cazador("H", mapa);

        Cueva cueva = new Cueva("C", mapa, mapa.generarUbicacionAleatoria(), monstruos);

        mapa.generarCueva(cueva);
        mapa.generarBonificador();

        for (int i = 0; i < monstruos; i++) {
            new Thread(new Monstruo("m", mapa)).start();
        }

        Thread thread1 = new Thread(cazador1);   
        Thread thread2 = new Thread(cazador2); 

        long startTime = System.currentTimeMillis();
        thread1.start(); 
        thread2.start();      

        while(thread1.isAlive() && (System.currentTimeMillis() - startTime < TIEMPOPARTIDA)){
            mapa.pintarMapa(mapa);
            System.out.println("Monstruos cazados: "+Cazador.kills);

            if(cazador1.kills == monstruos){
                System.out.println("todos los monstruos han sido capturados");
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println("\rFin de la partida");
    }
}