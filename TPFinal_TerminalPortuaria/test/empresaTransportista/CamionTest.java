package empresaTransportista;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CamionTest {
	
	Camion camion;
	
	@Test 
	void verificarPatente() {
		Chofer chofer = mock(Chofer.class);
		camion = new Camion("jkl456", chofer);
		
		assertEquals("jkl456", camion.getPatente());
	}

	@Test
	void verificarChoferTest() {
		Chofer chofer = mock(Chofer.class);
		
		camion = new Camion("asd123",chofer);
		//Configuro el comportamiento del mock
		
		when(chofer.getDni()).thenReturn(38689123);
		
		boolean resultado = camion.estaRegistradoElChofer(chofer);
		//deberia ser true ya que esta registrado
		
		assertTrue(resultado);
		
		//Verificar que se llamo al metetodo getDni()
		
		verify(chofer, atLeastOnce()).getDni();
		
	}
	
	@Test
	void verificarChoferIncorrecto(){
		
		Chofer chofer = mock(Chofer.class);
		Chofer chofer1 = mock(Chofer.class);
		
		camion = new Camion("asd123",chofer);
		//Configuro el comportamiento del mock
		
		when(chofer.getDni()).thenReturn(38689123);
		when(chofer1.getDni()).thenReturn(45612300);
		
		boolean resultado = camion.estaRegistradoElChofer(chofer1);
		//deberia ser true ya que esta registrado
		
		assertFalse(resultado);
		
		//Verificar que se llamo al metetodo getDni()
		
		verify(chofer, atLeastOnce()).getDni();
	}
	
	@Test
	void verificarCambioDeChoferTest() {
		Chofer chofer = mock(Chofer.class);
		Chofer chofer1 = mock(Chofer.class);
		
		camion = new Camion("asd123",chofer);
		//Configuro el comportamiento del mock
		
		when(chofer.getDni()).thenReturn(38689123);
		when(chofer.getDni()).thenReturn(45389123);
		
		camion.setChofer(chofer1); //Registro solo a chofer 
		
		boolean resultado = camion.estaRegistradoElChofer(chofer1);
		//deberia ser true ya que esta registrado
		
		assertTrue(resultado);
		assertEquals(chofer1,camion.getChofer());
		
		
		
	}

}
