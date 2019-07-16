/**
  Archivo: Control.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	mes d, 2019
  Versión: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * 
 */
public class Control {
	private ImageIcon fichaIcon = null;
	private ImageIcon backIcon;
	private String imageRoute = "src/imagenes/";
	private ArrayList<Ficha> fichas = new ArrayList<Ficha>(28);
	private ArrayList<Ficha> pila = new ArrayList<Ficha>();
	private Jugador jugador = new Jugador();
	private Cartera cartera;
	private Ficha backFicha;
	private int apuesta;
	
	public Control() {
		try {
			backIcon = new ImageIcon(ImageIO.read(new File("src/imagenes/back-vertical.png")));//.getScaledInstance(100, 50, Image.SCALE_SMOOTH));
			backFicha = new Ficha(backIcon);
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
					"No se ha encontrado la imágen en: \"" + imageRoute + "\"");
		}
	}
	
	public void nuevaRonda() {
		pila.clear();
		pila.addAll(fichas);
		apuesta = 0;
		
	}
	
	public void nuevaPartida() {
		cartera = new Cartera(30);
		jugador = new Jugador();
		nuevaRonda();
	}
	
	public Ficha getBackFicha() {
		return backFicha;
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
}
