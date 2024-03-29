/**
  Archivo: Domino.java
  Fecha creación:		Jul 14, 2019
  Última modificación:	Jul 22, 2019
  Versión: 0.9
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;
import java.awt.Point;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import codigoExterno.Titulos;

// TODO: Auto-generated Javadoc
/**
 * The Class Domino.
 */
public class Domino extends JFrame {

	/** The Constant FICHA_VSIZE. */
	private static final int 
		WINDOW_HSIZE   = 1270, WINDOW_VSIZE   = 720,
							   TITULOP_VSIZE  =  30,
							   OPONENTP_VSIZE =  80,
		TABLEROP_HSIZE = 1160, TABLEROP_VSIZE = 620,
		ZONAJUEGO_VSIZE = TABLEROP_VSIZE+OPONENTP_VSIZE,
		PILABACKP_HSIZE = WINDOW_HSIZE - TABLEROP_HSIZE,
		PILABUTP_VSIZE = 35,
		PILAP_VSIZE = ZONAJUEGO_VSIZE - PILABUTP_VSIZE,
		JUGADORP_VSIZE = WINDOW_VSIZE-TABLEROP_VSIZE,
		
		FICHA_HSIZE = 40, FICHA_VSIZE = 80;
	
	/** The Constant FICHA_H. */
	private static final Dimension 
		WINDOW_SIZE    = new Dimension(WINDOW_HSIZE,    WINDOW_VSIZE),
		TITULOP_SIZE   = new Dimension(WINDOW_HSIZE,    TITULOP_VSIZE),
		OPONENTP_SIZE  = new Dimension(TABLEROP_HSIZE,  OPONENTP_VSIZE),
		TABLEROP_SIZE  = new Dimension(TABLEROP_HSIZE,  TABLEROP_VSIZE),
		ZONAJUEGO_SIZE = new Dimension(TABLEROP_HSIZE,  ZONAJUEGO_VSIZE),
		PILABACKP_SIZE = new Dimension(PILABACKP_HSIZE, ZONAJUEGO_VSIZE),
		PILABUTP_SIZE  = new Dimension(PILABACKP_HSIZE, PILABUTP_VSIZE),
		PILAP_SIZE     = new Dimension(PILABACKP_HSIZE, PILAP_VSIZE),
		JUGADORP_SIZE  = new Dimension(WINDOW_HSIZE,    JUGADORP_VSIZE),
		INICIOFICHAP   = new Dimension(WINDOW_HSIZE,    WINDOW_VSIZE - TITULOP_VSIZE),
		FICHA_V        = new Dimension(FICHA_HSIZE,     FICHA_VSIZE),
		FICHA_H        = new Dimension(FICHA_VSIZE,     FICHA_HSIZE);
	
	/** The escucha. */
	private Escuchas escucha;
	
	/** The escucha botones. */
	private EscuchaBotones escuchaBotones;
	
	/** The escucha inicio. */
	private EscuchaInicio escuchaInicio;
	
	/** The get ficha. */
	private JButton nuevo, salir, getFicha;
	
	/** The layered pane. */
	private JLayeredPane layeredPane; // necesario para arrastrar las fichas (se colola en this.getContentPane()
	
	/** The jugador panel. */
	private JPanel allPanel,    // Panel para agregar los demás paneles
				   tituloPanel, // North -- contiene el botón nuevo y salir y el título "Dominó"  
				   zonaJuego,	// Center -- contiene los paneles opponentPanel, tablero y jugadorPanel.
				   oponentPanel,// Center > North -- Contiene las fichas del computador
				   pilaPanel,	// West -- contiene las fichas disponibles para comer (pila)
				   pilaBackPanel,
				   pilaButtonPanel,
				   jugadorPanel;// center > South Contiene las Fichas del Jugador y un JTextArea
	
	/** The inicio panel. */
	private JPanel inicioPanel; // Aquí se muestran las fichas para escoger quien inicia
				  
	
	/** The tablero panel. */
	private ImageJPanel tableroPanel;// Center > Center // Contiene las fichas que se colocan durante la partida
	
	/** The control. */
	private Control control;  // Lleva el mecanismo del juego
	
	/** The dinero text. */
	private JTextArea dineroText;  // Contiene el Dinero y la apuesta (visualmente)
	
	/** The c. */
	private GridBagConstraints c = new GridBagConstraints();
	
