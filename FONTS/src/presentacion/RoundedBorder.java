package presentacion;

import java.awt.*;
import javax.swing.border.AbstractBorder;

/**
 * RoundedBorder es una implementación personalizada de {@link AbstractBorder} que dibuja un borde redondeado
 * alrededor de un componente Swing.
 * 
 * <p>Permite especificar el radio de redondeo de las esquinas y define insets personalizados para el borde.
 * Es útil para dar un aspecto moderno y suave a los componentes gráficos.
 *
 * @author
 */
public class RoundedBorder extends AbstractBorder {
    private final int radius;

    /**
     * Crea un borde redondeado con el radio especificado.
     *
     * @param radius Radio de las esquinas redondeadas.
     */
    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    /**
     * Dibuja el borde redondeado alrededor del componente.
     *
     * @param c      Componente sobre el que se pinta el borde.
     * @param g      Contexto gráfico.
     * @param x      Coordenada X del borde.
     * @param y      Coordenada Y del borde.
     * @param width  Ancho del borde.
     * @param height Alto del borde.
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.GRAY);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    /**
     * Devuelve los insets (espaciado) del borde.
     *
     * @param c Componente asociado.
     * @return Insets del borde.
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(5, 10, 5, 10);
    }

    /**
     * Modifica los insets proporcionados para ajustarlos al borde redondeado.
     *
     * @param c      Componente asociado.
     * @param insets Insets a modificar.
     * @return Insets modificados.
     */
    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(5, 10, 5, 10);
        return insets;
    }
}
