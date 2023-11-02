package COLEGYM;

import Conexion.Conexion;
import Controller.VistaControlador;
import Login.Login;
import Models.ModeloIngresos;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import org.json.JSONObject;
import util.Deuda;
import util.Reflection;
/**
 *
 * @author lperez
 */
public class DashboardColegym extends javax.swing.JFrame {

    public Connection connectionVPS;
    public Connection connection5;
    Login login;
    DialogoAfiliados dialogoAfiliados = new DialogoAfiliados(this, true);
    Conexion con = new Conexion();
    VistaControlador vistaControlador = new VistaControlador();
    Deuda deuda = new Deuda();

    public DashboardColegym(JSONObject permisJson) throws SQLException {
        connection5 = con.GetConnection5();
        initComponents();
        lblUsuario.setText(Login.username);
        mostrarIngresosHoy();
        txtDNI.requestFocus();
        //JOptionPane.showMessageDialog(null, "AYUDA, TENGO SUEÑO 'O_-", "Mensaje del sistema", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarIngresosHoy() {
        try {
            String sql = "SELECT CONVERT(VARCHAR(2), DATEPART(HOUR, i.fecha)) + ':' + CONVERT(VARCHAR(2), DATEPART(MINUTE, i.fecha)) "
                    + "AS horaIngreso, a.nombre, i.fecha, i.numdoc "
                    + "FROM gym_ingresos i "
                    + "LEFT JOIN gym_adh_inscripto a ON i.numdoc = a.numdoc "
                    + "WHERE CONVERT(VARCHAR(10),fecha, 103) = CONVERT(VARCHAR(10),GETDATE(), 103) AND a.nombre IS NOT NULL "
                    + "UNION "
                    + "SELECT CONVERT(VARCHAR(2), DATEPART(HOUR, i.fecha)) + ':' + CONVERT(VARCHAR(2), DATEPART(MINUTE, i.fecha)) AS horaIngreso, p.nombre, i.fecha, i.numdoc "
                    + "FROM gym_ingresos i "
                    + "LEFT JOIN prestadores p ON p.numdoc = i.numdoc "
                    + "LEFT JOIN gym_med_inscripto m ON m.codme = p.codme "
                    + "WHERE CONVERT(VARCHAR(10),i.fecha, 103) = "
                    + "CONVERT(VARCHAR(10),GETDATE(), 103) AND p.tsocio <= 4 AND p.entidad = 0 AND p.nombre IS NOT NULL ORDER BY i.fecha DESC";
            ArrayList arrayIng = new ArrayList();
            List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayIng, connection5);
            if (lista.size() > 0) {
                getTblIngresos().setModel(new ModeloIngresos(lista));
                TableColumnModel columnModel = tblIngresos.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(300);
            } else {
                System.out.println("No hay ingresos");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getVencimiento(int numdoc) throws SQLException, ParseException{
        String sql = "SELECT TOP 1 fechavto, fechapago FROM gym_movimi WHERE numdoc = ? ORDER BY fechavto DESC";
        ArrayList arrayVto = new ArrayList() {};
        arrayVto.add(numdoc);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayVto, connection5);
        if(lista.size() > 0){
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date fechaVto = sdfEntrada.parse(lista.get(0).get(".fechavto").toString());
            SimpleDateFormat sdfSalida  = new SimpleDateFormat("dd/MM/yyyy");
            String fechaVtoF = sdfSalida.format(fechaVto);
            Date fechaPago = sdfEntrada.parse(lista.get(0).get(".fechapago").toString());
            String fechaPagoF = sdfSalida.format(fechaPago);
            JOptionPane.showMessageDialog(null, "Ultimo pago: "+fechaPagoF+" \n Vencimiento: "+fechaVtoF+"","Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Ultimo pago: SIN REGISTRO. \n Vencimiento: SIN REGISTRO.","Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
<<<<<<< HEAD
    public void insertIngreso() throws SQLException, ParseException {
=======
    public void insertIngreso() throws SQLException {
>>>>>>> 35df7de (solucionando mocos del bicho)
        String numdoc = txtDNI.getText();
        if(deuda.tieneDeudaPorIngreso(Integer.valueOf(numdoc), connection5)){
            JOptionPane.showMessageDialog(null, "El Socio titular presenta DEUDA.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        }
        String sql = "SELECT numdoc FROM gym_ingresos "
                + "WHERE numdoc = ? AND DAY(fecha) = DAY(GETDATE()) "
                + "AND MONTH(fecha) = MONTH(GETDATE()) AND YEAR(fecha) = YEAR(GETDATE())";
        ArrayList arrayExisteIngreso = new ArrayList();
        arrayExisteIngreso.add(numdoc);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayExisteIngreso, connection5);
        if (lista.size() > 0) {
            JOptionPane.showMessageDialog(null, "El afiliado ya fue ingresado.", "Mensaje del Sistema", 2);
            txtDNI.requestFocus();
        } else {
            String sql2 = "SELECT numdoc, nombre FROM gym_adh_inscripto WHERE numdoc = ? AND estado = ?";
            ArrayList arrayExisteIngresoAdh = new ArrayList();
            arrayExisteIngresoAdh.add(numdoc);
            arrayExisteIngresoAdh.add(1);
            List<Map<String, Object>> lista2 = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayExisteIngresoAdh, connection5);
            if (lista2.size() > 0) {
<<<<<<< HEAD
                getVencimiento(Integer.valueOf(txtDNI.getText()));
=======
>>>>>>> 35df7de (solucionando mocos del bicho)
                String insert = "INSERT INTO gym_ingresos "
                        + "VALUES (" + numdoc + ", GETDATE())";
                connection5.createStatement().execute(insert);
                mostrarIngresosHoy();
                txtDNI.requestFocus();
            } else {
                String sql3 = "SELECT p.numdoc FROM prestadores p "
                        + "LEFT JOIN gym_med_inscripto g ON p.codme=g.codme "
                        + "WHERE p.numdoc = ? AND p.tsocio <= ? AND p.codme <= ? AND p.entidad = ? AND estado = ?";
                ArrayList arrayExisteIngresoSoc = new ArrayList();
                arrayExisteIngresoSoc.add(numdoc);
                arrayExisteIngresoSoc.add(4);
                arrayExisteIngresoSoc.add(75000);
                arrayExisteIngresoSoc.add(0);
                arrayExisteIngresoSoc.add(1);
                List<Map<String, Object>> lista3 = Reflection.getMapQueryResultByPreparedStatement(sql3, arrayExisteIngresoSoc, connection5);
                if (lista3.size() > 0) {
                    String insert = "INSERT INTO gym_ingresos "
                            + "VALUES (" + numdoc + ", GETDATE())";
                    connection5.createStatement().execute(insert);
                    mostrarIngresosHoy();
                    txtDNI.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "El D.N.I. ingresado no corresponde a un afiliado activo.", "Mensaje del Sistema", 2);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtDNI = new javax.swing.JTextField();
        btnMarcar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIngresos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnRutinas = new javax.swing.JButton();
        btnAfiliados = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("COLEGYM");
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Marcar ingreso"));

        txtDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDNIActionPerformed(evt);
            }
        });
        txtDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNIKeyPressed(evt);
            }
        });

        btnMarcar.setText("Marcar");
        btnMarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMarcar)
                        .addGap(63, 63, 63))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(btnMarcar)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingresos del día"));