	/** The y der. */
	private int xIzq, yIzq, //Coordenadas del lado izquierdo del tablero (para colocar fichas)
				xDer, yDer; //Coordenadas del lado derecho del tablero
	
	/** The Der A 1. */
	private boolean esquinaIzq, IzqA1, esquinaDer, DerA1;
	
	/**
	 * Instantiates a new domino.
	 */
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
	
	/**
	 * Inits the GUI.
	 */
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
		escuchaBotones = new EscuchaBotones();
		
		//-------------- Panel superior --------------
		tituloPanel = new JPanel();
		tituloPanel.setLayout(new GridBagLayout());
		tituloPanel.setBackground(Color.black);
		tituloPanel.setPreferredSize(TITULOP_SIZE);
		
		// Botón Nuevo
		nuevo = new JButton("Nuevo");
		nuevo.addActionListener(escuchaBotones);
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
		salir.addActionListener(escuchaBotones);
		
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
		zonaJuego.setPreferredSize(ZONAJUEGO_SIZE);
		
		// Panel del oponente (computador)
		oponentPanel = new JPanel();
		oponentPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		oponentPanel.setPreferredSize(OPONENTP_SIZE);
		oponentPanel.setBackground(Color.black);
		Titulos oponente = new Titulos("Oponente:", 25, Color.black);
		
		oponentPanel.add(oponente);
		
		zonaJuego.add(oponentPanel, BorderLayout.PAGE_START);
		
