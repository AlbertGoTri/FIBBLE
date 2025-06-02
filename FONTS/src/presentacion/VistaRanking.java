package presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * VistaRanking muestra el ranking de los jugadores en el sistema Scrabble.
 * Presenta hasta 10 posiciones, con colores diferenciados para oro, plata y bronce,
 * y permite volver al menú principal mediante un botón.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Título de la vista.</li>
 *   <li>panelRanking: Lista vertical de hasta 10 posiciones del ranking.</li>
 *   <li>btnVolver: Botón para regresar al menú principal.</li>
 * </ul>
 *
 * El método {@link #cargarRanking()} actualiza las posiciones del ranking
 * a partir de los datos proporcionados por el {@link ControladorPresentacion}.
 *
 * @author
 */
public class VistaRanking extends JPanel {
    private ControladorPresentacion controlador;
    private JPanel panelRanking;
    private JLabel[] lblRanking;

    /**
     * Crea la vista de ranking de jugadores.
     * Inicializa los componentes gráficos, los estilos y carga el ranking actual.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y datos.
     */
    public VistaRanking(ControladorPresentacion controlador) {
        this.controlador = controlador;
        setLayout(new GridBagLayout());
        setBackground(new Color(34, 34, 34));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel lblTitulo = new JLabel("RANKING DE JUGADORES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 20)));

        gbc.gridy = 0;
        add(panelTitulo, gbc);

        panelRanking = new JPanel();
        panelRanking.setLayout(new GridLayout(10, 1, 0, 12));
        panelRanking.setBackground(new Color(34, 34, 34));
        panelRanking.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        lblRanking = new JLabel[10];
        Dimension cuadroDim = new Dimension(320, 38);
        Color oro = new Color(255, 236, 139);
        Color plata = new Color(220, 220, 230);
        Color bronce = new Color(222, 184, 135);
        Color fondo = new Color(34, 34, 34);

        for (int i = 0; i < lblRanking.length; i++) {
            lblRanking[i] = new JLabel("-", SwingConstants.CENTER);
            lblRanking[i].setFont(new Font("Arial", Font.BOLD, 16));
            lblRanking[i].setOpaque(true);
            lblRanking[i].setPreferredSize(cuadroDim);
            lblRanking[i].setMinimumSize(cuadroDim);
            lblRanking[i].setMaximumSize(cuadroDim);
            if (i == 0) {
                lblRanking[i].setBackground(oro);
                lblRanking[i].setForeground(new Color(0, 0, 0));
            } else if (i == 1) {
                lblRanking[i].setBackground(plata);
                lblRanking[i].setForeground(new Color(0, 0, 0));
            } else if (i == 2) {
                lblRanking[i].setBackground(bronce);
                lblRanking[i].setForeground(new Color(0, 0, 0));
            } else {
                lblRanking[i].setBackground(fondo);
                lblRanking[i].setForeground(new Color(220, 220, 220));
            }
            lblRanking[i].setUI(new RoundedLabelUI());
            panelRanking.add(lblRanking[i]);
        }

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(panelRanking, gbc);

        JButton btnVolver = new JButton("Atrás");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVolver.setBackground(new Color(255, 182, 193));
        btnVolver.setForeground(new Color(34, 34, 34));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                btnVolver.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnVolver.setUI(new RoundedButtonUI());
        btnVolver.setPreferredSize(new Dimension(150, 50));
        btnVolver.setMaximumSize(new Dimension(150, 50));
        btnVolver.setMinimumSize(new Dimension(150, 50));
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> controlador.mostrarVista("INICIO"));

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(34, 34, 34));
        panelBoton.add(btnVolver);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(panelBoton, gbc);

        cargarRanking();
    }

    /**
     * Actualiza la lista de posiciones del ranking en la vista.
     * Si no hay usuarios, muestra un mensaje indicativo.
     * Si hay menos de 10 usuarios, las posiciones restantes se rellenan con "-".
     */
    public void cargarRanking() {
        List<String> ranking = controlador.getRanking();
        if (ranking == null || ranking.isEmpty()) {
            lblRanking[0].setText("No hay usuarios registrados en el sistema.");
            for (int i = 1; i < lblRanking.length; i++) {
                lblRanking[i].setText("-");
            }
        } else {
            int i = 0;
            for (; i < ranking.size() && i < lblRanking.length; i++) {
                lblRanking[i].setText((i + 1) + ". " + ranking.get(i));
            }
            for (; i < lblRanking.length; i++) {
                lblRanking[i].setText("-");
            }
        }
    }
}
