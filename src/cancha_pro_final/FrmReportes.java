/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cancha_pro_final;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

    
public class FrmReportes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmReportes.class.getName());


    
    Clientes[] listaCli = new Clientes[200];
    int totalCli = 0;
    Canchas[] listaCan = new Canchas[100];
    int totalCan = 0;
    Reserva[] listaRes = new Reserva[300];
    int totalRes = 0;
    Pagos[] listaPag = new Pagos[300];
    int totalPag = 0;
    Reportes[] canasta = new Reportes[300];
    int cuantos = 0;

    DefaultTableModel modelo;
    boolean guardado = true;
    
    
    public FrmReportes() {
        initComponents();
        this.getContentPane().setBackground(new java.awt.Color(25, 65, 30));
        this.setLocationRelativeTo(null);
        modelo = (DefaultTableModel) JTable.getModel();
        modelo.setRowCount(0);

        cargarTodo();
        }
    private void cargarTodo() {
        totalCli = Clientes.CargarArchivosClientes(listaCli);
        totalCan = Canchas.CargarCanchas(listaCan, 100);
        totalRes = Reserva.CargarArchivosReservas(listaRes);
        totalPag = Pagos.CargarArchivosPagos(listaPag);

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        txtfechayhora.setText(ahora.format(fmt));

        ComboReserva.removeAllItems();
        ComboReserva.addItem("Todas");
        for (int i = 0; i < totalRes; i++) {
            Reserva r = listaRes[i];
            if (r != null) {
                String nom = Reportes.buscarNombreCliente(listaCli, totalCli, r.getCodigoCliente());
                ComboReserva.addItem(r.getCodigoR() + " - " + nom);
            }
        }

        ComboCanchas.removeAllItems();
        ComboCanchas.addItem("Todas");
        for (int i = 0; i < totalCan; i++) {
            if (listaCan[i] != null) {
                ComboCanchas.addItem(listaCan[i].getCodigoC());
            }
        }
        llenarResumen();
    }
    private void llenarResumen() {
        txtcanchamasusuada.setText(Reportes.obtenerCanchaTop(listaRes, totalRes, listaCan, totalCan));
        txthorariopico.setText(Reportes.obtenerHorarioTop(listaRes, totalRes));
        txtclientetop.setText(Reportes.obtenerClienteTop(listaRes, totalRes, listaCli, totalCli));
        txttotalderegistros1.setText(String.valueOf(cuantos));

        double suma = Reportes.sumarDineroCanasta(canasta, cuantos);
        txttotaldedinero.setText("Q" + String.format(Locale.US, "%.2f", suma));
    }
    private void mostrarTabla() {
        modelo.setRowCount(0);
        for (int i = 0; i < cuantos; i++) {
          Reportes r = canasta[i];
          if (r != null) {
          modelo.addRow(new Object[]{r.getCodigoCriterio(),r.getDetalles(),r.getFechaHora(),r.getCantidad(),"Q" + String.format(Locale.US, "%.2f", r.getSaldoMonto())}); 
         }
        }}
    private void procesarReporte() {
        if (ComboCanchas.getSelectedItem() == null || ComboEstado.getSelectedItem() == null) {
            return;
        }
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        txtfechayhora.setText(ahora.format(fmt));

        int tipo = ComboTipoReporte.getSelectedIndex();
        String inicio = txtfechadeinicio1.getText().trim();
        String fin = txtfechadefin.getText().trim();
        String cancha = ComboCanchas.getSelectedItem().toString();
        String estado = ComboEstado.getSelectedItem().toString();
        cuantos = 0;
       if (tipo == 0) {
            cuantos = Reportes.reporteReservasPorFecha(canasta, listaRes, totalRes, listaCli, totalCli, listaCan, totalCan, inicio, fin, cancha, estado);
        } else if (tipo == 1) {
            cuantos = Reportes.reporteReservasPorCancha(canasta, listaRes, totalRes, listaCli, totalCli, listaCan, totalCan, inicio, fin, cancha);
        } else if (tipo == 2) {
            cuantos = Reportes.reporteIngresosPorPeriodo(canasta, listaPag, totalPag, inicio, fin);
        } else if (tipo == 3) {
            cuantos = Reportes.reportePagosPendientes(canasta, listaRes, totalRes, listaCli, totalCli, listaCan, totalCan, cancha);
        } else if (tipo == 4) {
            cuantos = Reportes.reporteReservasCanceladas(canasta, listaRes, totalRes, listaCli, totalCli, inicio, fin);
        } else if (tipo == 5) {
            cuantos = Reportes.reporteCanchaMasUtilizada(canasta, listaRes, totalRes, listaCan, totalCan);
        } else if (tipo == 6) {
            cuantos = Reportes.reporteHorariosMayorDemanda(canasta, listaRes, totalRes);
        } else if (tipo == 7) {
            cuantos = Reportes.reporteClientesFrecuentes(canasta, listaRes, totalRes, listaCli, totalCli);
        }
        String resSel = ComboReserva.getSelectedItem().toString();
        if (!resSel.equals("Todas") && (tipo == 0 || tipo == 1 || tipo == 3 || tipo == 4)) {
            String codRes = resSel.split(" - ")[0].trim();
            Reportes[] temp = new Reportes[300];
            int k = 0;
            for (int i = 0; i < cuantos; i++) {
              if (canasta[i] != null && canasta[i].getCodigoCriterio().equalsIgnoreCase(codRes)) {
             temp[k] = canasta[i];
             k++;
             } }
            canasta = temp;
            cuantos = k;
        }
        mostrarTabla();
        llenarResumen();
        guardado = false;
        if (cuantos == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros con esos filtros.");
        }
    }
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        JTable = new javax.swing.JTable();
        JPaneltitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        ButonRegresar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        blbtipodereporte = new javax.swing.JLabel();
        ComboTipoReporte = new javax.swing.JComboBox<>();
        lblseccionreserva1 = new javax.swing.JLabel();
        ComboReserva = new javax.swing.JComboBox<>();
        txtfechayhora = new javax.swing.JTextField();
        lblfechayhora1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblCancha = new javax.swing.JLabel();
        lblfechadeinicio1 = new javax.swing.JLabel();
        lblfechafin1 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtfechadefin = new javax.swing.JTextField();
        txtfechadeinicio1 = new javax.swing.JTextField();
        ComboCanchas = new javax.swing.JComboBox<>();
        ComboEstado = new javax.swing.JComboBox<>();
        ButonReportes = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblsubtitulo = new javax.swing.JLabel();
        lbltotaldedinero = new javax.swing.JLabel();
        lblcanchamasusada1 = new javax.swing.JLabel();
        lblhorariopico1 = new javax.swing.JLabel();
        lblclientetop1 = new javax.swing.JLabel();
        lbltotalregistro1 = new javax.swing.JLabel();
        txtcanchamasusuada = new javax.swing.JTextField();
        txthorariopico = new javax.swing.JTextField();
        txtclientetop = new javax.swing.JTextField();
        txttotaldedinero = new javax.swing.JTextField();
        txttotalderegistros1 = new javax.swing.JTextField();
        ButonGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 153, 0));

        JTable.setBackground(new java.awt.Color(255, 255, 255));
        JTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        JTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        JTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo o Criterio", "Detalles", "Fecha y Hora", "Cantidad", "Saldo"
            }
        ));
        JTable.setGridColor(new java.awt.Color(204, 204, 204));
        JTable.setRowHeight(16);
        JTable.setSelectionBackground(new java.awt.Color(30, 80, 50));
        JTable.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(JTable);

        JPaneltitulo.setBackground(new java.awt.Color(51, 102, 0));
        JPaneltitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("REPORTES");

        ButonRegresar.setBackground(new java.awt.Color(255, 255, 255));
        ButonRegresar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ButonRegresar.setForeground(new java.awt.Color(0, 0, 0));
        ButonRegresar.setText("Regresar");
        ButonRegresar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonRegresar.addActionListener(this::ButonRegresarActionPerformed);

        javax.swing.GroupLayout JPaneltituloLayout = new javax.swing.GroupLayout(JPaneltitulo);
        JPaneltitulo.setLayout(JPaneltituloLayout);
        JPaneltituloLayout.setHorizontalGroup(
            JPaneltituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPaneltituloLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(ButonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(399, 399, 399)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        JPaneltituloLayout.setVerticalGroup(
            JPaneltituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPaneltituloLayout.createSequentialGroup()
                .addGroup(JPaneltituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(ButonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel4.setBackground(new java.awt.Color(51, 102, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        blbtipodereporte.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        blbtipodereporte.setForeground(new java.awt.Color(255, 255, 255));
        blbtipodereporte.setText("Tipo de reoporte");

        ComboTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1. Reservas por fecha", "2. Reservas por cancha", "3. Ingresos por periodo", "4. Pagos pendientes", "5. Reservas canceladas", "6. Cancha mas utilizada", "7. Horarios de mayor demanda", "8. Clientes frecuentes" }));

        lblseccionreserva1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblseccionreserva1.setForeground(new java.awt.Color(255, 255, 255));
        lblseccionreserva1.setText("Seleccione reserva");

        ComboReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtfechayhora.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtfechayhora.setForeground(new java.awt.Color(0, 0, 0));

        lblfechayhora1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblfechayhora1.setForeground(new java.awt.Color(255, 255, 255));
        lblfechayhora1.setText("Fecha y Hora");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(blbtipodereporte, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblseccionreserva1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfechayhora, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(lblfechayhora1)
                    .addContainerGap(363, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(blbtipodereporte, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(ComboTipoReporte)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblseccionreserva1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(ComboReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfechayhora, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(108, Short.MAX_VALUE)
                    .addComponent(lblfechayhora1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(40, 40, 40)))
        );

        jPanel5.setBackground(new java.awt.Color(51, 102, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblCancha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCancha.setForeground(new java.awt.Color(255, 255, 255));
        lblCancha.setText("Cancha");

        lblfechadeinicio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblfechadeinicio1.setForeground(new java.awt.Color(255, 255, 255));
        lblfechadeinicio1.setText("Fecha de inicio");

        lblfechafin1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblfechafin1.setForeground(new java.awt.Color(255, 255, 255));
        lblfechafin1.setText("Fecha de fin");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado.setText("Estado");

        txtfechadefin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtfechadefin.setForeground(new java.awt.Color(0, 0, 0));

        txtfechadeinicio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtfechadeinicio1.setForeground(new java.awt.Color(0, 0, 0));

        ComboCanchas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "PENDIENTE", "CONFIRMADA", "EN CURSO", "FINALIZADA", "CANCELADA" }));

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "PENDIENTE", "CONFIRMADA", "EN_CURSO", "FINALIZADA", "CANCELADA" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblCancha, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboCanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblfechafin1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfechadeinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtfechadefin, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(lblfechadeinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(339, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(txtfechadeinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblfechafin1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfechadefin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCancha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboCanchas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(lblfechadeinicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(134, Short.MAX_VALUE)))
        );

        ButonReportes.setBackground(new java.awt.Color(255, 255, 255));
        ButonReportes.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ButonReportes.setForeground(new java.awt.Color(0, 0, 0));
        ButonReportes.setText("Genera Reporte");
        ButonReportes.setActionCommand("Guardar");
        ButonReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonReportes.addActionListener(this::ButonReportesActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ButonReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ButonReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(51, 153, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel6.setBackground(new java.awt.Color(51, 102, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblsubtitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblsubtitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblsubtitulo.setText("Resumen e Indicaciones ");

        lbltotaldedinero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltotaldedinero.setForeground(new java.awt.Color(255, 255, 255));
        lbltotaldedinero.setText("Total de Dinero");

        lblcanchamasusada1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblcanchamasusada1.setForeground(new java.awt.Color(255, 255, 255));
        lblcanchamasusada1.setText("Cancha mas usada");

        lblhorariopico1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblhorariopico1.setForeground(new java.awt.Color(255, 255, 255));
        lblhorariopico1.setText("Horario Pico");

        lblclientetop1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblclientetop1.setForeground(new java.awt.Color(255, 255, 255));
        lblclientetop1.setText("Cliente Top");

        lbltotalregistro1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltotalregistro1.setForeground(new java.awt.Color(255, 255, 255));
        lbltotalregistro1.setText("Total de Registros");

        txtcanchamasusuada.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtcanchamasusuada.setForeground(new java.awt.Color(0, 0, 0));

        txthorariopico.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txthorariopico.setForeground(new java.awt.Color(0, 0, 0));

        txtclientetop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtclientetop.setForeground(new java.awt.Color(0, 0, 0));

        txttotaldedinero.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txttotaldedinero.setForeground(new java.awt.Color(0, 0, 0));

        txttotalderegistros1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txttotalderegistros1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtcanchamasusuada, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(lbltotalregistro1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttotalderegistros1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblhorariopico1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblcanchamasusada1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(120, 120, 120)
                        .addComponent(lblsubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblclientetop1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtclientetop, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txthorariopico, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(lbltotaldedinero, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttotaldedinero, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblsubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblcanchamasusada1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbltotalregistro1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttotalderegistros1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtcanchamasusuada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblhorariopico1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltotaldedinero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthorariopico, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttotaldedinero, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblclientetop1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtclientetop, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        ButonGuardar.setBackground(new java.awt.Color(255, 255, 255));
        ButonGuardar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ButonGuardar.setForeground(new java.awt.Color(0, 0, 0));
        ButonGuardar.setText("Guardar");
        ButonGuardar.setActionCommand("Guardar");
        ButonGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonGuardar.addActionListener(this::ButonGuardarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(JPaneltitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(JPaneltitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ButonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonRegresarActionPerformed
        if (!guardado && cuantos > 0) {
            int opion = JOptionPane.showConfirmDialog(this, "Tienes un reporte sin guadr al txt. ¿Salir sin guardar?", "Aviso", JOptionPane.YES_NO_OPTION);
            if (opion != JOptionPane.YES_OPTION) {
            return;
            }}
        FrmInicio menu = new FrmInicio(); 
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ButonRegresarActionPerformed

    private void ButonReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonReportesActionPerformed
        procesarReporte();
    }//GEN-LAST:event_ButonReportesActionPerformed

    private void ButonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonGuardarActionPerformed
       if (cuantos == 0) {
            JOptionPane.showMessageDialog(this, "Primero genera un reporte antes de guardar.");
            return;
        }
        String nombreRep = ComboTipoReporte.getSelectedItem().toString();
        String fecha = txtfechayhora.getText();
        Reportes.GuardarArchivoReportes(canasta, cuantos, nombreRep, fecha);
        guardado = true;
        JOptionPane.showMessageDialog(this, "Reporte guardado en Reportes.txt");
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
        java.awt.EventQueue.invokeLater(() -> new FrmReportes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButonGuardar;
    private javax.swing.JButton ButonRegresar;
    private javax.swing.JButton ButonReportes;
    private javax.swing.JComboBox<String> ComboCanchas;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboReserva;
    private javax.swing.JComboBox<String> ComboTipoReporte;
    private javax.swing.JPanel JPaneltitulo;
    private javax.swing.JTable JTable;
    private javax.swing.JLabel blbtipodereporte;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCancha;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblcanchamasusada1;
    private javax.swing.JLabel lblclientetop1;
    private javax.swing.JLabel lblfechadeinicio1;
    private javax.swing.JLabel lblfechafin1;
    private javax.swing.JLabel lblfechayhora1;
    private javax.swing.JLabel lblhorariopico1;
    private javax.swing.JLabel lblseccionreserva1;
    private javax.swing.JLabel lblsubtitulo;
    private javax.swing.JLabel lbltotaldedinero;
    private javax.swing.JLabel lbltotalregistro1;
    private javax.swing.JTextField txtcanchamasusuada;
    private javax.swing.JTextField txtclientetop;
    private javax.swing.JTextField txtfechadefin;
    private javax.swing.JTextField txtfechadeinicio1;
    private javax.swing.JTextField txtfechayhora;
    private javax.swing.JTextField txthorariopico;
    private javax.swing.JTextField txttotaldedinero;
    private javax.swing.JTextField txttotalderegistros1;
    // End of variables declaration//GEN-END:variables
}