		// Panel del tablero
		Image imagen;
		try {
			imagen = new ImageIcon(ImageIO.read(new File("src/imagenes/tablero.jpg"))
								  ).getImage().getScaledInstance(TABLEROP_HSIZE, TABLEROP_VSIZE, Image.SCALE_SMOOTH);
			tableroPanel = new ImageJPanel(imagen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableroPanel.setLayout(new GridBagLayout());
		tableroPanel.setPreferredSize(TABLEROP_SIZE);
		
		// Crear la matriz del tablero (GridBagLayout)
		Dimension HPanelDimension = new Dimension(20, 0);
		Dimension VPanelDimension = new Dimension(0, 20);
		c = new GridBagConstraints();
		c.gridy = 0;
		for (int col=1; col<=58; col++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(HPanelDimension);
			c.gridx = col;
			tableroPanel.add(panel, c);
		}
		c.gridx = 0;
		for (int row=1; row<=31; row++) {
			JPanel panel = new JPanel();
			panel.setPreferredSize(VPanelDimension);
			c.gridy = row;
			tableroPanel.add(panel, c);
		}
		
		
		zonaJuego.add(tableroPanel, BorderLayout.PAGE_END);
		
		allPanel.add(zonaJuego, BorderLayout.CENTER);
		
		//-------------- Panel jugador --------------
		jugadorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		jugadorPanel.setPreferredSize(JUGADORP_SIZE);
		jugadorPanel.setBackground(Color.black);
		jugadorPanel.addMouseListener(escucha);
		jugadorPanel.addMouseMotionListener(escucha);
		
		dineroText = new JTextArea();
		dineroText.setForeground(Color.white);
		dineroText.setBackground(Color.black);
		dineroText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		dineroText.setEditable(false);
		dineroText.setPreferredSize(new Dimension(90, 90));
		//printDinero();
		
		jugadorPanel.add(dineroText);
		
		allPanel.add(jugadorPanel, BorderLayout.PAGE_END);
		
		pilaPanel = new JPanel(new FlowLayout());
		pilaPanel.setPreferredSize(PILAP_SIZE);
		pilaPanel.setBackground(Color.black);
		
		Titulos tituloPila = new Titulos("Fichas ", 30, Color.black);
		
		pilaPanel.add(tituloPila);
		
		pilaBackPanel = new JPanel(new BorderLayout());
		pilaBackPanel.setPreferredSize(PILABACKP_SIZE);
		pilaBackPanel.add(pilaPanel, BorderLayout.CENTER);
		
		pilaButtonPanel = new JPanel(new BorderLayout());
		pilaButtonPanel.setPreferredSize(PILABUTP_SIZE);
		pilaButtonPanel.setBackground(Color.black);
		
		getFicha = new JButton("Coger Ficha");
		getFicha.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		getFicha.setForeground(Color.white);
		getFicha.setBackground(Color.cyan.darker().darker().darker());
		getFicha.setPreferredSize(PILABUTP_SIZE);
		getFicha.addActionListener(escuchaBotones);
		
		pilaButtonPanel.add(getFicha, BorderLayout.CENTER);
		
		pilaBackPanel.add(pilaButtonPanel, BorderLayout.PAGE_END);
		
		allPanel.add(pilaBackPanel, BorderLayout.LINE_END);
		
		layeredPane.setBackground(Color.black);
		
		revalidate();
		repaint();
	}
	
	/**
	 * Nueva partida.
	 */
	public void nuevaPartida() {
		control.nuevaPartida();
		escogerInicio();
	}
	
	/**
	 * Escoger inicio.
	 */
	private void escogerInicio() { // Escoge quién inicia la partida
		// si el jugador saca más alto él inicia y retorna true, si la máquina empieza retorna false
		if (!control.puedeApostar()) {
			JOptionPane.showMessageDialog(this, "¿No tienes dinero? ¡Fuera de aquí!");
			return;
		}
		escuchaInicio = new EscuchaInicio();
		ArrayList<Ficha> fichas = control.getFichas();
		inicioPanel = (JPanel) this.getGlassPane();
		inicioPanel.setLayout(new BorderLayout());
		inicioPanel.setOpaque(true);
		inicioPanel.setPreferredSize(WINDOW_SIZE);
		inicioPanel.setBackground(Color.black);
		inicioPanel.setVisible(true);
		
		Titulos tituloInicio = new Titulos("Escoge una ficha", 30, Color.black);
		inicioPanel.add(tituloInicio, BorderLayout.PAGE_START);
		
		JPanel inicioFichasPanel = new JPanel(new GridLayout(4, 7));
		inicioFichasPanel.setPreferredSize(INICIOFICHAP);
		inicioFichasPanel.setBackground(Color.black);
		inicioFichasPanel.addMouseListener(escuchaInicio);
		inicioPanel.add(inicioFichasPanel, BorderLayout.CENTER);
		inicioPanel.setLocation(10, 0);
		for (int i=0; i<28; i++) {
			fichas.get(i).taparFicha();
			fichas.get(i).setHorizontalAlignment(JLabel.CENTER);
			inicioFichasPanel.add(fichas.get(i), i);
		}
		//inicioPanel.setVisible(false);
		revalidate();
		repaint();
		
		return; //place holder
	}
	
	/**
	 * Iniciar.
	 *
	 * @param quien the quien
	 */
	private void iniciar(boolean quien) {
		ArrayList<Ficha> fichasJugador = control.getFichasJugador();
		for (int i=0; i<fichasJugador.size(); i++) {
			fichasJugador.get(i).destaparFicha();
		}
		inicioPanel.removeAll();
		inicioPanel.setVisible(false);
		nuevaRonda(quien);
	}
	
	/**
	 * Nueva ronda.
	 *
	 * @param inicia the inicia
	 */
	public void nuevaRonda(boolean inicia) {
		control.nuevaRonda();
		c = new GridBagConstraints();
		xIzq = 28;
		yIzq = 12;
		xDer = xIzq;
		yDer = yIzq;
		esquinaIzq = false;
		IzqA1 = false;
		esquinaDer = false;
		DerA1 = false;
		if (!inicia) { // inicia la máquina
			Ficha ficha1 = control.getFichasOponente().get(0);
			if (colocarFicha(ficha1)) {
				control.getFichasOponente().remove(ficha1);
				oponentPanel.remove(ficha1);
				tableroPanel.add(ficha1, c);
			}
		}
		printDinero();
		printFichas();
	}
	
	/**
	 * Prints the dinero.
	 */
	private void printDinero() { // Muestra el dinero y la apuesta actual
		dineroText.setText(
				"Dinero:\n"+ control.getDinero() + "\n" +
				"Apuesta:\n" + control.getApuesta());
	}
	
	
	/**
	 * Prints the fichas.
	 */
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
	
	
	/**
	 * Coger ficha.
	 *
	 * @param quien the quien
	 * @return the ficha
	 */
	private Ficha cogerFicha(boolean quien) {
		if (!quien) {
			Ficha ficha = null;
			ficha = control.hacerJugada();
			while (control.getPila().size() > 0) {
				ficha = control.hacerJugada();
				if (ficha != null)
					return ficha;
				if(!control.cogerFicha(false)) {
					if(ganar())
						escogerInicio();
					return ficha;
				}
			}
			return ficha;
		} else {
			control.cogerFicha(true);
			printFichas();
			if(ganar())
	    		escogerInicio();
		}
		return null;
	}
	
	/**
	 * Hacer jugada.
	 */
	private void hacerJugada() {
		Ficha fichaOponente = cogerFicha(false);
		for (int i=0; i<control.getFichasOponente().size(); i++) {
			oponentPanel.add(control.getFichasOponente().get(i));
			pilaPanel.remove(control.getFichasOponente().get(i));
		}
		pilaPanel.revalidate();
		pilaPanel.repaint();
        if (fichaOponente != null) {
        	if (colocarFicha(fichaOponente)) {
        		control.getFichasOponente().remove(fichaOponente);
            	oponentPanel.remove(fichaOponente);
            	tableroPanel.add(fichaOponente, c);
            	revalidate();
        		repaint();
            	if(ganar())
        			escogerInicio();
        		return;
        	}
        	else {
        		JOptionPane.showMessageDialog(allPanel, "Something is wrong...");
        		//something is wrong...
        	}
        } else { // computador no puede continuar
        	if(ganar())
        		escogerInicio();
        }
	}
	
	/**
	 * Ganar.
	 *
	 * @return true, if successful
	 */
	private boolean ganar() {
		int ganador = control.ganar();
		switch (ganador) {
		case -2:
		case -1:
			//el juego sigue, nadie ha ganado
			return false;
		case 0:
			//perdedor (ganó la máquina)
			printDinero();
			printFichas();
			revalidate();
			repaint();
			JOptionPane.showMessageDialog(jugadorPanel, "Has perdido.");
			return true;
		case 1:
			//ganador
			printDinero();
			printFichas();
			repaint();
			JOptionPane.showMessageDialog(jugadorPanel, "GANADOR");
			return true;
		}
		return false;
	}
	
	/**
	 * Colocar ficha.
	 *
	 * @param ficha the ficha
	 * @return true, if successful
	 */
	private boolean colocarFicha(Ficha ficha) { // Coloca la ficha en el tablero
		ArrayList<Ficha> fichasTablero = control.getFichasTablero();
		int tableroSize = fichasTablero.size(),
			vIzqTablero,
			vDerTablero,
			vIzq = ficha.getvIzq(), 
			vDer = ficha.getvDer();
		if (tableroSize == 0) { // PRIMERA FICHA, puede ser cualquier ficha
			if (vIzq != vDer) { // Si no es doble la coloca horizontal
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4;
				xDer +=  4;
				ficha.girarFicha(Ficha.ROTAR_IZQ);
        		ficha.setPreferredSize(FICHA_H);
			} else {  // Si es doble la coloca vertical
				c.gridx = xIzq + 1;
				c.gridy = yIzq - 1;
				c.gridwidth = 2;
				c.gridheight = 4;
				xIzq += -3;
				xDer +=  3;
				ficha.destaparFicha();
			}
			fichasTablero.add(ficha);
			return true;
		}
		vIzqTablero = fichasTablero.get(0).getvIzq();
		vDerTablero = fichasTablero.get(tableroSize-1).getvDer();
		if (ficha.getvIzq() != vIzqTablero &&
			ficha.getvIzq() != vDerTablero &&
			ficha.getvDer() != vIzqTablero &&
			ficha.getvDer() != vDerTablero
			) { // si la ficha no es una jugada válida, no la coloca en el tablero.
			return false;
		}
		ficha.destaparFicha();
		//------------------- TABLERO LADO IZQUIERDO -------------------
		if (vIzqTablero == ficha.getvIzq() || vIzqTablero == ficha.getvDer()) {
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
			if (ficha.getvIzq() == ficha.getvDer()) { // ¿Es doble cara? Vertical
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
				fichasTablero.add(0, ficha);
				return true;
				// FICHA LADO DERECHO == TABLERO LADO IZQUIERDO
			} else if (ficha.getvDer() == vIzqTablero) { 
				// El lado derecho de la ficha coincide con el izquierdo del tablero?
				ficha.girarFicha(Ficha.ROTAR_IZQ*porMenos); // gira la ficha hacia la izquierda
        		ficha.setPreferredSize(FICHA_H);
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				if (esquinaIzq) {
        			ficha.cambiarVal();
        			IzqA1 = false;
        		}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					ficha.girarFicha(Ficha.ROTAR_ABAJO);
					ficha.setPreferredSize(FICHA_V);
					ficha.cambiarVal();
					esquinaIzq = true;
					IzqA1 = true;
				}
        		
        		fichasTablero.add(0, ficha);
				return true;
				// FICHA LADO IZQUIERDO == TABLERO LADO IZQUIERDO (ELSE)
			} else { // el lado izquierdo entonces coincide con el izquierdo del tablero.
				c.gridx = xIzq;
				c.gridy = yIzq;
				c.gridwidth = 4;
				c.gridheight = 2;
				xIzq += -4*porMenos;
				ficha.girarFicha(Ficha.ROTAR_DER*porMenos); // gira la ficha hacia la derecha
				ficha.setPreferredSize(FICHA_H);
				if (esquinaIzq) {
        			ficha.cambiarVal();
        			IzqA1 = false;
        		}
				if (esquina && !esquinaIzq) {
					xIzq += 4;
					yIzq += 4;
					c.gridwidth = 2;
					c.gridheight = 4;
					ficha.girarFicha(Ficha.ROTAR_0);
					ficha.setPreferredSize(FICHA_V);
					ficha.cambiarVal();
					esquinaIzq = true;
					IzqA1 = true;
				}
				
				fichasTablero.add(0, ficha);
				return true;
			}
		}
		// ------------------- TABLERO LADO DERECHO ------------------------
		if (vDerTablero == ficha.getvIzq() || vDerTablero == ficha.getvDer()) {
			// ¿La ficha se puede colocar en la derecha?
			boolean esquina = false;
			int porMenos = 1;
			if (xDer > 58-4) {
				if (fichasTablero.get(tableroSize-1).getvIzq()
					==
					fichasTablero.get(tableroSize-1).getvDer())
					// La ficha anterior era doble cara?
					yDer += -1;
				yDer += -4;
				xDer += -2;
				c.gridwidth = 2;
				c.gridheight = 4;
				esquina = true;
			}
			if (esquinaDer)
				porMenos = -1;
			//  -------------- FICHA CON IZQ == DER ----------------
			if (ficha.getvIzq() == ficha.getvDer()) { // ¿Es doble cara? Vertical
				if (!esquina && !esquinaDer) {
					yDer += -1;
				}
				c.gridx = xDer;
				c.gridy = yDer;
				yDer += 1;
				c.gridwidth = 2;
				c.gridheight = 4;
				xDer += 2;
				if (esquinaDer) {
					c.gridx = xDer;
					xDer += 2;
					c.gridy = yDer - 2;
					if(DerA1) {
						yDer += -3;
						c.gridy = yDer;
						yDer += 2;
						DerA1 = false;
					}
					yDer += -1;
					xDer += -6;
				}
				if (esquina && !esquinaDer) {
					xDer += -4;
					yDer += -3;
					DerA1 = true;
					esquinaDer = true;
				}
				fichasTablero.add(ficha);
				return true;
				
				// FICHA LADO IZQUIERDO == TABLERO LADO DERECHO
			} else if (ficha.getvIzq() == vDerTablero) { 
				// El lado derecho de la ficha coincide con el izquierdo del tablero?
				ficha.girarFicha(Ficha.ROTAR_IZQ*porMenos); // gira la ficha hacia la izquierda
				ficha.setPreferredSize(FICHA_H);
				c.gridx = xDer;
				c.gridy = yDer;
				c.gridwidth = 4;
				c.gridheight = 2;
				xDer += 4*porMenos;
				if (esquinaDer) {
					ficha.cambiarVal();
					if(DerA1)
						DerA1 = false;
				}
				if (esquina && !esquinaDer) {
					xDer += -6;
					yDer += -2;
					c.gridwidth = 2;
					c.gridheight = 4;
					ficha.girarFicha(Ficha.ROTAR_ABAJO);
					ficha.setPreferredSize(FICHA_V);
					ficha.cambiarVal();
					DerA1 = true;
					esquinaDer = true;
				}

				fichasTablero.add(ficha);
				return true;
				// FICHA LADO DERECHO == TABLERO LADO DERECHO (ELSE)
			} else { // el lado derecho entonces coincide con el derecho del tablero.
				ficha.girarFicha(Ficha.ROTAR_DER*porMenos); // gira la ficha hacia la derecha
				ficha.setPreferredSize(FICHA_H);
				c.gridx = xDer;
				c.gridy = yDer;
				c.gridwidth = 4;
				c.gridheight = 2;
				xDer += 4*porMenos;
				if (esquinaDer) {
					ficha.cambiarVal();
					if(DerA1)
						DerA1 = false;
				}
				if (esquina && !esquinaDer) {
					xDer += -6;
					yDer += -2;
					c.gridwidth = 2;
					c.gridheight = 4;
					ficha.girarFicha(Ficha.ROTAR_0);
					ficha.setPreferredSize(FICHA_V);
					ficha.cambiarVal();
					DerA1 = true;
					esquinaDer = true;
				}

				fichasTablero.add(ficha);
				return true;
			}
		}
		
    	return false;
	}
	

