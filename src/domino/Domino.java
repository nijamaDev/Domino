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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Point;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import misComponentes.Titulos;

/**
 * 
 */
public class Domino extends JFrame {

	private static final int WINDOW_HSIZE = 1270;
	private static final int WINDOW_VSIZE = 640;
	private static final int FICHA_HSIZE = 50;
	private static final int FICHA_VSIZE = 100;
	private static final Dimension WINDOW_SIZE = new Dimension(WINDOW_HSIZE, WINDOW_VSIZE);
	private static final Dimension FICHA_V = new Dimension(FICHA_HSIZE, FICHA_VSIZE);
	private static final Dimension FICHA_H = new Dimension(FICHA_VSIZE, FICHA_HSIZE);
	private Escuchas escucha;
	private JButton nuevo, salir, getFicha;
	private JLayeredPane layeredPane; // necesario para arrastrar las fichas (se colola en this.getContentPane()
	private JPanel allPanel,    // Panel para agregar los demás paneles
				   tituloPanel, // North -- contiene el botón nuevo y salir y el título "Dominó"  
				   zonaJuego,	// Center -- contiene los paneles opponentPanel, tablero y jugadorPanel.
				   oponentPanel,// Center > North -- Contiene las fichas del computador
				   pilaPanel,	// West -- contiene las fichas disponibles para comer (pila)
				   jugadorPanel;// center > South Contiene las Fichas del Jugador y un JTextArea
	private ImageJPanel tablero;// Center > Center // Contiene las fichas que se colocan durante la partida
	private Control control;  // Lleva el mecanismo del juego
	private JTextArea texto;  // Contiene el Dinero y la apuesta (visualmente)
	private ArrayList<Ficha> fichasTablero;
	private GridBagConstraints c = new GridBagConstraints();
	
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
	}
	
	public void nuevaRonda() {
		control.nuevaRonda(escogerInicio());
		c = new GridBagConstraints();
		printDinero();
		printFichas();
	}
	
	public void nuevaPartida() {
		control.nuevaPartida();
		nuevaRonda();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// define window container and layout
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(WINDOW_SIZE);
		allPanel = new JPanel(new BorderLayout());
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
		
		c = new GridBagConstraints();
		
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tablero.setLayout(new GridBagLayout());
		tablero.setPreferredSize(new Dimension(1150, 400));
		tablero.addMouseListener(escucha);
		
		Titulos tabl = new Titulos("holi ", 30, Color.black);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		tablero.add(tabl, c);
		
		Dimension HPanelDimension = new Dimension(100, 0);
		Dimension VPanelDimension = new Dimension(0, 100);
		c = new GridBagConstraints();
		c.gridy = 0;
		c.gridwidth = 3;
		for (int row=0; row<10; row++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(HPanelDimension);
			c.gridx = 1 + row*3;
			tablero.add(panel, c);
		}
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		for (int col=0; col<3; col++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(VPanelDimension);
			c.gridy = 1 + col*3;
			tablero.add(panel, c);
		}
		
		tablero.repaint();
		
		zonaJuego.add(tablero, BorderLayout.PAGE_END);
		
		allPanel.add(zonaJuego, BorderLayout.CENTER);
		
		//-------------- Panel jugador --------------
		jugadorPanel = new JPanel();
		jugadorPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		jugadorPanel.setPreferredSize(new Dimension(1200, 110));
		jugadorPanel.setBackground(Color.black);
		jugadorPanel.addMouseListener(escucha);
		jugadorPanel.addMouseMotionListener(escucha);
				
		
		texto = new JTextArea();
		texto.setForeground(Color.white);
		texto.setBackground(Color.black);
		texto.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		texto.setEditable(false);
		texto.setPreferredSize(new Dimension(100, 100));
		//printDinero();
		
		jugadorPanel.add(texto);
		
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
		Ficha ficha;
		int numFichas;
		// Mostrar fichas del Oponente
		list = control.getFichasOponente();
		numFichas = list.size();
		for (int i=0; i<numFichas; i++)
		{
			ficha = list.get(i);
			ficha.setPreferredSize(FICHA_V);
			oponentPanel.add(ficha);
		}
		// Mostrar fichas del Jugador
		list = control.getFichasJugador();
		numFichas = list.size();
		for (int i=0; i<numFichas; i++)
		{
			ficha = list.get(i);
			ficha.setPreferredSize(FICHA_V);
			jugadorPanel.add(ficha);
		}
		// Mostrar fichas de la pila de fichas restantes
		list = control.getPila();
		numFichas = list.size();
		for (int i=0; i<numFichas; i++)
		{
			ficha = list.get(i);
			ficha.setPreferredSize(FICHA_V);
			pilaPanel.add(ficha);
		}
		revalidate();
		repaint();
	}
	
	private void colocarFicha() {
		
	}
	
	private boolean escogerInicio() { // Escoge quién inicia la partida
		return false; //place holder
	}
	
	private class Escuchas extends MouseAdapter implements ActionListener{
		private Ficha dragFicha = null;
        private int x, y, dragFichaHCenter, dragFichaVCenter;
        private JPanel clickedPanel = null;

		@Override
		public void actionPerformed(ActionEvent eventAction) {
			// TODO Auto-generated method stub
			// responde a los botones
			if (eventAction.getSource() == salir) {
				System.exit(0);
			} else if (eventAction.getSource() == nuevo) {
				nuevaPartida();
			} else if (eventAction.getSource() == getFicha) {
				//getFicha();
			}
		}
		
		@Override
        public void mousePressed(MouseEvent me) {
			clickedPanel = jugadorPanel;
            if (!(clickedPanel.getComponentAt(me.getPoint()) instanceof Ficha)) {
            	return; // Sino es una Ficha lo que se clickeo, se cancela.
            }
            ArrayList<Ficha> fichasJugador = control.getFichasJugador();
            // Busca cuál de las fichas del jugador fue la que se clickeo.
            for (int i=0; i < fichasJugador.size(); i++) {
            	if ((Ficha) clickedPanel.getComponentAt(me.getPoint()) == fichasJugador.get(i)) {
            		dragFicha = (Ficha) clickedPanel.getComponentAt(me.getPoint());
            		break;
            	}
            }
            if (dragFicha == null)  // Just in case
            	return;
            clickedPanel.remove(dragFicha);
            clickedPanel.revalidate();
            clickedPanel.repaint();

            dragFichaHCenter = dragFicha.getWidth() / 2;
            dragFichaVCenter = dragFicha.getHeight() / 2;

            x = me.getX() - dragFichaHCenter;
            y = me.getY() + WINDOW_VSIZE - 110 - dragFichaVCenter;
            layeredPane.add(dragFicha, JLayeredPane.DRAG_LAYER);
            dragFicha.setLocation(x, y);
            repaint();
        }
		
		@Override
        public void mouseDragged(MouseEvent me) {
            if (dragFicha == null) {
                return;
            }
            x = me.getX() - dragFichaHCenter;
            y = me.getY() + WINDOW_VSIZE - 110 - dragFichaVCenter;
            dragFicha.setLocation(x, y);
            repaint();
        }
		
		@Override
        public void mouseReleased(MouseEvent me) {
			//System.out.println("MouseReleased");
            if (dragFicha == null) { // Just in case
                return;
            }
            remove(dragFicha); // Quita la ficha del layeredPane
            Point p = new Point(x, y);
            JPanel droppedPanel = (JPanel) zonaJuego.getComponentAt(p);
            if (droppedPanel != tablero) {
                // Si la ficha no se coloca en el tablero, la devuelve al jugador
                clickedPanel.add(dragFicha);
                clickedPanel.revalidate();
            } else {
            	
            	c.gridx += 3;
            	c.gridy = 1;
            	c.gridwidth = 3;
            	dragFicha.girarFicha();
            	dragFicha.setPreferredSize(FICHA_H);
            	droppedPanel.add(dragFicha, c);
            	dragFicha = null;
            	revalidate();
            	repaint();
            	return;
            	/*
                int r = -1;
                int c = -1;
                /*
                searchTablero: for (int row = 0; row < panelGrid.length; row++) {
                    for (int col = 0; col < panelGrid[row].length; col++) {
                        if (panelGrid[row][col] == droppedPanel) {
                            r = row;
                            break searchTablero;
                        }
                    }
                } 

                if (r == -1) {
                    // if off the grid, return label to home
                    clickedPanel.add(dragFicha);
                    clickedPanel.revalidate();
                } else {
                	clickedPanel.add(dragFicha);
                    clickedPanel.revalidate();
                    /*droppedPanel.add(dragFicha);
                    droppedPanel.revalidate();
                    
                }*/
            }

            repaint();
            dragFicha = null;
        }
		
		
	}
}

