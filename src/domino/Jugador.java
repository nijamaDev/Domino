/**
  Archivo: Jugador.java
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

import java.util.ArrayList;

/**
 * 
 */
public class Jugador {
	
	protected ArrayList<Ficha> fichasDelJugador = new ArrayList<Ficha>();
	
	
	public void addFicha(Ficha nuevaFicha) {
		fichasDelJugador.add(nuevaFicha);
	}
	
	public int puedeJugar(ArrayList<Ficha> tablero) {
		int vIzqTablero = tablero.get(0).getvIzq();
		int vDerTablero = tablero.get(tablero.size()-1).getvDer();
		
		for (int i=0; i<fichasDelJugador.size(); i++) {
			Ficha ficha = fichasDelJugador.get(i);
			int vIzq = ficha.getvIzq();
			int vDer = ficha.getvDer();
			if(vIzq == vIzqTablero || vIzq == vDerTablero ||
			   vDer == vIzqTablero || vDer == vDerTablero) {
				return i;
			}
		}
		return -1;
	}
	
	public int valFichas() {
		int valor = 0;
		for (int i=0; i<fichasDelJugador.size(); i++) {
			Ficha ficha = fichasDelJugador.get(i);
			valor += ficha.getvIzq();
			valor += ficha.getvDer();
		}
		return valor;
	}
	
	public ArrayList<Ficha> getFichasJugador() {
		return fichasDelJugador;
		
	}
	
}
