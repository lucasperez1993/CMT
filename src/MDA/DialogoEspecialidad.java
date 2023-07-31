package MDA;

import Models.ModeloDomEspe;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objeto.Cargador;
import objeto.cmt_configuracion;
import org.json.JSONObject;
import util.CallService;
import util.MD5;
/**
 *
 * @author lperez
 */
public class DialogoEspecialidad extends javax.swing.JDialog {
    public Connection connection;
    public int codme;
    Dashboard dashboard;
    Cargador objetoCargador;
    public List<Map<String, Object>> listaMedicos;
    
    public DialogoEspecialidad(java.awt.Frame parent, boolean modal, Dashboard dashboard, Connection connection) {
        super(parent, modal);
        this.connection = null;
        codme = 0;
        try {
            initComponents();
            this.connection = connection;
            this.dashboard = dashboard;
            cargarTabla();
        }
        catch (Exception ex) {
            Logger.getLogger(DialogoEspecialidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarTabla() throws Exception {
        String _token = cmt_configuracion.getToken(27, connection);
        String _md5 = MD5.getEncodedString(dashboard.codme + _token);
        String url = "http://138.219.43.212:8084/sprestador/servicio/consulta/getDatosPersonales/" + dashboard.codme + "/" + _md5;
        JSONObject jsonResult = CallService.getServicioJson(url, connection);
        if(jsonResult.length() > 0){
            lblEspecialidad.setText(jsonResult.getString("especialidad"));
            tblEspe.setModel(new ModeloDomEspe(jsonResult.getJSONArray("domproJson")));
        }else{
            lblEspecialidad.setText("MEDICO");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblEspecialidad = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEspe = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblEspecialidad.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEspecialidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tblEspe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblEspe);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(lblEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JTable tblEspe;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JLabel getLblEspecialidad() {
        return lblEspecialidad;
    }

    public void setLblEspecialidad(javax.swing.JLabel lblEspecialidad) {
        this.lblEspecialidad = lblEspecialidad;
    }

    public javax.swing.JTable getTblEspe() {
        return tblEspe;
    }

    public void setTblEspe(javax.swing.JTable tblEspe) {
        this.tblEspe = tblEspe;
    }
}
