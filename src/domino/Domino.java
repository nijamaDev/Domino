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

import componentes.Titulos;

/**
 * 
 */
public class Domino extends JFrame {
	private ImageIcon fichaIcon = null;
	private String imageRoute = "src/images/";
	private ArrayList<Ficha> pila;
	private Escuchas escucha;
	
	public Domino() {
		try {
			for (int i=0; i<7; i++) {
				for (int j = 0; j<7; j++) {
					fichaIcon = new ImageIcon(ImageIO.read(new File(imageRoute + i + "-" + j + ".png")));
					pila.add(new Ficha(fichaIcon, i, j));
				}
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
	
	private void initGUI() {
		// define window container and layout
		
		// crear el escucha
		escucha = new Escuchas();
		
		// crear la GUI
		
		// Titulo
		Titulos titulo = new Titulos("Puzzle Gen�rico #1", 30, Color.black);
		add(titulo, BorderLayout.NORTH);
		
		// Zona de juego - centralPanel
		dividirImage();
		
		// panel buttons
		panelBotones = new JPanel();
		ayuda = new JButton("Ayuda");
		ayuda.addActionListener(escucha);
		panelBotones.add(ayuda);
		revolver = new JButton("Revolver");
		revolver.addActionListener(escucha);
		panelBotones.add(revolver);
		salir = new JButton("Salir");
		salir.addActionListener(escucha);
		panelBotones.add(salir);
		
		add(panelBotones, BorderLayout.SOUTH);
	}
	
	private class Escuchas extends MouseAdapter implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent eventAction) {
			// TODO Auto-generated method stub
			// responde a los botones ayuda, revolver, salir
			if (eventAction.getSource() == salir) {
				System.exit(0);
			}
			else if(eventAction.getSource() == ayuda) {
				// llamar a la ventana ayuda
				ventanaAyuda.setVisible(true);
				miMisma.setEnabled(false);
			}
			else {
				// llamar a la funcion revolver ficha
				revolverFichas();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent eventMouse) {
			//intercambiar fichas
			Ficha fichaClick = (Ficha)eventMouse.getSource();
			ClickedFicha(fichaClick);
		}
		
	}
}

