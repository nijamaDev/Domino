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

import codigoExterno.RotatedIcon;
import codigoExterno.RotatedIcon.Rotate;

/**
 * 
 */
public class Ficha extends JLabel {
	public static final int ROTAR_0 = 0, ROTAR_IZQ = 1, ROTAR_DER = 2, ROTAR_ABAJO = 3;
	public static ImageIcon back;
	private ImageIcon front;
	private RotatedIcon rotateIcon;
	private int vIzq, vDer;
	
	public Ficha() {}
	
	public Ficha(ImageIcon image, int vIzq, int vDer) {
		setIcon(back);
		this.front = image;
		this.vIzq = vIzq;
		this.vDer = vDer;
	}
	
	public Ficha(Ficha newFicha) {
		front = newFicha.front;
		vIzq = newFicha.getvIzq();
		vDer = newFicha.getvDer();
		setIcon(newFicha.getIcon());
	}
	
	/*
	public Ficha(ImageIcon image) {
		this.front = image;
		setIcon(back);
	}
	*/
	
	public void girarFicha(int rotar) {
		switch (rotar) {
		case 0: //vertical, original
			setIcon(front);
			break;
		case 1: // horizontal a la izquierda
			rotateIcon = new RotatedIcon(front, Rotate.UP);
			setIcon(rotateIcon);
			break;
		case 2: // horizontal a la derecha
			rotateIcon = new RotatedIcon(front, Rotate.DOWN);
			setIcon(rotateIcon);
			break;
		case 3: // vertical, al revés
			rotateIcon = new RotatedIcon(front, Rotate.UPSIDE_DOWN);
			setIcon(rotateIcon);
			break;
		}
		
	}
	
	public void taparFicha() {
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
