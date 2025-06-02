package presentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * RoundedLabelUI proporciona una UI personalizada para etiquetas {@link JLabel} con esquinas redondeadas,
 * fondo oscuro y bordes suaves en tono gris claro.
 * 
 * <p>Esta clase extiende {@link BasicLabelUI} y redefine la apariencia del JLabel,
 * aplicando antialiasing y colores personalizados para fondo y borde.
 * El color de fondo se toma del propio componente, permitiendo personalización por instancia.
 * 
 * <p>Se recomienda usar esta UI para etiquetas que requieran una apariencia moderna y diferenciada.
 *
 * @author
 */
public class RoundedLabelUI extends BasicLabelUI {

    private final Color colorFondo = new Color(34, 34, 34);
    private final Color colorTexto = new Color(220, 220, 220);
    private final Color colorBorde = new Color(180, 180, 180);
    private final int arc = 20;

    /**
     * Instala la UI personalizada en el componente, configurando opacidad y padding interno.
     *
     * @param c Componente sobre el que se instala la UI.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        c.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    /**
     * Pinta el JLabel con esquinas redondeadas, fondo y borde personalizados.
     * Utiliza antialiasing para mejorar la calidad visual.
     *
     * @param g Contexto gráfico sobre el que se pinta el componente.
     * @param c Componente a pintar.
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(c.getBackground());
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arc, arc);

        g2.dispose();

        super.paint(g, c);
    }

    /**
     * Devuelve el tamaño preferido del componente, añadiendo espaciado extra para el diseño redondeado.
     *
     * @param c Componente para el que se calcula el tamaño preferido.
     * @return Dimensiones preferidas del componente.
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension size = super.getPreferredSize(c);
        size.width += 10;
        size.height += 5;
        return size;
    }
}
