package servicio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import container.Container;
import container.Reefer;
import orden.Orden;
import viaje.Viaje;

class ServicioTest {
	
		Electricidad servElectrico;
		Almacenamiento servAlmacenamiento;
		Lavado servLavado;
		Pesado servPesado;

		Container container1;

		Orden ordenNro1;
		LocalDateTime salidaContainer;//
		LocalDateTime entregaContainer;//
		Reefer reefer1;
		@BeforeEach
		void setUp() throws Exception {
			servElectrico = new Electricidad("Electricidad",LocalDate.now(), 20.0);
			servAlmacenamiento = new Almacenamiento("Almacenamiento",LocalDate.now(), 20);
			servLavado = new Lavado("Lavado",LocalDate.now(),10,50,80);
			servPesado = new Pesado("Pesado",LocalDate.now(), 20);
			reefer1 = new Reefer("Reefer", 50,60,200, 50, false,20);
			container1 = mock(Container.class);
			ordenNro1 = mock(Orden.class);
			
		}
		
		@Test
		void servicioDeElectricidadTest() {
			assertEquals(20.0, servElectrico.getCostoKW());
			servElectrico.setCostoKW(100);
			assertEquals(100, servElectrico.getCostoKW());
			
			servElectrico.setReefer(reefer1);
			LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0, 0); // 1 de Enero, 10:00:00
	        LocalDateTime fin = LocalDateTime.of(2025, 1, 1, 20, 0, 0);     // 1 de Enero, 20:00:00
	        
			reefer1.conectar(inicio);
			reefer1.desconectar(fin);
			
			assertEquals(20000, servElectrico.costoServicio(ordenNro1));

		}
		@Test
		void servicioDePesadoTest() {
			assertEquals(20, servPesado.getCostoPesaje());
			servPesado.setCostoPesaje(100);
			assertEquals(100, servPesado.getCostoPesaje());
			assertEquals(100, servPesado.costoServicio(ordenNro1));

		}
		@Test
		void servicioDeLavadoVolumenMenorA70Test() {
			servLavado.setPrecioNoSuperaVolumen(20);
			servLavado.setPrecioSuperaVolumen(40);
			when(ordenNro1.getContainer()).thenReturn(container1);
			when(container1.volumen()).thenReturn(60);
			assertEquals(20,servLavado.costoServicio(ordenNro1));
				
		}
		@Test
		void servicioDeLavadoVolumenMayorA70Test() {
			servLavado.setPrecioNoSuperaVolumen(20);
			servLavado.setPrecioSuperaVolumen(40);
			when(ordenNro1.getContainer()).thenReturn(container1);
			when(container1.volumen()).thenReturn(80);
			assertEquals(40,servLavado.costoServicio(ordenNro1));
				
		}
		
		@Test
		
		void servicioDeAlmacenamientoExcedenteTest() {
		    assertEquals(20, servAlmacenamiento.getCostoPorDia());
		    servAlmacenamiento.setCostoPorDia(100);
		    assertEquals(100, servAlmacenamiento.getCostoPorDia());
		    
		    LocalDateTime llegadaContainer = LocalDateTime.of(2025, 11, 2, 14, 30);

		    Viaje viajeMock = mock(Viaje.class);
		    when(ordenNro1.getViaje()).thenReturn(viajeMock);
		    when(viajeMock.fechaLlegadaTerminalGestionada()).thenReturn(llegadaContainer);

		    assertEquals(14700.0, servAlmacenamiento.costoServicio(ordenNro1));
		}
}
