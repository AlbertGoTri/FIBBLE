package presentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;

/**
 * RoundedFieldUI proporciona una UI personalizada para campos de texto ({@link JTextField})
 * con esquinas redondeadas, fondo oscuro y bordes suaves en tono gris claro.
 * 
 * <p>Esta clase extiende {@link BasicTextFieldUI} y redefine la apariencia del campo de texto,
 * aplicando antialiasing y colores personalizados para fondo, texto y borde.
 * Se recomienda usar esta UI para campos de texto que requieran una apariencia moderna y diferenciada.
 *
 * @author
 */
public class RoundedFieldUI extends BasicTextFieldUI {

    private final Color colorFondo = new Color(34, 34, 34);
    private final Color colorTexto = new Color(220, 220, 220);
    private final Color colorBorde = new Color(180, 180, 180);
    private final int arc = 20;

    /**
     * Instala la UI personalizada en el componente, configurando opacidad, colores y bordes.
     *
     * @param c Componente sobre el que se instala la UI.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        c.setBackground(colorFondo);
        c.setForeground(colorTexto);
        c.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    /**
     * Pinta el campo de texto con esquinas redondeadas, fondo y borde personalizados.
     * Utiliza antialiasing para mejorar la calidad visual.
     *
     * @param g Contexto gráfico sobre el que se pinta el componente.
     */
    @Override
    public void paintSafely(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        JComponent c = getComponent();

        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arc, arc);

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arc, arc);

        g2.dispose();

        super.paintSafely(g);
    }
}
