package MDA;

import Models.ModeloRelacionMedicoCargador;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import objeto.Prestadores;
import objeto.cmt_medicocargador;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Reflection;

/**
 *
 * @author lperez
 */
public class DialogoDelegaciones extends javax.swing.JDialog {
    public List<Map<String, Object>> listaDelegado;
    public List<Map<String, Object>> listaMed;
    Frame parent;
    Dashboard dashboard;
    public Connection connection;
    public int codme;
    public int idmedicocargador;
    public int id_cargador;
    public int codmeCargador;
    public String nombreCargador;
    JSONArray listaPrestadorJson;

    public DialogoDelegaciones(java.awt.Frame parent, boolean modal, Connection connection) {
        super(parent, modal);
        this.connection = null;
        this.id_cargador = -1;
        initComponents();
        this.connection = connection;
        this.parent = parent;
    }

    public void datosDelegado() throws JSONException {
        codmeCargador = Integer.parseInt(this.txtCargador.getText());
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nombre, idcargador  FROM cmt_cargador WHERE codme = " + codmeCargador + "");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombreCargador = rs.getString(1);
                id_cargador = rs.getInt(2);
            }
            else {
                JOptionPane.showMessageDialog(null, "¡No existe!");
            }
            lblNombre.setText(this.nombreCargador);
            precargaDelegado();
        }
        catch (SQLException ex) {
            Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "¡No existe!");
        }
    }
    
    public void precargaDelegado() throws SQLException, JSONException {
        try {
            String query = "SELECT p.codme, p.nombre, m.idmedicocargador FROM prestadores p LEFT JOIN cmt_medicocargador m on p.codme=m.codme WHERE m.idcargador = " + id_cargador + " and m.estado = 1";
            ArrayList arrayDel = new ArrayList();
            List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(query, arrayDel, connection);
            if (lista.size() > 0) {
                for (int indice = 0; indice < lista.size(); ++indice) {
                    JSONObject json = new JSONObject()
                            .put("nombre", (Object)lista.get(indice).get(".nombre").toString().trim())
                            .put("codme", lista.get(indice).get(".codme"))
                            .put("idmedicocargador", lista.get(indice).get(".idmedicocargador"));
                    listaPrestadorJson.put((Object)json);
                }
                getTblDelegaciones().setModel(new ModeloRelacionMedicoCargador(listaPrestadorJson));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void datosMedico() throws SQLException, JSONException {
        try {
            codme = Integer.parseInt(txtCodme.getText());
        }
        catch (Exception ex) {}
        JSONObject prestadorJson = Prestadores.getNombrePrestadorJson(codme, connection);
        if (prestadorJson.getBoolean("result")) {
            listaPrestadorJson.put((Object)prestadorJson);
            getTblDelegaciones().setModel(new ModeloRelacionMedicoCargador(listaPrestadorJson));
        }
        else {
            JOptionPane.showMessageDialog(null, "¡No existe!");
        }
    }
    
    public void eliminarMedicoCargador(int idmedicocargador, int codme, int idcargador) {
        try {
            String update = "UPDATE cmt_medicocargador SET estado = 0 WHERE idmedicocargador = " + idmedicocargador + " and codme = " + codme + " and idcargador = " + idcargador + "";
            connection.createStatement().execute(update);
        }
        catch (SQLException ex) {}
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCargador = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDelegaciones = new javax.swing.JTable();
        lblNombre = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Altas y Delegaciones"));

        txtCargador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCargadorActionPerformed(evt);
            }
        });
        txtCargador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCargadorKeyPressed(evt);
            }
        });

        jLabel1.setText("Cargador");

        jLabel2.setText("N° Socio");

        txtCodme.setEnabled(false);
        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.setEnabled(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.setEnabled(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblDelegaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDelegaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDelegacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDelegaciones);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCargador, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCargador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar))
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sweep.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalir)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnLimpiar)
                        .addComponent(btnGuardar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCargadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCargadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCargadorActionPerformed

    private void txtCargadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCargadorKeyPressed
        if (evt.getKeyCode() == 10) {
            if (txtCargador.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío.");
            }
            else {
                try {
                    listaPrestadorJson = new JSONArray();
                    datosDelegado();
                    btnAgregar.setEnabled(true);
                    txtCodme.setEnabled(true);
                    txtCodme.requestFocus();
                }
                catch (JSONException ex) {
                    Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
                }
            }
        }
    }//GEN-LAST:event_txtCargadorKeyPressed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == 10) {
            if (txtCodme.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío.");
            }
            else {
                try {
                    datosMedico();
                    txtCodme.setText("");
                    txtCodme.requestFocus();
                }
                catch (SQLException ex) {
                    Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (JSONException ex2) {
                    Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, (Throwable)ex2);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            datosMedico();
            txtCodme.setText("");
            txtCodme.requestFocus();
        }
        catch (SQLException ex) {
            Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (JSONException ex2) {
            Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, (Throwable)ex2);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblDelegacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDelegacionesMouseClicked
        if (evt.getClickCount() == 2) {
            int dialogButton = JOptionPane.showConfirmDialog(null, "¿Desea eliminar médico?", "Mensaje del Sistema", 0);
            try {
                int _codme = 0;
                int indice = tblDelegaciones.getSelectedRow();
                try {
                    idmedicocargador = listaPrestadorJson.getJSONObject(indice).getInt("idmedicocargador");
                }
                catch (Exception ex2) {}
                _codme = listaPrestadorJson.getJSONObject(tblDelegaciones.getSelectedRow()).getInt("codme");
                if (dialogButton == 0) {
                    eliminarMedicoCargador(idmedicocargador, _codme, id_cargador);
                    btnAgregar.setEnabled(true);
                    txtCodme.setEnabled(true);
                    txtCodme.requestFocus();
                    listaPrestadorJson.remove(indice);
                    getTblDelegaciones().setModel(new ModeloRelacionMedicoCargador(listaPrestadorJson));
                    JOptionPane.showMessageDialog(null, "Médico Elminado.");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cancelado por el Usuario");
                }
            }
            catch (JSONException ex) {
                Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
            }
        }
    }//GEN-LAST:event_tblDelegacionesMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtCargador.setText("");
        txtCodme.setText("");
        txtCodme.setEnabled(false);
        btnAgregar.setEnabled(false);
        TableModel newModel = new DefaultTableModel();
        tblDelegaciones.setModel(newModel);
        lblNombre.setText("");
        txtCargador.requestFocus();
        listaPrestadorJson = new JSONArray();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int dialogButton = JOptionPane.showConfirmDialog(null, "¿Desea agregar?", "Mensaje del Sistema", 0);
        if (dialogButton == 0) {
            int cantidadAgregada = 0;
            for (int indice = 0; indice < listaPrestadorJson.length(); ++indice) {
                try {
                    int codme = listaPrestadorJson.getJSONObject(indice).getInt("codme");
                    boolean existeRelacion = cmt_medicocargador.existCargadorPorCodme(codme, id_cargador, connection);
                    if (!existeRelacion && id_cargador > 0) {
                        cmt_medicocargador mc = new cmt_medicocargador();
                        mc.codme = codme;
                        mc.fecha = cmt_medicocargador.getFechaActual();
                        mc.idcargador = id_cargador;
                        mc.estado = 1;
                        cmt_medicocargador.create(mc, connection);
                        ++cantidadAgregada;
                    }
                }
                catch (JSONException ex) {
                    Logger.getLogger(DialogoDelegaciones.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
                }
            }
            JOptionPane.showMessageDialog(null, "Se agregó " + cantidadAgregada + " a la lista");
            this.txtCargador.setText("");
            this.txtCargador.requestFocus();
        }
        else {
            JOptionPane.showMessageDialog(null, "Cancelado por el Usuario");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblDelegaciones;
    private javax.swing.JTextField txtCargador;
    private javax.swing.JTextField txtCodme;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTable getTblDelegaciones() {
        return tblDelegaciones;
    }

    public void setTblDelegaciones(javax.swing.JTable tblDelegaciones) {
        this.tblDelegaciones = tblDelegaciones;
    }

    public javax.swing.JTextField getTxtCargador() {
        return txtCargador;
    }

    public void setTxtCargador(javax.swing.JTextField txtCargador) {
        this.txtCargador = txtCargador;
    }

    public javax.swing.JTextField getTxtCodme() {
        return txtCodme;
    }

    public void setTxtCodme(javax.swing.JTextField txtCodme) {
        this.txtCodme = txtCodme;
    }

    public javax.swing.JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(javax.swing.JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }
}
