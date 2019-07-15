/**
  Archivo: Fichas.java
  Fecha creaci�n:		Jul 14, 2019
  �ltima modificaci�n:	mes d, 2019
  Versi�n: 0.1
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
	private int idImage;
	
	public Ficha(ImageIcon image, int idImage) {
		this.image = image;
		this.idImage = idImage;
	}
}