package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * VistaGestionUsuario representa la interfaz gráfica para la gestión de usuario en el sistema Scrabble.
 * Permite al usuario acceder a las opciones de editar perfil, ver estadísticas o volver atrás.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de la vista.</li>
 *   <li>panelBotones: Panel con los botones de acción.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar la navegación y las acciones.
 *
 * @author
 */
public class VistaGestionUsuario extends JPanel {

    private JButton btnCrearPerfil;
    private JButton btnEditarPerfil;
    private JButton btnVerEstadisticas;
    private JButton btnAtras;

    /**
     * Crea la vista de gestión de usuario, inicializando los componentes gráficos y los listeners de los botones.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaGestionUsuario(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel lblTitulo = new JLabel("GESTIÓN DE USUARIO", SwingConstants.CENTER);
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

        btnEditarPerfil = crearBoton("Editar Perfil", new Color(255, 239, 213), new Dimension(250, 50));
        btnVerEstadisticas = crearBoton("Ver Estadísticas", new Color(255, 239, 213), new Dimension(250, 50));
        btnAtras = crearBoton("Atrás", new Color(255, 182, 193), new Dimension(150, 50));

        panelBotones.add(btnEditarPerfil);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 25)));
        panelBotones.add(btnVerEstadisticas);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 25)));
        panelBotones.add(btnAtras);

        add(panelBotones, BorderLayout.CENTER);

        btnEditarPerfil.addActionListener(e -> controlador.manejarOpcionGestionUsuario(2));
        btnVerEstadisticas.addActionListener(e -> controlador.manejarOpcionGestionUsuario(3));
        btnAtras.addActionListener(e -> controlador.manejarOpcionGestionUsuario(4));
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