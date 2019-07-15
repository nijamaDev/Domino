/**
  Archivo: Vista.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	mes d, 2019
  Versión: 0.1
  Licencia: GPL

  Autores:	Nicolas Jaramillo Mayor        1840558

  Email:	nicolas.jaramillo@correounivalle.edu.co

*/
package domino;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 */
public class Domino extends JFrame {
	private ImageIcon fichaIcon = null;
	private String imageRoute = "src/images/";
	private ArrayList<Ficha> pila;
	
	public Domino() {
		try {
			for (int i=0; i<28; i++) {
				fichaIcon = new ImageIcon(ImageIO.read(new File(imageRoute + i + ".png")));
				pila.add(new Ficha(fichaIcon, i));
			}
			
			/*
			initGUI();

			// default window configuration
			this.setUndecorated(true);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, 
					"No se ha encontrado la imágen en: \"" + imageRoute + "\"");
		}
	}
}

