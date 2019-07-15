/**
  Archivo: Fichas.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	mes d, 2019
  Versión: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * 
 */
public class Ficha extends JLabel {
	private ImageIcon image;
	private int vIzq, vDer;
	
	public Ficha(ImageIcon image, int vIzq, int vDer) {
		this.image = image;
		this.vIzq = vIzq;
		this.vDer = vDer;
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
}
