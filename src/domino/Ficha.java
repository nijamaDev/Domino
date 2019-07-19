/**
  Archivo: Ficha.java
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 */
public class Ficha extends JLabel {
	public static ImageIcon back;
	private ImageIcon front;
	private RotatedIcon rotateIcon;
	
	private int vIzq, vDer;
	
	public Ficha() {}
	
	public Ficha(Ficha newFicha) {
		front = newFicha.front;
	}
	
	

	public Ficha(ImageIcon image) {
		this.front = image;
		setIcon(back);
	}
	public Ficha(ImageIcon image, int vIzq, int vDer) {
		this.front = image;
		setIcon(back);
		this.vIzq = vIzq;
		this.vDer = vDer;
	}
	
	public void girarFicha() {
		rotateIcon = new RotatedIcon(front);
		setIcon(rotateIcon);
	}
	
	public void voltearFicha() {
		setIcon(back);
	}
	
	public void destaparFicha() {
		setIcon(front);
	}

	/**
	 * @return the vIzq
	 */
	public int getvIzq() {
		return vIzq;
	}

	/**
	 * @return the vDer
	 */
	public int getvDer() {
		return vDer;
	}
	
	/**
	 * @return the front
	 */
	public ImageIcon getFront() {
		return front;
	}
}
