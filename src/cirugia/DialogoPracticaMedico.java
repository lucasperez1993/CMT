/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cirugia;

import Conexion.Conexion;
import Models.ModeloResumenCirugia;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author djaime
 */
public class DialogoPracticaMedico extends javax.swing.JDialog {

    /**
     * Creates new form DialogoPracticaMedico
     */
    public DialogoPracticaMedico(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPracticaMedico = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        textCodigo = new javax.swing.JTextField();
        textPeriodo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaPracticaMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaPracticaMedico);

        jLabel1.setText("Codigo:");

        textCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textCodigoKeyPressed(evt);
            }
        });

        textPeriodo.setText("202301");
        textPeriodo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textPeriodoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(textPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void textCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Conexion conection = new Conexion();
            int practica = 0;
            int periodo = 202301;
            try {
                periodo = Integer.valueOf(textPeriodo.getText());
            } catch (Exception e) {
            }
            try {
                practica = Integer.valueOf(textCodigo.getText());
            } catch (Exception e) {
            } finally {
                try {
                    conection.GetConnection5().close();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoBuscarPractica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            tablaPracticaMedico.setModel(new ModeloResumenCirugia(SqlCirugia.getMedicoPorPractica(periodo, practica, conection.GetConnection5())));
            tablaPracticaMedico.getColumnModel().getColumn(0).setPreferredWidth(20);
            tablaPracticaMedico.getColumnModel().getColumn(1).setPreferredWidth(20);
            tablaPracticaMedico.getColumnModel().getColumn(2).setPreferredWidth(250);
            tablaPracticaMedico.getColumnModel().getColumn(3).setPreferredWidth(20);
            tablaPracticaMedico.getColumnModel().getColumn(4).setPreferredWidth(250);
        }
    }//GEN-LAST:event_textCodigoKeyPressed

    private void textPeriodoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPeriodoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPeriodoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPracticaMedico;
    private javax.swing.JTextField textCodigo;
    private javax.swing.JTextField textPeriodo;
    // End of variables declaration//GEN-END:variables
}
