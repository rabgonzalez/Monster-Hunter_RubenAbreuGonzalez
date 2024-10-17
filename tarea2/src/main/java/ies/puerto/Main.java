package ies.puerto;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Mapa mapa = new Mapa(5);  
        Cazador cazador1 = new Cazador("1", mapa);
        Cazador cazador2 = new Cazador("2", mapa);
        Thread thread1 = new Thread(cazador1);   
        Thread thread2 = new Thread(cazador2);  
        
        mapa.generarMonstruo(new Monstruo("M"));
        mapa.generarMonstruo(new Monstruo("M"));
        mapa.generarMonstruo(new Monstruo("M"));
        mapa.generarMonstruo(new Monstruo("M"));
        mapa.generarMonstruo(new Monstruo("M"));
        mapa.generarMonstruo(new Monstruo("M"));

        thread1.start(); 
        thread2.start();        

        while(thread1.isAlive() && thread2.isAlive()){
            pintarMapa(mapa);
        }
    }

    public static void pintarMapa(Mapa mapa) throws InterruptedException{
        Personaje[][] ubicaciones = mapa.getUbicaciones();
        for(int i = 0; i < ubicaciones.length; i++){
            for(int j = 0; j <= ubicaciones.length-1; j++){
                if(ubicaciones[i][j] != null){
                    System.out.print(ubicaciones[i][j].getNombre() + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        Thread.sleep(1500);
    }
}