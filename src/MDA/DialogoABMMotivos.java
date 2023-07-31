package MDA;

import Conexion.Conexion;
import Models.ModeloMotivo;
import java.sql.Connection;
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
public class DialogoABMMotivos extends javax.swing.JDialog {
    public List<Map<String, Object>> listaMotivo;
    public Connection connection;
    public String motivo;
    public int id;
    public int indice;
    
    public DialogoABMMotivos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        motivo = "";
        initComponents();
        Conexion con = new Conexion();
        this.connection = con.GetConnectionCloud();
        cargarMotivos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMotivos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMotivo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblMotivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblMotivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMotivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMotivos);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Agregar/Borrar/Modificar Motivos");

        jLabel2.setText("Motivo");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMotivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblMotivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMotivosMouseClicked
        if (evt.getClickCount() == 1) {
            indice = Integer.valueOf(listaMotivo.get(tblMotivos.getSelectedRow()).get(".id_motivo").toString());
            String mot = listaMotivo.get(tblMotivos.getSelectedRow()).get(".motivo").toString();
            txtMotivo.setText(mot);
            btnModificar.setEnabled(true);
        }
        if (evt.getClickCount() == 2) {
            final int id_motivo = Integer.valueOf(listaMotivo.get(tblMotivos.getSelectedRow()).get(".id_motivo").toString());
            try {
                int dialogButton = JOptionPane.showConfirmDialog(null, "¿Desea eliminar motivo?", "Mensaje del Sistema", 0);
                if (dialogButton == 1) {
                    JOptionPane.showMessageDialog(null, "Cancelado por el Usuario");
                }
                if (dialogButton == 0) {
                    eliminarMotivo(id_motivo);
                    JOptionPane.showMessageDialog(null, "Motivo Eliminado");
                }
                cargarMotivos();
            }
            catch (SQLException ex) {
                Logger.getLogger(DialogoABMMotivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tblMotivosMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            modificarMotivo(indice);
            cargarMotivos();
        }
        catch (SQLException ex) {
            Logger.getLogger(DialogoABMMotivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.guardarMotivo();
        this.cargarMotivos();
    }//GEN-LAST:event_btnGuardarActionPerformed

public void cargarMotivos() {
        try {
            String query = "SELECT * from cmt_motivomesa where estado = 1";
            ArrayList arrayMotivo = new ArrayList();
            listaMotivo = Reflection.getMapQueryResultByPreparedStatement(query, arrayMotivo, connection);
            getTblMotivos().setModel(new ModeloMotivo(listaMotivo));
        }
        catch (SQLException ex) {
            Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eliminarMotivo(final int id_motivo) throws SQLException {
        String update = "UPDATE cmt_motivomesa SET estado = 0 WHERE id_motivo = " + id_motivo + "";
        connection.createStatement().execute(update);
    }
    
    public void modificarMotivo(final int indice) throws SQLException {
        String update = "UPDATE cmt_motivomesa SET motivo = '" + txtMotivo.getText() + "' WHERE id_motivo = " + indice + "";
        connection.createStatement().execute(update);
    }
    
    public void guardarMotivo() {
        motivo = txtMotivo.getText();
        try {
            final String insert = "insert into cmt_motivomesa (motivo, estado) values ('" + motivo + "',1)";
            connection.createStatement().execute(insert);
        }
        catch (SQLException ex) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMotivos;
    private javax.swing.JTextField txtMotivo;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTable getTblMotivos() {
        return tblMotivos;
    }

    public void setTblMotivos(javax.swing.JTable tblMotivos) {
        this.tblMotivos = tblMotivos;
    }

    public javax.swing.JTextField getTxtMotivo() {
        return txtMotivo;
    }

    public void setTxtMotivo(javax.swing.JTextField txtMotivo) {
        this.txtMotivo = txtMotivo;
    }
}
