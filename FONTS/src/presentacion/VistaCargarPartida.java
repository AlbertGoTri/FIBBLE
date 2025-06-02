package presentacion;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * VistaCargarPartida representa la interfaz gráfica para cargar una partida guardada en el sistema Scrabble.
 * Permite al usuario seleccionar un archivo de partida guardada, confirmar la carga o cancelar la operación.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de la vista.</li>
 *   <li>panelCentral: Panel con el selector de archivo y los botones de acción.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar la carga de la partida o la navegación.
 *
 * @author
 */
public class VistaCargarPartida extends JPanel {

    private JButton btnSeleccionarArchivo;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblArchivoSeleccionado;
    private String nombreArchivo;

    /**
     * Crea la vista de carga de partida, inicializando los componentes gráficos y los listeners de los botones.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaCargarPartida(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel lblTitulo = new JLabel("CARGAR PARTIDA", SwingConstants.CENTER);
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

        btnSeleccionarArchivo = crearBoton("Seleccionar Archivo", new Color(255, 239, 213), new Dimension(250, 50));
        btnSeleccionarArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblArchivoSeleccionado = new JLabel("Ningún archivo seleccionado");
        lblArchivoSeleccionado.setFont(new Font("Arial", Font.PLAIN, 14));
        lblArchivoSeleccionado.setForeground(new Color(245, 245, 245));
        lblArchivoSeleccionado.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentral.add(btnSeleccionarArchivo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(lblArchivoSeleccionado);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(new Color(34, 34, 34));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnConfirmar = crearBoton("Confirmar", new Color(144, 238, 144), new Dimension(150, 50));
        btnCancelar = crearBoton("Cancelar", new Color(255, 182, 193), new Dimension(150, 50));

        panelBotones.add(btnConfirmar);
        panelBotones.add(Box.createRigidArea(new Dimension(50, 0)));
        panelBotones.add(btnCancelar);

        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(panelBotones);

        add(panelCentral, BorderLayout.CENTER);

        btnSeleccionarArchivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("saves");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Seleccionar Partida Guardada");

            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                lblArchivoSeleccionado.setText("Archivo: " + selectedFile.getName());
                nombreArchivo = selectedFile.getName();
            }
        });

        btnConfirmar.addActionListener(e -> {
            if (nombreArchivo == null || nombreArchivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un archivo antes de confirmar.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    controlador.cargarPartida(nombreArchivo);
                    controlador.mostrarVista("PARTIDA");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Nombre de partida incorrecto, por favor seleccione una partida de la carpeta /saves",
                        "Error al cargar partida",
                        JOptionPane.ERROR_MESSAGE
                    );
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