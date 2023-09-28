package COLEGYM;

import java.awt.event.KeyEvent;
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
import util.Reflection;

/**
 *
 * @author lperez
 */
public class DialogoRutina extends javax.swing.JDialog {

    public Connection connection5;
    public boolean banderaSoc;
    public boolean banderaAdh;

    public DialogoRutina(java.awt.Frame parent, boolean modal, Connection connection) {
        super(parent, modal);
        this.connection5 = connection;
        initComponents();
        txtDNISocio.requestFocus();
        banderaAdh = false;
        banderaSoc = false;
    }

    public void cargarRutinaAdh() throws SQLException {
        String numdoc = txtDNIAdh.getText();
        if (!existeAdhInscripto(Integer.valueOf(numdoc))) {
            JOptionPane.showMessageDialog(null, "El afiliado no se encuentra activo.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } else {
            PreparedStatement ps = connection5.prepareStatement("SELECT r.id_rutina, r.rutina, a.nombre, r.numdoc "
                    + "FROM gym_rutinas r "
                    + "LEFT JOIN gym_adh_inscripto a ON r.numdoc = a.numdoc "
                    + "WHERE r.numdoc = ? AND a.numdoc = ?");
            ps.setObject(1, numdoc);
            ps.setObject(2, numdoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtRutinaAdh.setText(rs.getString(2));
                btnGuardarAdh.setEnabled(true);
                banderaAdh = true;
            } else {
                JOptionPane.showMessageDialog(null, "Nueva rutina.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
                banderaAdh = false;
            }
        }
    }

    public void cargarRutinaSoc() throws SQLException {
        String numdoc = txtDNISocio.getText();
        if (!existeSocioInscripto(Integer.valueOf(numdoc))) {
            JOptionPane.showMessageDialog(null, "El afiliado no se encuentra activo.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println(existeSocioInscripto(Integer.valueOf(numdoc)));
            PreparedStatement ps = connection5.prepareStatement("SELECT r.id_rutina, r.rutina, p.nombre, r.numdoc "
                + "FROM gym_rutinas r "
                + "LEFT JOIN prestadores p ON r.numdoc = p.numdoc "
                + "LEFT JOIN gym_med_inscripto a ON a.codme = p.codme "
                + "WHERE r.numdoc = ? AND p.numdoc = ?");
            ps.setObject(1, numdoc);
            ps.setObject(2, numdoc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                txtRutinaSocio.setText(rs.getString(2));
                btnGuardarSoc.setEnabled(true);
                banderaSoc = true;
            } else {
                JOptionPane.showMessageDialog(null, "Nueva rutina.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
                banderaSoc = false;
            }
        }
    }

    public void limpiarCampos() {
        txtDNISocio.requestFocus();
        txtDNISocio.setText("");
        txtDNIAdh.setText("");
        txtRutinaSocio.setText("");
        txtRutinaAdh.setText("");
        txtNombreSocio.setText("");
        txtNombreAdh.setText("");
        btnGuardarAdh.setEnabled(false);
        btnGuardarSoc.setEnabled(false);
        banderaAdh = false;
        banderaSoc = false;
    }

    public boolean existeSocioInscripto(int numdoc) throws SQLException {
        String sql = "SELECT p.nombre, p.numdoc FROM gym_med_inscripto m "
                + "LEFT JOIN prestadores p ON m.codme = p.codme "
                + "WHERE p.numdoc = ? AND m.estado = ?";
        ArrayList existeSocioInscripto = new ArrayList();
        existeSocioInscripto.add(numdoc);
        existeSocioInscripto.add(1);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, existeSocioInscripto, connection5);
        if(lista.size() > 0){
            txtNombreSocio.setText(lista.get(0).get(".nombre").toString().trim());
            txtDNISocio.setText(lista.get(0).get(".numdoc").toString().trim());
            btnGuardarSoc.setEnabled(true);
            return true;
        }else{
            return false;
        }
    }

    public boolean existeAdhInscripto(int numdoc) throws SQLException {
        String sql = "SELECT * FROM gym_adh_inscripto WHERE numdoc = ? AND estado = ?";
        ArrayList existeAdhInscripto = new ArrayList();
        existeAdhInscripto.add(numdoc);
        existeAdhInscripto.add(1);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, existeAdhInscripto, connection5);
        if(lista.size() > 0){
            txtNombreAdh.setText(lista.get(0).get(".nombre").toString().trim());
            txtDNIAdh.setText(lista.get(0).get(".numdoc").toString().trim());
            btnGuardarAdh.setEnabled(true);
            return true;
        }else{
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDNISocio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreSocio = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRutinaSocio = new javax.swing.JTextArea();
        btnSalir1 = new javax.swing.JButton();
        btnGuardarSoc = new javax.swing.JButton();
        btnLimpiar1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDNIAdh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNombreAdh = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtRutinaAdh = new javax.swing.JTextArea();
        btnLimpiar = new javax.swing.JButton();
        btnGuardarAdh = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rutinas");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutinas e Info"));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Afiliado"));

        jLabel1.setText("D.N.I.:");

        txtDNISocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNISocioKeyPressed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        txtNombreSocio.setEditable(false);
        txtNombreSocio.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDNISocio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreSocio, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDNISocio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutina"));

        txtRutinaSocio.setColumns(20);
        txtRutinaSocio.setRows(5);
        jScrollPane1.setViewportView(txtRutinaSocio);

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });

        btnGuardarSoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnGuardarSoc.setEnabled(false);
        btnGuardarSoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSocActionPerformed(evt);
            }
        });

        btnLimpiar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sweep.png"))); // NOI18N
        btnLimpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnLimpiar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarSoc)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir1)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnLimpiar1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGuardarSoc))
                    .addComponent(btnSalir1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Socio", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Afiliado"));

        jLabel3.setText("D.N.I.:");

        txtDNIAdh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNIAdhKeyPressed(evt);
            }
        });

        jLabel4.setText("Nombre:");

        txtNombreAdh.setEditable(false);
        txtNombreAdh.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDNIAdh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreAdh)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDNIAdh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreAdh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Rutina"));

        txtRutinaAdh.setColumns(20);
        txtRutinaAdh.setRows(5);
        jScrollPane2.setViewportView(txtRutinaAdh);

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sweep.png"))); // NOI18N
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnGuardarAdh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnGuardarAdh.setEnabled(false);
        btnGuardarAdh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAdhActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarAdh)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGuardarAdh))
                    .addComponent(btnSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Adherente", jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtDNISocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNISocioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtDNISocio.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un D.N.I.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    cargarRutinaSoc();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoRutina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtDNISocioKeyPressed

    private void txtDNIAdhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIAdhKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtDNIAdh.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un D.N.I.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    cargarRutinaAdh();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoRutina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtDNIAdhKeyPressed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalir1ActionPerformed

    private void btnLimpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiar1ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiar1ActionPerformed

    private void btnGuardarSocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSocActionPerformed
        String rutina = txtRutinaSocio.getText();
        int numdoc = Integer.valueOf(txtDNISocio.getText());
        if (rutina.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo rutina no puede estar vacío.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!banderaSoc) {
                insertRutina(rutina, numdoc);
            } else {
                updateRutina(rutina, numdoc);
            }
        }

    }//GEN-LAST:event_btnGuardarSocActionPerformed

    private void btnGuardarAdhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAdhActionPerformed
        String rutina = txtRutinaAdh.getText();
        int numdoc = Integer.valueOf(txtDNIAdh.getText());
        if (rutina.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo rutina no puede estar vacío.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!banderaAdh) {
                insertRutina(rutina, numdoc);
            } else {
                updateRutina(rutina, numdoc);
            }
        }
    }//GEN-LAST:event_btnGuardarAdhActionPerformed

    public void insertRutina(String rutina, int numdoc) {
        String insert = "INSERT INTO gym_rutinas "
                + "VALUES ('" + rutina + "', " + numdoc + ")";
        try {
            connection5.createStatement().execute(insert);
            JOptionPane.showMessageDialog(null, "Rutina guardada.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DialogoRutina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateRutina(String rutina, int numdoc) {
        String update = "UPDATE gym_rutinas SET rutina = '" + rutina + "' WHERE numdoc = " + numdoc + "";
        try {
            connection5.createStatement().execute(update);
            JOptionPane.showMessageDialog(null, "Rutina modificada.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DialogoRutina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarAdh;
    private javax.swing.JButton btnGuardarSoc;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiar1;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtDNIAdh;
    private javax.swing.JTextField txtDNISocio;
    private javax.swing.JTextField txtNombreAdh;
    private javax.swing.JTextField txtNombreSocio;
    private javax.swing.JTextArea txtRutinaAdh;
    private javax.swing.JTextArea txtRutinaSocio;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTextField getTxtDNI() {
        return txtDNISocio;
    }

    public void setTxtDNI(javax.swing.JTextField txtDNI) {
        this.txtDNISocio = txtDNI;
    }

    public javax.swing.JTextField getTxtNombre() {
        return txtNombreSocio;
    }

    public void setTxtNombre(javax.swing.JTextField txtNombre) {
        this.txtNombreSocio = txtNombre;
    }

    public javax.swing.JTextArea getTxtRutina() {
        return txtRutinaSocio;
    }

    public void setTxtRutina(javax.swing.JTextArea txtRutina) {
        this.txtRutinaSocio = txtRutina;
    }

    public javax.swing.JTextField getTxtDNIAhd() {
        return txtDNIAdh;
    }

    public void setTxtDNIAhd(javax.swing.JTextField txtDNIAhd) {
        this.txtDNIAdh = txtDNIAhd;
    }

    public javax.swing.JTextField getTxtDNISocio() {
        return txtDNISocio;
    }

    public void setTxtDNISocio(javax.swing.JTextField txtDNISocio) {
        this.txtDNISocio = txtDNISocio;
    }

    public javax.swing.JTextArea getTxtRutinaAdh() {
        return txtRutinaAdh;
    }

    public void setTxtRutinaAdh(javax.swing.JTextArea txtRutinaAdh) {
        this.txtRutinaAdh = txtRutinaAdh;
    }

    public javax.swing.JTextArea getTxtRutinaSocio() {
        return txtRutinaSocio;
    }

    public void setTxtRutinaSocio(javax.swing.JTextArea txtRutinaSocio) {
        this.txtRutinaSocio = txtRutinaSocio;
    }
}
