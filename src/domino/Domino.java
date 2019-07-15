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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JLabel;
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
	private JPanel zonaJuego;
	
	public Domino() {
		try {
			//pila.ensureCapacity(28);
			for (int i=0; i<7; i++) {
				for (int j = i; j<7; j++) {
					//System.out.println( "i: " + i + ", j: " + j);
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
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(Color.black);
		
		// crear la GUI
		zonaJuego = new JPanel();
		
		zonaJuego.setPreferredSize(new Dimension(410,180));
		zonaJuego.setBackground(Color.black);
		// Titulo
		nuevo = new JButton("Nuevo");
		//nuevo.setBackground(Color.black);
		nuevo.addActionListener(escucha);
		c.gridx = 0;
		c.gridy = 0;
		//c.weightx = 0.2;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		zonaJuego.add(nuevo, c);
		
		Titulos empty1 = new Titulos("      ", 30, Color.black);
		c.gridx = 1;
		c.gridwidth = 1;
		zonaJuego.add(empty1, c);
		
		Titulos titulo = new Titulos("Dominó", 30, Color.black);
		c.gridx = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 1;
		zonaJuego.add(titulo, c);
		
		Titulos empty2 = new Titulos("      ", 30, Color.black);
		c.gridx = 5;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.VERTICAL;
		zonaJuego.add(empty2, c);
		
		salir = new JButton("Salir");
		salir.addActionListener(escucha);
		//c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 7;
		zonaJuego.add(salir, c);
		
		Titulos tablero = new Titulos("holi ", 30, Color.black);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 9;
		c.gridheight = 6;
		//c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 300;
		zonaJuego.add(tablero, c);
		
		//zonaJuego.setBorder(new TitledBorder("Zona Juego"));
		add(zonaJuego);
		
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

