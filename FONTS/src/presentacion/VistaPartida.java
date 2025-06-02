package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import dominio.Vista;

/**
 * VistaPartida representa la vista principal de una partida de Scrabble.
 * Gestiona la interfaz gráfica del tablero, el atril del jugador, los botones de control,
 * la visualización de puntuaciones y la interacción con el usuario y el controlador.
 *
 * <p>Permite colocar letras, confirmar/cancelar movimientos, pasar turno, guardar y finalizar la partida,
 * así como gestionar el turno de los robots y mostrar mensajes de error o confirmación.
 *
 * @author
 */
public class VistaPartida extends JPanel {

    /**
     * Referencia a la vista base.
     */
    private Vista vista;

    /**
     * Matriz de botones que representa el tablero de juego.
     */
    private JButton[][] botonesTablero;

    /**
     * Área de texto para mostrar el saco (no usada en la versión actual).
     */
    private JTextArea areaSaco;

    /**
     * Botones principales de control de la partida.
     */
    private JButton btnConfirmar, btnCancelarMovimiento, btnPasarTurno;

    /**
     * Botón de letra actualmente seleccionado en el atril.
     */
    private JButton botonLetraSeleccionado = null;

    /**
     * Etiquetas para mostrar las puntuaciones de los jugadores.
     */
    private JLabel[] lblPuntuaciones;

    /**
     * Referencia al controlador de presentación.
     */
    private ControladorPresentacion controlador;

    /**
     * Letra seleccionada actualmente del atril.
     */
    private String letraSeleccionada = null;

    /**
     * Índice de la letra seleccionada en el atril.
     */
    private int indiceLetraSeleccionada = -1;

    /**
     * Letras del jugador en turno.
     */
    private List<String> letrasJugador = new ArrayList<>();

    /**
     * Botones del tablero donde se han colocado letras en el turno actual.
     */
    private List<JButton> botonesColocados = new ArrayList<>();

    /**
     * Botones del atril.
     */
    private List<JButton> botonesSaco = new ArrayList<>();

    /**
     * Letras colocadas en el turno actual.
     */
    private List<String> letrasColocadas = new ArrayList<>();

    /**
     * Mapa de la jugada actual: clave = "letra_fila_columna", valor = coordenadas.
     */
    private Map<String, int[]> jugadaActual = new java.util.HashMap<>();

    /**
     * Crea la vista de partida e inicializa los componentes principales.
     * @param controlador Referencia al controlador de presentación.
     */
    public VistaPartida(ControladorPresentacion controlador) {
        this.vista = new Vista();
        this.controlador = controlador;
        setLayout(new BorderLayout());
        setBackground(new Color(34, 34, 34));
    }

    /**
     * Carga el tablero de juego, inicializándolo si es la primera vez,
     * o actualizando solo el contenido si ya existe.
     */
    public void cargarTablero() {
        if (botonesTablero != null) {
            actualizarTablero();
        } else {
            inicializarTablero();
        }
    }

