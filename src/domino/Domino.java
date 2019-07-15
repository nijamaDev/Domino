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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import misComponentes.Titulos;

/**
 * 
 */
public class Domino extends JFrame {
	private ImageIcon fichaIcon = null;
	private String imageRoute = "src/imagenes/";
	private ArrayList<Ficha> pila = new ArrayList<Ficha>(28);
	private Escuchas escucha;
	private JButton nuevo, salir;
	private Ficha ficha;
	
	public Domino() {
		try {
			//pila.ensureCapacity(28);
			for (int i=0; i<7; i++) {
				for (int j = i; j<7; j++) {
					System.out.println( "i: " + i + ", j: " + j);
					fichaIcon = new ImageIcon(ImageIO.read(new File(imageRoute +i+ "-" +j+ ".png")));
					ficha = new Ficha(fichaIcon, i, j);
					pila.add(ficha);
					
				}
			}
			
			initGUI();

			// default window configuration
			this.setUndecorated(true);
			pack();
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, 
					"No se ha encontrado la imágen en: \"" + imageRoute + "\"");
		}
	}
	
	private void initGUI() {
		// define window container and layout
		
		// crear el escucha
		escucha = new Escuchas();
		
		// crear la GUI
		
		// Titulo
		nuevo = new JButton("Salir");
		nuevo.addActionListener(escucha);
		add(nuevo, BorderLayout.NORTH);
		
		Titulos titulo = new Titulos("Puzzle Gen�rico #1", 30, Color.black);
		add(titulo, BorderLayout.NORTH);
		
		salir = new JButton("Salir");
		salir.addActionListener(escucha);
		add(salir, BorderLayout.NORTH);
		
		// Zona de juego - centralPanel
		
		
		
	}
	
	private class Escuchas extends MouseAdapter implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent eventAction) {
			// TODO Auto-generated method stub
			// responde a los botones ayuda, revolver, salir
			if (eventAction.getSource() == salir) {
				System.exit(0);
			}
			
			else {
				// llamar a la funcion ???
				
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			//intercambiar fichas
			Ficha fichaClick = (Ficha)eventMouse.getSource();
			//ClickedFicha(fichaClick);
		}
		
	}
}

