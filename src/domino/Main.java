/**
  Archivo: Main.java
  Fecha creaci�n:		Jul 14, 2019
  �ltima modificaci�n:	mes d, 2019
  Versi�n: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

import java.awt.EventQueue;

import javax.swing.UIManager;

/**
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String className = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		}
		catch (Exception e) {}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Domino myWindow = new Domino();
			}
		}
		);
	}

}
