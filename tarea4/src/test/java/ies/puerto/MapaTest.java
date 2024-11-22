package ies.puerto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapaTest {
    Mapa mapa;
    Cazador cazador;
    Monstruo monstruo;
    Bonificador bonificador;
    int[] posicion;
    
    @BeforeEach
    void beforeEach(){
        mapa = new Mapa(5);
        cazador = new Cazador("h", mapa);
        monstruo = new Monstruo("m", mapa);
        bonificador = new Bonificador("B", mapa);
        posicion = mapa.generarUbicacionAleatoria();
    }

    @Test
    void moverCazadorTest(){
        mapa.moverCazador(cazador, posicion);

        Assertions.assertEquals(cazador, mapa.getUbicaciones()[posicion[0]][posicion[1]]);
    }

    @Test
    void moverCazadorMonstruoTest(){
        mapa.moverMonstruo(monstruo, posicion);

        Assertions.assertEquals(monstruo, mapa.getUbicaciones()[posicion[0]][posicion[1]]);
    }

    @Test
    void cazadorBonificacionTest(){
        mapa.generarBonificador(posicion, bonificador);

        int beforeDamage = cazador.getDamage();
        mapa.moverCazador(cazador, posicion);

        int expected = beforeDamage+bonificador.getBonificacion();

        Assertions.assertEquals(expected, cazador.getDamage());
    }

    @Test
    void monstruoBonificacionTest(){
        mapa.generarBonificador(posicion, bonificador);

        int beforeVida = monstruo.getVida();
        mapa.moverMonstruo(monstruo, posicion);

        int expected = beforeVida+bonificador.getBonificacion();

        Assertions.assertEquals(expected, cazador.getDamage());
    }
}
