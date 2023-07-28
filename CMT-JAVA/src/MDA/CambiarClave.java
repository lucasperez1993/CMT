package MDA;

import Controller.ControladorMesaAyuda;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.Cargador;
import otro.Constante;
import util.MD5;
import util.Reflection;
/**
 *
 * @author lperez
 */
public class CambiarClave extends javax.swing.JDialog {
    public Connection connection;
    Cargador objetoCargador;
    Dashboard dashboard;
    DialogoConsulta dialogoconsulta;
    String username;
    public boolean esCambiodeClave;
    public boolean esCambiodeClaveIPSST;
    //Cambios cambios;
    ControladorMesaAyuda controlador;

    public CambiarClave(java.awt.Frame parent, boolean modal, Cargador objetoCargador, ControladorMesaAyuda controlador, 
            DialogoConsulta dialogoconsulta, String username, Connection connection) {
        super(parent, modal);
        this.dashboard = dashboard;
        this.connection = null;
        esCambiodeClave = false;
        esCambiodeClaveIPSST = false;
        //this.cambios = new Cambios();
        initComponents();
        this.connection = connection;
        this.objetoCargador = objetoCargador;
        this.dialogoconsulta = dialogoconsulta;
        this.username = username;
        txtConfirmar1.requestFocus();
        rbClaveCMT.setSelected(true);
        this.controlador = controlador;
    }
    
    public void cambioClave(Cargador objetoCargador, Connection connection) throws SQLException {
        if (txtConfirmar1.getText().equals("") && txtConfirmar2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe completar los campos.");
        }
        else if (txtConfirmar1.getText().equals(txtConfirmar2.getText())) {
            if (rbClaveCMT.isSelected()) {
                updateClaveCMT(objetoCargador);
                JOptionPane.showMessageDialog(null, "La clave ha sido cambiada correctamente.");
                dispose();
            }
            if (rbClaveIPSST.isSelected()) {
                updateClaveIPSST(objetoCargador);
                JOptionPane.showMessageDialog(null, "La clave ha sido cambiada correctamente.");
                dispose();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
        }
    }
    
    public void updateClaveCMT(Cargador objetoCargador) throws SQLException {
        try {
            String update = "UPDATE cmt_clave SET clave = '" + txtConfirmar2.getText() + "' WHERE codme = " + objetoCargador.codme + "";
            connection.createStatement().execute(update);
            String _md5 = MD5.getEncodedString(this.txtConfirmar2.getText().trim());
            String updateMD5 = "UPDATE usuario SET contrasena = '" + _md5 + "' WHERE nrosocio = " + objetoCargador.codme + "";
            connection.createStatement().execute(updateMD5);
            esCambiodeClave = true;
        }
        catch (SQLException ex) {
            this.esCambiodeClave = false;
        }
        String execSql = "INSERT INTO cmt_auditoria_mda VALUES ('" + username + "','" + Constante.CAMBIO_CLAVE_CMT + "', " + objetoCargador.codme + ",GETDATE(),'" + esCambiodeClave + "')";
        connection.createStatement().execute(execSql);
    }
    
    public void updateClaveIPSST(Cargador objetoCargador) throws SQLException {
        try {
            String consulta = "SELECT * FROM cmt_claveipsst WHERE codme = " + objetoCargador.codme + "";
            connection.createStatement().execute(consulta);
            ArrayList arrayConsulta = new ArrayList();
            List<Map<String, Object>> listaConsulta = Reflection.getMapQueryResultByPreparedStatement(consulta, arrayConsulta, connection);
            if (listaConsulta.size() > 0) {
                String update = "UPDATE cmt_claveipsst SET clave = '" + txtConfirmar2.getText() + "' WHERE codme = " + objetoCargador.codme + "";
                connection.createStatement().execute(update);
                esCambiodeClaveIPSST = true;
            }
            else {
                insertNuevoIPSST(objetoCargador);
            }
        }
        catch (SQLException ex) {
            esCambiodeClaveIPSST = false;
        }
        String execSql = "INSERT INTO cmt_auditoria_mda VALUES ('" + username + "','" + Constante.CAMBIO_CLAVE_IPSST + "', " + objetoCargador.codme + ",GETDATE(),'" + esCambiodeClaveIPSST + "')";
        connection.createStatement().execute(execSql);
    }
    
    public void insertNuevoIPSST(final Cargador objetoCargador) {
        try {
            String insert = "INSERT INTO cmt_claveipsst VALUES(" + objetoCargador.codme + ", '" + txtConfirmar2.getText() + "', 1)";
            connection.createStatement().execute(insert);
        }
        catch (SQLException ex) {
            Logger.getLogger(CambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        rbClaveCMT = new javax.swing.JRadioButton();
        rbClaveIPSST = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtConfirmar1 = new javax.swing.JTextField();
        txtConfirmar2 = new javax.swing.JTextField();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cambiar Clave"));

        buttonGroup1.add(rbClaveCMT);
        rbClaveCMT.setSelected(true);
        rbClaveCMT.setText("Clave CMT");

        buttonGroup1.add(rbClaveIPSST);
        rbClaveIPSST.setText("Clave IPSST");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nueva Clave");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Confirmar Clave");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(rbClaveCMT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbClaveIPSST)
                .addGap(21, 21, 21))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtConfirmar1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtConfirmar2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbClaveCMT)
                    .addComponent(rbClaveIPSST))
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtConfirmar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtConfirmar2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(btnGuardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            cambioClave(objetoCargador, connection);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(CambiarClave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton rbClaveCMT;
    private javax.swing.JRadioButton rbClaveIPSST;
    private javax.swing.JTextField txtConfirmar1;
    private javax.swing.JTextField txtConfirmar2;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JRadioButton getRbClaveCMT() {
        return rbClaveCMT;
    }

    public void setRbClaveCMT(javax.swing.JRadioButton rbClaveCMT) {
        this.rbClaveCMT = rbClaveCMT;
    }

    public javax.swing.JRadioButton getRbIPSST() {
        return rbClaveIPSST;
    }

    public void setRbIPSST(javax.swing.JRadioButton rbIPSST) {
        this.rbClaveIPSST = rbIPSST;
    }

    public javax.swing.JTextField getTxtConfirmar1() {
        return txtConfirmar1;
    }

    public void setTxtConfirmar1(javax.swing.JTextField txtConfirmar1) {
        this.txtConfirmar1 = txtConfirmar1;
    }

    public javax.swing.JTextField getTxtConfirmar2() {
        return txtConfirmar2;
    }

    public void setTxtConfirmar2(javax.swing.JTextField txtConfirmar2) {
        this.txtConfirmar2 = txtConfirmar2;
    }
}
