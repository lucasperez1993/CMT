package MDA;

import Conexion.Conexion;
import Login.Login;
import Models.ModeloConsulta;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.json.JSONObject;
import tables.tblConsultaMesa;
import util.Reflection;
/**
 *
 * @author lperez
 */
public class DialogoControl extends javax.swing.JDialog {
    public List<Map<String, Object>> listaControl;
    ArrayList<tblConsultaMesa> listaTblConsultaMesa;
    Dashboard dashboard;
    Excel excel;
    Login login;
    public Connection connection;
    public String usercmb;
    public String consulta;
    public String path;
    public int id;
    public String periodoini;
    public String periodofin;
    public String user;
    public int tipo;
    public String username;
    public JSONObject permisoJson;
    
    
    public DialogoControl(java.awt.Frame parent, Dashboard dashboard, Excel excel, Connection connection) {
        super(parent);
        this.connection = null;
        path = "C:\\Mesa de Ayuda\\";
        
        login = new Login();
        user = Login.user;
        tipo = Login.tipo;
        username = Login.username;
        initComponents();
        cmbUser.setSelectedItem(user);
        this.btnExcel.setEnabled(false);
        this.dcDesde.setEnabled(false);
        this.dcHasta.setEnabled(false);
        this.connection = connection;
        dashboard = dashboard;
        if (this.tipo == 0) {
            this.cmbUser.setEnabled(true);
        }
    }

    public void cargarTabla() throws SQLException {
        Conexion con = new Conexion();
        if (tipo == 0) {
            try {
                getUserSelected();
                if (cbTodos.isSelected()) {
                    consulta = "SELECT id, codme, nombre, motivo, solucion, medio, os, observaciones, fechaini, fechafin FROM cmt_consultamesa WHERE usuariocmt = '" + this.usercmb + "' ORDER BY fechaini desc";
                }
                else {
                    dateFormat();
                    consulta = "SELECT id, codme, nombre, motivo, solucion, medio, os, observaciones, fechaini, fechafin FROM cmt_consultamesa WHERE usuariocmt = '" + this.usercmb + "' AND fechaini >='" + this.periodoini.replaceAll("-", "") + "' AND fechafin <='" + this.periodofin.replaceAll("-", "") + "' ORDER BY fechaini desc";
                }
                 ArrayList arrayBuscar = new ArrayList();
                listaControl = Reflection.getMapQueryResultByPreparedStatement(consulta, arrayBuscar, con.GetConnectionCloud());
                getTblConsulta().setModel(new ModeloConsulta(listaControl));
            }
            catch (SQLException ex) {
               
            }
        }
        if (tipo == 1) {
            try {
                if (cbTodos.isSelected()) {
                    consulta = "SELECT id, codme, nombre, motivo, solucion, medio, os, observaciones, fechaini, fechafin FROM cmt_consultamesa WHERE usuariocmt = '" + this.username + "' ORDER BY fechaini desc";
                }
                else {
                    dateFormat();
                    consulta = "SELECT id, codme, nombre, motivo, solucion, medio, os, observaciones, fechaini, fechafin FROM cmt_consultamesa WHERE usuariocmt = '" + this.username + "' AND fechaini >='" + this.periodoini.replaceAll("-", "") + "' AND fechafin <='" + this.periodofin.replaceAll("-", "") + "' ORDER BY fechaini desc";
                }
                ArrayList arrayBuscar = new ArrayList();
                listaControl = Reflection.getMapQueryResultByPreparedStatement(consulta, arrayBuscar, con.GetConnectionCloud());
                getTblConsulta().setModel(new ModeloConsulta(listaControl));
            }
            catch (SQLException ex) {
                
            }
        }
    }
    
    public void dateFormat() {
        final DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        this.periodoini = f.format(this.dcDesde.getDate());
        this.periodofin = f.format(this.dcHasta.getDate());
    }
    
