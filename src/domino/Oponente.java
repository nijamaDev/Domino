/**
  Archivo: Oponente.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	Jul 22, 2019
  Versión: 0.9
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
public class Oponente extends Jugador{

	/**
	 * @param tablero
	 */
	public Ficha hacerjugada(ArrayList<Ficha> tablero) {
		int nficha;
		nficha = puedeJugar(tablero);
		if (nficha < 0)
			return null;
		return fichasDelJugador.get(nficha);
	}
}
