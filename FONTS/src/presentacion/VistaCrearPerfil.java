package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * VistaCrearPerfil representa la interfaz gráfica para crear un nuevo perfil de usuario en el sistema Scrabble.
 * Permite al usuario ingresar un nombre de usuario y una contraseña, y confirmar o cancelar la creación del perfil.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el título de la vista.</li>
 *   <li>panelCentral: Panel con los campos de entrada y los botones de acción.</li>
 * </ul>
 *
 * Los botones llaman a métodos del {@link ControladorPresentacion} para gestionar las acciones.
 *
 * @author
 */
public class VistaCrearPerfil extends JPanel {

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtRepeatPassword;

    /**
     * Crea la vista de creación de perfil, inicializando los componentes gráficos y los listeners de los botones.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaCrearPerfil(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));

        JLabel lblTitulo = new JLabel("CREAR PERFIL", SwingConstants.CENTER);
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

        int fieldWidth = 300;
        int buttonWidth = 30;
        int totalWidth = fieldWidth + buttonWidth + 5;

        JLabel lblUsername = new JLabel("Introduzca Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUsername.setForeground(new Color(245, 245, 245));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(totalWidth, 30));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsername.setUI(new RoundedFieldUI());

        JLabel lblPassword = new JLabel("Introduzca Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPassword.setForeground(new Color(245, 245, 245));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelPassword = new JPanel();
        panelPassword.setLayout(new BoxLayout(panelPassword, BoxLayout.X_AXIS));
        panelPassword.setBackground(new Color(34, 34, 34));
        panelPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPassword.setMaximumSize(new Dimension(totalWidth, 30));

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(fieldWidth, 30));
        txtPassword.setUI(new RoundedPasswordFieldUI());
        txtPassword.setEchoChar('*');

        JButton btnMostrarPassword = crearBotonPequeno("👁", txtPassword);

        panelPassword.add(txtPassword);
        panelPassword.add(Box.createRigidArea(new Dimension(5, 0)));
        panelPassword.add(btnMostrarPassword);

        JLabel lblRepeatPassword = new JLabel("Repita Contraseña:");
        lblRepeatPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        lblRepeatPassword.setForeground(new Color(245, 245, 245));
        lblRepeatPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel para repetir contraseña y botón
        JPanel panelRepeatPassword = new JPanel();
        panelRepeatPassword.setLayout(new BoxLayout(panelRepeatPassword, BoxLayout.X_AXIS));
        panelRepeatPassword.setBackground(new Color(34, 34, 34));
        panelRepeatPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelRepeatPassword.setMaximumSize(new Dimension(totalWidth, 30));

        txtRepeatPassword = new JPasswordField();
        txtRepeatPassword.setPreferredSize(new Dimension(fieldWidth, 30));
        txtRepeatPassword.setUI(new RoundedPasswordFieldUI());
        txtRepeatPassword.setEchoChar('*');

        JButton btnMostrarRepeatPassword = crearBotonPequeno("👁", txtRepeatPassword);

        panelRepeatPassword.add(txtRepeatPassword);
        panelRepeatPassword.add(Box.createRigidArea(new Dimension(5, 0)));
        panelRepeatPassword.add(btnMostrarRepeatPassword);

        panelCentral.add(lblUsername);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(txtUsername);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(lblPassword);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelPassword);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(lblRepeatPassword);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelRepeatPassword);
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

        panelCentral.add(panelBotones);

        add(panelCentral, BorderLayout.CENTER);

        btnConfirmar.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String repeatPassword = new String(txtRepeatPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(repeatPassword)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    controlador.crearPerfil(username, password);
                    JOptionPane.showMessageDialog(this, "Perfil creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    controlador.mostrarVista("LOGIN");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> controlador.mostrarVista("LOGIN"));
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

    /**
     * Crea un botón pequeño para mostrar/ocultar la contraseña en el campo correspondiente.
     *
     * @param texto         Texto o icono a mostrar en el botón.
     * @param passwordField Campo de contraseña asociado.
     * @return JButton configurado para mostrar/ocultar la contraseña.
     */
    private JButton crearBotonPequeno(String texto, JPasswordField passwordField) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.PLAIN, 12));
        boton.setBackground(new Color(34, 34, 34));
        boton.setForeground(new Color(220, 220, 220));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(34, 34, 34), 1));
        boton.setPreferredSize(new Dimension(30, 30));
        boton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '*') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
        return boton;
    }
}