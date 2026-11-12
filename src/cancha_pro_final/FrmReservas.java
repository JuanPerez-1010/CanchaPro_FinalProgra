/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cancha_pro_final;

/**
 *
 * @author PERSONAL
 */

import java.time.LocalDate;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FrmReservas extends javax.swing.JFrame {
    
    Reserva[] arregloReservas = new Reserva[200];
    int contadorReservas = 0;
    Clientes[] arregloClientes = new Clientes[200];
    int contadorClientes = 0;
    Canchas[] arregloCanchas = new Canchas[50];
    int contadorCanchas = 0;
    boolean cambios = false;
    int indiceEditar = -1;
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmReservas.class.getName());

    /**
     * Creates new form FrmReservas
     */
    public FrmReservas() {
         initComponents();
        setLocationRelativeTo(null);

        // 1. Cargar los 3 archivos .txt a los arreglos en memoria
        contadorClientes = Clientes.CargarArchivosClientes(arregloClientes);
        contadorCanchas = Canchas.CargarCanchas(arregloCanchas, ICONIFIED);
        contadorReservas = Reserva.CargarArchivosReservas(arregloReservas);

        // 2. Llenar combos de Clientes y Canchas DISPONIBLES
        cargarComboClientes();
        cargarComboCanchas();

        // 3. Llenar combo de Estados y criterios de búsqueda
        ComboEstado.removeAllItems();
        ComboEstado.addItem("PENDIENTE");
        ComboEstado.addItem("CONFIRMADA");
        ComboEstado.addItem("EN_CURSO");
        ComboEstado.addItem("FINALIZADA");

        ComboBuscarReserva.removeAllItems();
        ComboBuscarReserva.addItem("Por Codigo");
        ComboBuscarReserva.addItem("Por Cliente");
        ComboBuscarReserva.addItem("Por Fecha");

        // 4. Configurar valores iniciales y bloquear campos automáticos
        txtFecha.setText(LocalDate.now().toString());
        Txtcodigo.setText(Reserva.generarCodigo(arregloReservas, contadorReservas));
        Txtcodigo.setEditable(false);
        txtfin.setEditable(false);
        TxtTotalAdicional.setEditable(false);
        ComboGuardarCambios.setEnabled(false); // Se activa solo al presionar Editar

        // 5. Cargar horarios de la cancha seleccionada, calcular y llenar tabla
        cargarHorariosDeCancha();
        calcularExtrasYFin();
        actualizarTablaYContadores();
   

    }
    //-------------------------------------------------------------------METODOOS
    private void cargarComboClientes() {
    comboclientes.removeAllItems();
    for (int i = 0; i < contadorClientes; i++) {
    if (arregloClientes[i] != null) {
     comboclientes.addItem(arregloClientes[i].getCodigo() + " - " + arregloClientes[i].getNombre());
     }
      }
    }
   private void cargarComboCanchas() {
        combocanchas.removeAllItems();
        for (int i = 0; i < contadorCanchas; i++) {
            Canchas c = arregloCanchas[i];
            if (c != null && c.getEstadoC().equalsIgnoreCase("DISPONIBLE")) {
                combocanchas.addItem(c.getCodigoC() + " - " + c.getNombreC());
            }
        }
    }
    private Canchas obtenerCanchaSeleccionada() {
        if (combocanchas.getSelectedItem() == null) return null;
        String codCancha = combocanchas.getSelectedItem().toString().split(" - ")[0].trim();
        for (int i = 0; i < contadorCanchas; i++) {
            if (arregloCanchas[i] != null && arregloCanchas[i].getCodigoC().equalsIgnoreCase(codCancha)) {
                return arregloCanchas[i];
            }
        }
        return null;
    }
    private void cargarHorariosDeCancha() {
        Canchas c = obtenerCanchaSeleccionada();
        ComboInicio.removeAllItems();
        ComboHoras.removeAllItems();

        ComboHoras.addItem("1");
        ComboHoras.addItem("2");
        ComboHoras.addItem("3");
        ComboHoras.addItem("4");

        if (c != null) {
            try {
                int horaAbre = Integer.parseInt(c.getHoraApertura().trim().substring(0, 2));
                int horaCierra = Integer.parseInt(c.getHoraCierre().trim().substring(0, 2));

                for (int h = horaAbre; h < horaCierra; h++) {
                    if (h < 10) {
                        ComboInicio.addItem("0" + h + ":00");
                    } else {
                        ComboInicio.addItem(h + ":00");
                    }
                }
            } catch (Exception e) {
                ComboInicio.addItem("08:00");
            }
        }
    }
    private String obtenerTextoServicios() {
        String servicios = "";
        if (checkBalon1.isSelected())      servicios += "Balon + ";
        if (checkchalecos.isSelected())    servicios += "Chalecos + ";
        if (checkIluminacion.isSelected()) servicios += "Iluminacion + ";
        if (checkAgua.isSelected())        servicios += "Agua + ";
        if (checkAgua1.isSelected())       servicios += "Agua2 + ";
        return servicios.isEmpty() ? "Ninguno" : servicios.substring(0, servicios.length() - 3);
    }
    private void calcularExtrasYFin() {
        double extra = 0.0;
        if (checkBalon1.isSelected())      extra = extra + 15.00;
        if (checkchalecos.isSelected())    extra = extra + 5.00;
        if (checkIluminacion.isSelected()) extra = extra + 20.00;
        if (checkAgua.isSelected())        extra = extra + 6.00;
        if (checkAgua1.isSelected())       extra = extra + 6.00;

        TxtTotalAdicional.setText(String.format(Locale.US, "%.2f", extra));

        if (ComboInicio.getSelectedItem() != null && ComboHoras.getSelectedItem() != null) {
            String inicio = ComboInicio.getSelectedItem().toString();
            int horas = Integer.parseInt(ComboHoras.getSelectedItem().toString());
            txtfin.setText(Reserva.calcularHoraFin(inicio, horas));
        }
    }
    private void actualizarTablaYContadores() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        int conf = 0;
        int pend = 0;
        int canc = 0;
        for (int i = 0; i < contadorReservas; i++) {
            Reserva r = arregloReservas[i];
            if (r != null) {
            String nomCliente = Clientes.obtenerNombrePorCodigo(arregloClientes, contadorClientes, r.getCodigoCliente());
            String horario = r.getHoraInicio() + "-" + r.getHoraFin();
           modelo.addRow(new Object[]{
            r.getCodigoR(),
            nomCliente,
             r.getCodigoCancha(),
            r.getFecha(),
             horario,
             String.format(Locale.US, "Q%.2f", r.getTotal()),
             String.format(Locale.US, "Q%.2f", r.getSaldo()),
             r.getEstado()
                });

                if (r.getEstado().equalsIgnoreCase("CONFIRMADA") || r.getEstado().equalsIgnoreCase("EN_CURSO")) {
                    conf++;
                } else if (r.getEstado().equalsIgnoreCase("PENDIENTE")) {
                    pend++;
                } else if (r.getEstado().equalsIgnoreCase("CANCELADA")) {
                    canc++;
                }
            }
        }

        jLabel8.setText("Confirmadas: " + conf);
        lblinicio1.setText("Pendientes: " + pend);
        lblinicio2.setText("Canceladas: " + canc);
    }
    private void limpiarFormulario() {
        indiceEditar = -1;
        Txtcodigo.setText(Reserva.generarCodigo(arregloReservas, contadorReservas));
        txtFecha.setText(LocalDate.now().toString());
        checkBalon1.setSelected(false);
        checkchalecos.setSelected(false);
        checkIluminacion.setSelected(false);
        checkAgua.setSelected(false);
        checkAgua1.setSelected(false);
        ComboEstado.setSelectedIndex(0);
        ButonAgregar.setEnabled(true);
        ComboGuardarCambios.setEnabled(false);
        jTable1.clearSelection();
        calcularExtrasYFin();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ButonRegresar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        ComboBuscarReserva = new javax.swing.JComboBox<>();
        lblinicio2 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        ButonBuscarRerserva = new javax.swing.JButton();
        ButonEditarReserva222 = new javax.swing.JButton();
        CancelarReserva222 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ButonGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblinicio = new javax.swing.JLabel();
        lblHoras = new javax.swing.JLabel();
        lblFin = new javax.swing.JLabel();
        ComboInicio = new javax.swing.JComboBox<>();
        ComboHoras = new javax.swing.JComboBox<>();
        txtfin = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        combocanchas = new javax.swing.JComboBox<>();
        ButonBuscarCancha1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        Txtcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboclientes = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        ButonBuscarCliente = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        lblinicio1 = new javax.swing.JLabel();
        checkchalecos = new javax.swing.JCheckBox();
        checkBalon1 = new javax.swing.JCheckBox();
        checkIluminacion = new javax.swing.JCheckBox();
        checkAgua = new javax.swing.JCheckBox();
        checkAgua1 = new javax.swing.JCheckBox();
        lbltotaladicional = new javax.swing.JLabel();
        TxtTotalAdicional = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        ComboGuardarCambios = new javax.swing.JButton();
        ButonAgregar = new javax.swing.JButton();
        ComboEstado = new javax.swing.JComboBox<>();
        ButonLimpiar2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("RESERVAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(241, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ButonRegresar.setText("Regresar");
        ButonRegresar.addActionListener(this::ButonRegresarActionPerformed);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setFocusable(false);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ComboBuscarReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Codigo", "Por Cliente ", " ", " " }));

        lblinicio2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblinicio2.setForeground(new java.awt.Color(255, 255, 255));
        lblinicio2.setText("Buscar Reservas y Herramientas");

        ButonBuscarRerserva.setText("Buscar");
        ButonBuscarRerserva.addActionListener(this::ButonBuscarRerservaActionPerformed);

        ButonEditarReserva222.setText("Editar");
        ButonEditarReserva222.addActionListener(this::ButonEditarReserva222ActionPerformed);

        CancelarReserva222.setText("Canselar");
        CancelarReserva222.addActionListener(this::CancelarReserva222ActionPerformed);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(234, 234, 234)
                                .addComponent(ButonEditarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblinicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(0, 61, Short.MAX_VALUE)
                                .addComponent(CancelarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtbuscar)))
                        .addGap(18, 18, 18)
                        .addComponent(ComboBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButonBuscarRerserva, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(lblinicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonBuscarRerserva, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonEditarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Pendientes");

        jLabel4.setText("confirmadas");

        jLabel5.setText("Pendientes");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(19, 19, 19))
        );

        ButonGuardar.setBackground(new java.awt.Color(255, 255, 255));
        ButonGuardar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonGuardar.setForeground(new java.awt.Color(0, 0, 0));
        ButonGuardar.setText("Guardar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 153, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel3.setFocusable(false);

        jPanel4.setBackground(new java.awt.Color(51, 102, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblinicio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblinicio.setForeground(new java.awt.Color(255, 255, 255));
        lblinicio.setText("Inicio");

        lblHoras.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblHoras.setForeground(new java.awt.Color(255, 255, 255));
        lblHoras.setText("Horas");

        lblFin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFin.setForeground(new java.awt.Color(255, 255, 255));
        lblFin.setText("Fin");

        ComboInicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboInicio.addActionListener(this::ComboInicioActionPerformed);

        ComboHoras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboHoras.addActionListener(this::ComboHorasActionPerformed);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Cancha");

        combocanchas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combocanchas.addActionListener(this::combocanchasActionPerformed);

        ButonBuscarCancha1.setText("Buscar");
        ButonBuscarCancha1.addActionListener(this::ButonBuscarCancha1ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblinicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHoras, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combocanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblFin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtfin, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(260, Short.MAX_VALUE)
                    .addComponent(ButonBuscarCancha1)
                    .addGap(2, 2, 2)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combocanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(177, Short.MAX_VALUE)
                    .addComponent(ButonBuscarCancha1)
                    .addContainerGap()))
        );

        jPanel5.setBackground(new java.awt.Color(51, 102, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Txtcodigo.setEditable(false);
        Txtcodigo.setBackground(new java.awt.Color(204, 0, 51));
        Txtcodigo.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Txtcodigo.setForeground(new java.awt.Color(255, 255, 255));
        Txtcodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        Txtcodigo.addActionListener(this::TxtcodigoActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Codigo");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cliente ");

        comboclientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboclientes.addActionListener(this::comboclientesActionPerformed);

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha");

        ButonBuscarCliente.setText("Buscar");
        ButonBuscarCliente.addActionListener(this::ButonBuscarClienteActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButonBuscarCliente)
                .addGap(80, 80, 80))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ButonBuscarCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(51, 102, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblinicio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblinicio1.setForeground(new java.awt.Color(255, 255, 255));
        lblinicio1.setText("SERVICIOS ADICIONALES");

        checkchalecos.setBackground(new java.awt.Color(51, 204, 0));
        checkchalecos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkchalecos.setForeground(new java.awt.Color(0, 0, 0));
        checkchalecos.setText("Chalecos (Q 5.00)");
        checkchalecos.addActionListener(this::checkchalecosActionPerformed);

        checkBalon1.setBackground(new java.awt.Color(51, 204, 0));
        checkBalon1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkBalon1.setForeground(new java.awt.Color(0, 0, 0));
        checkBalon1.setText("Balones (Q 15.00)");
        checkBalon1.addActionListener(this::checkBalon1ActionPerformed);

        checkIluminacion.setBackground(new java.awt.Color(51, 204, 0));
        checkIluminacion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkIluminacion.setForeground(new java.awt.Color(0, 0, 0));
        checkIluminacion.setText("Iluminacion (Q 20.00)");
        checkIluminacion.setActionCommand("Iluminacion (Q 20.00)");
        checkIluminacion.addActionListener(this::checkIluminacionActionPerformed);

        checkAgua.setBackground(new java.awt.Color(102, 204, 0));
        checkAgua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAgua.setForeground(new java.awt.Color(0, 0, 0));
        checkAgua.setText("Arbitro (Q 50.00)");
        checkAgua.addActionListener(this::checkAguaActionPerformed);

        checkAgua1.setBackground(new java.awt.Color(51, 204, 0));
        checkAgua1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAgua1.setForeground(new java.awt.Color(0, 0, 0));
        checkAgua1.setText("Agua (Q 6.00)");
        checkAgua1.addActionListener(this::checkAgua1ActionPerformed);

        lbltotaladicional.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltotaladicional.setForeground(new java.awt.Color(255, 255, 255));
        lbltotaladicional.setText("Total de lo adicional: Q");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(lbltotaladicional, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtTotalAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(lblinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBalon1)
                            .addComponent(checkIluminacion)
                            .addComponent(checkAgua1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkAgua, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkchalecos))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkchalecos)
                    .addComponent(checkBalon1))
                .addGap(12, 12, 12)
                .addComponent(checkIluminacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAgua1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAgua))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbltotaladicional, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTotalAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel8.setBackground(new java.awt.Color(51, 102, 0));

        ComboGuardarCambios.setText("Guardar Cambios");
        ComboGuardarCambios.addActionListener(this::ComboGuardarCambiosActionPerformed);

        ButonAgregar.setText("Agregar");
        ButonAgregar.addActionListener(this::ButonAgregarActionPerformed);

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ButonLimpiar2.setText("Limpiar");
        ButonLimpiar2.addActionListener(this::ButonLimpiar2ActionPerformed);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButonLimpiar2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(ButonLimpiar2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ComboGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Cliente", "Cancha", "Fecha", "Horario", "Total", "Saldo", "Estado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane1))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//--------------------------------------------------------------------------------BOTONES Y TABLAS  DE LA INTERFAZ  
    private void ButonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonRegresarActionPerformed
       if (cambios == true) {
        int resp = JOptionPane.showConfirmDialog(this,"¿Desea salir sin guardar los cambios en Reservas.txt?","Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resp == JOptionPane.YES_OPTION) {
         FrmInicio inicio = new FrmInicio();
        inicio.setVisible(true);
          this.dispose();
            }
        } else {
            FrmInicio inicio = new FrmInicio();
            inicio.setVisible(true);
            this.dispose();
        } 
    }//GEN-LAST:event_ButonRegresarActionPerformed

    private void TxtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtcodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtcodigoActionPerformed

    private void ComboGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboGuardarCambiosActionPerformed
      if (indiceEditar == -1) return;

        String fecha = txtFecha.getText().trim();
        try {
            LocalDate.parse(fecha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Use el formato AAAA-MM-DD.");
            return;
        }
        calcularExtrasYFin();
        Canchas canchaObj = obtenerCanchaSeleccionada();
        Reserva rActual = arregloReservas[indiceEditar];

        String codCan = canchaObj.getCodigoC().trim();
        String inicio = ComboInicio.getSelectedItem().toString();
        int horas = Integer.parseInt(ComboHoras.getSelectedItem().toString());
        String fin = txtfin.getText().trim();

        if (!Reserva.cabeEnHorarioCancha(inicio, fin, canchaObj.getHoraApertura(), canchaObj.getHoraCierre())) {
            JOptionPane.showMessageDialog(this, "Horario fuera del rango de apertura y cierre de la cancha.");
            return;
        }

        if (Reserva.hayChoqueDeHorario(arregloReservas, contadorReservas, codCan, fecha, inicio, fin, rActual.getCodigoR())) {
            JOptionPane.showMessageDialog(this, "¡Conflicto de horario con otra reserva existente!");
            return;
        }

        double extra = Double.parseDouble(TxtTotalAdicional.getText());
        double nuevoTotal = Reserva.calcularTotal(canchaObj.getPrecioBase(), horas, extra);

        if (rActual.getAnticipo() > nuevoTotal) {
            JOptionPane.showMessageDialog(this, "El nuevo total es menor al anticipo ya pagado (Q" + rActual.getAnticipo() + ").");
            return;
        }

        rActual.setCodigoCliente(comboclientes.getSelectedItem().toString().split(" - ")[0].trim());
        rActual.setCodigoCancha(codCan);
        rActual.setFecha(fecha);
        rActual.setHoraInicio(inicio);
        rActual.setHoras(horas);
        rActual.setHoraFin(fin);
        rActual.setServicios(obtenerTextoServicios());
        rActual.setTotal(nuevoTotal);
        rActual.setSaldo(nuevoTotal - rActual.getAnticipo());
        rActual.setEstado(ComboEstado.getSelectedItem().toString());

        cambios = true;
        actualizarTablaYContadores();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Reserva actualizada. Presione GUARDAR para escribir en Reservas.txt.");
    } 
    private void ButonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
       
    }//GEN-LAST:event_ComboGuardarCambiosActionPerformed

    private void ButonBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButonBuscarClienteActionPerformed

    private void checkchalecosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkchalecosActionPerformed
        calcularExtrasYFin();
    }//GEN-LAST:event_checkchalecosActionPerformed

    private void checkBalon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBalon1ActionPerformed
        calcularExtrasYFin();
    }//GEN-LAST:event_checkBalon1ActionPerformed

    private void checkIluminacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkIluminacionActionPerformed
       calcularExtrasYFin();
    }//GEN-LAST:event_checkIluminacionActionPerformed

    private void checkAguaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAguaActionPerformed
        calcularExtrasYFin();
    }//GEN-LAST:event_checkAguaActionPerformed

    private void checkAgua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAgua1ActionPerformed
       calcularExtrasYFin();
    }//GEN-LAST:event_checkAgua1ActionPerformed

    private void ButonBuscarCancha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonBuscarCancha1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButonBuscarCancha1ActionPerformed

    private void ButonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonAgregarActionPerformed
      if (comboclientes.getSelectedItem() == null || combocanchas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente y una cancha.");
            return;
        }

        String fecha = txtFecha.getText().trim();
        try {
            LocalDate.parse(fecha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Use el formato AAAA-MM-DD (ej. 2026-11-12).");
            return;
        }
        calcularExtrasYFin();
        Canchas canchaObj = obtenerCanchaSeleccionada();
        String codR = Txtcodigo.getText().trim();
        String codCli = comboclientes.getSelectedItem().toString().split(" - ")[0].trim();
        String codCan = canchaObj.getCodigoC().trim();
        String inicio = ComboInicio.getSelectedItem().toString();
        int horas = Integer.parseInt(ComboHoras.getSelectedItem().toString());
        String fin = txtfin.getText().trim();
        if (!Reserva.cabeEnHorarioCancha(inicio, fin, canchaObj.getHoraApertura(), canchaObj.getHoraCierre())) {
            JOptionPane.showMessageDialog(this, "Horario fuera de servicio. Esta cancha atiende de "
                    + canchaObj.getHoraApertura() + " a " + canchaObj.getHoraCierre() + ".");
            return;
        }
        if (Reserva.hayChoqueDeHorario(arregloReservas, contadorReservas, codCan, fecha, inicio, fin, "")) {
            JOptionPane.showMessageDialog(this, "¡Conflicto de horario! Esa cancha ya está reservada en ese turno.");
            return;
        }
        double extra = Double.parseDouble(TxtTotalAdicional.getText());
        double total = Reserva.calcularTotal(canchaObj.getPrecioBase(), horas, extra);
        String antTexto = JOptionPane.showInputDialog(this,"Total de la reserva: Q" + String.format(Locale.US, "%.2f", total)   + "\nIngrese el anticipo o pago inicial (Q):", "0.00");
        if (antTexto == null) return;
        double anticipo = 0.0;
        try {
            anticipo = Double.parseDouble(antTexto.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto numérico válido.");
            return;
        }

        if (anticipo < 0 || anticipo > total) {
            JOptionPane.showMessageDialog(this, "El anticipo no puede ser negativo ni mayor al Total (Q" + total + ").");
            return;
        }
        double saldo = total - anticipo;
        String estado = ComboEstado.getSelectedItem().toString();
        String servicios = obtenerTextoServicios();
        arregloReservas[contadorReservas] = new Reserva(codR, codCli, codCan, fecha,inicio, horas, fin, servicios, total, anticipo, saldo, estado, "Ninguno");
        contadorReservas++;
        cambios = true;
        actualizarTablaYContadores();
        limpiarFormulario();
        JOptionPane.showMessageDialog(this, "Reserva agregada. Presione GUARDAR para escribir en Reservas.txt.");
    }//GEN-LAST:event_ButonAgregarActionPerformed

    private void ButonLimpiar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonLimpiar2ActionPerformed
         limpiarFormulario();
    }//GEN-LAST:event_ButonLimpiar2ActionPerformed

    private void ButonBuscarRerservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonBuscarRerservaActionPerformed
       String texto = txtbuscar.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            actualizarTablaYContadores();
            return;
        }
        String criterio = ComboBuscarReserva.getSelectedItem().toString();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (int i = 0; i < contadorReservas; i++) {
            Reserva r = arregloReservas[i];
            if (r != null) {
                String nomCliente = Clientes.obtenerNombrePorCodigo(arregloClientes, contadorClientes, r.getCodigoCliente());
                boolean coincide = false;
                if (criterio.equalsIgnoreCase("Por Codigo") && r.getCodigoR().toLowerCase().contains(texto)) {
                    coincide = true;
                } else if (criterio.equalsIgnoreCase("Por Cliente") && nomCliente.toLowerCase().contains(texto)) {
                    coincide = true;
                } else if (criterio.equalsIgnoreCase("Por Fecha") && r.getFecha().toLowerCase().contains(texto)) {
                    coincide = true;
                }
                if (coincide) {
                    modelo.addRow(new Object[]{
                    r.getCodigoR(), nomCliente, r.getCodigoCancha(), r.getFecha(),
                    r.getHoraInicio() + "-" + r.getHoraFin(),
                    String.format(Locale.US, "Q%.2f", r.getTotal()),
                    String.format(Locale.US, "Q%.2f", r.getSaldo()),
                    r.getEstado()
                    });
                }
            }
        }
    }//GEN-LAST:event_ButonBuscarRerservaActionPerformed

    private void ButonEditarReserva222ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonEditarReserva222ActionPerformed
       int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva de la tabla para editar.");
            return;
        }

        String codR = jTable1.getValueAt(fila, 0).toString();
        int pos = Reserva.buscarPosicionPorCodigo(arregloReservas, contadorReservas, codR);
        if (pos == -1) return;
        Reserva r = arregloReservas[pos];
        if (r.getEstado().equalsIgnoreCase("FINALIZADA") || r.getEstado().equalsIgnoreCase("CANCELADA")) {
            JOptionPane.showMessageDialog(this, "No se puede modificar una reserva " + r.getEstado() + ".");
            return;
        }
        indiceEditar = pos;
        Txtcodigo.setText(r.getCodigoR());
        txtFecha.setText(r.getFecha());

        for (int i = 0; i < comboclientes.getItemCount(); i++) {
            if (comboclientes.getItemAt(i).startsWith(r.getCodigoCliente() + " -")) {
            comboclientes.setSelectedIndex(i);
            break;
            }
        }
        for (int i = 0; i < combocanchas.getItemCount(); i++) {
            if (combocanchas.getItemAt(i).startsWith(r.getCodigoCancha() + " -")) {
             combocanchas.setSelectedIndex(i);
              break;
            }
        }
        cargarHorariosDeCancha();
        ComboInicio.setSelectedItem(r.getHoraInicio());
        ComboHoras.setSelectedItem(String.valueOf(r.getHoras()));
        ComboEstado.setSelectedItem(r.getEstado());
        checkBalon1.setSelected(r.getServicios().contains("Balon"));
        checkchalecos.setSelected(r.getServicios().contains("Chalecos"));
        checkIluminacion.setSelected(r.getServicios().contains("Iluminacion"));
        checkAgua.setSelected(r.getServicios().contains("Agua"));
        checkAgua1.setSelected(r.getServicios().contains("Agua2"));
        calcularExtrasYFin();
        ButonAgregar.setEnabled(false);
        ComboGuardarCambios.setEnabled(true);
    }//GEN-LAST:event_ButonEditarReserva222ActionPerformed

    private void CancelarReserva222ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarReserva222ActionPerformed
       int fila = jTable1.getSelectedRow();
        if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una reserva de la tabla para cancelar.");
        return;
        }

        String codR = jTable1.getValueAt(fila, 0).toString();
        int pos = Reserva.buscarPosicionPorCodigo(arregloReservas, contadorReservas, codR);
        if (pos == -1) return;

        Reserva r = arregloReservas[pos];

        if (r.getEstado().equalsIgnoreCase("CANCELADA") || r.getEstado().equalsIgnoreCase("FINALIZADA")) {
        JOptionPane.showMessageDialog(this, "Esta reserva ya está " + r.getEstado() + ".");
        return;
        }
        String motivo = JOptionPane.showInputDialog(this, "Ingrese el motivo de cancelación:");
        if (motivo == null || motivo.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe indicar un motivo para cancelar la reserva.");
        return;
        }
        r.setEstado("CANCELADA");
        r.setMotivoCancelacion(motivo.trim() + " (" + LocalDate.now() + ")");
        cambios = true;
        actualizarTablaYContadores();
        JOptionPane.showMessageDialog(this, "Reserva cancelada y conservada en el historial. Presione GUARDAR.");
        
    }//GEN-LAST:event_CancelarReserva222ActionPerformed

    private void ComboInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboInicioActionPerformed
        calcularExtrasYFin();
    }//GEN-LAST:event_ComboInicioActionPerformed

    private void ComboHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboHorasActionPerformed
       calcularExtrasYFin();
    }//GEN-LAST:event_ComboHorasActionPerformed

    private void comboclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboclientesActionPerformed
        String dato = JOptionPane.showInputDialog(this, "Ingrese código, nombre o teléfono del cliente:");
        if (dato != null && !dato.trim().isEmpty()) {
            int pos = Clientes.buscarPosicionCliente(arregloClientes, contadorClientes, dato);
            if (pos != -1) {
                comboclientes.setSelectedIndex(pos);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }
        }
    }//GEN-LAST:event_comboclientesActionPerformed

    private void combocanchasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocanchasActionPerformed
        String dato = JOptionPane.showInputDialog(this, "Ingrese código o nombre de la cancha:");
        if (dato != null && !dato.trim().isEmpty()) {
            String buscar = dato.toLowerCase().trim();
            for (int i = 0; i < combocanchas.getItemCount(); i++) {
                if (combocanchas.getItemAt(i).toLowerCase().contains(buscar)) {
                    combocanchas.setSelectedIndex(i);
                    cargarHorariosDeCancha();
                    calcularExtrasYFin();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Cancha no encontrada o no disponible.");
        }
    }//GEN-LAST:event_combocanchasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmReservas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButonAgregar;
    private javax.swing.JButton ButonBuscarCancha1;
    private javax.swing.JButton ButonBuscarCliente;
    private javax.swing.JButton ButonBuscarRerserva;
    private javax.swing.JButton ButonEditarReserva222;
    private javax.swing.JButton ButonGuardar;
    private javax.swing.JButton ButonLimpiar2;
    private javax.swing.JButton ButonRegresar;
    private javax.swing.JButton CancelarReserva222;
    private javax.swing.JComboBox<String> ComboBuscarReserva;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JButton ComboGuardarCambios;
    private javax.swing.JComboBox<String> ComboHoras;
    private javax.swing.JComboBox<String> ComboInicio;
    private javax.swing.JTextField TxtTotalAdicional;
    private javax.swing.JTextField Txtcodigo;
    private javax.swing.JCheckBox checkAgua;
    private javax.swing.JCheckBox checkAgua1;
    private javax.swing.JCheckBox checkBalon1;
    private javax.swing.JCheckBox checkIluminacion;
    private javax.swing.JCheckBox checkchalecos;
    private javax.swing.JComboBox<String> combocanchas;
    private javax.swing.JComboBox<String> comboclientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFin;
    private javax.swing.JLabel lblHoras;
    private javax.swing.JLabel lblinicio;
    private javax.swing.JLabel lblinicio1;
    private javax.swing.JLabel lblinicio2;
    private javax.swing.JLabel lbltotaladicional;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtfin;
    // End of variables declaration//GEN-END:variables
}
