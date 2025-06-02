package presentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import java.awt.*;

/**
 * RoundedPasswordFieldUI proporciona un estilo personalizado para los campos de contraseña (JPasswordField)
 * con esquinas redondeadas, fondo oscuro y borde suave, para una apariencia moderna en la interfaz gráfica.
 *
 * <p>Esta clase extiende {@link BasicPasswordFieldUI} y redefine la apariencia del campo de contraseña,
 * aplicando antialiasing y colores personalizados para fondo, texto y borde.
 *
 * @author
 */
public class RoundedPasswordFieldUI extends BasicPasswordFieldUI {

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
     * Pinta el campo de contraseña con esquinas redondeadas, fondo y borde personalizados.
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
