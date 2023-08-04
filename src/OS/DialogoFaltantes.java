package OS;

import Conexion.Conexion;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Reflection;
import util.SqlValorCombo;

/**
 *
 * @author lperez
 */
public class DialogoFaltantes extends javax.swing.JDialog {

    public Connection connection5;
    public Connection connection;
    public List<Map<String, Object>> lista;
    public int consulta;
    public int practica;
    public int cirugia;
    public int sello;
    public int pedido;
    public int informe;
    public int fs;
    public int total;

    public DialogoFaltantes(java.awt.Frame parent, boolean modal, Connection connection) {
        super(parent, modal);
        this.connection = connection;
        Conexion con = new Conexion();
        this.connection5 = con.GetConnection5();
        initComponents();
        this.lista = SqlValorCombo.llenarComboPor("select periodo from os_periodos where estado = 1", cbPeriodo, "periodo", connection5);
        txtCodme.requestFocus();
        txtCodme.setSelectionStart(0);
        txtCodme.setSelectionEnd(txtCodme.getText().length());
    }

    public void cargarDatosMedico() throws SQLException {
        int codme = Integer.parseInt(txtCodme.getText());
        int matricula = Integer.parseInt(txtMatricula.getText());
        int periodo = Integer.valueOf(cbPeriodo.getSelectedItem().toString());
        String sql = "SELECT * FROM os_faltantes_ipsst WHERE codme = ? AND periodo = ?";
        ArrayList array = new ArrayList();
        array.add(codme);
        array.add(periodo);
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql, array, connection5);
        if (lista.size() > 0) {
            getDatosPersonales(codme, matricula);
            consulta = Integer.valueOf(lista.get(0).get(".consulta").toString());
            practica = Integer.valueOf(lista.get(0).get(".practica").toString());
            cirugia = Integer.valueOf(lista.get(0).get(".cirugia").toString());
            sello = Integer.valueOf(lista.get(0).get(".sello").toString());
            pedido = Integer.valueOf(lista.get(0).get(".pedido").toString());
            informe = Integer.valueOf(lista.get(0).get(".informe").toString());
            fs = Integer.valueOf(lista.get(0).get(".fs").toString());
            txtPreliq.setText(lista.get(0).get(".preliq").toString());
            txtHay.setText(lista.get(0).get(".hay").toString());
            lblFaltan.setText(lista.get(0).get(".faltan").toString());
            selectItems(consulta, practica, cirugia, sello, pedido, informe, fs);
        } else {
            JOptionPane.showMessageDialog(null, "Nuevo registro.");
            getDatosPersonales(codme, matricula);
        }
    }

    public void selectItems(int consulta, int practica, int cirugia, int sello, int pedido,
            int informe, int fs) {
        if (consulta == 1) {
            rbConsultas.setSelected(true);
        }
        if (practica == 1) {
            rbPracticas.setSelected(true);
        }
        if (cirugia == 1) {
            rbCirugias.setSelected(true);
        }
        if (sello == 1) {
            this.cbSello.setSelected(true);
        }
        if (pedido == 1) {
            this.cbPedido.setSelected(true);
        }
        if (informe == 1) {
            this.cbInforme.setSelected(true);
        }
        if (fs == 1) {
            this.cbFS.setSelected(true);
        }
    }

    public void getDatosPersonales(int codme, int matricula) throws SQLException {
        if (codme > 0) {
            String sql2 = "SELECT codme, matric, nombre FROM prestadores "
                    + "WHERE codme = ? AND codme <= 75000 AND tsocio <= 4 AND entidad = 0";
            ArrayList arrayPrestador = new ArrayList();
            arrayPrestador.add(codme);
            List<Map<String, Object>> listaPrestador = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayPrestador, connection5);
            txtMatricula.setText(listaPrestador.get(0).get(".matric").toString().trim());
            lblNombre.setText(listaPrestador.get(0).get(".nombre").toString().trim());
        } else {
            String sql2 = "SELECT codme, matric, nombre FROM prestadores "
                    + "WHERE matric = ? AND codme <= 75000 AND tsocio <= 4 AND entidad = 0";
            ArrayList arrayPrestador = new ArrayList();
            arrayPrestador.add(matricula);
            List<Map<String, Object>> listaPrestador = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayPrestador, connection5);
            txtCodme.setText(listaPrestador.get(0).get(".codme").toString().trim());
            lblNombre.setText(listaPrestador.get(0).get(".nombre").toString().trim());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        rbConsultas = new javax.swing.JRadioButton();
        rbPracticas = new javax.swing.JRadioButton();
        rbCirugias = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPreliq = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtHay = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblFaltan = new javax.swing.JLabel();
        cbSello = new javax.swing.JCheckBox();
        cbPedido = new javax.swing.JCheckBox();
        cbInforme = new javax.swing.JCheckBox();
        cbFS = new javax.swing.JCheckBox();
        btnBuscar = new javax.swing.JButton();
        cbPeriodo = new javax.swing.JComboBox<>();
        btnVolver = new javax.swing.JButton();
        btnGrabar = new javax.swing.JButton();
        btnVer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Faltantes IPSST"));

        jLabel1.setText("Periodo:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("N° Socio:");

        txtCodme.setText("0");
        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        jLabel3.setText("Matrícula:");

        txtMatricula.setText("0");
        txtMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatriculaKeyPressed(evt);
            }
        });

        jLabel4.setText("Nombre:");

        lblNombre.setForeground(new java.awt.Color(51, 153, 255));

        buttonGroup1.add(rbConsultas);
        rbConsultas.setSelected(true);
        rbConsultas.setText("Consultas");
        rbConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbConsultasActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbPracticas);
        rbPracticas.setText("Prácticas");

        buttonGroup1.add(rbCirugias);
        rbCirugias.setText("Cirugías");

        jLabel5.setText("Falta:");

        jLabel6.setText("Preliq.:");

        jLabel8.setText("Hay:");

        jLabel9.setText("Faltan:");

        lblFaltan.setForeground(new java.awt.Color(255, 51, 51));

        cbSello.setText("Sello");
        cbSello.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSelloActionPerformed(evt);
            }
        });

        cbPedido.setText("Pedido");
        cbPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPedidoActionPerformed(evt);
            }
        });

        cbInforme.setText("Informe");
        cbInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInformeActionPerformed(evt);
            }
        });

        cbFS.setText("F/S");
        cbFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFSActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/minisearch.png"))); // NOI18N

        cbPeriodo.setEditable(true);
        cbPeriodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbPeriodoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbConsultas)
                                .addGap(59, 59, 59)
                                .addComponent(rbPracticas)
                                .addGap(84, 84, 84)
                                .addComponent(rbCirugias))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbSello)
                                .addGap(18, 18, 18)
                                .addComponent(cbPedido)
                                .addGap(18, 18, 18)
                                .addComponent(cbInforme)
                                .addGap(18, 18, 18)
                                .addComponent(cbFS))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPreliq, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFaltan, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(99, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCodme, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbConsultas)
                        .addComponent(rbCirugias)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rbPracticas))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSello)
                    .addComponent(cbPedido)
                    .addComponent(cbInforme)
                    .addComponent(cbFS))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFaltan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPreliq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/arrow-left_1.png"))); // NOI18N
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnGrabar.setToolTipText("Grabar");
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        btnVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ver.png"))); // NOI18N
        btnVer.setToolTipText("Ver cargas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGrabar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGrabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int codme = Integer.valueOf(txtCodme.getText());
            if (codme == 0) {
                JOptionPane.showMessageDialog(null, "Campo vacío, ingrese matrícula.", "Mensaje del Sistema", 1);
                txtMatricula.requestFocus();
            } else {
                try {
                    cargarDatosMedico();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoFaltantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void txtMatriculaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatriculaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int matricula = Integer.valueOf(txtMatricula.getText());
            if (matricula == 0) {
                JOptionPane.showMessageDialog(null, "Campo vacío, ingrese matrícula.", "Mensaje del Sistema", 1);
            } else {
                try {
                    cargarDatosMedico();
                } catch (SQLException ex) {
                    Logger.getLogger(DialogoFaltantes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtMatriculaKeyPressed

    private void cbPeriodoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPeriodoItemStateChanged

    }//GEN-LAST:event_cbPeriodoItemStateChanged

    private void cbSelloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSelloActionPerformed
        if (cbSello.isSelected()) {
            sello = 1;
        } else {
            sello = 0;
        }
    }//GEN-LAST:event_cbSelloActionPerformed

    private void cbPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPedidoActionPerformed
        if (cbPedido.isSelected()) {
            pedido = 1;
        } else {
            pedido = 0;
        }
    }//GEN-LAST:event_cbPedidoActionPerformed

    private void cbInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInformeActionPerformed
        if (cbInforme.isSelected()) {
            informe = 1;
        } else {
            informe = 0;
        }
    }//GEN-LAST:event_cbInformeActionPerformed

    private void cbFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFSActionPerformed
        if (cbFS.isSelected()) {
            fs = 1;
        } else {
            fs = 0;
        }
    }//GEN-LAST:event_cbFSActionPerformed

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        System.out.println(practica);
        System.out.println(sello);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void rbConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbConsultasActionPerformed
    }//GEN-LAST:event_rbConsultasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGrabar;
    private javax.swing.JButton btnVer;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cbFS;
    private javax.swing.JCheckBox cbInforme;
    private javax.swing.JCheckBox cbPedido;
    private javax.swing.JComboBox<String> cbPeriodo;
    private javax.swing.JCheckBox cbSello;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFaltan;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JRadioButton rbCirugias;
    private javax.swing.JRadioButton rbConsultas;
    private javax.swing.JRadioButton rbPracticas;
    private javax.swing.JTextField txtCodme;
    private javax.swing.JTextField txtHay;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtPreliq;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JCheckBox getjCheckBox1() {
        return cbSello;
    }

    public void setjCheckBox1(javax.swing.JCheckBox jCheckBox1) {
        this.cbSello = jCheckBox1;
    }

    public javax.swing.JCheckBox getjCheckBox2() {
        return cbPedido;
    }

    public void setjCheckBox2(javax.swing.JCheckBox jCheckBox2) {
        this.cbPedido = jCheckBox2;
    }

    public javax.swing.JCheckBox getjCheckBox3() {
        return cbInforme;
    }

    public void setjCheckBox3(javax.swing.JCheckBox jCheckBox3) {
        this.cbInforme = jCheckBox3;
    }

    public javax.swing.JCheckBox getjCheckBox5() {
        return cbFS;
    }

    public void setjCheckBox5(javax.swing.JCheckBox jCheckBox5) {
        this.cbFS = jCheckBox5;
    }

    public javax.swing.JLabel getLblCantidad() {
        return lblFaltan;
    }

    public void setLblCantidad(javax.swing.JLabel lblCantidad) {
        this.lblFaltan = lblCantidad;
    }

    public javax.swing.JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(javax.swing.JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public javax.swing.JRadioButton getRbCirugías() {
        return rbCirugias;
    }

    public void setRbCirugías(javax.swing.JRadioButton rbCirugías) {
        this.rbCirugias = rbCirugías;
    }

    public javax.swing.JRadioButton getRbConsultas() {
        return rbConsultas;
    }

    public void setRbConsultas(javax.swing.JRadioButton rbConsultas) {
        this.rbConsultas = rbConsultas;
    }

    public javax.swing.JRadioButton getRbPracticas() {
        return rbPracticas;
    }

    public void setRbPracticas(javax.swing.JRadioButton rbPracticas) {
        this.rbPracticas = rbPracticas;
    }

    public javax.swing.JTextField getTxtCodme() {
        return txtCodme;
    }

    public void setTxtCodme(javax.swing.JTextField txtCodme) {
        this.txtCodme = txtCodme;
    }

    public javax.swing.JTextField getTxtHay() {
        return txtHay;
    }

    public void setTxtHay(javax.swing.JTextField txtHay) {
        this.txtHay = txtHay;
    }

    public javax.swing.JTextField getTxtMatricula() {
        return txtMatricula;
    }

    public void setTxtMatricula(javax.swing.JTextField txtMatricula) {
        this.txtMatricula = txtMatricula;
    }

    public javax.swing.JTextField getTxtPreliq() {
        return txtPreliq;
    }

    public void setTxtPreliq(javax.swing.JTextField txtPreliq) {
        this.txtPreliq = txtPreliq;
    }

    public javax.swing.JComboBox<String> getCbPeriodo() {
        return cbPeriodo;
    }

    public void setCbPeriodo(javax.swing.JComboBox<String> cbPeriodo) {
        this.cbPeriodo = cbPeriodo;
    }

    public javax.swing.JCheckBox getCbFS() {
        return cbFS;
    }

    public void setCbFS(javax.swing.JCheckBox cbFS) {
        this.cbFS = cbFS;
    }

    public javax.swing.JCheckBox getCbInforme() {
        return cbInforme;
    }

    public void setCbInforme(javax.swing.JCheckBox cbInforme) {
        this.cbInforme = cbInforme;
    }

    public javax.swing.JCheckBox getCbPedido() {
        return cbPedido;
    }

    public void setCbPedido(javax.swing.JCheckBox cbPedido) {
        this.cbPedido = cbPedido;
    }

    public javax.swing.JCheckBox getCbSello() {
        return cbSello;
    }

    public void setCbSello(javax.swing.JCheckBox cbSello) {
        this.cbSello = cbSello;
    }
}