    public void getUserSelected() {
        if (this.cmbUser.getSelectedItem().equals("Giselle Piana")) {
            this.usercmb = "Giselle Piana";
        }
        if (this.cmbUser.getSelectedItem().equals("Adrian Aragon")) {
            this.usercmb = "Adrian Aragon";
        }
        if (this.cmbUser.getSelectedItem().equals("Ailen Parajon")) {
            this.usercmb = "Ailen Parajon";
        }
        if (this.cmbUser.getSelectedItem().equals("Natalia Flores")) {
            this.usercmb = "Natalia Flores";
        }
        if (this.cmbUser.getSelectedItem().equals("Administrador")) {
            this.usercmb = "Admin";
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbUser = new javax.swing.JComboBox<>();
        cbTodos = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        dcDesde = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dcHasta = new com.toedter.calendar.JDateChooser();
        btnConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConsulta = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Control"));

        jLabel1.setText("Usuario:");

        cmbUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Giselle Piana", "Ailen Parajon", "Natalia Flores", "Adrian Aragon" }));

        cbTodos.setSelected(true);
        cbTodos.setText("Todas las fechas");
        cbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTodosActionPerformed(evt);
            }
        });

        jLabel2.setText("Desde:");

        jLabel3.setText("Hasta:");

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minisearch.png"))); // NOI18N
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        tblConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConsultaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblConsulta);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTodos)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConsultar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcDesde, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcHasta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sweep.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/excel.png"))); // NOI18N
        btnExcel.setEnabled(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVolver)
                    .addComponent(btnExcel)
                    .addComponent(btnLimpiar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        try {
            cargarTabla();
            btnExcel.setEnabled(true);
        }
        catch (SQLException ex) {
            Logger.getLogger(DialogoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void tblConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConsultaMouseClicked
//        DialogoModificarConsulta dialogo = null;
//        if (evt.getClickCount() == 2) {
//            this.id = Integer.valueOf(this.listaControl.get(this.tblConsulta.getSelectedRow()).get(".id").toString());
//            dialogo = new DialogoModificarConsulta(this.formularioPrincipal, true, this.formularioPrincipal, this.id, this, this.connection);
//            final int indice = this.getTblConsulta().getSelectedRow();
//            dialogo.getTxtSocioCargador().setText(this.listaControl.get(indice).get(".codme").toString());
//            dialogo.getTxtNombre().setText(this.listaControl.get(indice).get(".nombre").toString());
//            dialogo.getTxtMotivo().setText(this.listaControl.get(indice).get(".motivo").toString());
//            dialogo.getTxtOS().setText(this.listaControl.get(indice).get(".os").toString());
//            dialogo.getTxtMedio().setText(this.listaControl.get(indice).get(".medio").toString());
//            dialogo.getTxtObservaciones().setText(this.listaControl.get(indice).get(".observaciones").toString());
//            dialogo.setVisible(true);
//        }
    }//GEN-LAST:event_tblConsultaMouseClicked

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            if (tipo == 0) {
                excel = new Excel();
                excel.cargarXLS(this, cmbUser.getSelectedItem().toString().replaceAll(" ", "_"));
                JOptionPane.showMessageDialog(null, "Archivo generado correctamente.");
                Runtime.getRuntime().exec("explorer.exe /start," + path);
            }
            else {
                excel.cargarXLS(this, username.replaceAll(" ", "_"));
                JOptionPane.showMessageDialog(null, "Archivo generado correctamente.");
                Runtime.getRuntime().exec("explorer.exe /start," + path);
            }
        }
        catch (SQLException | IOException ex3) {
            Exception ex2 = null;
            Exception ex = ex2;
            JOptionPane.showMessageDialog(null, "El archivo no pudo generarse. Contacte al Administrador.");
            Logger.getLogger(DialogoControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void cbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTodosActionPerformed
        if (this.cbTodos.isSelected()) {
            this.dcDesde.setEnabled(false);
            this.dcHasta.setEnabled(false);
        }
        else {
            this.dcDesde.setEnabled(true);
            this.dcHasta.setEnabled(true);
        }
    }//GEN-LAST:event_cbTodosActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    public void limpiarCampos() {
        TableModel newModel = new DefaultTableModel();
        tblConsulta.setModel(newModel);
        dcDesde.setDate((Date)null);
        dcHasta.setDate((Date)null);
        dcDesde.setEnabled(false);
        dcHasta.setEnabled(false);
        cbTodos.setSelected(true);
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox cbTodos;
    private javax.swing.JComboBox<String> cmbUser;
    private com.toedter.calendar.JDateChooser dcDesde;
    private com.toedter.calendar.JDateChooser dcHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConsulta;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JCheckBox getCbTodos() {
        return cbTodos;
    }

    public void setCbTodos(javax.swing.JCheckBox cbTodos) {
        this.cbTodos = cbTodos;
    }

    public javax.swing.JComboBox<String> getCmbUser() {
        return cmbUser;
    }

    public void setCmbUser(javax.swing.JComboBox<String> cmbUser) {
        this.cmbUser = cmbUser;
    }

    public com.toedter.calendar.JDateChooser getDcHasta() {
        return dcHasta;
    }

    public void setDcHasta(com.toedter.calendar.JDateChooser dcHasta) {
        this.dcHasta = dcHasta;
    }

    public javax.swing.JTable getTblConsulta() {
        return tblConsulta;
    }

    public void setTblConsulta(javax.swing.JTable tblConsulta) {
        this.tblConsulta = tblConsulta;
    }
}
