/**
  Archivo: Control.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	Jul 15, 2019
  Versión: 0.4
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

/**
 * 
 */
public class Control {
	private ImageIcon fichaIcon = null;
	private String imageRoute = "src/imagenes/";
	private ArrayList<Ficha> fichas, pila, fichasTablero;
	private Jugador jugador;
	private Oponente oponente;
	private Cartera cartera;
	private int apuesta;
	
	public Control() {
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
	
	public void nuevaRonda(boolean inicia) {
		fichasTablero.clear();
		pila.clear();
		for(int i=0; i<fichas.size(); i++) {
			fichas.get(i).girarFicha(Ficha.ROTAR_0);
			fichas.get(i).taparFicha();
		}
			
		
		pila.addAll(fichas);       // Crea una pila con todas las fichas
		Collections.shuffle(pila); // Revuelve la pila de fichas
		apuesta = 0;
		for (int i=0; i<7; i++) {  // Saca 7 elementos de la pila para jugador y 7 para la m�quina
			pila.get(0).destaparFicha();
			jugador.addFicha(pila.get(0));
			pila.remove(0);
			//pila.get(0).voltearFicha();
			oponente.addFicha(pila.get(0));
			pila.remove(0);
		}
		 // si no inicia el jugador, inicia la m�quina
		//oponente.juega(inicia);
		while(true) {
			//jugador.juega();
			//oponente.juega();
			break;
		}
		
		
	}
	
	public void nuevaPartida() {
		cartera = new Cartera(30);
		jugador = new Jugador();
		oponente = new Oponente();
	}
	
	private int ganar() {
		if (jugador.getFichasJugador().size() == 0) {
			return 1;
		}
		else if (oponente.getFichasJugador().size() == 0) {
			return 0;
		} else {
			return -1;
		}
		
	}
	
	public void cogerFicha(boolean quien) {
		if (pila.size() < 1)
			return;
		if (quien) {
			pila.get(0).destaparFicha();
			jugador.addFicha(pila.get(0));
			pila.remove(0);
			
		} else {
			oponente.addFicha(pila.get(0));
			pila.remove(0);
		}
	}
	
	public int getDinero() {
		return cartera.getDinero();
	}
	
	public int getApuesta() {
		return apuesta;
	}
	
	public ArrayList<Ficha> getFichasJugador() {
		return jugador.getFichasJugador();
	}
	
	public ArrayList<Ficha> getFichasOponente() {
		return oponente.getFichasJugador();
	}
	
	public ArrayList<Ficha> getPila() {
		return pila;
	}
	
	public ArrayList<Ficha> getFichasTablero() {
		return fichasTablero;
	}
	
	public ArrayList<Ficha> getFichas() {
		return fichas;
	}
	
}
