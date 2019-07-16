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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;


import javax.imageio.ImageIO;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import misComponentes.Titulos;

/**
 * 
 */
public class Domino extends JFrame {
	
	private Escuchas escucha;
	private JButton nuevo, salir;
	private JPanel tituloPanel, // North // título
				   zonaJuego,	// Center
				   oponentPanel,// Center > North // Fichas computador
				   jugadorPanel;// South          // Fichas Jugador, dinero, apuesta
	private ImageJPanel tablero;// Center > South // tablero de juego
	private Control control;
	private Ficha back;
	private JTextArea texto;
	
	//coordenadas del mouse
	private int x;
	private int y;
	
	public Domino() {
		control = new Control();
		initGUI();

		// default window configuration
		this.setUndecorated(true);
		pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void nuevaRonda() {
		control.nuevaRonda();
	}
	
	public void nuevaPartida() {
		control.nuevaPartida();
		nuevaRonda();
	}
	
	private void initGUI() {
		// define window container and layout
		
		// crear el escucha
		escucha = new Escuchas();
		
		// crear la GUI
		this.getContentPane().setPreferredSize(new Dimension(1200, 640));
		this.getContentPane().setBackground(Color.black);
		
		GridBagConstraints c = new GridBagConstraints();
		
		//-------------- Panel superior --------------
		tituloPanel = new JPanel();
		tituloPanel.setLayout(new GridBagLayout());
		tituloPanel.setBackground(Color.black);
		
		// Botón Nuevo
		nuevo = new JButton("Nuevo");
		nuevo.addActionListener(escucha);
		nuevo.setFont(new Font(Font.SANS_SERIF, Font.ITALIC+Font.BOLD, 12));
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.01;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		tituloPanel.add(nuevo, c);
		
		// Titulo
		Titulos titulo = new Titulos("Dominó", 30, Color.black);
		
		c.gridx = 1;
		c.weightx = 10;
		c.gridheight = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		
		tituloPanel.add(titulo, c);
		
		// Botón salir
		salir = new JButton("X");
		salir.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		salir.setForeground(Color.white);
		salir.setBackground(Color.red);
		salir.setPreferredSize(new Dimension(42,25));
		salir.addActionListener(escucha);
		
		c.gridx = 2;
		c.weightx = 0.01;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		
		tituloPanel.add(salir, c);
		
		this.getContentPane().add(tituloPanel, BorderLayout.PAGE_START);
		
		//-------------- Panel zona de juego --------------
		zonaJuego = new JPanel();
		zonaJuego.setBackground(Color.black);
		
		// Panel del oponente (computador)
		oponentPanel = new JPanel();
		oponentPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		oponentPanel.setPreferredSize(new Dimension(1150, 100));
		oponentPanel.setBackground(Color.black);
		Titulos oponente = new Titulos("Oponente:", 25, Color.black);
		
		oponentPanel.add(oponente);
		
		back = control.getBackFicha();
		back.setPreferredSize(new Dimension(50, 100));

		oponentPanel.add(back);
		
		zonaJuego.add(oponentPanel, BorderLayout.PAGE_START);
		
		
		// Panel del tablero
		Image imagen;
		try {
			imagen = new ImageIcon(ImageIO.read(new File("src/imagenes/tablero.jpg"))
								  ).getImage().getScaledInstance(1150, 400, Image.SCALE_SMOOTH);
			tablero = new ImageJPanel(imagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tablero.setLayout(new GridBagLayout());
		tablero.repaint();
		tablero.setPreferredSize(new Dimension(1150, 400));
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		Titulos tabl = new Titulos("holi ", 30, Color.black);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 10;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		tablero.add(tabl);
		
		zonaJuego.add(tablero, BorderLayout.PAGE_END);
		
		this.getContentPane().add(zonaJuego, BorderLayout.CENTER);
		
		//-------------- Panel jugador --------------
		jugadorPanel = new JPanel();
		jugadorPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		jugadorPanel.setPreferredSize(new Dimension(1200, 110));
		jugadorPanel.setBackground(Color.black);
		
		texto = new JTextArea();
		texto.setForeground(Color.white);
		texto.setBackground(Color.black);
		texto.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		texto.setEditable(false);
		texto.setPreferredSize(new Dimension(100, 100));
		//printDinero();
		
		jugadorPanel.add(texto);
		
		this.getContentPane().add(jugadorPanel, BorderLayout.PAGE_END);
	}
	
	private void printDinero() { // Muestra el dinero y la apuesta actual
		texto.setText(
				"Dinero:\n"+ control.getDinero() + "\n" +
				"Apuesta:\n" + control.getApuesta());
	}
	
	private void printFichas() { // Muestra las fichas del jugador y las de la máquina
		
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
		
		public void this_mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
	    }
	
	    public void this_mouseDragged(MouseEvent e) {
	            Point point = MouseInfo.getPointerInfo().getLocation();
	            setLocation(point.x - x, point.y - y);
	            
	    }
		
	}
}

