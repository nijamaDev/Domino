/**
  Archivo: Domino.java
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;


import javax.imageio.ImageIO;
import javax.security.auth.callback.TextOutputCallback;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

import misComponentes.Titulos;

/**
 * 
 */
public class Domino extends JFrame {

	private static final int WINDOW_HSIZE = 1270;
	private static final Dimension WINDOW_SIZE = new Dimension(WINDOW_HSIZE, 640);
	private static final Dimension FICHA_VSIZE = new Dimension(50, 100);
	private static final Dimension FICHA_HSIZE = new Dimension(100, 50);
	private Escuchas escucha;
	private JButton nuevo, salir;
	private JLayeredPane layeredPane;
	private JPanel allPanel,
				   tituloPanel, // North // título
				   zonaJuego,	// Center
				   oponentPanel,// Center > North // Fichas computador
				   pilaPanel,	// West //fichas de la pila
				   jugadorPanel;// center > South          // Fichas Jugador, dinero, apuesta
	private ImageJPanel tablero;// Center > Center // tablero de juego
	private Control control;
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
		nuevaPartida();
		/*for (int i=0; i<control.getPila().size(); i++) {
			control.getPila().get(i).addMouseListener(escucha);
		}*/
	}
	
	public void nuevaRonda() {
		control.nuevaRonda(escogerInicio());
		printDinero();
		printFichas();
	}
	
	public void nuevaPartida() {
		control.nuevaPartida();
		nuevaRonda();
	}
	
	private void initGUI() {
		// define window container and layout
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(WINDOW_SIZE);
		allPanel = new JPanel(new BorderLayout());
		allPanel.setPreferredSize(WINDOW_SIZE);
		//this.getContentPane().setPreferredSize(WINDOW_SIZE);
		this.getContentPane().add(layeredPane, BorderLayout.CENTER);
		allPanel.setSize(layeredPane.getPreferredSize());
		allPanel.setLocation(0, 0);
		//layeredPane.setOpaque(false);
		//allPanel.setOpaque(false);
		this.getContentPane().setBackground(Color.black);
		layeredPane.add(allPanel, JLayeredPane.DEFAULT_LAYER);
		add(layeredPane);
		
		
		
		// crear el escucha
		escucha = new Escuchas();
		
		// crear la GUI
		//this.getContentPane().setPreferredSize(new Dimension(1270, 640));
		//this.getContentPane().setBackground(Color.black);
		
		GridBagConstraints c = new GridBagConstraints();
		
		//-------------- Panel superior --------------
		tituloPanel = new JPanel();
		tituloPanel.setLayout(new GridBagLayout());
		tituloPanel.setBackground(Color.black);
		//tituloPanel.setPreferredSize(new Dimension(WINDOW_HSIZE, 100));
		
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
		//titulo.addMouseListener(escucha);
		
		c.gridx = 1;
		c.weightx = 1;
		c.gridheight = 2;
		//c.fill = GridBagConstraints.HORIZONTAL;
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
		
		allPanel.add(tituloPanel, BorderLayout.PAGE_START);
		
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
		
		/*
		back = control.getBackFicha();
		back.setPreferredSize(new Dimension(50, 100));
		
		//Funcion para girar imagen
		AffineTransform tx = AffineTransform.getRotateInstance(Math.PI/2,
	            back.getWidth()/2, back.getHeight()/2);
		
		
		oponentPanel.add(back);
		*/
		
		zonaJuego.add(oponentPanel, BorderLayout.PAGE_START);
		
		
		// Panel del tablero
		Image imagen;
		try {
			imagen = new ImageIcon(ImageIO.read(new File("src/imagenes/tablero.jpg"))
								  ).getImage().getScaledInstance(1150, 400, Image.SCALE_SMOOTH);
			tablero = new ImageJPanel(imagen);
			tablero.addMouseMotionListener(escucha);
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
		tablero.add(tabl, c);
		
		zonaJuego.add(tablero, BorderLayout.PAGE_END);
		
		allPanel.add(zonaJuego, BorderLayout.CENTER);
		
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
		
		//jugadorPanel.add(texto);
		
		allPanel.add(jugadorPanel, BorderLayout.PAGE_END);
		
		pilaPanel = new JPanel();
		pilaPanel.setLayout(new FlowLayout());
		pilaPanel.setPreferredSize(new Dimension(110, 615));
		pilaPanel.setBackground(Color.black);
		
		Titulos tituloPila = new Titulos("Fichas ", 30, Color.black);
		
		pilaPanel.add(tituloPila);
		
		allPanel.add(pilaPanel, BorderLayout.LINE_END);
		
		layeredPane.setBackground(Color.black);
		//layeredPane.add(allPanel, JLayeredPane.DEFAULT_LAYER);
		//add(allPanel, JLayeredPane.DEFAULT_LAYER);
	}
	
	private void printDinero() { // Muestra el dinero y la apuesta actual
		texto.setText(
				"Dinero:\n"+ control.getDinero() + "\n" +
				"Apuesta:\n" + control.getApuesta());
	}
	
	private void printFichas() { // Muestra las fichas del jugador y las de la máquina
		ArrayList<Ficha> list;
		list = control.getFichasOponente();
		int numFichas = list.size();
		for (int i=0; i<numFichas; i++)
			oponentPanel.add(list.get(i));
		
		list = control.getFichasJugador();
		numFichas = list.size();
		for (int i=0; i<numFichas; i++)
		{
			JPanel fichaPanel = new JPanel();
			fichaPanel.setPreferredSize(FICHA_VSIZE);
			Ficha ficha = list.get(i);
			control.getFichasJugador().get(i).addMouseListener(escucha);
			control.getFichasJugador().get(i).addMouseMotionListener(escucha);
			fichaPanel.add(ficha);
			jugadorPanel.add(fichaPanel);
			System.out.println("escucha added");
			
		}
		
		list = control.getPila();
		numFichas = list.size();
		for (int i=0; i<numFichas; i++)
			pilaPanel.add(list.get(i));
			
		
	}
	
	private boolean escogerInicio() { // Escoge quién inicia la partida
		return false; //place holder
	}
	
	private class Escuchas extends MouseAdapter implements ActionListener{
		private JLabel dragLabel = null;
        private int dragLabelWidthDiv2;
        private int dragLabelHeightDiv2;
        private JPanel clickedPanel = null;

		@Override
		public void actionPerformed(ActionEvent eventAction) {
			// TODO Auto-generated method stub
			// responde a los botones
			if (eventAction.getSource() == salir) {
				System.exit(0);
			} else if (eventAction.getSource() == nuevo) {
				nuevaPartida();
			} else {
				// llamar a la funcion ???
				
			}
		}
		
		@Override
        public void mousePressed(MouseEvent me) {
			System.out.println("mouse pressed");
            clickedPanel = (JPanel) jugadorPanel.getComponentAt(me.getPoint());
            System.out.println("mouse pressed");
            Component[] components = clickedPanel.getComponents();
            if (components.length == 0) {
                return;
            }
            // if we click on jpanel that holds a jlabel
            if (components[0] instanceof Ficha) {

                // remove label from panel
            	System.out.println("mouse pressed");
                dragLabel = (Ficha) components[0];
                clickedPanel.remove(dragLabel);
                clickedPanel.revalidate();
                clickedPanel.repaint();

                dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
                dragLabelHeightDiv2 = dragLabel.getHeight() / 2;

                int x = me.getPoint().x - dragLabelWidthDiv2;
                int y = me.getPoint().y - dragLabelHeightDiv2;
                dragLabel.setLocation(x, y);
                layeredPane.add(dragLabel, JLayeredPane.DRAG_LAYER);
                repaint();
                
                
            }
        }
		
		@Override
        public void mouseDragged(MouseEvent me) {
            if (dragLabel == null) {
                return;
            }
            int x = me.getPoint().x - dragLabelWidthDiv2;
            int y = me.getPoint().y - dragLabelHeightDiv2;
            dragLabel.setLocation(x, y);
            repaint();
            System.out.println("mouse dragged");
        }
		
		@Override
        public void mouseReleased(MouseEvent me) {
            if (dragLabel == null) {
                return;
            }
            remove(dragLabel); // remove dragLabel for drag layer of JLayeredPane
            JPanel droppedPanel = (JPanel) jugadorPanel.getComponentAt(me.getPoint());
            if (droppedPanel == null) {
                // if off the grid, return label to home
                clickedPanel.add(dragLabel);
                clickedPanel.revalidate();
            } else {
                int r = -1;
                /*searchPanelGrid: for (int row = 0; row < panelGrid.length; row++) {
                    for (int col = 0; col < panelGrid[row].length; col++) {
                        if (panelGrid[row][col] == droppedPanel) {
                            r = row;
                            c = col;
                            break searchPanelGrid;
                        }
                    }
                }*/

                if (r == -1) {
                    // if off the grid, return label to home
                    clickedPanel.add(dragLabel);
                    clickedPanel.revalidate();
                } else {
                    droppedPanel.add(dragLabel);
                    droppedPanel.revalidate();
                }
            }

            repaint();
            dragLabel = null;
        }
		
		
	}
}

