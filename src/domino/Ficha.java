/**
  Archivo: Ficha.java
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import codigoExterno.RotatedIcon;
import codigoExterno.RotatedIcon.Rotate;

/**
 * 
 */
public class Ficha extends JLabel {
	public static final int ROTAR_0 = 0, ROTAR_IZQ = -1, ROTAR_DER = 1, ROTAR_ABAJO = 3;
	public static ImageIcon back;
	private ImageIcon front;
	private RotatedIcon rotateIcon;
	private int vIzq, vDer, vIzqO, vDerO;
	
	public Ficha() {}
	
	public Ficha(ImageIcon image, int vIzq, int vDer) {
		setIcon(back);
		this.front = image;
		this.vIzqO = vIzq;
		this.vDerO = vDer;
		this.vIzq = vIzq;
		this.vDer = vDer;
	}
	
	public Ficha(Ficha newFicha) {
		front = newFicha.front;
		vIzqO = newFicha.getvIzq();
		vDerO = newFicha.getvDer();
		vIzq = vIzqO;
		vDer = vDerO;
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
			vIzq = vIzqO;
			vDer = vDerO;
			break;
		case -1: // horizontal a la izquierda
			rotateIcon = new RotatedIcon(front, Rotate.UP);
			setIcon(rotateIcon);
			vIzq = vIzqO;
			vDer = vDerO;
			break;
		case 1: // horizontal a la derecha
			rotateIcon = new RotatedIcon(front, Rotate.DOWN);
			setIcon(rotateIcon);
			vIzq = vDerO;
			vDer = vIzqO;
			break;
		case 3: // vertical, al revés
			rotateIcon = new RotatedIcon(front, Rotate.UPSIDE_DOWN);
			setIcon(rotateIcon);
			vIzq = vDerO;
			vDer = vIzqO;
			break;
		}
		
	}
	
	public void cambiarVal() {
		int aux = vIzq;
		vIzq = vDer;
		vDer = aux;
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
