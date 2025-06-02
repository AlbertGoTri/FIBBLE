package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * VistaLogin representa la interfaz gráfica para el inicio de sesión de usuarios en el sistema Scrabble.
 * Permite al usuario introducir su nombre de usuario y contraseña, iniciar sesión, cancelar la operación
 * o navegar a la vista de creación de perfil si no tiene cuenta.
 *
 * <p>Componentes principales:
 * <ul>
 *   <li>panelTitulo: Panel con el logo y el título de la vista.</li>
 *   <li>panelCentral: Panel con los campos de usuario y contraseña, y los botones de acción.</li>
 *   <li>panelCrearCuenta: Panel con el enlace para crear una nueva cuenta.</li>
 * </ul>
 *
 * @author
 */
public class VistaLogin extends JPanel {

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    /**
     * Crea la vista de inicio de sesión, inicializando todos los componentes gráficos y listeners.
     *
     * @param controlador Referencia al controlador de presentación para la gestión de vistas y acciones.
     */
    public VistaLogin(ControladorPresentacion controlador) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

        ImageIcon iconoLogoOriginal = new ImageIcon(getClass().getResource("/logo_no_bg.png"));
        Image imagenLogo = iconoLogoOriginal.getImage().getScaledInstance(400, 80, Image.SCALE_SMOOTH);
        ImageIcon iconoLogo = new ImageIcon(imagenLogo);
        JLabel lblLogo = new JLabel(iconoLogo);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelTitulo.add(lblLogo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 70)));
        panelTitulo.add(lblTitulo);

        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(34, 34, 34));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        int fieldWidth = 300;
        int buttonWidth = 30;
        int totalWidth = fieldWidth + buttonWidth + 5;

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUsername.setForeground(new Color(245, 245, 245));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(totalWidth, 30));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsername.setUI(new RoundedFieldUI());

        JLabel lblPassword = new JLabel("Contraseña:");
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

        panelCentral.add(lblUsername);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(txtUsername);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentral.add(lblPassword);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelPassword);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(new Color(34, 34, 34));
        panelBotones.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConfirmar = crearBoton("Confirmar", new Color(144, 238, 144), new Dimension(150, 50));
        btnCancelar = crearBoton("Salir", new Color(255, 182, 193), new Dimension(150, 50));

        panelBotones.add(btnConfirmar);
        panelBotones.add(Box.createRigidArea(new Dimension(50, 0)));
        panelBotones.add(btnCancelar);

        panelCentral.add(panelBotones);

        JPanel panelCrearCuenta = new JPanel();
        panelCrearCuenta.setLayout(new BoxLayout(panelCrearCuenta, BoxLayout.Y_AXIS));
        panelCrearCuenta.setBackground(new Color(34, 34, 34));
        panelCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCrearCuenta.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel lblCrearCuenta = new JLabel("<html>¿No tienes cuenta? Haz click <span style='color:#90EE90; text-decoration:underline;'>aquí</span></html>");
        lblCrearCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCrearCuenta.setForeground(new Color(245, 245, 245));
        lblCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblCrearCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                controlador.mostrarVista("CREAR_PERFIL");
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblCrearCuenta.setText("<html>¿No tienes cuenta? Haz click <span style='color:white; text-decoration:underline;'>aquí</span></html>");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblCrearCuenta.setText("<html>¿No tienes cuenta? Haz click <span style='color:#90EE90; text-decoration:underline;'>aquí</span></html>");
            }
        });

        panelCrearCuenta.add(lblCrearCuenta);
        panelCentral.add(panelCrearCuenta);

        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorCentral.setBackground(new Color(34, 34, 34));
        contenedorCentral.add(panelCentral);

        add(contenedorCentral, BorderLayout.CENTER);

        btnConfirmar.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    controlador.login(username, password);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> controlador.manejarOpcionInicio(4));
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
