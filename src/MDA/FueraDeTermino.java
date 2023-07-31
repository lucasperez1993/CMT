package MDA;

import Models.ModeloFtermino;
import Models.SqlMesaDeAyuda;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.cmt_presentacion_ftermino;
import util.Reflection;
import util.SqlValorCombo;
/**
 *
 * @author lperez
 */
public class FueraDeTermino extends javax.swing.JDialog {

    public Dashboard dashboard;
    public List<Map<String, Object>> lista;
    public List<Map<String, Object>> listaLlamadas;
    public Connection connection;
    int estado;
    SqlMesaDeAyuda sql;
    DialogoABMMotivos dialogo = new DialogoABMMotivos(dashboard, true);
    public List<Map<String, Object>> listaMotivos;

    public FueraDeTermino(java.awt.Frame parent, boolean modal, Connection connection) {
        super(parent, modal);
        this.connection = null;
        this.estado = 1;
        sql = new SqlMesaDeAyuda();
        initComponents();

        this.connection = connection;
        listaMotivos = SqlValorCombo.llenarComboPor("SELECT * FROM cmt_motivomesa WHERE estado = 1 ORDER BY motivo ASC", cbMotivos, "motivo", connection);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbGrupoEstado = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNSocio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        rbAtiende = new javax.swing.JRadioButton();
        rbNoAtiende = new javax.swing.JRadioButton();
        rbSinServicio = new javax.swing.JRadioButton();
        rbWP = new javax.swing.JRadioButton();
        cbMotivos = new javax.swing.JComboBox<>();
        btnGrabar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFtermino = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        btnMotivos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("N° Socio:");

        txtNSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNSocioKeyPressed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        lblNombre.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        rbGrupoEstado.add(rbAtiende);
        rbAtiende.setSelected(true);
        rbAtiende.setText("Atiende");
        rbAtiende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAtiendeActionPerformed(evt);
            }
        });

        rbGrupoEstado.add(rbNoAtiende);
        rbNoAtiende.setText("No atiende");
        rbNoAtiende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNoAtiendeActionPerformed(evt);
            }
        });

        rbGrupoEstado.add(rbSinServicio);
        rbSinServicio.setText("Fuera de Servicio");
        rbSinServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSinServicioActionPerformed(evt);
            }
        });

        rbGrupoEstado.add(rbWP);
        rbWP.setText("Enviado por WhatsApp");
        rbWP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbWPActionPerformed(evt);
            }
        });

        btnGrabar.setText("Grabar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        tblFtermino.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblFtermino);

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
                        .addComponent(txtNSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbAtiende)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbNoAtiende)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbSinServicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbWP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMotivos, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGrabar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNSocio)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMotivos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbAtiende)
                        .addComponent(rbNoAtiende)
                        .addComponent(rbSinServicio)
                        .addComponent(rbWP)
                        .addComponent(btnGrabar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnMotivos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/addc.png"))); // NOI18N
        btnMotivos.setToolTipText("Ver Motivos");
        btnMotivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotivosActionPerformed(evt);
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
                        .addComponent(btnMotivos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(btnMotivos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        if (txtNSocio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Debe completar N° de Socio.");
        } else {
            int codme = Integer.valueOf(txtNSocio.getText());
            try {
                insertLlamada(codme);
            } catch (SQLException ex) {
                Logger.getLogger(FueraDeTermino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnGrabarActionPerformed

    public void insertLlamada(int codme) throws SQLException {
        try {
            String idmotivo = listaMotivos.get(cbMotivos.getSelectedIndex()).get(".id_motivo").toString();
            String insert = "INSERT INTO cmt_presentacion_ftermino VALUES(" + codme + ", GETDATE(), " + idmotivo + ", " + estado + ")";
            connection.createStatement().execute(insert);
            JOptionPane.showMessageDialog(null, "Se agregó comunicación con prestador.");
            cargarSocio();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un problema para agregar comunicación con prestador.");
        }

    }

    private void rbAtiendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAtiendeActionPerformed
        estado = 1;
    }//GEN-LAST:event_rbAtiendeActionPerformed

    private void rbNoAtiendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNoAtiendeActionPerformed
        estado = 2;
    }//GEN-LAST:event_rbNoAtiendeActionPerformed

    private void rbSinServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSinServicioActionPerformed
        estado = 3;
    }//GEN-LAST:event_rbSinServicioActionPerformed

    public void getNombrePrestadorPorCodme(int codme, Connection connection) throws SQLException {
        String sql = "SELECT nombre FROM prestadores WHERE codme = " + codme + " AND codme <= 75000 AND tsocio<=4 AND entidad = 0";
        ArrayList listaParametro = new ArrayList();
        String nombre = "";
        try {
            lista = Reflection.getMapQueryResultByPreparedStatement(sql, listaParametro, connection);
            if (lista.size() > 0) {
                nombre = lista.get(0).get(".nombre").toString();
                lblNombre.setText(nombre);
            }
        } catch (SQLException ex) {
        }
    }

    private void txtNSocioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNSocioKeyPressed
        if (evt.getKeyCode() == 10) {
            cargarSocio();
        }
    }//GEN-LAST:event_txtNSocioKeyPressed

    public void cargarSocio() {
        int nrosocio = Integer.valueOf(txtNSocio.getText());
        int codme = 0;
        cmt_presentacion_ftermino p = new cmt_presentacion_ftermino();
        try {
            getNombrePrestadorPorCodme(nrosocio, connection);
            codme = Integer.valueOf(txtNSocio.getText());
            getEstadoLlamadas(codme);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingrese el número sin guiones ni barra.", "Mensaje del Sistema", 2);
        }
    }

    public void getEstadoLlamadas(int codme) throws SQLException {
        String query = "SELECT f.codme, f.fecha, m.motivo, f.estado FROM cmt_presentacion_ftermino f "
                + "LEFT JOIN cmt_motivomesa m "
                + "ON f.id_motivo=m.id_motivo "
                + "WHERE codme = ? ORDER BY fecha desc";
        ArrayList arrayLlamadas = new ArrayList();
        arrayLlamadas.add(codme);
        listaLlamadas = Reflection.getMapQueryResultByPreparedStatement(query, arrayLlamadas, connection);
        getTblFtermino().setModel(new ModeloFtermino(listaLlamadas, connection));
    }

    private void rbWPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbWPActionPerformed
        estado = 4;
    }//GEN-LAST:event_rbWPActionPerformed

    private void btnMotivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotivosActionPerformed
        dialogo.setVisible(true);
    }//GEN-LAST:event_btnMotivosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnMotivos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbMotivos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JRadioButton rbAtiende;
    private javax.swing.ButtonGroup rbGrupoEstado;
    private javax.swing.JRadioButton rbNoAtiende;
    private javax.swing.JRadioButton rbSinServicio;
    private javax.swing.JRadioButton rbWP;
    private javax.swing.JTable tblFtermino;
    private javax.swing.JTextField txtNSocio;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTable getjTable1() {
        return tblFtermino;
    }

    public void setjTable1(javax.swing.JTable jTable1) {
        this.tblFtermino = jTable1;
    }

    public javax.swing.JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(javax.swing.JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public javax.swing.JTextField getTxtNSocio() {
        return txtNSocio;
    }

    public void setTxtNSocio(javax.swing.JTextField txtNSocio) {
        this.txtNSocio = txtNSocio;
    }

    public javax.swing.JTable getTblFtermino() {
        return tblFtermino;
    }

    public void setTblFtermino(javax.swing.JTable tblFtermino) {
        this.tblFtermino = tblFtermino;
    }
}
