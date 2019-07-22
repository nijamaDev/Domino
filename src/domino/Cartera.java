/**
  Archivo: Cartera.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	Jul 22, 2019
  Versión: 0.5
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor         1840558
  			Crhistian Alexander Garcia		1832124

  Email:	nicolas.jaramillo@correounivalle.edu.co
  			garcia.crhistian@correounivalle.edu.co
*/

package domino;

/**
 * Maneja la cantidad de dinero del jugador y las apuestas
 */
public class Cartera {
	
	private int dinero;
	
	public Cartera(int dinero) {
		this.dinero = dinero;
	}
	
	public void addDinero(int suma) {
		dinero += suma;
	}
	
	public int getDinero() {
		return dinero;
	}
	
	public boolean puedeApostar(int apuesta) {
		if (dinero < apuesta)
			return false;
		else return true;
	}
	
}