        tblIngresos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblIngresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIngresosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblIngresos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo-colegym.png"))); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(51, 153, 255));
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logo Colegio chico.png"))); // NOI18N

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/offlog.png"))); // NOI18N
        btnSalir.setToolTipText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnRutinas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/capacitacion (1).png"))); // NOI18N
        btnRutinas.setToolTipText("RUTINAS");
        btnRutinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutinasActionPerformed(evt);
            }
        });

        btnAfiliados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add-user.png"))); // NOI18N
        btnAfiliados.setToolTipText("GESTIÓN DE AFILIADOS");
        btnAfiliados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAfiliadosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAfiliados)
                .addGap(18, 18, 18)
                .addComponent(btnRutinas)
                .addGap(18, 18, 18)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRutinas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAfiliados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnMarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarActionPerformed
        try {
            if (txtDNI.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un D.N.I.", "Mensaje del Sistema", 1);
                txtDNI.requestFocus();
            } else {
                insertIngreso();
                txtDNI.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
<<<<<<< HEAD
        } catch (ParseException ex) {
            Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
=======
>>>>>>> 35df7de (solucionando mocos del bicho)
        }
    }//GEN-LAST:event_btnMarcarActionPerformed

    private void txtDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (txtDNI.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un D.N.I.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
                    txtDNI.requestFocus();
                } else {
                    insertIngreso();
<<<<<<< HEAD
=======
                    getVencimiento(Integer.valueOf(txtDNI.getText()));
>>>>>>> 35df7de (solucionando mocos del bicho)
                    txtDNI.setText("");
                }
            } catch (SQLException ex) {
                Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtDNIKeyPressed

    
    private void btnAfiliadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAfiliadosActionPerformed
        vistaControlador.vistaDialogoAfiliados(this, true);
    }//GEN-LAST:event_btnAfiliadosActionPerformed

    private void txtDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDNIActionPerformed
        
    }//GEN-LAST:event_txtDNIActionPerformed

    private void btnRutinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutinasActionPerformed
        vistaControlador.vistaDialogoRutina(this, true, connection5);
    }//GEN-LAST:event_btnRutinasActionPerformed

    private void tblIngresosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIngresosMouseClicked
        if (evt.getClickCount() == 2) {
        int filaSeleccionada = tblIngresos.getSelectedRow();
            int columnaSeleccionada = 1;
            if (filaSeleccionada != -1) {
                Object valor = tblIngresos.getValueAt(filaSeleccionada, columnaSeleccionada);
                if (valor != null && valor instanceof Number && ((Number) valor).intValue() != 0) {
                    int numdoc = ((Number) valor).intValue();
                    try {
                        getVencimiento(numdoc);
                    } catch (SQLException ex) {
                        Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(DashboardColegym.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El adherente debe actualizar sus datos en Despacho.", "Mensaje del sistema", 2);
                }
            }
        }
    }//GEN-LAST:event_tblIngresosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfiliados;
    private javax.swing.JButton btnMarcar;
    private javax.swing.JButton btnRutinas;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblIngresos;
    private javax.swing.JTextField txtDNI;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTable getTblIngresos() {
        return tblIngresos;
    }

    public void setTblIngresos(javax.swing.JTable tblIngresos) {
        this.tblIngresos = tblIngresos;
    }

    public javax.swing.JTextField getTxtDNI() {
        return txtDNI;
    }

    public void setTxtDNI(javax.swing.JTextField txtDNI) {
        this.txtDNI = txtDNI;
    }

    public javax.swing.JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(javax.swing.JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }
}
