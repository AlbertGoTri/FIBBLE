package presentacion;

import javax.swing.*;
import java.awt.*;
import dominio.Estadistica;

/**
 * VistaEstadisticas muestra las estadísticas del usuario actual en el sistema Scrabble.
 * Presenta información como puntos totales, puntuación media, partidas jugadas, ganadas, perdidas,
 * tasa de victorias, palabras jugadas y la mejor palabra.
 * Permite al usuario reiniciar sus estadísticas o volver a la gestión de usuario.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de la vista.</li>
 *   <li>panelContenido: Panel con las estadísticas del usuario.</li>
 *   <li>panelBoton: Panel con los botones de reiniciar estadísticas y volver atrás.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar las acciones.
 *
 * @author
 */
public class VistaEstadisticas extends JPanel {
    private ControladorPresentacion controlador;

    /**
     * Crea la vista de estadísticas, inicializando los componentes gráficos y mostrando los datos del usuario.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaEstadisticas(ControladorPresentacion controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel lblTitulo = new JLabel("ESTADÍSTICAS DEL JUGADOR", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new GridLayout(0, 2, 20, 10));
        panelContenido.setBackground(new Color(34, 34, 34));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        Estadistica estadisticas = controlador.getEstadisticasCurrentUser();
      
        if (estadisticas != null) {
            agregarEstadistica(panelContenido, "Puntos Totales:", estadisticas.getTotalPoints());
            agregarEstadistica(panelContenido, "Puntuación Media:", estadisticas.getAveragePoints());
            agregarEstadistica(panelContenido, "Puntuación Máxima:", estadisticas.getMaxPoints());
            agregarEstadistica(panelContenido, "Partidas Jugadas:", estadisticas.getNumMatches());
            agregarEstadistica(panelContenido, "Partidas Ganadas:", estadisticas.getWins());
            agregarEstadistica(panelContenido, "Partidas Perdidas:", estadisticas.getLoses());
            agregarEstadistica(panelContenido, "Tasa de Victorias:", estadisticas.getWinRate());
            agregarEstadistica(panelContenido, "Palabras Totales Jugadas:", estadisticas.getTotalWords());
            agregarEstadistica(panelContenido, "Mejor Palabra:", estadisticas.getBestWord());
        } else {
            JLabel lblNoStats = new JLabel("No hay estadísticas disponibles.");
            lblNoStats.setFont(new Font("Arial", Font.PLAIN, 16));
            lblNoStats.setForeground(new Color(245, 245, 245));
            lblNoStats.setHorizontalAlignment(SwingConstants.CENTER);
            panelContenido.add(lblNoStats);
        }

        add(panelContenido, BorderLayout.CENTER);

        JButton btnVolver = crearBoton("Atrás", new Color(255, 182, 193), new Dimension(150, 50));
        btnVolver.addActionListener(e -> controlador.mostrarVista("GESTION_USUARIO"));

        JButton btnReiniciar = crearBoton("Reiniciar Estadísticas", new Color(255, 239, 213), new Dimension(200, 50));
        btnReiniciar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres reiniciar tus estadísticas?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controlador.reiniciarEstadisticas();
                JOptionPane.showMessageDialog(this, "Estadísticas reiniciadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                controlador.mostrarVista("GESTION_USUARIO");
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(34, 34, 34));
        panelBoton.add(btnReiniciar);
        panelBoton.add(Box.createRigidArea(new Dimension(30, 0)));
        panelBoton.add(btnVolver);

        add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Añade una estadística al panel de contenido, mostrando el nombre y el valor.
     *
     * @param panel Panel donde se añade la estadística.
     * @param nombre Nombre de la estadística.
     * @param valor Valor de la estadística.
     */
    private void agregarEstadistica(JPanel panel, String nombre, Object valor) {
        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNombre.setForeground(new Color(245, 245, 245));
        lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(lblNombre);

        String textoValor = (valor != null) ? valor.toString() : "N/A";

        JLabel lblValor = new JLabel(textoValor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 16));
        lblValor.setBackground(new Color(34, 34, 34));
        lblValor.setForeground(new Color(245, 245, 245));
        lblValor.setOpaque(true);
        lblValor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblValor.setUI(new RoundedLabelUI());
        lblValor.setPreferredSize(new Dimension(100, 30));
        lblValor.setMinimumSize(new Dimension(100, 30));
        panel.add(lblValor);
    }

    /**
     * Crea un botón estilizado para las acciones principales de la vista.
     *
     * @param texto      Texto a mostrar en el botón.
     * @param colorFondo Color de fondo del botón.
     * @param botonSize  Dimensiones del botón.
     * @return JButton configurado.
     */
    private JButton crearBoton(String texto, Color colorFondo, Dimension botonSize) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 16));
        boton.setBackground(colorFondo);
        boton.setForeground(new Color(34, 34, 34));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setPreferredSize(botonSize);
        boton.setUI(new RoundedButtonUI());
        return boton;
    }

    /**
     * Método para actualizar la vista con las estadísticas del usuario actual.
     * Se invoca desde el controlador cuando se necesita refrescar la vista.
     */
    public void cargarEstadisticas() {
        JPanel panelContenido = (JPanel) getComponent(1);
        panelContenido.removeAll();
        
        Estadistica estadisticas = controlador.getEstadisticasCurrentUser();
        
        if (estadisticas != null) {
            agregarEstadistica(panelContenido, "Puntos Totales:", estadisticas.getTotalPoints());
            agregarEstadistica(panelContenido, "Puntuación Media:", estadisticas.getAveragePoints());
            agregarEstadistica(panelContenido, "Puntuación Máxima:", estadisticas.getMaxPoints());
            agregarEstadistica(panelContenido, "Partidas Jugadas:", estadisticas.getNumMatches());
            agregarEstadistica(panelContenido, "Partidas Ganadas:", estadisticas.getWins());
            agregarEstadistica(panelContenido, "Partidas Perdidas:", estadisticas.getLoses());
            agregarEstadistica(panelContenido, "Tasa de Victorias:", estadisticas.getWinRate());
            agregarEstadistica(panelContenido, "Palabras Totales Jugadas:", estadisticas.getTotalWords());
            agregarEstadistica(panelContenido, "Mejor Palabra:", estadisticas.getBestWord());
        } else {
            JLabel lblNoStats = new JLabel("No hay estadísticas disponibles.");
            lblNoStats.setFont(new Font("Arial", Font.PLAIN, 16));
            lblNoStats.setForeground(new Color(245, 245, 245));
            lblNoStats.setHorizontalAlignment(SwingConstants.CENTER);
            panelContenido.add(lblNoStats);
        }
        
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
