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
    
     Canchas[] arregloCanchas = new Canchas[50];
     int contadorCanchas = 0; 
    Reserva[] arregloReservas = new Reserva[200];
    int contadorReservas = 0;
    Clientes[] arregloClientes = new Clientes[200];
    int contadorClientes = 0;
    boolean cambios = false;
    int indiceEditar = -1;
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmReservas.class.getName());

    /**
     * Creates new form FrmReservas
     */
    public FrmReservas() {
       initComponents();
        jTable1.getTableHeader().setBackground(new java.awt.Color(30, 80, 50));
        jTable1.getTableHeader().setForeground(java.awt.Color.WHITE);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
       this.setSize(1280, 670);
        this.pack();
        setLocationRelativeTo(null);
        contadorClientes = Clientes.CargarArchivosClientes(arregloClientes);
        contadorCanchas = Canchas.CargarCanchas(arregloCanchas, arregloCanchas.length);
        contadorReservas = Reserva.CargarArchivosReservas(arregloReservas);

        cargarComboClientes();
        cargarComboCanchas();

        ComboEstado.removeAllItems();
        ComboEstado.addItem("PENDIENTE");
        ComboEstado.addItem("CONFIRMADA");
        ComboEstado.addItem("EN_CURSO");
        ComboEstado.addItem("FINALIZADA");

        ComboBuscarReserva.removeAllItems();
        ComboBuscarReserva.addItem("Por Codigo");
        ComboBuscarReserva.addItem("Por Cliente");
        ComboBuscarReserva.addItem("Por Fecha");

        txtFecha.setText(LocalDate.now().toString());
        Txtcodigo.setText(Reserva.generarCodigo(arregloReservas, contadorReservas));
        Txtcodigo.setEditable(false);
        txtfin.setEditable(false);
        TxtTotalAdicional.setEditable(false);
        ComboGuardarCambios.setEnabled(false); 

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
        if (FrmMenuCanchas.contadorCanchas > 0) {
            contadorCanchas = FrmMenuCanchas.contadorCanchas;
            for (int i = 0; i < contadorCanchas; i++) {
                arregloCanchas[i] = FrmMenuCanchas.arregloCanchas[i];
            }
        }
        for (int i = 0; i < contadorCanchas; i++) {
            Canchas c = arregloCanchas[i];
            if (c != null && c.getEstadoC().trim().equalsIgnoreCase("DISPONIBLE")) {
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
        if (checkBalon1.isSelected() == true) {
            servicios = servicios + "Balon ";
        }
        if (checkchalecos.isSelected() == true) {
            servicios = servicios + "Chalecos ";
        }
        if (checkIluminacion.isSelected() == true) {
            servicios = servicios + "Iluminacion ";
        }
        if (CheckArbitro.isSelected() == true) {
            servicios = servicios + "Arbitro ";
        }
        if (checkAgua1.isSelected() == true) {
            servicios = servicios + "Agua ";
        }

        if (servicios.equals("")) {
            return "Ninguno";
        } else {
            return servicios.trim();
        }
    }
    private void calcularExtrasYFin() {
       double extra = 0;

        if (checkBalon1.isSelected() == true) {
            extra = extra + 15.0;
        }
        if (checkchalecos.isSelected() == true) {
            extra = extra + 5.0;
        }
        if (checkIluminacion.isSelected() == true) {
            extra = extra + 20.0;
        }
        if (CheckArbitro.isSelected() == true) {
            extra = extra + 50.0;
        }
        if (checkAgua1.isSelected() == true) {
            extra = extra + 6.0;
        }
        TxtTotalAdicional.setText(String.valueOf(extra));
        if (ComboInicio.getSelectedItem() != null && ComboHoras.getSelectedItem() != null) {
        String inicio = ComboInicio.getSelectedItem().toString();
        String horasTexto = ComboHoras.getSelectedItem().toString();
        int horas = Integer.parseInt(horasTexto);
        String horaFin = Reserva.calcularHoraFin(inicio, horas);
        txtfin.setText(horaFin);
    }}
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
        CheckArbitro.setSelected(false);
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
        txtbuscar = new javax.swing.JTextField();
        ButonBuscarRerserva = new javax.swing.JButton();
        ButonEditarReserva222 = new javax.swing.JButton();
        CancelarReserva222 = new javax.swing.JButton();
        lblbuscar = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblinicio1 = new javax.swing.JLabel();
        lblinicio2 = new javax.swing.JLabel();
        ButonGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblinicio = new javax.swing.JLabel();
        lblHoras = new javax.swing.JLabel();
        lblFin = new javax.swing.JLabel();
        ComboInicio = new javax.swing.JComboBox<>();
        ComboHoras = new javax.swing.JComboBox<>();
        txtfin = new javax.swing.JTextField();
        combocanchas = new javax.swing.JComboBox<>();
        ButonBuscarCancha1 = new javax.swing.JButton();
        lblCanchas = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Txtcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboclientes = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        ButonBuscarCliente = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        checkchalecos = new javax.swing.JCheckBox();
        checkBalon1 = new javax.swing.JCheckBox();
        checkIluminacion = new javax.swing.JCheckBox();
        CheckArbitro = new javax.swing.JCheckBox();
        checkAgua1 = new javax.swing.JCheckBox();
        lbltotaladicional = new javax.swing.JLabel();
        TxtTotalAdicional = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        ComboGuardarCambios = new javax.swing.JButton();
        ButonAgregar = new javax.swing.JButton();
        ComboEstado = new javax.swing.JComboBox<>();
        ButonLimpiar2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 102, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        ButonRegresar.setBackground(new java.awt.Color(255, 255, 255));
        ButonRegresar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ButonRegresar.setForeground(new java.awt.Color(0, 0, 0));
        ButonRegresar.setText("Regresar");
        ButonRegresar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonRegresar.addActionListener(this::ButonRegresarActionPerformed);
        getContentPane().add(ButonRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 14, 151, 46));

        jPanel2.setBackground(new java.awt.Color(51, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel2.setFocusable(false);

        jPanel7.setBackground(new java.awt.Color(51, 102, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ComboBuscarReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Codigo", "Por Cliente ", " ", " " }));

        ButonBuscarRerserva.setText("Buscar");
        ButonBuscarRerserva.addActionListener(this::ButonBuscarRerservaActionPerformed);

        ButonEditarReserva222.setBackground(new java.awt.Color(204, 204, 0));
        ButonEditarReserva222.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonEditarReserva222.setForeground(new java.awt.Color(0, 0, 0));
        ButonEditarReserva222.setText("Editar");
        ButonEditarReserva222.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonEditarReserva222.addActionListener(this::ButonEditarReserva222ActionPerformed);

        CancelarReserva222.setBackground(new java.awt.Color(255, 0, 0));
        CancelarReserva222.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CancelarReserva222.setForeground(new java.awt.Color(255, 255, 255));
        CancelarReserva222.setText("Canselar");
        CancelarReserva222.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        CancelarReserva222.addActionListener(this::CancelarReserva222ActionPerformed);

        lblbuscar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblbuscar.setForeground(new java.awt.Color(255, 255, 255));
        lblbuscar.setText("Busqueda de Cancha y Herramietnas ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtbuscar)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(CancelarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(ComboBuscarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButonBuscarRerserva, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(ButonEditarReserva222, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(lblbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel9.setBackground(new java.awt.Color(51, 102, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("0");

        lblinicio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblinicio1.setForeground(new java.awt.Color(255, 255, 255));
        lblinicio1.setText("0");

        lblinicio2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblinicio2.setForeground(new java.awt.Color(255, 255, 255));
        lblinicio2.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(324, 324, 324))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblinicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ButonGuardar.setBackground(new java.awt.Color(255, 255, 255));
        ButonGuardar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonGuardar.setForeground(new java.awt.Color(0, 0, 0));
        ButonGuardar.setText("Guardar");
        ButonGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonGuardar.addActionListener(this::ButonGuardarActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(97, 97, 97))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 460, 280));

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

        txtfin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        combocanchas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combocanchas.addActionListener(this::combocanchasActionPerformed);

        ButonBuscarCancha1.setBackground(new java.awt.Color(153, 153, 153));
        ButonBuscarCancha1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButonBuscarCancha1.setForeground(new java.awt.Color(0, 0, 0));
        ButonBuscarCancha1.setText("Buscar");
        ButonBuscarCancha1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonBuscarCancha1.addActionListener(this::ButonBuscarCancha1ActionPerformed);

        lblCanchas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCanchas.setForeground(new java.awt.Color(255, 255, 255));
        lblCanchas.setText("Seleccione una Cancha ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(combocanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButonBuscarCancha1, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblFin, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtfin, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combocanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonBuscarCancha1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel7.setText("Seleccione a un Cliente ");

        comboclientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboclientes.addActionListener(this::comboclientesActionPerformed);

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("Fecha");

        txtFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        ButonBuscarCliente.setBackground(new java.awt.Color(153, 153, 153));
        ButonBuscarCliente.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButonBuscarCliente.setForeground(new java.awt.Color(0, 0, 0));
        ButonBuscarCliente.setText("Buscar");
        ButonBuscarCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonBuscarCliente.addActionListener(this::ButonBuscarClienteActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButonBuscarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(Txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(Txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(51, 102, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
        checkIluminacion.addActionListener(this::checkIluminacionActionPerformed);

        CheckArbitro.setBackground(new java.awt.Color(51, 204, 0));
        CheckArbitro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CheckArbitro.setForeground(new java.awt.Color(0, 0, 0));
        CheckArbitro.setText("Arbitro (Q 50.00)");
        CheckArbitro.addActionListener(this::CheckArbitroActionPerformed);

        checkAgua1.setBackground(new java.awt.Color(51, 204, 0));
        checkAgua1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        checkAgua1.setForeground(new java.awt.Color(0, 0, 0));
        checkAgua1.setText("Agua (Q 6.00)");
        checkAgua1.addActionListener(this::checkAgua1ActionPerformed);

        lbltotaladicional.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltotaladicional.setForeground(new java.awt.Color(255, 255, 255));
        lbltotaladicional.setText("Total de lo adicional: Q");

        TxtTotalAdicional.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Productos Extras");

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
                        .addGap(19, 19, 19)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBalon1)
                            .addComponent(checkIluminacion)
                            .addComponent(checkAgua1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckArbitro, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkchalecos))))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkchalecos)
                    .addComponent(checkBalon1))
                .addGap(12, 12, 12)
                .addComponent(checkIluminacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAgua1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckArbitro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbltotaladicional, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTotalAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel8.setBackground(new java.awt.Color(51, 102, 0));

        ComboGuardarCambios.setBackground(new java.awt.Color(204, 204, 204));
        ComboGuardarCambios.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ComboGuardarCambios.setForeground(new java.awt.Color(0, 0, 0));
        ComboGuardarCambios.setText("Guardar Cambios");
        ComboGuardarCambios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ComboGuardarCambios.addActionListener(this::ComboGuardarCambiosActionPerformed);

        ButonAgregar.setBackground(new java.awt.Color(204, 204, 204));
        ButonAgregar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonAgregar.setForeground(new java.awt.Color(0, 0, 0));
        ButonAgregar.setText("Agregar");
        ButonAgregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonAgregar.addActionListener(this::ButonAgregarActionPerformed);

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ButonLimpiar2.setBackground(new java.awt.Color(204, 204, 204));
        ButonLimpiar2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonLimpiar2.setForeground(new java.awt.Color(0, 0, 0));
        ButonLimpiar2.setText("Limpiar");
        ButonLimpiar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
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
                    .addComponent(ComboGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonLimpiar2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 260));

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowHeight(16);
        jTable1.setSelectionBackground(new java.awt.Color(30, 80, 50));
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 780, 282));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancha_pro_final/iconosyimagenes/sesper.png"))); // NOI18N
        jLabel13.setText("jLabel1");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 880, 570));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancha_pro_final/iconosyimagenes/sesper.png"))); // NOI18N
        jLabel14.setText("jLabel1");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 570));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancha_pro_final/iconosyimagenes/sesper.png"))); // NOI18N
        jLabel15.setText("jLabel1");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 880, 570));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancha_pro_final/iconosyimagenes/sesper.png"))); // NOI18N
        jLabel16.setText("jLabel1");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 880, 570));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancha_pro_final/iconosyimagenes/sesper.png"))); // NOI18N
        jLabel17.setText("jLabel1");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 880, 570));

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
       if (cambios) {
            int opcion = JOptionPane.showConfirmDialog( this, "¿Desea salir sin guardar los cambios en Reservas.txt?", "Advertencia", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if (opcion != JOptionPane.YES_OPTION) {
                return; 
            }
        }
       this.dispose();
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
       String dato = JOptionPane.showInputDialog(this, "Ingrese código, nombre o teléfono del cliente:");
        if (dato != null && !dato.trim().isEmpty()) {
        int pos = Clientes.buscarPosicionCliente(arregloClientes, contadorClientes, dato);
        if (pos != -1) {
            comboclientes.setSelectedIndex(pos);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
        }
    }
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

    private void CheckArbitroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckArbitroActionPerformed
        calcularExtrasYFin();
    }//GEN-LAST:event_CheckArbitroActionPerformed

    private void checkAgua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAgua1ActionPerformed
       calcularExtrasYFin();
    }//GEN-LAST:event_checkAgua1ActionPerformed

    private void ButonBuscarCancha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonBuscarCancha1ActionPerformed
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
            JOptionPane.showMessageDialog(this, "Fecha inválida use el formato AAAA-MM-DD (ej. 2026-11-12).");
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
        
        double anticipoMinimo = total * 0.50;
        String totalTexto = String.format(Locale.US, "%.2f", total);
        String minimoTexto = String.format(Locale.US, "%.2f", anticipoMinimo);
        String mensaje = "Total de la reserva: Q" + totalTexto + "\n" + "Anticipo mínimo a dar: Q" + minimoTexto + "\n" + "Ingrese el anticipo o pago inicial (Q):";

        String datoAnticipo = JOptionPane.showInputDialog(this, mensaje, minimoTexto);

        if (datoAnticipo == null) {
            return; 
        }
       double anticipo = 0;
        try {
            anticipo = Double.parseDouble(datoAnticipo.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido en números.");
            return;
        }
        if (anticipo < anticipoMinimo) {
            JOptionPane.showMessageDialog(this, "El anticipo debe ser mayor o igual a Q" + minimoTexto);
            return;
        }
        if (anticipo > total) {
            JOptionPane.showMessageDialog(this, "El anticipo no puede ser mayor al total de Q" + totalTexto);
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
        CheckArbitro.setSelected(r.getServicios().contains("Arbitro"));
        checkAgua1.setSelected(r.getServicios().contains("Agua"));
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

    }//GEN-LAST:event_comboclientesActionPerformed

    private void combocanchasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocanchasActionPerformed
    cargarHorariosDeCancha();
    calcularExtrasYFin();
    }//GEN-LAST:event_combocanchasActionPerformed

    private void ButonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonGuardarActionPerformed
      try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter("Reservas.txt"))) {
            for (int i = 0; i < contadorReservas; i++) {
           Reserva r = arregloReservas[i];
            if (r != null) {
          pw.println(r.getCodigoR() + "," +r.getCodigoCliente() + "," +r.getCodigoCancha() + "," +r.getFecha() + "," +r.getHoraInicio() + "," +r.getHoraFin() + "," +r.getTotal() + "," +r.getSaldo() + "," +r.getEstado()); 
             }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al Guardar al TXT " + e.getMessage());
        }
        if (contadorReservas == 0) {
            JOptionPane.showMessageDialog(this, "No hay reservas en la tabla. Primero presione Agregar.");
            return;
        }
        Reserva.GuardarArchivoReservas(arregloReservas, contadorReservas);
        cambios = false; 
        JOptionPane.showMessageDialog(this, "Reservas guardadas correctamente en Reservas.txt.");   
    }//GEN-LAST:event_ButonGuardarActionPerformed

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
    private javax.swing.JCheckBox CheckArbitro;
    private javax.swing.JComboBox<String> ComboBuscarReserva;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JButton ComboGuardarCambios;
    private javax.swing.JComboBox<String> ComboHoras;
    private javax.swing.JComboBox<String> ComboInicio;
    private javax.swing.JTextField TxtTotalAdicional;
    private javax.swing.JTextField Txtcodigo;
    private javax.swing.JCheckBox checkAgua1;
    private javax.swing.JCheckBox checkBalon1;
    private javax.swing.JCheckBox checkIluminacion;
    private javax.swing.JCheckBox checkchalecos;
    private javax.swing.JComboBox<String> combocanchas;
    private javax.swing.JComboBox<String> comboclientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel lblCanchas;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFin;
    private javax.swing.JLabel lblHoras;
    private javax.swing.JLabel lblbuscar;
    private javax.swing.JLabel lblinicio;
    private javax.swing.JLabel lblinicio1;
    private javax.swing.JLabel lblinicio2;
    private javax.swing.JLabel lbltotaladicional;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtfin;
    // End of variables declaration//GEN-END:variables
}
