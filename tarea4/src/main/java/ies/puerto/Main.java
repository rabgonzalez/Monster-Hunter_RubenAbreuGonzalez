package ies.puerto;

public class Main {
    private static final long TIEMPOPARTIDA = 15000;

    public static void main(String[] args) throws InterruptedException{
        Mapa mapa = new Mapa(4); 

        Cueva cueva = new Cueva("C", mapa, mapa.generarUbicacionAleatoria());
        mapa.generarCueva(cueva);
        
        Cazador cazador1 = new Cazador("H", mapa);
        Cazador cazador2 = new Cazador("H", mapa);
        Monstruo monstruo1 = new Monstruo("M", mapa);
        Monstruo monstruo2 = new Monstruo("M", mapa);
        Monstruo monstruo3 = new Monstruo("M", mapa);

        Thread thread1 = new Thread(cazador1);   
        Thread thread2 = new Thread(cazador2); 
        Thread thread3 = new Thread(monstruo1);
        Thread thread4 = new Thread(monstruo2);
        Thread thread5 = new Thread(monstruo3);

        long startTime = System.currentTimeMillis();
        thread1.start(); 
        thread2.start();  
        thread3.start();
        thread4.start();
        thread5.start();      

        while((thread1.isAlive() && thread2.isAlive()) && (System.currentTimeMillis() - startTime < TIEMPOPARTIDA)){
            mapa.pintarMapa(mapa);
            System.out.println("Monstruos cazados: "+Cazador.kills);
            Thread.sleep(1000);
        }
        System.out.println("\rFin de la partida");
    }
}