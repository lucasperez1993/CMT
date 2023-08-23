package COLEGYM;

import Conexion.Conexion;
import Models.ModeloBuscar;
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
public class BuscarSocio extends javax.swing.JDialog {

    public Connection connection = null;
    public DashboardColegym dashboard;
    public DialogoAfiliados dialogo;
    public int codme;
    public String condicion;
    public int bandera;
    public List<Map<String, Object>> listaMedicos;

    public BuscarSocio(java.awt.Frame parent, boolean modal, DialogoAfiliados dialogo, Connection connection) {
        super(parent, modal);
        codme = 0;
        condicion = "";
        initComponents();
        txtBuscar.requestFocus();
        this.connection = connection;
        this.dialogo = dialogo;
    }

    public void Condicion() {
        if (rbNombre.isSelected()) {
            bandera = 0;
            condicion = "nombre";
        }
        if (rbMatricula.isSelected()) {
            bandera = 0;
            condicion = "matric";
        }
    }

    public void buscar() throws SQLException {
        Conexion con = new Conexion();
        if (txtBuscar.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo vacío");
            txtBuscar.requestFocus();
        } else {
            try {
                Condicion();
                if (bandera == 0) {
                    String query = "SELECT codme, nombre FROM prestadores WHERE tsocio<=4 AND " + condicion + " LIKE '%" + txtBuscar.getText() + "%'";
                    ArrayList arrayBuscar = new ArrayList();
                    listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayBuscar, con.GetConnectionCloud());
                    if (listaMedicos.size() > 0) {
                        getTblBuscar().setModel(new ModeloBuscar(listaMedicos));
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe.");
                    }
                } else if (bandera == 1) {
                    String query = "SELECT p.codme, p.nombre FROM prestadores p"
                            + " LEFT JOIN especialidades e ON p.espe1 = e.esp_id OR p.espe2 = e.esp_id OR p.espe3 = e.esp_id"
                            + " WHERE e.nombre LIKE '%" + txtBuscar.getText() + "%'";
                    ArrayList arrayBuscar = new ArrayList();
                    listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayBuscar, con.GetConnectionCloud());
                    if (listaMedicos.size() > 0) {
                        getTblBuscar().setModel(new ModeloBuscar(listaMedicos));
                    } else {
                        JOptionPane.showMessageDialog(null, "No existe.");
                    }
                } else {
                    try {
                        Condicion();
                        String query = "SELECT * FROM cmt_cargador WHERE " + condicion + " LIKE '%" + txtBuscar.getText() + "%'";
                        ArrayList arrayBuscar = new ArrayList();
                        listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayBuscar, con.GetConnectionCloud());
                        if (listaMedicos.size() > 0) {
                            getTblBuscar().setModel(new ModeloBuscar(listaMedicos));
                        } else {
                            JOptionPane.showMessageDialog(null, "No existe.");
                        }
                    } catch (SQLException ex) {
                    }
                }
            } catch (SQLException ex) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rbNombre = new javax.swing.JRadioButton();
        rbMatricula = new javax.swing.JRadioButton();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuscar = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Socio / Cargador");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar por:");

        buttonGroup2.add(rbNombre);
        rbNombre.setSelected(true);
        rbNombre.setText("Nombre");

        buttonGroup2.add(rbMatricula);
        rbMatricula.setText("Matrícula");

        txtBuscar.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        tblBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBuscarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBuscar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBuscar)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(rbNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbMatricula)
                .addGap(96, 96, 96))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbNombre)
                    .addComponent(rbMatricula))
                .addGap(18, 18, 18)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnVolver.setToolTipText("Volver");
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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == 10) {
            try {
                buscar();
            } catch (SQLException ex) {
                Logger.getLogger(BuscarSocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void tblBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBuscarMouseClicked
        if (evt.getClickCount() == 2) {
            codme = Integer.valueOf(listaMedicos.get(tblBuscar.getSelectedRow()).get(".codme").toString());
            String nombre = listaMedicos.get(tblBuscar.getSelectedRow()).get(".nombre").toString();
            dialogo.txtCodme.setText(codme + "");
            dialogo.txtNombre.setText(nombre.trim());
            dispose();
        }
    }//GEN-LAST:event_tblBuscarMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbMatricula;
    private javax.swing.JRadioButton rbNombre;
    private javax.swing.JTable tblBuscar;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables


    public javax.swing.JRadioButton getRbMatricula() {
        return rbMatricula;
    }

    public void setRbMatricula(javax.swing.JRadioButton rbMatricula) {
        this.rbMatricula = rbMatricula;
    }

    public javax.swing.JRadioButton getRbNombre() {
        return rbNombre;
    }

    public void setRbNombre(javax.swing.JRadioButton rbNombre) {
        this.rbNombre = rbNombre;
    }

    public javax.swing.JTable getTblBuscar() {
        return tblBuscar;
    }

    public void setTblBuscar(javax.swing.JTable tblBuscar) {
        this.tblBuscar = tblBuscar;
    }

    public javax.swing.JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(javax.swing.JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }
}
