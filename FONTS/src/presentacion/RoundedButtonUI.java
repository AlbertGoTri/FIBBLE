package presentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 * RoundedButtonUI proporciona una UI personalizada para botones ({@link JButton}) con esquinas redondeadas,
 * efecto hover y pulsado que oscurece el color base del botón.
 * 
 * <p>Esta clase extiende {@link BasicButtonUI} y redefine la apariencia del botón,
 * aplicando antialiasing, fondo redondeado y borde personalizado.
 * El color del botón se oscurece al pasar el ratón por encima (hover) o al pulsarlo.
 * 
 * <p>Se recomienda usar esta UI para botones que requieran una apariencia moderna y diferenciada.
 *
 * @author
 */
public class RoundedButtonUI extends BasicButtonUI {

    /**
     * Instala la UI personalizada en el componente, configurando opacidad y eliminando el borde por defecto.
     *
     * @param c Componente sobre el que se instala la UI.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        if (c instanceof JButton) {
            JButton button = (JButton) c;
            button.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    /**
     * Pinta el botón con esquinas redondeadas, fondo y borde personalizados.
     * Aplica efecto hover y pulsado oscureciendo el color base.
     * Utiliza antialiasing para mejorar la calidad visual.
     *
     * @param g Contexto gráfico sobre el que se pinta el componente.
     * @param c Componente a pintar.
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        JButton button = (JButton) c;

        g2.setColor(c.getParent().getBackground());
        g2.fillRect(0, 0, button.getWidth(), button.getHeight());

        int arc = 30;

        Color baseColor = button.getBackground();

        if (button.getModel().isPressed()) {
            baseColor = adjustBrightness(baseColor, 0.75f);
        } else if (button.getModel().isRollover()) {
            baseColor = adjustBrightness(baseColor, 0.9f);
        }

        g2.setColor(baseColor);
        g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), arc, arc);

        g2.setColor(new Color(245, 245, 245));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, button.getWidth() - 3, button.getHeight() - 3, arc, arc);

        FontMetrics fm = g2.getFontMetrics();
        String text = button.getText();
        Rectangle textRect = new Rectangle(0, 0, button.getWidth(), button.getHeight());
        int textHeight = fm.getAscent();
        int textWidth = fm.stringWidth(text);
        int x = (textRect.width - textWidth) / 2;
        int y = (textRect.height + textHeight) / 2 - 2;

        g2.setColor(button.getForeground());
        g2.setFont(button.getFont());
        g2.drawString(text, x, y);

        g2.dispose();
    }

    /**
     * Ajusta la luminosidad de un color manteniendo su saturación.
     * Un factor menor que 1 oscurece el color, un factor mayor que 1 lo aclara.
     *
     * @param color  Color original.
     * @param factor Factor de ajuste de brillo.
     * @return Nuevo color ajustado.
     */
    private Color adjustBrightness(Color color, float factor) {
        int r = Math.max(Math.min((int)(color.getRed() * factor), 255), 0);
        int g = Math.max(Math.min((int)(color.getGreen() * factor), 255), 0);
        int b = Math.max(Math.min((int)(color.getBlue() * factor), 255), 0);
        return new Color(r, g, b);
    }
}
