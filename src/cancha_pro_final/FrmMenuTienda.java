/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cancha_pro_final;

/**
 *
 * @author PERSONAL
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class FrmMenuTienda extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMenuTienda.class.getName());
    Tienda_Cafeteria[] arregloProductos = new Tienda_Cafeteria[100];
    int contadorProductos = 0;
    Tienda_Cafeteria[] arregloVentas = new Tienda_Cafeteria[200];
    int contadorVentas = 0;
    String[] carritoCodigo = new String[50];
    String[] carritoNombre = new String[50];
    int[] carritoCantidad = new int[50];
    double[] carritoPrecio = new double[50];
    double[] carritoSubtotal = new double[50];
    int contadorCarrito = 0;
    DefaultTableModel modeloVentas;
    DefaultTableModel modeloProductos;
    /**
     * Creates new form FrmMenuTienda
     */
    public FrmMenuTienda() {
       initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(25, 65, 30));
        DefaultTableCellRenderer celdas = new DefaultTableCellRenderer();
        celdas.setForeground(java.awt.Color.BLACK);
        celdas.setBackground(java.awt.Color.WHITE);
        celdas.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableProductos.setDefaultRenderer(Object.class, celdas);
        TableProductos.setDefaultRenderer(Integer.class, celdas);
        TableProductos.setDefaultRenderer(Double.class, celdas);

        TableVentas.setDefaultRenderer(Object.class, celdas);
        TableVentas.setDefaultRenderer(Integer.class, celdas);
        TableVentas.setDefaultRenderer(Double.class, celdas);;
        TableVentas.getTableHeader().setDefaultRenderer(celdas);
        TableProductos.getTableHeader().setDefaultRenderer(celdas);
        Txtcodigo.setEditable(false);
        TxtTotalVenta.setEditable(false);
        txtcodigoproducto.setEditable(false);
        modeloVentas = (DefaultTableModel) TableVentas.getModel();
        modeloProductos = (DefaultTableModel) TableProductos.getModel();
        contadorProductos = Tienda_Cafeteria.CargarProductos(arregloProductos);
        contadorVentas = Tienda_Cafeteria.CargarVentas(arregloVentas);

        actualizarTablaProductos();
        cargarComboProductos();
        limpiarVenta();
        limpiarProducto();
    }
    public void actualizarTablaProductos() {
        modeloProductos.setRowCount(0);
        for (int i = 0; i < contadorProductos; i++) {
            Tienda_Cafeteria p = arregloProductos[i];
            if (p != null) {
                modeloProductos.addRow(new Object[]{p.getCodigo(),p.getNombre(),"Q" + String.format("%.2f", p.getPrecio()),p.getExistencia(),p.getStock(), p.getEstado()});    
            }}   
             }

    public void cargarComboProductos() {
        ComboProductos.removeAllItems();
        for (int i = 0; i < contadorProductos; i++) {
            Tienda_Cafeteria p = arregloProductos[i];
            if (p != null && p.getEstado().equalsIgnoreCase("DISPONIBLE") && p.getExistencia() > 0) {
                ComboProductos.addItem(p.getCodigo() + " - " + p.getNombre() + " (Q" + p.getPrecio() + ")");
            } }
          }

    public void actualizarTablaCarrito() {
        modeloVentas.setRowCount(0);
        double totalGeneral = 0;
        for (int i = 0; i < contadorCarrito; i++) {
            totalGeneral = totalGeneral + carritoSubtotal[i];
            modeloVentas.addRow(new Object[]{carritoCodigo[i],carritoNombre[i],carritoCantidad[i],"Q" + String.format("%.2f", carritoPrecio[i]),"Q" + String.format("%.2f", carritoSubtotal[i])});  
        }
        TxtTotalVenta.setText("Q" + String.format("%.2f", totalGeneral));
         }
    public void limpiarVenta() {
        Txtcodigo.setText(Tienda_Cafeteria.generaCodVenta(arregloVentas, contadorVentas));
        txtcantidaddeproductos.setText("");
        TxtTotalVenta.setText("Q0.00");
        contadorCarrito = 0;
        modeloVentas.setRowCount(0);
        if (ComboMetodoPago.getItemCount() > 0) {
            ComboMetodoPago.setSelectedIndex(0);
        }
        }

    public void limpiarProducto() {
        txtcodigoproducto.setText(Tienda_Cafeteria.generaCodProducto(arregloProductos, contadorProductos));
        txtnombreproducto.setText("");
        txtpreciproducto.setText("");
        txtexistencia.setText("");
        txtstock.setText("");
        if (Comboestadoproducto.getItemCount() > 0) {
            Comboestadoproducto.setSelectedIndex(0);
        }
      }

    public Tienda_Cafeteria buscarProducto(String codigoBuscado) {
        for (int i = 0; i < contadorProductos; i++) {
            if (arregloProductos[i] != null && arregloProductos[i].getCodigo().equalsIgnoreCase(codigoBuscado)) {
                return arregloProductos[i];
            }
        }
        return null;
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
        jPanel3 = new javax.swing.JPanel();
        lblcodigo1 = new javax.swing.JLabel();
        Txtcodigo = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtcantidaddeproductos = new javax.swing.JTextField();
        lblProducto = new javax.swing.JLabel();
        ComboProductos = new javax.swing.JComboBox<>();
        Agregarproductoaventa = new javax.swing.JButton();
        lbltituventa = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblmetodopago = new javax.swing.JLabel();
        lbltotalventa = new javax.swing.JLabel();
        ButonLimpiar = new javax.swing.JButton();
        ButonConfirmarventa = new javax.swing.JButton();
        TxtTotalVenta = new javax.swing.JTextField();
        ComboMetodoPago = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        ButonRegresar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lbltituventa1 = new javax.swing.JLabel();
        lblcodigoproducto = new javax.swing.JLabel();
        txtnombreproducto = new javax.swing.JTextField();
        lblstok = new javax.swing.JLabel();
        lblNommbreproducto = new javax.swing.JLabel();
        txtpreciproducto = new javax.swing.JTextField();
        lblestadoproducto = new javax.swing.JLabel();
        lblexistencia = new javax.swing.JLabel();
        txtexistencia = new javax.swing.JTextField();
        lblpreciorpoducto = new javax.swing.JLabel();
        Comboestadoproducto = new javax.swing.JComboBox<>();
        ButonGuardarProducto = new javax.swing.JButton();
        ButonNuevoProductos = new javax.swing.JButton();
        txtstock = new javax.swing.JTextField();
        txtcodigoproducto = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 153, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel3.setBackground(new java.awt.Color(51, 102, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblcodigo1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblcodigo1.setForeground(new java.awt.Color(255, 255, 255));
        lblcodigo1.setText("Cantidad");

        Txtcodigo.setEditable(false);
        Txtcodigo.setBackground(new java.awt.Color(204, 0, 51));
        Txtcodigo.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Txtcodigo.setForeground(new java.awt.Color(255, 255, 255));
        Txtcodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        Txtcodigo.addActionListener(this::TxtcodigoActionPerformed);

        lblCodigo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setText("CODIGO");

        lblProducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblProducto.setText("Producto");

        ComboProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Agregarproductoaventa.setBackground(new java.awt.Color(153, 153, 153));
        Agregarproductoaventa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Agregarproductoaventa.setForeground(new java.awt.Color(0, 0, 0));
        Agregarproductoaventa.setText("Agregar");
        Agregarproductoaventa.addActionListener(this::AgregarproductoaventaActionPerformed);

        lbltituventa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbltituventa.setForeground(new java.awt.Color(255, 255, 255));
        lbltituventa.setText("NUEVA VENTA");

        TableVentas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Producto", "Cantidad", "Precio C/U", "Sutbotal"
            }
        ));
        jScrollPane3.setViewportView(TableVentas);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblcodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtcantidaddeproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbltituventa, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Agregarproductoaventa, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Txtcodigo)
                    .addComponent(lbltituventa, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcantidaddeproductos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblcodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Agregarproductoaventa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 84, -1, -1));

        jPanel2.setBackground(new java.awt.Color(51, 153, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblmetodopago.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblmetodopago.setForeground(new java.awt.Color(255, 255, 255));
        lblmetodopago.setText("Metodo de Pago");

        lbltotalventa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbltotalventa.setForeground(new java.awt.Color(255, 255, 255));
        lbltotalventa.setText("Total");

        ButonLimpiar.setBackground(new java.awt.Color(153, 153, 153));
        ButonLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonLimpiar.setForeground(new java.awt.Color(0, 0, 0));
        ButonLimpiar.setText("Limpiar");
        ButonLimpiar.setActionCommand("");

        ButonConfirmarventa.setBackground(new java.awt.Color(153, 153, 153));
        ButonConfirmarventa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonConfirmarventa.setForeground(new java.awt.Color(0, 0, 0));
        ButonConfirmarventa.setText("Confimar Venta");
        ButonConfirmarventa.addActionListener(this::ButonConfirmarventaActionPerformed);

        ComboMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Transferencia" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(ButonConfirmarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblmetodopago, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbltotalventa, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ButonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblmetodopago, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbltotalventa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboMetodoPago))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButonConfirmarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 494, 537, -1));

        jPanel5.setBackground(new java.awt.Color(51, 102, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("TIENDA / CAFETERIA ");

        ButonRegresar.setBackground(new java.awt.Color(255, 255, 255));
        ButonRegresar.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ButonRegresar.setForeground(new java.awt.Color(0, 0, 0));
        ButonRegresar.setText("Regresar");
        ButonRegresar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ButonRegresar.addActionListener(this::ButonRegresarActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ButonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(287, 287, 287)
                .addComponent(lblTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1265, -1));

        jPanel6.setBackground(new java.awt.Color(51, 153, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel4.setBackground(new java.awt.Color(51, 102, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        lbltituventa1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lbltituventa1.setForeground(new java.awt.Color(255, 255, 255));
        lbltituventa1.setText("PRODUCTOS");

        lblcodigoproducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblcodigoproducto.setForeground(new java.awt.Color(255, 255, 255));
        lblcodigoproducto.setText("CODIGO");

        lblstok.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblstok.setForeground(new java.awt.Color(255, 255, 255));
        lblstok.setText("Stock");

        lblNommbreproducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNommbreproducto.setForeground(new java.awt.Color(255, 255, 255));
        lblNommbreproducto.setText("Nombre");

        lblestadoproducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblestadoproducto.setForeground(new java.awt.Color(255, 255, 255));
        lblestadoproducto.setText("Estado");

        lblexistencia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblexistencia.setForeground(new java.awt.Color(255, 255, 255));
        lblexistencia.setText("Excistencia");

        lblpreciorpoducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblpreciorpoducto.setForeground(new java.awt.Color(255, 255, 255));
        lblpreciorpoducto.setText("Precio");

        Comboestadoproducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DISPONIBLE", "INACTIVO" }));

        ButonGuardarProducto.setBackground(new java.awt.Color(153, 153, 153));
        ButonGuardarProducto.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonGuardarProducto.setForeground(new java.awt.Color(0, 0, 0));
        ButonGuardarProducto.setText("Guardar Productro");
        ButonGuardarProducto.setActionCommand("");
        ButonGuardarProducto.addActionListener(this::ButonGuardarProductoActionPerformed);

        ButonNuevoProductos.setBackground(new java.awt.Color(153, 153, 153));
        ButonNuevoProductos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ButonNuevoProductos.setForeground(new java.awt.Color(0, 0, 0));
        ButonNuevoProductos.setText("Nuevo");
        ButonNuevoProductos.setActionCommand("");

        txtcodigoproducto.setEditable(false);
        txtcodigoproducto.setBackground(new java.awt.Color(204, 0, 51));
        txtcodigoproducto.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        txtcodigoproducto.setForeground(new java.awt.Color(255, 255, 255));
        txtcodigoproducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        txtcodigoproducto.addActionListener(this::txtcodigoproductoActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblestadoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Comboestadoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(lbltituventa1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblcodigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcodigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131)
                                .addComponent(txtnombreproducto))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblpreciorpoducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpreciproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblexistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtexistencia, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblstok, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtstock, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                        .addGap(53, 53, 53))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(ButonNuevoProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145)
                .addComponent(ButonGuardarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(227, 227, 227)
                    .addComponent(lblNommbreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(276, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltituventa1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblcodigoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcodigoproducto))
                    .addComponent(txtnombreproducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblpreciorpoducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpreciproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblexistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtexistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstok, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtstock, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblestadoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Comboestadoproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButonNuevoProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButonGuardarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(lblNommbreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(176, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 350, -1, -1));

        TableProductos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Precio", "Excistente", "Stock", "Estado"
            }
        ));
        jScrollPane4.setViewportView(TableProductos);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 645, 246));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonRegresarActionPerformed
         FrmInicio menu = new FrmInicio();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ButonRegresarActionPerformed

    private void TxtcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtcodigoActionPerformed
       
    }//GEN-LAST:event_TxtcodigoActionPerformed

    private void ButonConfirmarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonConfirmarventaActionPerformed
       if (contadorCarrito == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto antes de confirmar la venta.");
            return;
        }
        String codV = Txtcodigo.getText().trim();
        String fechaHoy = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String metodo = ComboMetodoPago.getSelectedItem().toString();
        double totalPagar = 0;
        String detalleProductos = "";
        for (int i = 0; i < contadorCarrito; i++) {
            totalPagar = totalPagar + carritoSubtotal[i];
            if (i == 0) {
             detalleProductos = carritoNombre[i] + " x" + carritoCantidad[i];
            } else {
            detalleProductos = detalleProductos + "; " + carritoNombre[i] + " x" + carritoCantidad[i];
            }
            Tienda_Cafeteria prod = buscarProducto(carritoCodigo[i]);
            if (prod != null) {
            int nuevaExistencia = prod.getExistencia() - carritoCantidad[i];
             prod.setExistencia(nuevaExistencia);
            }
        }
        arregloVentas[contadorVentas] = new Tienda_Cafeteria(codV, fechaHoy, detalleProductos, totalPagar, metodo);
        contadorVentas++;
        Tienda_Cafeteria.GuardarProductos(arregloProductos, contadorProductos);
        Tienda_Cafeteria.GuardarVentas(arregloVentas, contadorVentas);
        JOptionPane.showMessageDialog(this, "Venta " + codV + " guardada por Q" + String.format("%.2f", totalPagar));
        actualizarTablaProductos();
        cargarComboProductos();
        limpiarVenta();
    }//GEN-LAST:event_ButonConfirmarventaActionPerformed

    private void txtcodigoproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodigoproductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodigoproductoActionPerformed

    private void AgregarproductoaventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarproductoaventaActionPerformed
        if (ComboProductos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles para vender.");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtcantidaddeproductos.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
                return;  
                } } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido en Cantidad.");
            return;
        }
        String textoCombo = ComboProductos.getSelectedItem().toString();
        String codProd = textoCombo.split(" - ")[0].trim();

        Tienda_Cafeteria prod = buscarProducto(codProd);
        if (prod == null) {
            return;
        }
        int posicionEnCarrito = -1;
        int cantidadYaPedida = 0;
        for (int i = 0; i < contadorCarrito; i++) {
            if (carritoCodigo[i].equalsIgnoreCase(codProd)) {
                posicionEnCarrito = i;
                cantidadYaPedida = carritoCantidad[i];
                break;
            }
        }
        if ((cantidadYaPedida + cantidad) > prod.getExistencia()) {
        JOptionPane.showMessageDialog(this, "Inventario insuficiente. Disponible: " + prod.getExistencia());
          return;
        }
        if (posicionEnCarrito != -1) {
        carritoCantidad[posicionEnCarrito] = cantidadYaPedida + cantidad;
        carritoSubtotal[posicionEnCarrito] = carritoCantidad[posicionEnCarrito] * prod.getPrecio();
        } else {
            carritoCodigo[contadorCarrito] = prod.getCodigo();
            carritoNombre[contadorCarrito] = prod.getNombre();
            carritoCantidad[contadorCarrito] = cantidad;
            carritoPrecio[contadorCarrito] = prod.getPrecio();
            carritoSubtotal[contadorCarrito] = cantidad * prod.getPrecio();
            contadorCarrito++;
        }
        txtcantidaddeproductos.setText("");
        actualizarTablaCarrito();
    }//GEN-LAST:event_AgregarproductoaventaActionPerformed

    private void ButonGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonGuardarProductoActionPerformed
       try {
            String cod = txtcodigoproducto.getText().trim();
            String nom = txtnombreproducto.getText().trim();
            double pre = Double.parseDouble(txtpreciproducto.getText().trim());
            int exi = Integer.parseInt(txtexistencia.getText().trim());
            int stk = Integer.parseInt(txtstock.getText().trim());
            String est = Comboestadoproducto.getSelectedItem().toString();
            if (nom.isEmpty() || pre <= 0 || exi < 0 || stk < 0) {
            JOptionPane.showMessageDialog(this, "Vefique si los datos ingresado son los correctso ");
            return;
            }
            Tienda_Cafeteria existente = buscarProducto(cod);
            if (existente != null) {
                existente.setNombre(nom);
                existente.setPrecio(pre);
                existente.setExistencia(exi);
                existente.setStock(stk);
                existente.setEstado(est);
            } else {
                arregloProductos[contadorProductos] = new Tienda_Cafeteria(cod, nom, pre, exi, stk, est);
                contadorProductos++;
            }
            Tienda_Cafeteria.GuardarProductos(arregloProductos, contadorProductos);
            JOptionPane.showMessageDialog(this, "Producto guardado en Productos.txt");
            actualizarTablaProductos();
            cargarComboProductos();
            limpiarProducto();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese números válidos en Precio, Existencia y Stock.");
        }
    }//GEN-LAST:event_ButonGuardarProductoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FrmMenuTienda().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregarproductoaventa;
    private javax.swing.JButton ButonConfirmarventa;
    private javax.swing.JButton ButonGuardarProducto;
    private javax.swing.JButton ButonLimpiar;
    private javax.swing.JButton ButonNuevoProductos;
    private javax.swing.JButton ButonRegresar;
    private javax.swing.JComboBox<String> ComboMetodoPago;
    private javax.swing.JComboBox<String> ComboProductos;
    private javax.swing.JComboBox<String> Comboestadoproducto;
    private javax.swing.JTable TableProductos;
    private javax.swing.JTable TableVentas;
    private javax.swing.JTextField TxtTotalVenta;
    private javax.swing.JTextField Txtcodigo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNommbreproducto;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblcodigo1;
    private javax.swing.JLabel lblcodigoproducto;
    private javax.swing.JLabel lblestadoproducto;
    private javax.swing.JLabel lblexistencia;
    private javax.swing.JLabel lblmetodopago;
    private javax.swing.JLabel lblpreciorpoducto;
    private javax.swing.JLabel lblstok;
    private javax.swing.JLabel lbltituventa;
    private javax.swing.JLabel lbltituventa1;
    private javax.swing.JLabel lbltotalventa;
    private javax.swing.JTextField txtcantidaddeproductos;
    private javax.swing.JTextField txtcodigoproducto;
    private javax.swing.JTextField txtexistencia;
    private javax.swing.JTextField txtnombreproducto;
    private javax.swing.JTextField txtpreciproducto;
    private javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables
}
