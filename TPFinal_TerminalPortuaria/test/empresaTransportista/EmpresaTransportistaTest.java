package empresaTransportista;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpresaTransportistaTest {
	
	EmpresaTransportista empT;
	
	@BeforeEach
	void setUp() {
		empT = new EmpresaTransportista();
	}
	
	@Test 
	void verificarRegistroDeCamionesTest() {
		Camion camion1 = mock(Camion.class);
		Camion camion2 = mock(Camion.class);
		
		empT.registrarCamion(camion1);
		empT.registrarCamion(camion2);
		
		List<Camion> resultado = empT.getCamiones();
		assertEquals(2,empT.getCamiones().size());
		assertTrue(resultado.contains(camion1));
		assertTrue(resultado.contains(camion2));
	}

	@Test
	void verificarRegistroDeCamionTest() {
		Camion camion1 = mock(Camion.class);
		Camion camion2 = mock(Camion.class);
		
		//Configuro el comportamiento del mock
		
		when(camion1.getPatente()).thenReturn("asd123");
		when(camion2.getPatente()).thenReturn("zzz555");
		
		empT.registrarCamion(camion1);
		
		boolean resultado = empT.estaRegistradoElCamion(camion1); 
		//Retorno True si esta registrado
		boolean resultado1 = empT.estaRegistradoElCamion(camion2);
		//Retorna False porque no hay ningun camion registrado con esa patente
		
		assertTrue(resultado);
		assertFalse(resultado1);
		
		//Verificar que se llamo al metetodo getPatente()
		
		verify(camion1, atLeastOnce()).getPatente();
	}
	
	@Test
    void testEstaRegistradoElCamion_CamionNoRegistrado() {
        // Given
		Camion camion1 = mock(Camion.class);
		Camion camion2 = mock(Camion.class);
		
        empT.registrarCamion(camion1);
        when(camion1.getPatente()).thenReturn("ABC123");
        when(camion2.getPatente()).thenReturn("XYZ789"); // Patente diferente

        // When
        boolean resultado = empT.estaRegistradoElCamion(camion2);

        // Then
        assertFalse(resultado);
        verify(camion1).getPatente();
        verify(camion2).getPatente();
    }
	
	@Test
    void testEstaRegistradoElChofer_ChoferRegistrado() {
        // Given
		Camion camion1 = mock(Camion.class);
		Chofer chofer1 = mock(Chofer.class);
        empT.registrarCamion(camion1);
        when(camion1.estaRegistradoElChofer(chofer1)).thenReturn(true);

        // When
        boolean resultado = empT.estaRegistradoElChofer(chofer1);

        // Then
        assertTrue(resultado);
        verify(camion1).estaRegistradoElChofer(chofer1);
    }
	
	@Test
    void testEstaRegistradoElChofer_ChoferNoRegistrado() {
        // Given
		Camion camion1 = mock(Camion.class);
		Chofer chofer1 = mock(Chofer.class);
        empT.registrarCamion(camion1);
        when(camion1.estaRegistradoElChofer(chofer1)).thenReturn(false);

        // When
        boolean resultado = empT.estaRegistradoElChofer(chofer1);

        // Then
        assertFalse(resultado);
        verify(camion1).estaRegistradoElChofer(chofer1);
    }
	
	@Test
    void testEstaRegistradoElChofer_MultiplesCamiones() {
        // Given
		Camion camion1 = mock(Camion.class);
		Camion camion2 = mock(Camion.class);
		Chofer chofer1 = mock(Chofer.class);
        empT.registrarCamion(camion1);
        empT.registrarCamion(camion2);
        when(camion1.estaRegistradoElChofer(chofer1)).thenReturn(false);
        when(camion2.estaRegistradoElChofer(chofer1)).thenReturn(true); // Este tiene el chofer

        // When
        boolean resultado = empT.estaRegistradoElChofer(chofer1);

        // Then
        assertTrue(resultado);
        verify(camion1).estaRegistradoElChofer(chofer1);
        verify(camion2).estaRegistradoElChofer(chofer1);
    }


}
