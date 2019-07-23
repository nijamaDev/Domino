package codigoExterno;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

// TODO: Auto-generated Javadoc
/**
 * The Class Titulos.
 */
public class Titulos extends JLabel {
    
    /**
     * Instantiates a new titulos.
     *
     * @param texto the texto
     * @param tamano the tamano
     * @param colorFondo the color fondo
     */
    public Titulos(String texto, int tamano, Color colorFondo) {
    	
    	this.setText(texto);
    	Font font = new Font (Font.SANS_SERIF,Font.BOLD+Font.ITALIC,tamano);
    	setFont(font);
    	this.setForeground(Color.WHITE);
    	this.setBackground(colorFondo);
    	this.setOpaque(true);
    	this.setHorizontalAlignment(JLabel.CENTER);
    }
}