	/**
	 * The Class EscuchaBotones.
	 */
	private class EscuchaBotones implements ActionListener{

		/**
		 * Action performed.
		 *
		 * @param eventAction the event action
		 */
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
	}
	
	/**
	 * The Class EscuchaInicio.
	 */
	private class EscuchaInicio extends MouseAdapter{
		
		/** The clicked panel. */
		private  JPanel clickedPanel = null;
		
		/** The clicked ficha. */
		private  Ficha clickedFicha = null;
		
		/**
		 * Mouse pressed.
		 *
		 * @param me the me
		 */
		@Override
        public void mousePressed(MouseEvent me) {
			clickedPanel = (JPanel) inicioPanel.getComponentAt(me.getPoint());
			ArrayList<Ficha> allFichas = control.getFichas();
			if (!(clickedPanel.getComponentAt(me.getPoint()) instanceof Ficha)) {
            	return; // Sino es una Ficha lo que se clickeo, se cancela.
            }
            
            // Busca cuál de las fichas se clickeo.
            for (int i=0; i < allFichas.size(); i++) {
            	if ((Ficha) clickedPanel.getComponentAt(me.getPoint()) == allFichas.get(i)) {
            		clickedFicha = (Ficha) clickedPanel.getComponentAt(me.getPoint());
            		break;
            	}
            }
            if (clickedFicha == null)  // Just in case
            	return;
            Random random = new Random();
            Ficha fichaOp = allFichas.get(random.nextInt(28));
            while (fichaOp == clickedFicha) {
            	fichaOp = allFichas.get(random.nextInt(28));
            }
            fichaOp.destaparFicha();
            clickedFicha.destaparFicha();
            clickedPanel.revalidate();
            clickedPanel.repaint();
            revalidate();
            repaint();
            int valFichaJu = clickedFicha.getvIzq() + clickedFicha.getvDer();
            int valFichaOp = fichaOp.getvIzq() + fichaOp.getvDer();
            if (valFichaJu >= valFichaOp) {
            	JOptionPane.showMessageDialog(allPanel, "Tu empiezas.");
                clickedFicha.taparFicha();
                fichaOp.taparFicha();
            	iniciar(true);
            } else {
            	JOptionPane.showMessageDialog(allPanel, "Yo empiezo.");
                clickedFicha.taparFicha();
                fichaOp.taparFicha();
            	iniciar(false);
            }
        }
	}
	
	/**
	 * The Class Escuchas.
	 */
	private  class Escuchas extends MouseAdapter{
		
		/** The drag ficha. */
		private  Ficha dragFicha = null;
		
		/** The drag ficha V center. */
		private  int mX, mY, dragFichaHCenter, dragFichaVCenter;
		
		/** The clicked panel. */
		private  JPanel clickedPanel = null;
		
        /**
         * Mouse pressed.
         *
         * @param me the me
         */
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
		
		/**
		 * Mouse dragged.
		 *
		 * @param me the me
		 */
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
		
		/**
		 * Mouse released.
		 *
		 * @param me the me
		 */
		@Override
        public void mouseReleased(MouseEvent me) {
			//System.out.println("MouseReleased"); //debug purpose
			boolean jugada = false;
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
            		jugada = true;
            		droppedPanel.add(dragFicha, c);
            		fichasJugador.remove(dragFicha);
            	} else { // Si no se puede colocar, se devuelve
            		clickedPanel.add(dragFicha);
            	}
            	revalidate();
            }
            repaint();
            dragFicha = null;
            if (jugada) {
            	if (ganar()) {
            		escogerInicio();
                	return;
                }
                hacerJugada();
            }
        }
	}
}
