package presentacion;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

/**
 * VistaCrearPartida representa la interfaz gráfica para la creación de una nueva partida en el sistema Scrabble.
 * Permite al usuario introducir el nombre de la partida, seleccionar el idioma y el número de bots, y confirmar o cancelar la creación.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de la vista.</li>
 *   <li>panelCentral: Panel con los campos de entrada y los botones de acción.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar la creación de la partida o la navegación.
 *
 * @author
 */
public class VistaCrearPartida extends JPanel {

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField txtNombrePartida;
    private JComboBox<String> comboIdiomas;
    private JComboBox<Integer> comboNumBots;

    /**
     * Crea la vista de creación de partida, inicializando los componentes gráficos y los listeners de los botones.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaCrearPartida(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel lblTitulo = new JLabel("CREAR NUEVA PARTIDA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 40)));
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(34, 34, 34));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel lblNombrePartida = new JLabel("Nombre de la Partida:");
        lblNombrePartida.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNombrePartida.setForeground(new Color(245, 245, 245));
        lblNombrePartida.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtNombrePartida = new JTextField();
        txtNombrePartida.setMaximumSize(new Dimension(300, 30));
        txtNombrePartida.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNombrePartida.setUI(new RoundedFieldUI());

        JLabel lblIdioma = new JLabel("Seleccionar Idioma:");
        lblIdioma.setFont(new Font("Arial", Font.PLAIN, 16));
        lblIdioma.setForeground(new Color(245, 245, 245));
        lblIdioma.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboIdiomas = new JComboBox<>(new String[]{"castellano", "catalan", "ingles"});
        comboIdiomas.setMaximumSize(new Dimension(300, 30));
        comboIdiomas.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboIdiomas.setBorder(new RoundedBorder(15));
        comboIdiomas.setBackground(new Color(34, 34, 34));
        comboIdiomas.setForeground(new Color(220, 220, 220));
        comboIdiomas.setOpaque(false);
        comboIdiomas.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new JButton("\u25BC");
                arrowButton.setFont(new Font("Arial", Font.BOLD, 12));
                arrowButton.setBackground(new Color(34, 34, 34));
                arrowButton.setForeground(new Color(200, 200, 200));
                arrowButton.setBorder(null);
                return arrowButton;
            }
        });

        JPanel panelNumBots = new JPanel();
        panelNumBots.setLayout(new BoxLayout(panelNumBots, BoxLayout.X_AXIS));
        panelNumBots.setBackground(new Color(34, 34, 34));
        panelNumBots.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNumBots = new JLabel("Elige el número de bots:");
        lblNumBots.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNumBots.setForeground(new Color(245, 245, 245));

        comboNumBots = new JComboBox<>(new Integer[]{1, 2, 3});
        comboNumBots.setMaximumSize(new Dimension(100, 30));
        comboNumBots.setBorder(new RoundedBorder(15));
        comboNumBots.setBackground(new Color(34, 34, 34));
        comboNumBots.setForeground(new Color(220, 220, 220));
        comboNumBots.setOpaque(false);
        comboNumBots.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = new JButton("\u25BC");
                arrowButton.setFont(new Font("Arial", Font.BOLD, 12));
                arrowButton.setBackground(new Color(34, 34, 34));
                arrowButton.setForeground(new Color(200, 200, 200));
                arrowButton.setBorder(null);
                return arrowButton;
            }
        });

        panelNumBots.add(lblNumBots);
        panelNumBots.add(Box.createRigidArea(new Dimension(10, 0)));
        panelNumBots.add(comboNumBots);

        panelCentral.add(lblNombrePartida);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(txtNombrePartida);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(lblIdioma);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(comboIdiomas);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(panelNumBots);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(new Color(34, 34, 34));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConfirmar = crearBoton("Confirmar", new Color(144, 238, 144), new Dimension(150, 50));
        btnCancelar = crearBoton("Cancelar", new Color(255, 182, 193), new Dimension(150, 50));

        panelBotones.add(btnConfirmar);
        panelBotones.add(Box.createRigidArea(new Dimension(50, 0)));
        panelBotones.add(btnCancelar);

        panelCentral.add(panelBotones); // Agregar los botones al panel central

        add(panelCentral, BorderLayout.CENTER);

        btnConfirmar.addActionListener(e -> {
            String nombrePartida = txtNombrePartida.getText().trim();
            String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();
            Integer numBots = (Integer) comboNumBots.getSelectedItem();
        
            if (nombrePartida.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de la partida es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (idiomaSeleccionado == null || idiomaSeleccionado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un idioma.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (numBots == null || numBots < 1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un bot.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    controlador.crearPartida(nombrePartida, idiomaSeleccionado, numBots);
                    controlador.mostrarVista("PARTIDA");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> controlador.mostrarVista("GESTION_PARTIDA"));
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
