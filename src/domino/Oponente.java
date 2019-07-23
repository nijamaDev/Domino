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

// TODO: Auto-generated Javadoc
/**
 * The Class Oponente.
 */
public class Oponente extends Jugador{

	/**
	 * Hacer jugada.
	 *
	 * @param tablero the tablero
	 * @return the ficha
	 */
	public Ficha hacerJugada(ArrayList<Ficha> tablero) {
		int nficha;
		nficha = puedeJugar(tablero);
		if (nficha < 0)
			return null;
		return fichasDelJugador.get(nficha);
	}
}
