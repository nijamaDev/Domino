/**
<<<<<<< HEAD
  Archivo: Jugador.java
=======
>>>>>>> fb4ee7bfdc1bd6b9b9748b98f73e9bdb4517f3b3
  Fecha creación:		Jul 14, 2019
  Última modificación:	Jul 15, 2019
  Versión: 0.4
  Archivo: Jugador.java
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor         1840558
  			Crhistian Alexander Garcia		1832124

  Email:	nicolas.jaramillo@correounivalle.edu.co
  			garcia.crhistian@correounivalle.edu.co
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
	
	
	public ArrayList<Ficha> getFichasJugador() {
		return fichasDelJugador;
		
	}
	
}
