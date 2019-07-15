/**
  Archivo: Jugador.java
  Fecha creaci�n:		Jul 14, 2019
  �ltima modificaci�n:	mes d, 2019
  Versi�n: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

import java.util.ArrayList;

/**
 * 
 */
public class Jugador {
	
	private ArrayList<Ficha> fichasDelJugador = new ArrayList<Ficha>();
	
	
	public void addFicha(Ficha nuevaFicha) {
		fichasDelJugador.add(nuevaFicha);
			
	}
	
	
	public Ficha getFichasJugador(Ficha fichasEnMano) {
		return fichasEnMano;
		
	}
	
}
