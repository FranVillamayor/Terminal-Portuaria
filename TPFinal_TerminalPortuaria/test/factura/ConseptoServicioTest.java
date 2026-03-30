package factura;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConseptoServicioTest {

		
		ConceptoServicio lavado;
		LocalDate fecha;

		@BeforeEach
		void setUp() throws Exception {
			fecha = LocalDate.of(2025, 11, 10);
			lavado = new ConceptoServicio ("Lavado",fecha,20.0);
		}

		@Test
		void getNombre() {
			assertEquals("Lavado",lavado.getNombreServicio());
			
		}
		@Test
		void getFecha() {
			assertEquals(fecha,lavado.getFechaEmision());
			
		}
		@Test
		void getMonto() {
			assertEquals(20.0,lavado.getMonto());
		}
		
	}

