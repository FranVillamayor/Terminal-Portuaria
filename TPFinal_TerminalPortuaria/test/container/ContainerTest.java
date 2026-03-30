package container;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerTest {
    Container container;
    Dry dry;
    Reefer reefer;
    Tanque tanque;
    DryComposite dryComposite;

    @BeforeEach
    public void setUp() {
        container = new Container("RODR1234567", 60 , 300 , 70 , 500 , true);
        dry = new Dry("MANU1234567", 50 , 200 , 50 , 200 );
        reefer = new Reefer("FRAN1234567", 30 , 200 , 60 , 500,false,3.0);
        tanque = new Tanque("ELIAS123467", 40 , 200 , 60 , 500, true);
        dryComposite = new DryComposite("COMP1234567", 50,400, 600, 1000 );
    }

    @Test
    void containerReeferTest() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 1, 1, 20, 0, 0);

        reefer.conectar(inicio);
        reefer.desconectar(fin);

        assertEquals(10.0, reefer.getHorasConectado());
        assertEquals(30.0, reefer.getHorasConectado() * reefer.getConsumoPorHora());
    }

    @Test
    public void verificoQueCuandoSeCreaContainerTieneTodasSusDimencionesDeclaradas() {
        assertEquals("RODR1234567", container.getId());
        assertEquals(70, container.getAltura());
        assertEquals(300, container.getLargo());
        assertEquals(60, container.getAncho());
        assertEquals(500, container.pesoTotal());
        assertTrue(container.isServicioEspecial());
    }

    @Test 
    public void verificoQueCuandoSeCreaContainerDryTieneTodasSusDimencionesDeclaradas() {
        assertEquals("MANU1234567", dry.getId());
        assertEquals(50, dry.getAltura());
        assertEquals(200, dry.getLargo());
        assertEquals(50, dry.getAncho());
        assertTrue(dry.isServicioEspecial());
    }

    @Test
    public void verificoQueCuandoSeCreaContainerTanqueTieneTodasSusDimencionesDeclaradas() {
        assertEquals("ELIAS123467", tanque.getId());
        assertEquals(40, tanque.getAncho());
        assertEquals(200, tanque.getLargo());
        assertEquals(60, tanque.getAltura());
        assertEquals(500, tanque.pesoTotal());
        assertTrue(tanque.isServicioEspecial());
    }

    @Test
    public void verificoQueCuandoSeCreaContainerReeferTieneTodasSusDimencionesDeclaradas() {
        assertEquals("FRAN1234567", reefer.getId());
        assertEquals(30, reefer.getAncho());
        assertEquals(200, reefer.getLargo());
        assertEquals(60, reefer.getAltura());
        assertEquals(500, reefer.pesoTotal());
        assertFalse(reefer.isServicioEspecial());
    }

    @Test
    public void pesoTotalDeDrys() {
        dryComposite.add(dry);
        dryComposite.add(dry);
        assertEquals(1400.0, dryComposite.pesoTotal());
    }
}
