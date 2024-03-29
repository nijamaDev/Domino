/**
  Archivo: Control.java
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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class Control.
 */
public class Control {
	
	/** The ficha icon. */
	private ImageIcon fichaIcon = null;
	
	/** The image route. */
	private String imageRoute = "src/imagenes/";
	
	/** The fichas tablero. */
	private ArrayList<Ficha> fichas, pila, fichasTablero;
	
	/** The jugador. */
	private Jugador jugador;
	
	/** The oponente. */
	private Oponente oponente;
	
	/** The cartera. */
	private Cartera cartera;
	
	/** The apuesta. */
	private int apuesta;
	
	/**
	 * Instantiates a new control.
	 */
	public Control() {
		apuesta = 10;
		fichas = new ArrayList<Ficha>(28); // Contiene todas las fichas
		pila = new ArrayList<Ficha>(); // Cada ronda se agregan todas las fichas, se revuelven y se reparten
		fichasTablero = new ArrayList<Ficha>(); // Fichas colocadas en el tablero
		try {
			Ficha.back = new ImageIcon(ImageIO.read(new File("src/imagenes/back-vertical.png")));//.getScaledInstance(100, 50, Image.SCALE_SMOOTH));
			
			for (int i=0; i<7; i++) {
				for (int j = i; j<7; j++) {
					//System.out.println( "i: " + i + ", j: " + j);
					fichaIcon = new ImageIcon(ImageIO.read(new File(imageRoute +i+ "-" +j+ ".png")));
					fichas.add(new Ficha(fichaIcon, i, j));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, 
					"No se ha encontrado la im�gen en: \"" + imageRoute + "\"");
		}
	}
	
	/**
	 * Nueva partida.
	 */
	public void nuevaPartida() {
		cartera = new Cartera(30);
		jugador = new Jugador();
		oponente = new Oponente();
	}
	
	/**
	 * Nueva ronda.
	 */
	public void nuevaRonda() {
		jugador.getFichasJugador().clear();
		oponente.getFichasJugador().clear();
		fichasTablero.clear();
		pila.clear();
		for(int i=0; i<fichas.size(); i++) {
			fichas.get(i).girarFicha(Ficha.ROTAR_0);
			fichas.get(i).taparFicha();
		}
		pila.addAll(fichas);       // Crea una pila con todas las fichas
		Collections.shuffle(pila); // Revuelve la pila de fichas
		
		for (int i=0; i<7; i++) {  // Saca 7 elementos de la pila para jugador y 7 para la máquina
			pila.get(0).destaparFicha();
			jugador.addFicha(pila.get(0));
			pila.remove(0);
			//pila.get(0).voltearFicha();
			oponente.addFicha(pila.get(0));
			pila.remove(0);
		}
		 // si no inicia el jugador, inicia la m�quina
		//oponente.juega(inicia);
	}
	
	/**
	 * Hacer jugada.
	 *
	 * @return the ficha
	 */
	public Ficha hacerJugada(){
		Ficha ficha = null;
		ficha = oponente.hacerJugada(fichasTablero);
			return ficha;
	}
	
	
	/**
	 * Ganar.
	 *
	 * @return the int
	 */
	public int ganar() {
		if (oponente.getFichasJugador().size() == 0) { //gana la máquina
			cartera.addDinero(-10);
			return 0;
		} else if (jugador.getFichasJugador().size() == 0) { // jugador ganó
			cartera.addDinero(10);
			return 1;
		} else if (pila.size() == 0) {
			if (jugador.puedeJugar(fichasTablero) >= 0 && oponente.puedeJugar(fichasTablero) >= 0) {
				return -1;
			} else {
				int valJugador  = jugador.valFichas();
				int valOponente = oponente.valFichas();
				if (valJugador < valOponente) {
					cartera.addDinero(10);
					return 1; //jugador gana
				}
				else {
					cartera.addDinero(-10);
					return 0; // máquina gana
				}
			}
		}
			return -1; // el juego sigue, pasa al siguiente turno
		
		
	}
	
	/**
	 * Coger ficha.
	 *
	 * @param quien the quien
	 * @return true, if successful
	 */
	public boolean cogerFicha(boolean quien) {
		if (pila.size() < 1)
			return false;
		if (quien) {
			pila.get(0).destaparFicha();
			jugador.addFicha(pila.get(0));
			pila.remove(0);
			
		} else {
			oponente.addFicha(pila.get(0));
			pila.remove(0);
		}
		return true;
	}
	
	/**
	 * Puede apostar.
	 *
	 * @return true, if successful
	 */
	public boolean puedeApostar() {
		if(cartera.puedeApostar(apuesta)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the dinero.
	 *
	 * @return the dinero
	 */
	public int getDinero() {
		return cartera.getDinero();
	}
	
	/**
	 * Gets the apuesta.
	 *
	 * @return the apuesta
	 */
	public int getApuesta() {
		return apuesta;
	}
	
	/**
	 * Gets the fichas jugador.
	 *
	 * @return the fichas jugador
	 */
	public ArrayList<Ficha> getFichasJugador() {
		return jugador.getFichasJugador();
	}
	
	/**
	 * Gets the fichas oponente.
	 *
	 * @return the fichas oponente
	 */
	public ArrayList<Ficha> getFichasOponente() {
		return oponente.getFichasJugador();
	}
	
	/**
	 * Gets the pila.
	 *
	 * @return the pila
	 */
	public ArrayList<Ficha> getPila() {
		return pila;
	}
	
	/**
	 * Gets the fichas tablero.
	 *
	 * @return the fichas tablero
	 */
	public ArrayList<Ficha> getFichasTablero() {
		return fichasTablero;
	}
	
	/**
	 * Gets the fichas.
	 *
	 * @return the fichas
	 */
	public ArrayList<Ficha> getFichas() {
		return fichas;
	}
	
}
