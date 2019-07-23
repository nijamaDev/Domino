/**
  Archivo: Ficha.java
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import codigoExterno.RotatedIcon;
import codigoExterno.RotatedIcon.Rotate;

// TODO: Auto-generated Javadoc
/**
 * The Class Ficha.
 */
public class Ficha extends JLabel {
	
	/** The Constant ROTAR_ABAJO. */
	public static final int ROTAR_0 = 0, ROTAR_IZQ = -1, ROTAR_DER = 1, ROTAR_ABAJO = 3;
	
	/** The back. */
	public static ImageIcon back;
	
	/** The front. */
	private ImageIcon front;
	
	/** The rotate icon. */
	private RotatedIcon rotateIcon;
	
	/** The v der O. */
	private int vIzq, vDer, vIzqO, vDerO;
	
	/**
	 * Instantiates a new ficha.
	 */
	public Ficha() {}
	
	/**
	 * Instantiates a new ficha.
	 *
	 * @param image the image
	 * @param vIzq the v izq
	 * @param vDer the v der
	 */
	public Ficha(ImageIcon image, int vIzq, int vDer) {
		setIcon(back);
		this.front = image;
		this.vIzqO = vIzq;
		this.vDerO = vDer;
		this.vIzq = vIzq;
		this.vDer = vDer;
	}
	
	/**
	 * Instantiates a new ficha.
	 *
	 * @param newFicha the new ficha
	 */
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
	
	/**
	 * Girar ficha.
	 *
	 * @param rotar the rotar
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
	
	/**
	 * Cambiar val.
	 */
	public void cambiarVal() {
		int aux = vIzq;
		vIzq = vDer;
		vDer = aux;
	}
	
	/**
	 * Tapar ficha.
	 */
	public void taparFicha() {
		setIcon(back);
	}
	
	/**
	 * Destapar ficha.
	 */
	public void destaparFicha() {
		setIcon(front);
	}

	/**
	 * Gets the v izq.
	 *
	 * @return the vIzq
	 */
	public int getvIzq() {
		return vIzq;
	}

	/**
	 * Gets the v der.
	 *
	 * @return the vDer
	 */
	public int getvDer() {
		return vDer;
	}
	
	/**
	 * Gets the front.
	 *
	 * @return the front
	 */
	public ImageIcon getFront() {
		return front;
	}
}
