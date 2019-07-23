/**
  Archivo: Cartera.java
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

// TODO: Auto-generated Javadoc
/**
 * Maneja la cantidad de dinero del jugador y las apuestas.
 */
public class Cartera {
	
	/** The dinero. */
	private int dinero;
	
	/**
	 * Instantiates a new cartera.
	 *
	 * @param dinero the dinero
	 */
	public Cartera(int dinero) {
		this.dinero = dinero;
	}
	
	/**
	 * Adds the dinero.
	 *
	 * @param suma the suma
	 */
	public void addDinero(int suma) {
		dinero += suma;
	}
	
	/**
	 * Gets the dinero.
	 *
	 * @return the dinero
	 */
	public int getDinero() {
		return dinero;
	}
	
	/**
	 * Puede apostar.
	 *
	 * @param apuesta the apuesta
	 * @return true, if successful
	 */
	public boolean puedeApostar(int apuesta) {
		if (dinero < apuesta)
			return false;
		else return true;
	}
	
}
