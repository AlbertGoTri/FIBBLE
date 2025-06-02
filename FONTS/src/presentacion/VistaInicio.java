package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * VistaInicio representa la pantalla principal del sistema Scrabble.
 * Permite al usuario acceder a la gestión de usuario, gestión de partida, ver el ranking o salir de la aplicación.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de bienvenida.</li>
 *   <li>panelBotones: Panel con los botones de navegación principales.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar la navegación.
 *
 * @author
 */
public class VistaInicio extends JPanel {

    private JButton btnGestionarUsuario;
    private JButton btnGestionarPartida;
    private JButton btnVerRanking;
    private JButton btnSalir;

    /**
     * Crea la vista de inicio, inicializando los componentes gráficos y los listeners de los botones.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaInicio(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel lblTitulo = new JLabel("BIENVENIDO A SCRABBLE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 40)));
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(34, 34, 34));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        btnGestionarUsuario = crearBoton("Gestionar Usuario", new Color(255, 239, 213), new Dimension(250, 50));
        btnGestionarPartida = crearBoton("Gestionar Partida", new Color(255, 239, 213), new Dimension(250, 50));
        btnVerRanking = crearBoton("Ver Ranking", new Color(255, 239, 213), new Dimension(250, 50));
        btnSalir = crearBoton("Salir", new Color(255, 182, 193), new Dimension(150, 50));

        panelBotones.add(btnGestionarUsuario);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 25)));
        panelBotones.add(btnGestionarPartida);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 25)));
        panelBotones.add(btnVerRanking);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 25)));
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        btnGestionarUsuario.addActionListener(e -> controlador.manejarOpcionInicio(1));
        btnGestionarPartida.addActionListener(e -> controlador.manejarOpcionInicio(2));
        btnVerRanking.addActionListener(e -> controlador.manejarOpcionInicio(3));
        btnSalir.addActionListener(e -> controlador.manejarOpcionInicio(4));
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
        boton.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        boton.setBorder(BorderFactory.createCompoundBorder(
                boton.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.setUI(new RoundedButtonUI());

        boton.setPreferredSize(botonSize);
        boton.setMaximumSize(botonSize);
        boton.setMinimumSize(botonSize);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        return boton;
    }
}
