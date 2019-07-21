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
	private static final int FICHA_HSIZE = 40;
	private static final int FICHA_VSIZE = 80;
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
				   pilaBackPanel,
				   pilaButtonPanel,
				   jugadorPanel;// center > South Contiene las Fichas del Jugador y un JTextArea
	private JPanel inicioPanel; // Aquí se muestran las fichas para escoger quien inicia
				  
	
	private ImageJPanel tableroPanel;// Center > Center // Contiene las fichas que se colocan durante la partida
	private Control control;  // Lleva el mecanismo del juego
	private JTextArea dineroText;  // Contiene el Dinero y la apuesta (visualmente)
	private GridBagConstraints c = new GridBagConstraints();
	private int xIzq, yIzq, //Coordenadas del lado izquierdo del tablero (para colocar fichas)
				xDer, yDer; //Coordenadas del lado derecho del tablero
	private boolean esquinaIzq, IzqA1, esquinaDer;
	
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
		xIzq = 21;
		yIzq = 8;
		xDer = 21;
		yDer = 8;
		esquinaIzq = false;
		IzqA1 = false;
		esquinaDer = false;
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
		tableroPanel.setPreferredSize(new Dimension(1161, 481));
		
		
		
		// Crear la matriz del tablero (GridBagLayout)
		Dimension HPanelDimension = new Dimension(20, 1);
		Dimension VPanelDimension = new Dimension(1, 20);
		c = new GridBagConstraints();
		c.gridy = 0;
		for (int col=1; col<=58; col++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(HPanelDimension);
			c.gridx = col;
			tableroPanel.add(panel, c);
		}
		c.gridx = 0;
		for (int row=1; row<=24; row++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(VPanelDimension);
			c.gridy = row;
			tableroPanel.add(panel, c);
		}
		
		
		zonaJuego.add(tableroPanel, BorderLayout.PAGE_END);
		
		allPanel.add(zonaJuego, BorderLayout.CENTER);
		
		//-------------- Panel jugador --------------
		jugadorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
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
		
		pilaPanel = new JPanel(new FlowLayout());
		pilaPanel.setPreferredSize(new Dimension(110, 580));
		pilaPanel.setBackground(Color.black);
		
		Titulos tituloPila = new Titulos("Fichas ", 30, Color.black);
		
		pilaPanel.add(tituloPila);
		
		pilaBackPanel = new JPanel(new BorderLayout());
		pilaBackPanel.setPreferredSize(new Dimension(110, 615));
		pilaBackPanel.add(pilaPanel, BorderLayout.CENTER);
		
		pilaButtonPanel = new JPanel(new BorderLayout());
		pilaButtonPanel.setPreferredSize(new Dimension(110, 35));
		pilaButtonPanel.setBackground(Color.black);
		
		getFicha = new JButton("Coger Ficha");
		getFicha.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		getFicha.setForeground(Color.white);
		getFicha.setBackground(Color.cyan.darker().darker().darker());
		getFicha.setPreferredSize(new Dimension(110,35));
		getFicha.addActionListener(escucha);
		
		pilaButtonPanel.add(getFicha);
		
		pilaBackPanel.add(pilaButtonPanel, BorderLayout.PAGE_END);
		
		allPanel.add(pilaBackPanel, BorderLayout.LINE_END);
		
		layeredPane.setBackground(Color.black);
		
		revalidate();
		repaint();
	}
	
	private boolean escogerInicio() { /*// Escoge quién inicia la partida
		// si el jugador saca más alto él inicia y retorna true, si la máquina empieza retorna false
		ArrayList<Ficha> fichas = control.getFichas();
		inicioPanel = (JPanel) this.getGlassPane();
		inicioPanel.setOpaque(true);
		inicioPanel.setPreferredSize(WINDOW_SIZE);
		inicioPanel.setBackground(Color.black);
		inicioPanel.setVisible(true);
		
		revalidate();
		repaint();
		*/
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
		if (tableroSize == 0) { // PRIMERA FICHA, puede ser cualquier ficha
			if (vIzq != vDer) { // Si no es doble la coloca horizontal
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4;
				xDer +=  4;
				dragFicha.girarFicha(Ficha.ROTAR_IZQ);
        		dragFicha.setPreferredSize(FICHA_H);
			} else {  // Si es doble la coloca vertical
				c.gridx = xIzq + 1;
				c.gridy = yIzq - 1;
				c.gridwidth = 2;
				c.gridheight = 4;
				xIzq += -3;
				xDer +=  3;
			}
			fichasTablero.add(dragFicha);
			return true;
		}
		vIzqTablero = fichasTablero.get(0).getvIzq();
		vDerTablero = fichasTablero.get(tableroSize-1).getvDer();
		if (dragFicha.getvIzq() != vIzqTablero &&
			dragFicha.getvIzq() != vDerTablero &&
			dragFicha.getvDer() != vIzqTablero &&
			dragFicha.getvDer() != vDerTablero
			) { // si la ficha no es una jugada válida, no la coloca en el tablero.
			return false;
		}
		//------------------- TABLERO LADO IZQUIERDO -------------------
		if (vIzqTablero == dragFicha.getvIzq() || vIzqTablero == dragFicha.getvDer()) {
			// ¿La ficha se puede colocar en la izquierda?
			boolean esquina = false;
			int porMenos = 1;
			if (xIzq < 1) {
				if (fichasTablero.get(0).getvIzq() == fichasTablero.get(0).getvDer())
					// ¿La ficha anterior era doble cara?
					yIzq += 1;
				yIzq += 2;
				xIzq += 4;
				c.gridwidth = 2;
				c.gridheight = 4;
				esquina = true;
			}
			if (esquinaIzq)
				porMenos = -1;
			//  -------------- FICHA CON IZQ == DER ----------------
			if (dragFicha.getvIzq() == dragFicha.getvDer()) { // ¿Es doble cara? Vertical
				if (!esquina && !esquinaIzq) {
					xIzq +=  2;
					yIzq += -1;
				}
				c.gridx = xIzq;
				c.gridy = yIzq;
				yIzq += 1;
				c.gridwidth = 2;
				c.gridheight = 4;
				xIzq += -4;
				if (esquinaIzq) {
					xIzq +=  6;
					yIzq += -1;
					if (IzqA1)	{ // Caso especial
						yIzq += 1;
						IzqA1 = false;
					}
					c.gridy = yIzq - 1;
				}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 3;
					IzqA1 = true;
					esquinaIzq = true;
				}
				fichasTablero.add(0, dragFicha);
				return true;
				// FICHA LADO DERECHO == TABLERO LADO IZQUIERDO
			} else if (dragFicha.getvDer() == vIzqTablero) { 
				// El lado derecho de la ficha coincide con el izquierdo del tablero?
				dragFicha.girarFicha(Ficha.ROTAR_IZQ*porMenos); // gira la ficha hacia la izquierda
        		dragFicha.setPreferredSize(FICHA_H);
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				if (esquinaIzq) {
        			dragFicha.cambiarVal();
        			IzqA1 = false;
        		}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					dragFicha.girarFicha(Ficha.ROTAR_ABAJO);
					dragFicha.setPreferredSize(FICHA_V);
					dragFicha.cambiarVal();
					esquinaIzq = true;
					IzqA1 = true;
				}
        		
        		fichasTablero.add(0, dragFicha);
				return true;
				// FICHA LADO IZQUIERDO == TABLERO LADO IZQUIERDO (ELSE)
			} else { // el lado izquierdo entonces coincide con el izquierdo del tablero.
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				dragFicha.girarFicha(Ficha.ROTAR_DER*porMenos); // gira la ficha hacia la derecha
				dragFicha.setPreferredSize(FICHA_H);
				if (esquinaIzq) {
        			dragFicha.cambiarVal();
        			IzqA1 = false;
        		}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					dragFicha.girarFicha(Ficha.ROTAR_0);
					dragFicha.setPreferredSize(FICHA_V);
					dragFicha.cambiarVal();
					esquinaIzq = true;
					IzqA1 = true;
				}
				
				fichasTablero.add(0, dragFicha);
				return true;
			}
		}
		// ------------------- TABLERO LADO DERECHO ------------------------
		if (vDerTablero == dragFicha.getvIzq() || vDerTablero == dragFicha.getvDer()) {
			// ¿La ficha se puede colocar en la derecha?
			boolean esquina = false;
			int porMenos = 1;
			if (xDer > 45-4) {
				if (fichasTablero.get(tableroSize-1).getvIzq() == fichasTablero.get(tableroSize-1).getvDer())
					// La ficha anterior era doble cara?
					yIzq += 1;
				yIzq += 2;
				xIzq += 4;
				c.gridwidth = 2;
				c.gridheight = 4;
				esquina = true;
			}
			if (esquinaIzq)
				porMenos = -1;
			//  -------------- FICHA CON IZQ == DER ----------------
			if (dragFicha.getvIzq() == dragFicha.getvDer()) { // ¿Es doble cara? Vertical
				if (!esquina && !esquinaDer) {
					yDer += -1;
				}
				c.gridx = xDer;
				c.gridy = yDer;
				yDer += 1;
				c.gridwidth = 2;
				c.gridheight = 4;
				xDer += 2;
				if (esquinaDer)
					xDer += -6;
				if (esquina && !esquinaDer) {
					xDer +=  4;
					yDer += -3;
					esquinaDer = true;
				}
				fichasTablero.add(dragFicha);
				return true;
				// FICHA LADO IZQUIERDO == TABLERO LADO DERECHO
			} else if (dragFicha.getvIzq() == vDerTablero) { 
				// El lado derecho de la ficha coincide con el izquierdo del tablero?
				dragFicha.girarFicha(Ficha.ROTAR_IZQ*porMenos); // gira la ficha hacia la izquierda
				dragFicha.setPreferredSize(FICHA_H);
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				if (esquinaIzq) {
					dragFicha.cambiarVal();
				}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					dragFicha.girarFicha(Ficha.ROTAR_ABAJO);
					dragFicha.setPreferredSize(FICHA_V);
					dragFicha.cambiarVal();
					esquinaIzq = true;
				}

				fichasTablero.add(dragFicha);
				return true;
				// FICHA LADO IZQUIERDO == TABLERO LADO IZQUIERDO (ELSE)
			} else { // el lado izquierdo entonces coincide con el izquierdo del tablero.
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				dragFicha.girarFicha(Ficha.ROTAR_DER*porMenos); // gira la ficha hacia la derecha
				dragFicha.setPreferredSize(FICHA_H);
				if (esquinaIzq) {
					dragFicha.cambiarVal();
				}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					dragFicha.girarFicha(Ficha.ROTAR_0);
					dragFicha.setPreferredSize(FICHA_V);
					dragFicha.cambiarVal();
					esquinaIzq = true;
				}

				fichasTablero.add(dragFicha);
				return true;
			}
		}
		
    	return false;
	}
	
	private void cogerFicha(boolean quien) {
		control.cogerFicha(quien);
		printFichas();
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
				cogerFicha(true);
			}
		}
		
		@Override
        public void mousePressed(MouseEvent me) {
			clickedPanel = jugadorPanel;
			ArrayList<Ficha> fichasJugador = control.getFichasJugador();
            if (!(clickedPanel.getComponentAt(me.getPoint()) instanceof Ficha)) {
            	return; // Sino es una Ficha lo que se clickeo, se cancela.
            }
            
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
			ArrayList<Ficha> fichasJugador = control.getFichasJugador();
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
            		fichasJugador.remove(dragFicha);
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




