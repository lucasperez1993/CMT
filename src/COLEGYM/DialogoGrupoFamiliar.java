package COLEGYM;

import Conexion.Conexion;
import Models.ModeloGrupoFamiliar;
import Models.ModeloIngresos;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import util.Reflection;

/**
 *
 * @author lperez
 */
public class DialogoGrupoFamiliar extends javax.swing.JDialog {
    public Connection connection5;
    Conexion con = new Conexion();
    
    public DialogoGrupoFamiliar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        connection5 = con.GetConnection5();
        initComponents();
    }

    public void cargarGrupoFamiliar() throws SQLException{
        int codme = Integer.valueOf(txtCodme.getText());
        String sql = "SELECT A.numdoc as dni, p.pre_id,P.CODME,P.NOMBRE,RTRIM(LTRIM(A.NOMBRE)) as nombrea,A.SUBC,A.PAREN paren,p.numdoc,a.numdoc numdoca "
                + "FROM club_ti C LEFT JOIN PRESTADORES P ON C.PRE_ID=P.PRE_ID " 
                + "LEFT JOIN CLUB_AD A ON C.PRE_ID=A.PRE_ID WHERE C.ESTADO=1 AND P.CODME = ? ORDER BY P.NOMBRE,a.subc";
        ArrayList arrayGrupoFamiliar = new ArrayList();
        arrayGrupoFamiliar.add(codme);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arrayGrupoFamiliar, connection5);
        if (lista.size() > 0) {
            lblNombre.setText(lista.get(0).get(".NOMBRE").toString());
            getTblGrupoFamiliar().setModel(new ModeloGrupoFamiliar(lista));
            TableColumnModel columnModel = tblGrupoFamiliar.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(300);
        } else {
            
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGrupoFamiliar = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar Grupo Familiar"));

        tblGrupoFamiliar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblGrupoFamiliar);

        jLabel1.setText("N° Socio:");

        txtCodme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodmeActionPerformed(evt);
            }
        });
        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodmeActionPerformed
        
    }//GEN-LAST:event_txtCodmeActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(txtCodme.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Campo vacío");
            }else{
                try {
                    cargarGrupoFamiliar();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoGrupoFamiliar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if(txtCodme.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Campo vacío");
        }else{
            try {
                cargarGrupoFamiliar();
            } catch (SQLException ex) {
                Logger.getLogger(DialogoGrupoFamiliar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblGrupoFamiliar;
    private javax.swing.JTextField txtCodme;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTable getTblGrupoFamiliar() {
        return tblGrupoFamiliar;
    }

    public void setTblGrupoFamiliar(javax.swing.JTable tblGrupoFamiliar) {
        this.tblGrupoFamiliar = tblGrupoFamiliar;
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
