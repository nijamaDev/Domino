/**
  Archivo: Cartera.java
  Fecha creaci�n:		Jul 14, 2019
  �ltima modificaci�n:	mes d, 2019
  Versi�n: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

/**
 * 
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