    /**
     * Actualiza el contenido de los botones del tablero según el estado actual.
     */
    private void actualizarTablero() {
        String[][] contenidoTablero = controlador.getContenidoTablero();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                botonesTablero[i][j].setText(contenidoTablero[i][j]);
            }
        }
    }

    /**
     * Inicializa todos los paneles y componentes gráficos de la vista de partida,
     * incluyendo el tablero, el atril, los botones de control y las puntuaciones.
     */
    private void inicializarTablero() {
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(34, 34, 34));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JLabel lblTitulo = new JLabel("JUGAR PARTIDA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(245, 245, 245));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        String[][] contenidoTablero = controlador.getContenidoTablero();
        JPanel panelCentral = new JPanel(new GridLayout(15, 15));
        panelCentral.setBackground(new Color(34, 34, 34));
        botonesTablero = new JButton[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setFont(new Font("Arial", Font.BOLD, 16));
                botonesTablero[i][j].setBackground(new Color(50, 50, 50));
                botonesTablero[i][j].setForeground(Color.WHITE);
                botonesTablero[i][j].setFocusPainted(false);
                botonesTablero[i][j].addActionListener(new BotonTableroListener(i, j));
                botonesTablero[i][j].setText(contenidoTablero[i][j]);
                botonesTablero[i][j].setMargin(new Insets(0, 0, 0, 0));
                botonesTablero[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                panelCentral.add(botonesTablero[i][j]);
            }
        }

        Color rojo = new Color(255, 120, 120);
        Color cian = new Color(120, 210, 220);
        Color rosa = new Color(255, 170, 210);
        Color naranja = new Color(255, 190, 120);
        Color black = new Color(0, 0, 0);

        int[][] rojos = {{0,0},{0,7},{0,14},{7,0},{7,14},{14,0},{14,7},{14,14}};
        for (int[] pos : rojos) {
            botonesTablero[pos[0]][pos[1]].setBackground(rojo);
            botonesTablero[pos[0]][pos[1]].setForeground(black);
        }

        int[][] cianes = {
            {0,3},{0,11},{2,6},{2,8},{3,0},{3,7},{3,14},{6,2},{6,6},{6,8},{6,12},
            {7,3},{7,11},{8,2},{8,6},{8,8},{8,12},{11,0},{11,7},{11,14},{12,6},{12,8},{14,3},{14,11}
        };
        for (int[] pos : cianes) {
            botonesTablero[pos[0]][pos[1]].setBackground(cian);
            botonesTablero[pos[0]][pos[1]].setForeground(black);
        }

        int[][] rosas = {
            {1,5},{1,9},{5,1},{5,5},{5,9},{5,13},{9,1},{9,5},{9,9},{9,13},{13,5},{13,9}
        };
        for (int[] pos : rosas) {
            botonesTablero[pos[0]][pos[1]].setBackground(rosa);
            botonesTablero[pos[0]][pos[1]].setForeground(black);
        }

        int[][] amarillos = {
            {1,1},{1,13},{2,2},{2,12},{3,3},{3,11},{4,4},{4,10},{5,5},{5,9}, {7,7},
            {10,4},{10,10},{11,3},{11,11},{12,2},{12,12},{13,1},{13,13}
        };
        for (int[] pos : amarillos) {
            botonesTablero[pos[0]][pos[1]].setBackground(naranja);
            botonesTablero[pos[0]][pos[1]].setForeground(black);
        }

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(new Color(34, 34, 34));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        List<String> nombresJugadores = controlador.getNombresJugadores();
        int numJugadores = nombresJugadores.size();
        JPanel panelPuntuaciones = new JPanel(new GridLayout(numJugadores, 1));
        panelPuntuaciones.setBackground(new Color(34, 34, 34));
        lblPuntuaciones = new JLabel[numJugadores];
        for (int i = 0; i < numJugadores; i++) {
            lblPuntuaciones[i] = new JLabel(nombresJugadores.get(i) + ": 0", SwingConstants.CENTER);
            lblPuntuaciones[i].setForeground(Color.WHITE);
            lblPuntuaciones[i].setFont(new Font("Arial", Font.BOLD, 14));
            panelPuntuaciones.add(lblPuntuaciones[i]);
        }
        panelDerecho.add(panelPuntuaciones, BorderLayout.NORTH);

        JPanel panelSacoYControles = new JPanel();
        panelSacoYControles.setLayout(new BorderLayout());
        panelSacoYControles.setBackground(new Color(34, 34, 34));
        panelSacoYControles.add(inicializarSaco(), BorderLayout.CENTER);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
        panelControles.setBackground(new Color(34, 34, 34));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        Dimension botonSize = new Dimension(180, 45);
        int separacion = 10;

        btnConfirmar = new JButton("Confirmar Movimiento");
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(34, 34, 34));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnConfirmar.setBorder(BorderFactory.createCompoundBorder(
                btnConfirmar.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnConfirmar.setUI(new RoundedButtonUI());
        btnConfirmar.setPreferredSize(botonSize);
        btnConfirmar.setMaximumSize(botonSize);
        btnConfirmar.setMinimumSize(botonSize);
        btnConfirmar.addActionListener(e -> confirmarMovimiento());
        panelControles.add(btnConfirmar);
        panelControles.add(Box.createRigidArea(new Dimension(0, separacion)));

        btnCancelarMovimiento = new JButton("Cancelar Movimiento");
        btnCancelarMovimiento.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelarMovimiento.setBackground(new Color(34, 34, 34));
        btnCancelarMovimiento.setForeground(Color.WHITE);
        btnCancelarMovimiento.setFocusPainted(false);
        btnCancelarMovimiento.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnCancelarMovimiento.setBorder(BorderFactory.createCompoundBorder(
                btnCancelarMovimiento.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnCancelarMovimiento.setUI(new RoundedButtonUI());
        btnCancelarMovimiento.setPreferredSize(botonSize);
        btnCancelarMovimiento.setMaximumSize(botonSize);
        btnCancelarMovimiento.setMinimumSize(botonSize);
        btnCancelarMovimiento.addActionListener(e -> cancelarMovimiento());
        panelControles.add(btnCancelarMovimiento);
        panelControles.add(Box.createRigidArea(new Dimension(0, separacion)));

        btnPasarTurno = new JButton("Pasar Turno");
        btnPasarTurno.setFont(new Font("Arial", Font.BOLD, 14));
        btnPasarTurno.setBackground(new Color(34, 34, 34));
        btnPasarTurno.setForeground(Color.WHITE);
        btnPasarTurno.setFocusPainted(false);
        btnPasarTurno.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnPasarTurno.setBorder(BorderFactory.createCompoundBorder(
                btnPasarTurno.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnPasarTurno.setUI(new RoundedButtonUI());
        btnPasarTurno.setPreferredSize(botonSize);
        btnPasarTurno.setMaximumSize(botonSize);
        btnPasarTurno.setMinimumSize(botonSize);
        btnPasarTurno.addActionListener(e -> pasarTurno());
        panelControles.add(btnPasarTurno);
        panelControles.add(Box.createRigidArea(new Dimension(0, separacion)));

        JButton btnFinalizarPartida = new JButton("Finalizar Partida");
        btnFinalizarPartida.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizarPartida.setBackground(new Color(255, 182, 193));
        btnFinalizarPartida.setForeground(new Color(34, 34, 34));
        btnFinalizarPartida.setFocusPainted(false);
        btnFinalizarPartida.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnFinalizarPartida.setBorder(BorderFactory.createCompoundBorder(
                btnFinalizarPartida.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnFinalizarPartida.setUI(new RoundedButtonUI());
        btnFinalizarPartida.setPreferredSize(botonSize);
        btnFinalizarPartida.setMaximumSize(botonSize);
        btnFinalizarPartida.setMinimumSize(botonSize);
        btnFinalizarPartida.addActionListener(e -> finalizarPartida());
        panelControles.add(btnFinalizarPartida);
        panelControles.add(Box.createRigidArea(new Dimension(0, separacion)));

        JButton btnGuardarPartida = new JButton("Guardar Partida");
        btnGuardarPartida.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardarPartida.setBackground(new Color(144, 238, 144));
        btnGuardarPartida.setForeground(new Color(34, 34, 34));
        btnGuardarPartida.setFocusPainted(false);
        btnGuardarPartida.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnGuardarPartida.setBorder(BorderFactory.createCompoundBorder(
                btnGuardarPartida.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnGuardarPartida.setUI(new RoundedButtonUI());
        btnGuardarPartida.setPreferredSize(botonSize);
        btnGuardarPartida.setMaximumSize(botonSize);
        btnGuardarPartida.setMinimumSize(botonSize);
        btnGuardarPartida.addActionListener(e -> controlador.salvarPartida());
        panelControles.add(btnGuardarPartida);
        panelControles.add(Box.createRigidArea(new Dimension(0, separacion)));

        JButton btnVolverMenu = new JButton("Volver al menú");
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolverMenu.setBackground(new Color(255, 239, 213));
        btnVolverMenu.setForeground(new Color(34, 34, 34));
        btnVolverMenu.setFocusPainted(false);
        btnVolverMenu.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 245), 2));
        btnVolverMenu.setBorder(BorderFactory.createCompoundBorder(
                btnVolverMenu.getBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnVolverMenu.setUI(new RoundedButtonUI());
        btnVolverMenu.setPreferredSize(botonSize);
        btnVolverMenu.setMaximumSize(botonSize);
        btnVolverMenu.setMinimumSize(botonSize);
        btnVolverMenu.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "Si no has guardado la partida, la perderás si sales ahora.\n¿Seguro que quieres salir?",
                "Aviso",
                JOptionPane.YES_NO_OPTION
            );
            if (respuesta == JOptionPane.YES_OPTION) {
                controlador.mostrarVista("INICIO");
            }
        });
        panelControles.add(btnVolverMenu);

        panelSacoYControles.add(panelControles, BorderLayout.SOUTH);

        panelDerecho.add(panelSacoYControles, BorderLayout.CENTER);

        add(panelDerecho, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    /**
     * Inicializa el panel del atril del jugador con las letras disponibles.
     * @return JPanel con los botones de las letras del atril.
     */
    private JPanel inicializarSaco() {
        JPanel panelSaco = new JPanel();
        panelSaco.setLayout(new BoxLayout(panelSaco, BoxLayout.Y_AXIS));
        panelSaco.setBackground(new Color(34, 34, 34));
        panelSaco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSaco.setPreferredSize(new Dimension(250, getHeight()));

        JLabel lblSaco = new JLabel("Tus letras:");
        lblSaco.setFont(new Font("Arial", Font.BOLD, 16));
        lblSaco.setForeground(new Color(245, 245, 245));
        lblSaco.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSaco.add(lblSaco);

        JPanel panelBotonesSaco = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBotonesSaco.setBackground(new Color(34, 34, 34));

        List<String> letras = controlador.getLetrasJugadorTurno();

        for (int i = 0; i < 7; i++) {
            String letra = (i < letras.size()) ? letras.get(i) : "";
            JButton botonLetra = new JButton(letra);
            botonLetra.setFont(new Font("Arial", Font.BOLD, 16));
            botonLetra.setPreferredSize(new Dimension(45, 45));
            botonLetra.setMaximumSize(new Dimension(45, 45));
            botonLetra.setMinimumSize(new Dimension(45, 45));
            botonLetra.setBackground(new Color(50, 50, 50));
            botonLetra.setForeground(Color.WHITE);
            botonLetra.setFocusPainted(false);
            botonLetra.setMargin(new Insets(0, 0, 0, 0));
            botonLetra.setHorizontalTextPosition(SwingConstants.CENTER);

            final int indice = i;
            final JButton botonLetraFinal = botonLetra;
            botonLetra.addActionListener(e -> {
                if (botonLetraSeleccionado != null) {
                    botonLetraSeleccionado.setBackground(new Color(50, 50, 50));
                    botonLetraSeleccionado.setForeground(Color.WHITE);
                }

                botonLetraSeleccionado = botonLetraFinal;
                letraSeleccionada = botonLetraFinal.getText();
                indiceLetraSeleccionada = indice;

                botonLetraFinal.setBackground(new Color(255, 200, 200)); // rojo claro
                botonLetraFinal.setForeground(Color.BLACK);
            });

            panelBotonesSaco.add(botonLetra);
            botonesSaco.add(botonLetra);
        }

        panelSaco.add(panelBotonesSaco);

        return panelSaco;
    }

    /**
     * Listener para los botones del tablero. Permite colocar letras en el tablero.
     */
    private class BotonTableroListener implements ActionListener {
        private int fila;
        private int columna;

        /**
         * Crea un listener para un botón del tablero.
         * @param fila Fila del botón.
         * @param columna Columna del botón.
         */
        public BotonTableroListener(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        /**
         * Maneja el evento de colocar una letra en el tablero.
         * @param e Evento de acción.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (letraSeleccionada == null || letraSeleccionada.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecciona una letra del saco antes de colocarla.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JButton boton = botonesTablero[fila][columna];
            if (boton.getText().isEmpty()) {
                boton.setText(letraSeleccionada);

                jugadaActual.put(letraSeleccionada + "_" + fila + "_" + columna, new int[]{fila, columna});

                if (botonLetraSeleccionado != null) {
                    botonLetraSeleccionado.setEnabled(false);
                    botonLetraSeleccionado.setBackground(new Color(80, 80, 80));
                    botonLetraSeleccionado.setForeground(Color.GRAY);
                    botonLetraSeleccionado = null;
                }

                botonesColocados.add(boton);
                letrasColocadas.add(letraSeleccionada);


                letraSeleccionada = null;
                indiceLetraSeleccionada = -1;
            } else {
                JOptionPane.showMessageDialog(null, "Esta casilla ya está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Confirma el movimiento actual del jugador, enviando la jugada al controlador.
     * Muestra mensajes de error o éxito según el resultado.
     */
    private void confirmarMovimiento() {
        if (jugadaActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No has colocado ninguna letra.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Map<String, List<int[]>> jugadaParaControlador = new java.util.HashMap<>();
        for (Map.Entry<String, int[]> entry : jugadaActual.entrySet()) {
            String clave = entry.getKey();
            String letra = clave.split("_")[0];
            int[] coords = entry.getValue();
            int[] coordsBase0 = new int[] { coords[0] + 1, coords[1] + 1 };

            jugadaParaControlador.computeIfAbsent(letra, k -> new ArrayList<>()).add(coordsBase0);
        }

        int resultado = controlador.realizarJugada(jugadaParaControlador);

        switch (resultado) {
            case 0:
                controlador.eliminarLetrasDelAtril(letrasColocadas);
                jugadaActual.clear();
                botonesColocados.clear();
                letrasColocadas.clear();
                actualizarPuntuaciones();
                JOptionPane.showMessageDialog(this, "Movimiento Confirmado","Nice", JOptionPane.INFORMATION_MESSAGE);
                pasarTurno();
                break;
            case -1:
                JOptionPane.showMessageDialog(this, "No se puede colocar una sola letra en el tablero vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -2:
                JOptionPane.showMessageDialog(this, "Las letras no están en el centro del tablero.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -3:
                JOptionPane.showMessageDialog(this, "Las letras no están alineadas en la misma fila o columna.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -4:
                JOptionPane.showMessageDialog(this, "Hay un espacio en blanco entre las letras en la misma columna.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -5:
                JOptionPane.showMessageDialog(this, "Hay un espacio en blanco entre las letras en la misma fila.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -6:
                JOptionPane.showMessageDialog(this, "Las letras no están contiguas a ninguna letra existente.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -7:
                JOptionPane.showMessageDialog(this, "Las letras no forman una palabra válida.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -8:
                JOptionPane.showMessageDialog(this, "PalabraVF no valida.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            case -9:
                JOptionPane.showMessageDialog(this, "PalabraHF no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Error desconocido al realizar la jugada.", "Error", JOptionPane.ERROR_MESSAGE);
                cancelarMovimiento();
                break;
        }
    }

    /**
     * Pasa el turno al siguiente jugador, gestionando el turno de los robots si corresponde.
     */
    private void pasarTurno() {
        
        controlador.pasarTurno();
        jugadaActual.clear();
        String nombreJugadorActual = controlador.getTurnoActual();
        if (nombreJugadorActual.startsWith("Bot")) {
            jugarTurnoRobot();
            pasarTurno();
        } else {
            cargarTablero();
            actualizarSaco();
            actualizarPuntuaciones();
        }
    }

    /**
     * Finaliza la partida, mostrando el ganador si procede y volviendo al menú principal.
     */
    private void finalizarPartida() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres finalizar la partida?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            String ganador = controlador.finalizarPartida();
            if(ganador != null) {
                JOptionPane.showMessageDialog(this, "Gana el jugador " + ganador + "!!", "Finalizar", JOptionPane.INFORMATION_MESSAGE);
                controlador.mostrarVista("INICIO");
            } else {
                JOptionPane.showMessageDialog(this, "Aún puedes jugar palabras.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Cancela el movimiento actual, restaurando el estado del tablero y el atril.
     */
    private void cancelarMovimiento() {
        for (JButton boton : botonesColocados) {
            boton.setText("");
        }
        for (int i = 0; i < botonesSaco.size(); i++) {
            JButton botonLetra = botonesSaco.get(i);
            if (!botonLetra.getText().isEmpty()) {
                botonLetra.setEnabled(true);
                botonLetra.setBackground(new Color(50, 50, 50));
                botonLetra.setForeground(Color.WHITE);
            }
        }
        botonLetraSeleccionado = null;
        letraSeleccionada = null;
        indiceLetraSeleccionada = -1;
        botonesColocados.clear();
        letrasColocadas.clear();
        jugadaActual.clear();

        cargarTablero();
    }

    /**
     * Actualiza el contenido del atril del jugador en turno.
     */
    private void actualizarSaco() {
        List<String> letras = controlador.getLetrasJugadorTurno();
        for (int i = 0; i < botonesSaco.size(); i++) {
            JButton botonLetra = botonesSaco.get(i);
            if (i < letras.size()) {
                botonLetra.setText(letras.get(i));
                botonLetra.setEnabled(true);
                botonLetra.setBackground(new Color(50, 50, 50));
                botonLetra.setForeground(Color.WHITE);
            } else {
                botonLetra.setText("");
                botonLetra.setEnabled(false);
                botonLetra.setBackground(new Color(80, 80, 80));
                botonLetra.setForeground(Color.GRAY);
            }
        }
    }

    /**
     * Gestiona el turno del robot, mostrando la jugada realizada y actualizando la vista.
     */
    private void jugarTurnoRobot() {
        String nombreJugadorActual = controlador.getTurnoActual();

        if (nombreJugadorActual.startsWith("Bot")) {
            Map<String, List<int[]>> jugadaRobot = controlador.getJugadaRobot();
            if (jugadaRobot == null) controlador.pasarTurno();

            if (jugadaRobot == null) {
                JOptionPane.showMessageDialog(this, "El robot no pudo realizar su jugada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            for (Map.Entry<String, List<int[]>> entrada : jugadaRobot.entrySet()) {
                String letra = entrada.getKey();
                List<int[]> coordenadasList = entrada.getValue();
    
                for (int[] coordenadas : coordenadasList) {
                    int fila = coordenadas[0]-1;
                    int columna = coordenadas[1]-1;
    
                    botonesTablero[fila][columna].setText(letra);
                }
            }

            actualizarPuntuaciones();
    
            actualizarSaco();
    
            JOptionPane.showMessageDialog(this, "El robot ha realizado su jugada.", "Turno del Robot", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Actualiza las puntuaciones de los jugadores en la vista.
     */
    private void actualizarPuntuaciones() {
        List<String> nombres = controlador.getNombresJugadores();
        List<Integer> puntuaciones = controlador.getPuntuacionesJugadores();

        for (int i = 0; i < lblPuntuaciones.length; i++) {
            lblPuntuaciones[i].setText(nombres.get(i) + ": " + puntuaciones.get(i));
        }
    }
}
