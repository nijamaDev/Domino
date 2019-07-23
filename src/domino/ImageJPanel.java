/**
  Archivo: ImageJPanel.java
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

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
// TODO: Auto-generated Javadoc

/**
 * The Class ImageJPanel.
 */
public class ImageJPanel extends JPanel {
 
    /** The imagen. */
    private Image imagen;
 
    /**
     * Instantiates a new image J panel.
     */
    public ImageJPanel() {
    }
 
    /**
     * Instantiates a new image J panel.
     *
     * @param nombreImagen the nombre imagen
     */
    public ImageJPanel(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(
                           getClass().getResource(nombreImagen)
                           ).getImage();
        }
    }
 
    /**
     * Instantiates a new image J panel.
     *
     * @param imagenInicial the imagen inicial
     */
    public ImageJPanel(Image imagenInicial) {
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }
    }
 
    /**
     * Sets the imagen.
     *
     * @param nombreImagen the new imagen
     */
    public void setImagen(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(
                   getClass().getResource(nombreImagen)
                   ).getImage();
        } else {
            imagen = null;
        }
 
        repaint();
    }
 
    /**
     * Sets the imagen.
     *
     * @param nuevaImagen the new imagen
     */
    public void setImagen(Image nuevaImagen) {
        imagen = nuevaImagen;
 
        repaint();
    }
 
    /**
     * Paint.
     *
     * @param g the g
     */
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            
        	g.drawImage(
            		imagen,
            		0, 0, getWidth(), getHeight(),
                              this);
 
            setOpaque(false);
        } else {
            setOpaque(true);
        }
 
        super.paint(g);
    }
}
