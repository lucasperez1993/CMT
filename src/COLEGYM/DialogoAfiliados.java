package COLEGYM;

import Conexion.Conexion;
import Controller.VistaControlador;
import Models.ModeloGrupoFamiliar;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import util.Deuda;
import util.Reflection;

/**
 *
 * @author lperez
 */
public class DialogoAfiliados extends javax.swing.JDialog {

    VistaControlador vistaControlador = new VistaControlador();
    DashboardColegym dashboardColegym;
    public int numdoca;
    public int fichaMed;
    public boolean bandera;
    public Connection connection5;
    Conexion con = new Conexion();
    Deuda deuda = new Deuda();
    public List<Map<String, Object>> listaGrupoFamiliar;

    public DialogoAfiliados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        connection5 = con.GetConnection5();
        initComponents();
        btnBaja.hide();
        bandera = false;
        txtCodme.requestFocus();
    }

    public void cargarSocio() throws SQLException {
        int codme = Integer.valueOf(txtCodme.getText());
        if (!deuda.tieneDeuda(codme, connection5)) {
            fichaMedica();
            String sql = "SELECT p.pre_id, p.codme, p.nombre, ISNULL(e.tipo, 'No inscripto') as tipo, ISNULL(i.estado, 0) as estado, ISNULL(i.fichamed, 0) as fichamed "
                    + "FROM prestadores p "
                    + "LEFT JOIN gym_med_inscripto i ON p.codme=i.codme "
                    + "LEFT JOIN gym_estados e ON i.estado=e.estado "
                    + "WHERE p.codme = ? AND p.codme <= 75000 AND p.tsocio <= ? AND p.entidad = ?";
            ArrayList arraySocio = new ArrayList();
            arraySocio.add(codme);
            arraySocio.add(4);
            arraySocio.add(0);
            List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, arraySocio, connection5);
            if (lista.size() > 0) {
                int estado = Integer.valueOf(lista.get(0).get(".estado").toString().trim());
                fichaMed = Integer.valueOf(lista.get(0).get(".fichamed").toString().trim());
                if (fichaMed == 1) {
                    rbSi.setSelected(true);
                } else {
                    rbNo.setSelected(true);
                }
                if (estado == 1) {
                    bandera = false;
                } else if (estado == 5) {
                    bandera = false;
                    JOptionPane.showMessageDialog(null, "ESTADO: " + lista.get(0).get(".tipo").toString().trim() + "", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    bandera = true;
                }
                lblCodme.setText(txtCodme.getText());
                lblNombre.setText(lista.get(0).get(".nombre").toString().trim());
                txtNombre.setText(lista.get(0).get(".nombre").toString().trim());
                lblEstadoSocio.setText(lista.get(0).get(".tipo").toString().trim());
                cargarGrupoFamiliar(codme);
            } else {
                JOptionPane.showMessageDialog(null, "El N° de socio ingresado no existe.", "Mensaje del sistema", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El socio posee deuda. Debe regularizar su situación en Dpto. Contaduría.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void cargarGrupoFamiliar(int codme) throws SQLException {
        String sql = "SELECT A.numdoc as dni, p.pre_id,P.CODME,P.NOMBRE,RTRIM(LTRIM(A.NOMBRE)) as nombrea,A.SUBC,A.PAREN paren,p.numdoc,a.numdoc numdoca "
                + "FROM club_ti C LEFT JOIN PRESTADORES P ON C.PRE_ID=P.PRE_ID "
                + "LEFT JOIN CLUB_AD A ON C.PRE_ID=A.PRE_ID WHERE C.ESTADO=1 AND P.CODME = ? ORDER BY P.NOMBRE,a.subc";
        ArrayList arrayGrupoFamiliar = new ArrayList();
        arrayGrupoFamiliar.add(codme);
        List<Map<String, Object>> listaGrupoFamiliar = Reflection.getMapQueryResultByPreparedStatement(sql, arrayGrupoFamiliar, connection5);
        if (listaGrupoFamiliar.size() > 0) {
            lblNombre.setText(listaGrupoFamiliar.get(0).get(".NOMBRE").toString().trim());
            getTblGrupoFamiliar().setModel(new ModeloGrupoFamiliar(listaGrupoFamiliar));
            TableColumnModel columnModel = tblGrupoFamiliar.getColumnModel();
        } else {
            JOptionPane.showMessageDialog(null, "No posee datos para el Grupo Familiar.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
            TableModel newModel = new DefaultTableModel();
            tblGrupoFamiliar.setModel(newModel);
        }
    }

    public void fichaMedica() {
        if (rbSi.isSelected()) {
            fichaMed = 1;
        } else {
            fichaMed = 0;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        rbGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rbSi = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        panelEstado = new javax.swing.JPanel();
        lblEstadoSocio = new javax.swing.JLabel();
        btnBaja = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblCodme = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGrupoFamiliar = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gestión de Afiliados"));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Consultar estado de Socio"));

        jLabel1.setText("N° Socio:");

        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Formulario de inscripción"));

        jLabel2.setText("Presenta Ficha Médica:");

        rbGroup.add(rbSi);
        rbSi.setText("Si");

        rbGroup.add(rbNo);
        rbNo.setSelected(true);
        rbNo.setText("No");

        jLabel5.setText("Estado:");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        panelEstado.setBackground(new java.awt.Color(0, 153, 255));
        panelEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEstadoSocio.setForeground(new java.awt.Color(255, 255, 255));
        lblEstadoSocio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEstadoSocio, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEstadoSocio, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        btnBaja.setBackground(new java.awt.Color(255, 0, 51));
        btnBaja.setText("Dar de baja");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(rbSi)
                        .addGap(18, 18, 18)
                        .addComponent(rbNo)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbSi)
                        .addComponent(rbNo)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnBaja)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Socios", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Socio titular"));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("N° Socio:");

        lblCodme.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Nombre:");

        lblNombre.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de adherente"));

        tblGrupoFamiliar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblGrupoFamiliar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGrupoFamiliarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGrupoFamiliar);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Adherentes", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVolver)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        vistaControlador.vistaBuscarSocioColegym(dashboardColegym, true, this, connection5);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCodme.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo vacío");
            } else {
                try {
                    cargarSocio();
                    txtCodme.requestFocus();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoGrupoFamiliar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void tblGrupoFamiliarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGrupoFamiliarMouseClicked
        if (evt.getClickCount() == 2) {
            int codme = Integer.valueOf(txtCodme.getText().toString());
            int filaSeleccionada = tblGrupoFamiliar.getSelectedRow();
            int columnaSeleccionada = 1;
            int columnaSeleccionada2 = 0;
            if (filaSeleccionada != -1) {
                Object valor = tblGrupoFamiliar.getValueAt(filaSeleccionada, columnaSeleccionada);
                Object valorString = tblGrupoFamiliar.getValueAt(filaSeleccionada, columnaSeleccionada2);
                if (valor != null && valor instanceof Number && ((Number) valor).intValue() != 0) {
                    int num = ((Number) valor).intValue();
                    String nombrea = valorString.toString().trim();
                    try {
                        vistaControlador.vistaDialogoAdherente(dashboardColegym, true, nombrea, num, codme, connection5);
                    } catch (SQLException ex) {
                        Logger.getLogger(DialogoAfiliados.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El adherente debe actualizar sus datos en Despacho.", "Mensaje del sistema", 2);
                }
            } else {
                System.out.println("Ninguna fila seleccionada");
            }
        }
    }//GEN-LAST:event_tblGrupoFamiliarMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        fichaMedica();
        if (!bandera) {
            String update = "UPDATE gym_med_inscripto "
                    + "SET fichamed = " + fichaMed + ""
                    + "WHERE codme = " + txtCodme.getText() + "";
            try {
                connection5.createStatement().execute(update);
                JOptionPane.showMessageDialog(null, "Datos modificados.", "Mensaje del Sistema", JOptionPane.INFORMATION_MESSAGE);
                cargarSocio();
            } catch (SQLException ex) {
                Logger.getLogger(DialogoAfiliados.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            int resultado = JOptionPane.showConfirmDialog(null, "¿Desea inscribir al socio " + txtNombre.getText() + "?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    String insert = "INSERT INTO gym_med_inscripto "
                            + "VALUES (" + txtCodme.getText() + ", GETDATE(), 0, " + fichaMed + ", 0, 5)";
                    connection5.createStatement().execute(insert);
                    JOptionPane.showMessageDialog(null, "Médico inscripto.", "Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
                    cargarSocio();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoAfiliados.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (resultado == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "Cancelado por el usuario.", "Mensaje del Sistema", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaja;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCodme;
    private javax.swing.JLabel lblEstadoSocio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JPanel panelEstado;
    private javax.swing.ButtonGroup rbGroup;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbSi;
    private javax.swing.JTable tblGrupoFamiliar;
    public static javax.swing.JTextField txtCodme;
    public static javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTextField getTxtCodme() {
        return txtCodme;
    }

    public void setTxtCodme(javax.swing.JTextField txtCodme) {
        this.txtCodme = txtCodme;
    }

    public javax.swing.JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(javax.swing.JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public javax.swing.JLabel getLblCodme() {
        return lblCodme;
    }

    public void setLblCodme(javax.swing.JLabel lblCodme) {
        this.lblCodme = lblCodme;
    }

    public javax.swing.JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(javax.swing.JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public javax.swing.ButtonGroup getRbGroup() {
        return rbGroup;
    }

    public void setRbGroup(javax.swing.ButtonGroup rbGroup) {
        this.rbGroup = rbGroup;
    }

    public javax.swing.JRadioButton getRbNo() {
        return rbNo;
    }

    public void setRbNo(javax.swing.JRadioButton rbNo) {
        this.rbNo = rbNo;
    }

    public javax.swing.JRadioButton getRbSi() {
        return rbSi;
    }

    public void setRbSi(javax.swing.JRadioButton rbSi) {
        this.rbSi = rbSi;
    }

    public javax.swing.JTable getTblGrupoFamiliar() {
        return tblGrupoFamiliar;
    }

    public void setTblGrupoFamiliar(javax.swing.JTable tblGrupoFamiliar) {
        this.tblGrupoFamiliar = tblGrupoFamiliar;
    }

    public javax.swing.JLabel getLblEstadoSocio() {
        return lblEstadoSocio;
    }

    public void setLblEstadoSocio(javax.swing.JLabel lblEstadoSocio) {
        this.lblEstadoSocio = lblEstadoSocio;
    }

}
