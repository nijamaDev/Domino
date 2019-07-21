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

import codigoExterno.Titulos;

/**
 * 
 */
public class Domino extends JFrame {

	private static final int WINDOW_HSIZE = 1270;
	private static final int WINDOW_VSIZE = 720;
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
	private JPanel inicioPanel; // Aquí se muestran las fichas para escoger quien inicia
				  
	
	private ImageJPanel tableroPanel;// Center > Center // Contiene las fichas que se colocan durante la partida
	private Control control;  // Lleva el mecanismo del juego
	private JTextArea dineroText;  // Contiene el Dinero y la apuesta (visualmente)
	private GridBagConstraints c = new GridBagConstraints();
	private int xIzq = 21, yIzq = 7,
				xDer = 21, yDer = 7;
	
	public Domino() {
		control = new Control();
		initGUI();

		// default window configuration
		setUndecorated(true);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		nuevaPartida();
	}
	
	public void nuevaPartida() {
		control.nuevaPartida();
		nuevaRonda();
	}
	
	public void nuevaRonda() {
		control.nuevaRonda(escogerInicio());
		c = new GridBagConstraints();
		printDinero();
		printFichas();
	}
	
	private void initGUI() { // crear la GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// define window container and layout
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(WINDOW_SIZE);
		allPanel = new JPanel(new BorderLayout());
		
		allPanel.setSize(layeredPane.getPreferredSize());
		allPanel.setLocation(0, 0);
		this.getContentPane().setBackground(Color.black);
		layeredPane.add(allPanel, JLayeredPane.DEFAULT_LAYER);
		add(layeredPane);
		this.getContentPane().add(layeredPane, BorderLayout.CENTER);
		c = new GridBagConstraints();
		
		// crea los escuchas
		escucha = new Escuchas();
		
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
		
		zonaJuego.add(oponentPanel, BorderLayout.PAGE_START);
		
		// Panel del tablero
		Image imagen;
		try {
			imagen = new ImageIcon(ImageIO.read(new File("src/imagenes/tablero.jpg"))
								  ).getImage().getScaledInstance(1150, 400, Image.SCALE_SMOOTH);
			tableroPanel = new ImageJPanel(imagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableroPanel.setLayout(new GridBagLayout());
		tableroPanel.setPreferredSize(new Dimension(1150, 480));
		
		Titulos tabl = new Titulos("h", 30, Color.black);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		tableroPanel.add(tabl, c);
		
		// Crear la matriz del tablero (GridBagLayout)
		Dimension HPanelDimension = new Dimension(25, 1);
		Dimension VPanelDimension = new Dimension(1, 25);
		c = new GridBagConstraints();
		c.gridy = 0;
		for (int col=1; col<=44; col++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(HPanelDimension);
			c.gridx = col;
			tableroPanel.add(panel, c);
		}
		c.gridx = 0;
		for (int row=1; row<=16; row++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(VPanelDimension);
			c.gridy = row;
			tableroPanel.add(panel, c);
		}
		
		
		zonaJuego.add(tableroPanel, BorderLayout.PAGE_END);
		
		allPanel.add(zonaJuego, BorderLayout.CENTER);
		
		//-------------- Panel jugador --------------
		jugadorPanel = new JPanel();
		jugadorPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		jugadorPanel.setPreferredSize(new Dimension(1200, 110));
		jugadorPanel.setBackground(Color.black);
		jugadorPanel.addMouseListener(escucha);
		jugadorPanel.addMouseMotionListener(escucha);
				
		
		dineroText = new JTextArea();
		dineroText.setForeground(Color.white);
		dineroText.setBackground(Color.black);
		dineroText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		dineroText.setEditable(false);
		dineroText.setPreferredSize(new Dimension(100, 100));
		//printDinero();
		
		jugadorPanel.add(dineroText);
		
		allPanel.add(jugadorPanel, BorderLayout.PAGE_END);
		
		pilaPanel = new JPanel();
		pilaPanel.setLayout(new FlowLayout());
		pilaPanel.setPreferredSize(new Dimension(110, 615));
		pilaPanel.setBackground(Color.black);
		
		Titulos tituloPila = new Titulos("Fichas ", 30, Color.black);
		
		pilaPanel.add(tituloPila);
		
		allPanel.add(pilaPanel, BorderLayout.LINE_END);
		
		layeredPane.setBackground(Color.black);
		
		revalidate();
		repaint();
	}
	
	private boolean escogerInicio() { // Escoge quién inicia la partida
		// si el jugador saca más alto él inicia y retorna true, si la máquina empieza retorna false
		ArrayList<Ficha> fichas = control.getFichas();
		inicioPanel = (JPanel) this.getGlassPane();
		inicioPanel.setOpaque(true);
		inicioPanel.setPreferredSize(WINDOW_SIZE);
		inicioPanel.setBackground(Color.black);
		inicioPanel.setVisible(true);
		
		revalidate();
		repaint();
		
		return false; //place holder
	}
	
	private void printDinero() { // Muestra el dinero y la apuesta actual
		dineroText.setText(
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
	
	private boolean colocarFicha(Ficha dragFicha) { // Coloca la ficha en el tablero
		ArrayList<Ficha> fichasTablero = control.getFichasTablero();
		int tableroSize = fichasTablero.size(),
			vIzqTablero,
			vDerTablero,
			vIzq = dragFicha.getvIzq(), 
			vDer = dragFicha.getvDer();
		if (tableroSize == 0) { // Primera ficha, puede ser cualquier ficha
			if (vIzq != vDer) {
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4;
				xDer += 4;
				dragFicha.girarFicha(Ficha.ROTAR_IZQ);
        		dragFicha.setPreferredSize(FICHA_H);
			} else {
				c.gridx = 22;
				c.gridy = 6;
				c.gridwidth = 2;
				c.gridheight = 4;
			}
			fichasTablero.add(dragFicha);
			return true;
		}
		vIzqTablero = fichasTablero.get(0).getvIzq();
		vDerTablero = fichasTablero.get(tableroSize-1).getvDer();
		
		
    	return false;
	}
	
	private void ganar(int ganador) {
		switch (ganador) {
		case -1:
			//el juego sigue, nadie ha ganado
			return;
		case 0:
			//perdedor (ganó la máquina)
			break;
		case 1:
			//ganador
			break;
		}
		
	}
	
	private  class Escuchas extends MouseAdapter implements ActionListener{
		private  Ficha dragFicha = null;
		private  int mX, mY, dragFichaHCenter, dragFichaVCenter;
		private  JPanel clickedPanel = null;
		
        @Override
		public void actionPerformed(ActionEvent eventAction) {
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

            mX = me.getX() - dragFichaHCenter;
            mY = me.getY() + WINDOW_VSIZE - 110 - dragFichaVCenter;
            layeredPane.add(dragFicha, JLayeredPane.DRAG_LAYER);
            dragFicha.setLocation(mX, mY);
            repaint();
        }
		
		@Override
        public void mouseDragged(MouseEvent me) {
            if (dragFicha == null) {
                return;
            }
            mX = me.getX() - dragFichaHCenter;
            mY = me.getY() + WINDOW_VSIZE - 110 - dragFichaVCenter;
            dragFicha.setLocation(mX, mY);
            repaint();
        }
		
		@Override
        public void mouseReleased(MouseEvent me) {
			//System.out.println("MouseReleased");
            if (dragFicha == null) { // Just in case
                return;
            }
            remove(dragFicha); // Quita la ficha del layeredPane
            Point p = new Point(mX, mY);
            JPanel droppedPanel = (JPanel) zonaJuego.getComponentAt(p);
            if (droppedPanel != tableroPanel) {
                // Si la ficha no se coloca en el tablero, la devuelve al jugador
                clickedPanel.add(dragFicha);
                clickedPanel.revalidate();
            } else {
            	if(colocarFicha(dragFicha)) { // Si la ficha puede colocarse se coloca
            		droppedPanel.add(dragFicha, c);
            	} else { // Si no se puede colocar, se devuelve
            		clickedPanel.add(dragFicha);
            	}
            	revalidate();
            }
            repaint();
            dragFicha = null;
        }
	}
}




