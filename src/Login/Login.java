package Login;

import Conexion.Conexion;
import Controller.VistaControlador;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objeto.jdb_permiso;
import org.json.JSONException;
import org.json.JSONObject;
import otro.Constante;
/**
 *
 * @author lperez
 */
public class Login extends javax.swing.JFrame {

    Connection connection;
    public static String username;
    public static String user;
    public static int tipo;
    public static String permisoJson;
    VistaControlador controlador = new VistaControlador();
    
    public Login() {
        final Conexion conn = new Conexion();
        this.connection = conn.GetConnection5();
        initComponents();
    }

    public void esUsuario() throws SQLException, JSONException {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM cmt_usuarios_java WHERE usuario = ? AND clave = ?");
            ps.setObject(1, txtUser.getText());
            ps.setObject(2, txtClave.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idusuario = rs.getInt(1);
                username = rs.getString(2);
                user = rs.getString(3);
                tipo = rs.getInt(5);
                permisoJson = rs.getString(6);
                JSONObject jsonPermiso = new JSONObject(permisoJson);
                this.dispose();
                if(jdb_permiso.getPermisoPorDelegacion(jsonPermiso, Constante.PERMISO_ADMINISTRADOR_CMT, connection)){
                    controlador.vistaAdmin(idusuario, tipo, jsonPermiso, this, connection);
                }
                else if(jdb_permiso.getPermisoPorDelegacion(jsonPermiso, Constante.PERMISO_MDA_CMT, connection)){
                    controlador.vistaDashboard(idusuario, tipo, jsonPermiso);
                }
                else if(jdb_permiso.getPermisoPorDelegacion(jsonPermiso, Constante.PERMISO_OS_CMT, connection)){
                    controlador.vistaDashboardOS(jsonPermiso, connection);
                }
                else if(jdb_permiso.getPermisoPorDelegacion(jsonPermiso, Constante.PERMISO_COLEGYM_CMT, connection)){
                    controlador.vistaDashboarColegym(jsonPermiso);
                }
                else{
                    JOptionPane.showMessageDialog(null, "No posee permisos para realizar esta acción.", "Mensaje del Sistema", 0);
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "!Usuario/Clave incorrecto!", "Mensaje del sistema", 0);
            }
        } catch (SQLServerException ex) {
            JOptionPane.showMessageDialog(null, "Compruebe su conexión a Internet");
        } catch (Exception ex2) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        imgLogo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        IMGuSER = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblUser = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        lblClave = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ingresar a CMT");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        imgLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_cmt_blanco_250.png"))); // NOI18N
        imgLogo.setMaximumSize(new java.awt.Dimension(523, 185));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblTitulo.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 153, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("INICIAR SESIÓN");

        IMGuSER.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        IMGuSER.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N

        txtUser.setForeground(new java.awt.Color(51, 153, 255));
        txtUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        lblUser.setForeground(new java.awt.Color(0, 0, 0));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("Usuario");

        btnIngresar.setForeground(new java.awt.Color(51, 153, 255));
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        btnIngresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnIngresarKeyPressed(evt);
            }
        });

        lblClave.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        lblClave.setForeground(new java.awt.Color(0, 0, 0));
        lblClave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClave.setText("Clave");

        txtClave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtClave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IMGuSER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUser)
                                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblClave, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(txtClave))
                                .addGap(57, 57, 57))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(117, Short.MAX_VALUE)
                        .addComponent(btnIngresar)
                        .addGap(105, 105, 105)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(IMGuSER)
                .addGap(26, 26, 26)
                .addComponent(lblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblClave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIngresar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
    }//GEN-LAST:event_txtUserActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        try {
            esUsuario();
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío");
            } else {
                txtClave.requestFocus();
            }
        }
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtClave.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío");
            } else {
                btnIngresar.requestFocus();
            }
        }
    }//GEN-LAST:event_txtClaveKeyPressed

    private void btnIngresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnIngresarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtUser.getText().equals("") && txtClave.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío");
            } else {
                try {
                    esUsuario();
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnIngresarKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IMGuSER;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel imgLogo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JPasswordField getTxtClave() {
        return txtClave;
    }

    public void setTxtClave(javax.swing.JPasswordField txtClave) {
        this.txtClave = txtClave;
    }

    public javax.swing.JTextField getTxtUser() {
        return txtUser;
    }

    public void setTxtUser(javax.swing.JTextField txtUser) {
        this.txtUser = txtUser;
    }
}
