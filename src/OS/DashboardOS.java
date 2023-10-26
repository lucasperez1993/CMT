package OS;

import Conexion.Conexion;
import Controller.VistaControlador;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.Cargador;
import objeto.cmt_configuracion;
import objeto.jdb_permiso;
import org.json.JSONException;
import org.json.JSONObject;
import otro.Constante;
import util.CallService;
import util.MD5;
import util.Reflection;

/**
 *
 * @author lperez
 */
public class DashboardOS extends javax.swing.JFrame {

    public Connection connection = null;
    public JSONObject permisoJson;
    public int codme;
    public int matric;
    Cargador objetoCargador;
    Conexion con = new Conexion();
    VistaControlador vistaControlador = new VistaControlador();

    public DashboardOS(JSONObject permisoJson, Connection connection) {
        this.connection = con.GetConnection5();
        this.permisoJson = permisoJson;
        initComponents();
    }

    public void borrarDatos() {
        txtMatricula.setText("");
        txtNombre.setText("");
        txtCUIL.setText("");
        txtFecha.setText("");
        txtCel.setText("");
        txtMail.setText("");
        txtCodme.setText("");
        btnFaltantes.setEnabled(false);
        txtEspe.setText("");
        txtCodme.requestFocus();
    }

    public void cargarDatosPorCodme() throws SQLException, JSONException, ParseException, Exception {
        codme = Integer.parseInt(txtCodme.getText());
        String sql = "SELECT codme, matric, gannro, nombre, CONVERT(DATE, fchnac) as fechan, tecel, mail "
                + "FROM prestadores WHERE codme <= 75000 AND entidad = 0 and tsocio <= 4 and codme = ?";
        ArrayList array = new ArrayList();
        array.add(codme);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, array, connection);
        if (lista.size() > 0) {
            txtMatricula.setText(lista.get(0).get(".matric").toString().trim());
            txtNombre.setText(lista.get(0).get(".nombre").toString().trim());
            txtCUIL.setText(lista.get(0).get(".gannro").toString().trim().replaceAll("-", ""));
            txtFecha.setText(lista.get(0).get(".fechan").toString().trim());
            txtCel.setText(lista.get(0).get(".tecel").toString().trim());
            txtMail.setText(lista.get(0).get(".mail").toString().trim());
            btnFaltantes.setEnabled(jdb_permiso.getPermisoPorDelegacion(this.permisoJson, Constante.PERMISO_FALTANTES_IPSST, connection));
            getEspecialidad(codme);
        }
    }

    public void cargarDatosPorMatric() throws SQLException, JSONException, ParseException, Exception {
        matric = Integer.parseInt(txtMatricula.getText());
        String sql = "SELECT codme, matric, gannro, nombre, CONVERT(DATE, fchnac) as fechan, tecel, mail "
                + "FROM prestadores WHERE codme <= 75000 AND entidad = 0 and tsocio <= 4 and matric = ?";
        ArrayList array = new ArrayList();
        array.add(matric);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, array, connection);
        if (lista.size() > 0) {
            txtCodme.setText(lista.get(0).get(".codme").toString().trim());
            txtNombre.setText(lista.get(0).get(".nombre").toString().trim());
            txtCUIL.setText(lista.get(0).get(".gannro").toString().trim().replaceAll("-", ""));
            txtFecha.setText(lista.get(0).get(".fechan").toString().trim());
            txtCel.setText(lista.get(0).get(".tecel").toString().trim());
            txtMail.setText(lista.get(0).get(".mail").toString().trim());
            btnFaltantes.setEnabled(jdb_permiso.getPermisoPorDelegacion(this.permisoJson, Constante.PERMISO_FALTANTES_IPSST, connection));
            getEspecialidad(codme);
        }
    }

    public void getEspecialidad(int codme) throws Exception {
        String _token = cmt_configuracion.getToken(27, connection);
        String _md5 = MD5.getEncodedString(codme + _token);
        String url = "http://138.219.43.212:8084/sprestador/servicio/consulta/getDatosPersonales/" + codme + "/" + _md5;
        JSONObject jsonResult = CallService.getServicioJson(url, connection);
        if (jsonResult.length() > 0) {
            txtEspe.setText(jsonResult.getString("especialidad"));
            //tblEspe.setModel(new ModeloDomEspe(jsonResult.getJSONArray("domproJson")));
        } else {
            txtEspe.setText("MEDICO");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtEspe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCUIL = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnFaltantes = new javax.swing.JButton();
        btnRecepcion = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnBorrar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info Socio"));

        jLabel5.setText("Especialidad:");

        txtEspe.setEditable(false);
        txtEspe.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Mail:");

        txtMail.setEditable(false);
        txtMail.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("CUIL:");

        txtCel.setEditable(false);
        txtCel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Fec. Nac.:");

        txtCUIL.setEditable(false);
        txtCUIL.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Cel:");

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Nombre:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEspe, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMail)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEspe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCUIL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/offlog.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logo Colegio chico.png"))); // NOI18N

        btnFaltantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/faltant.png"))); // NOI18N
        btnFaltantes.setToolTipText("Faltantes IPSST");
        btnFaltantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaltantesActionPerformed(evt);
            }
        });

        btnRecepcion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/campana-de-recepcion.png"))); // NOI18N
        btnRecepcion.setToolTipText("Control de Recepción");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRecepcion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFaltantes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnFaltantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRecepcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Socio:");

        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        jLabel3.setText("Matrícula:");

        txtMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatriculaKeyPressed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(255, 51, 51));
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar Datos");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minisearch.png"))); // NOI18N
        jButton1.setToolTipText("Buscar Socio");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCodme.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío, ingrese matrícula.");
                txtMatricula.requestFocus();
            } else {
                try {
                    cargarDatosPorCodme();
                } catch (JSONException ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        borrarDatos();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtMatriculaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatriculaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtMatricula.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío, ingrese número de socio.");
                txtCodme.requestFocus();
            } else {
                try {
                    cargarDatosPorMatric();
                } catch (JSONException ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DashboardOS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtMatriculaKeyPressed

    private void btnFaltantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaltantesActionPerformed
        vistaControlador.vistaFaltantesIPSST(this, true, connection);
    }//GEN-LAST:event_btnFaltantesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnFaltantes;
    private javax.swing.JButton btnRecepcion;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtCUIL;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCodme;
    private javax.swing.JTextField txtEspe;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTextField getTxtCUIL() {
        return txtCUIL;
    }

    public void setTxtCUIL(javax.swing.JTextField txtCUIL) {
        this.txtCUIL = txtCUIL;
    }

    public javax.swing.JTextField getTxtCel() {
        return txtCel;
    }

    public void setTxtCel(javax.swing.JTextField txtCel) {
        this.txtCel = txtCel;
    }

    public javax.swing.JTextField getTxtCodme() {
        return txtCodme;
    }

    public void setTxtCodme(javax.swing.JTextField txtCodme) {
        this.txtCodme = txtCodme;
    }

    public javax.swing.JTextField getTxtEspe() {
        return txtEspe;
    }

    public void setTxtEspe(javax.swing.JTextField txtEspe) {
        this.txtEspe = txtEspe;
    }

    public javax.swing.JTextField getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(javax.swing.JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public javax.swing.JTextField getTxtMail() {
        return txtMail;
    }

    public void setTxtMail(javax.swing.JTextField txtMail) {
        this.txtMail = txtMail;
    }

    public javax.swing.JTextField getTxtMatricula() {
        return txtMatricula;
    }

    public void setTxtMatricula(javax.swing.JTextField txtMatricula) {
        this.txtMatricula = txtMatricula;
    }

    public javax.swing.JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(javax.swing.JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
}
