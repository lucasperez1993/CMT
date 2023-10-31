/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cirugia;

import Conexion.Conexion;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.json.JSONException;
import util.DigitoVerificador;
import static util.MaskCUIT.maskCUIT;

/**
 *
 * @author djaime
 */
public class FormCirugia extends javax.swing.JDialog {

    int matricula = 0;
    private int idusuario = 0;
    private int tipoUsuario = 0;
    ControladorCirugia controlador;
    Connection connection;
    DialogoAgregarMedico dialogo;
    int hora = 0;
    int minuto = 0;
    public java.awt.Frame parent;
    /**
     * Creates new form Carga
     */
    public FormCirugia(java.awt.Frame parent, boolean modal, int idusuario, int tipoUsuario, Connection connection) throws ParseException {
        super(parent, modal);
        this.parent = parent;
        this.connection = connection;
        this.idusuario = idusuario;
        this.tipoUsuario = tipoUsuario;
        controlador = new ControladorCirugia(this, connection);
        initComponents();
        controlador.cargarCombo();
        controlador.cargarTablaCirugiaGral();
        getTextCuil().setFocusable(true);
        getTextCuil().selectAll();
    }

    public JSpinner getShora() {
        return shora;
    }

    public void setShora(JSpinner shora) {
        this.shora = shora;
    }

    public JSpinner getSminuto() {
        return sminuto;
    }

    public void setSminuto(JSpinner sminuto) {
        this.sminuto = sminuto;
    }

    public JButton getBotonUpload() {
        return botonUpload;
    }

    public void setBotonUpload(JButton botonUpload) {
        this.botonUpload = botonUpload;
    }

    public JLabel getLabelCodme() {
        return labelCodme;
    }

    public void setLabelCodme(JLabel labelCodme) {
        this.labelCodme = labelCodme;
    }

    public JLabel getLabelMatricula() {
        return labelMatricula;
    }

    public void setLabelMatricula(JLabel labelMatricula) {
        this.labelMatricula = labelMatricula;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public JDateChooser getDateFecha() {
        return dateFecha;
    }

    public void setDateFecha(JDateChooser dateFecha) {
        this.dateFecha = dateFecha;
    }

    public JTextField getTextCuil() {
        return textCuil;
    }

    public void setTextCuil(JTextField textCuil) {
        this.textCuil = textCuil;
    }

    public JTextField getTextNombreAfiliado() {
        return textNombreAfiliado;
    }

    public void setTextNombreAfiliado(JTextField textNombre) {
        this.textNombreAfiliado = textNombre;
    }

    public JLabel getTxtNombreCirujano() {
        return txtNombreCirujano;
    }

    public void setTxtNombreCirujano(JLabel txtNombre) {
        this.txtNombreCirujano = txtNombre;
    }

    public JTable getTablaAyudante() {
        return tablaAyudante;
    }

    public void setTablaAyudante(JTable tablaAyudante) {
        this.tablaAyudante = tablaAyudante;
    }

    public JLabel getLabelFechaVTO() {
        return labelFechaVTO;
    }

    public void setLabelFechaVTO(JLabel labelFechaVTO) {
        this.labelFechaVTO = labelFechaVTO;
    }

    public JButton getBotonCrearRegistro() {
        return botonCrearRegistro;
    }

    public void setBotonCrearRegistro(JButton botonCrearRegistro) {
        this.botonCrearRegistro = botonCrearRegistro;
    }

    public JButton getBotonModificar() {
        return botonModificar;
    }

    public void setBotonModificar(JButton botonModificar) {
        this.botonModificar = botonModificar;
    }

    public JCheckBox getCheckHC() {
        return checkHC;
    }

    public void setCheckHC(JCheckBox checkHC) {
        this.checkHC = checkHC;
    }

    public JCheckBox getCheckProtocolo() {
        return checkProtocolo;
    }

    public void setCheckProtocolo(JCheckBox checkProtocolo) {
        this.checkProtocolo = checkProtocolo;
    }

    public JCheckBox getCheckOtro() {
        return checkOtro;
    }

    public void setCheckOtro(JCheckBox checkOtro) {
        this.checkOtro = checkOtro;
    }

    public JCheckBox getCheckPedido() {
        return checkPedido;
    }

    public void setCheckPedido(JCheckBox checkPedido) {
        this.checkPedido = checkPedido;
    }

    public JComboBox<String> getComboEstado() {
        return comboEstado;
    }

    public void setComboEstado(JComboBox<String> comboEstado) {
        this.comboEstado = comboEstado;
    }

    public JPanel getPanelCirugia() {
        return panelCirugia;
    }

    public void setPanelCirugia(JPanel panelCirugia) {
        this.panelCirugia = panelCirugia;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCirugia = new javax.swing.JTable();
        panelCirugia = new javax.swing.JPanel();
        txtNombreCirujano = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelCodme = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelMatricula = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaAyudante = new javax.swing.JTable();
        botonAgregarAyudante = new javax.swing.JButton();
        botonQuitarAyudante = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        comboSanatorio = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        textNombreAfiliado = new javax.swing.JTextField();
        textCuil = new javax.swing.JTextField();
        botonUpload = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelFechaVTO = new javax.swing.JLabel();
        botonCalcular = new javax.swing.JButton();
        botonCrearRegistro = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        botonPracticas = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        shora = new javax.swing.JSpinner();
        sminuto = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        botonModificar = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        checkOtro = new javax.swing.JCheckBox();
        checkHC = new javax.swing.JCheckBox();
        checkProtocolo = new javax.swing.JCheckBox();
        checkPedido = new javax.swing.JCheckBox();
        comboEstado = new javax.swing.JComboBox<>();
        botonQuitarCirujano = new javax.swing.JButton();
        botonAgregarCirujano = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        totalCirugia = new javax.swing.JLabel();
        totalAyudantia = new javax.swing.JLabel();
        totalVideo = new javax.swing.JLabel();
        totalInterconsulta = new javax.swing.JLabel();
        totalMio = new javax.swing.JLabel();
        cirugia = new javax.swing.JLabel();
        ayudantia = new javax.swing.JLabel();
        interconsulta = new javax.swing.JLabel();
        video = new javax.swing.JLabel();
        mio = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        sumaTotal = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaCirugia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaCirugia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCirugiaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCirugia);

        panelCirugia.setBackground(new java.awt.Color(255, 255, 255));
        panelCirugia.setBorder(javax.swing.BorderFactory.createTitledBorder("Cirujano"));

        txtNombreCirujano.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNombreCirujano.setForeground(new java.awt.Color(0, 51, 51));
        txtNombreCirujano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNombreCirujano.setText("-");

        jLabel4.setText("Socio:");

        labelCodme.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelCodme.setText("-");

        jLabel7.setText("Matricula:");

        labelMatricula.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelMatricula.setText("-");

        javax.swing.GroupLayout panelCirugiaLayout = new javax.swing.GroupLayout(panelCirugia);
        panelCirugia.setLayout(panelCirugiaLayout);
        panelCirugiaLayout.setHorizontalGroup(
            panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCirugiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCirugiaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCirugiaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreCirujano, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCirugiaLayout.setVerticalGroup(
            panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCirugiaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelCodme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNombreCirujano)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCirugiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(labelMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ayudante"));
        jPanel3.setForeground(new java.awt.Color(255, 255, 204));

        tablaAyudante.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tablaAyudante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaAyudante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAyudanteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaAyudante);

        botonAgregarAyudante.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonAgregarAyudante.setText("agregar");
        botonAgregarAyudante.setEnabled(false);
        botonAgregarAyudante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarAyudanteActionPerformed(evt);
            }
        });

        botonQuitarAyudante.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonQuitarAyudante.setText("Quitar");
        botonQuitarAyudante.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 161, Short.MAX_VALUE)
                        .addComponent(botonAgregarAyudante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonQuitarAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAgregarAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonQuitarAyudante, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Entidad"));

        comboSanatorio.setFocusable(false);

        jLabel2.setText("CUIL:");

        textNombreAfiliado.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        textNombreAfiliado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textNombreAfiliado.setEnabled(false);

        textCuil.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        textCuil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCuilActionPerformed(evt);
            }
        });
        textCuil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textCuilKeyPressed(evt);
            }
        });

        botonUpload.setText("...");
        botonUpload.setEnabled(false);
        botonUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonUploadActionPerformed(evt);
            }
        });

        jLabel1.setText("FECHA:");

        dateFecha.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        dateFecha.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                dateFechaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        dateFecha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateFechaMouseClicked(evt);
            }
        });
        dateFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                modificarFecha(evt);
            }
        });
        dateFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateFechaKeyPressed(evt);
            }
        });

        jLabel3.setText("Subir Documentos");

        jLabel6.setText("Medico - Entidad:");

        labelFechaVTO.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        labelFechaVTO.setText("VTO: 0000-00-00");

        botonCalcular.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonCalcular.setText("Calcular");
        botonCalcular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonCalcularMouseClicked(evt);
            }
        });
        botonCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCalcularActionPerformed(evt);
            }
        });

        botonCrearRegistro.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonCrearRegistro.setText("Crear Registro");
        botonCrearRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearRegistroActionPerformed(evt);
            }
        });

        jLabel5.setText("Agregar Practicas");

        botonPracticas.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonPracticas.setText("Ver practicas");
        botonPracticas.setEnabled(false);
        botonPracticas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPracticasActionPerformed(evt);
            }
        });

        jLabel9.setText("Hora:");

        jLabel10.setText("Minuto:");

        shora.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        shora.setMaximumSize(new java.awt.Dimension(0, 23));
        shora.setMinimumSize(new java.awt.Dimension(0, 0));
        shora.setName(""); // NOI18N

        sminuto.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboSanatorio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textCuil, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textNombreAfiliado))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelFechaVTO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonCalcular))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonCrearRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shora, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sminuto, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonPracticas)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSanatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textCuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNombreAfiliado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFechaVTO, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(shora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sminuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(botonUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(botonPracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonCrearRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        botonModificar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonModificar.setText("Modificar");
        botonModificar.setEnabled(false);
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jButton6.setText("Volver");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addComponent(botonModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(102, 153, 255));
        jPanel6.setForeground(new java.awt.Color(153, 204, 255));

        jButton1.setText("Previsualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        checkOtro.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        checkOtro.setText("OTROS");
        checkOtro.setEnabled(false);

        checkHC.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        checkHC.setText("HC");
        checkHC.setEnabled(false);

        checkProtocolo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        checkProtocolo.setText("Protocolo");
        checkProtocolo.setEnabled(false);

        checkPedido.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        checkPedido.setText("Pedido");
        checkPedido.setEnabled(false);

        comboEstado.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkProtocolo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkHC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkOtro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboEstado, 0, 172, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkPedido)
                    .addComponent(checkProtocolo)
                    .addComponent(checkHC)
                    .addComponent(checkOtro)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        botonQuitarCirujano.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonQuitarCirujano.setText("Quitar");
        botonQuitarCirujano.setEnabled(false);
        botonQuitarCirujano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonQuitarCirujanoActionPerformed(evt);
            }
        });

        botonAgregarCirujano.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        botonAgregarCirujano.setText("agregar");
        botonAgregarCirujano.setEnabled(false);
        botonAgregarCirujano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarCirujanoActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Cargas Realizadas");

        totalCirugia.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        totalCirugia.setForeground(new java.awt.Color(204, 204, 0));
        totalCirugia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalCirugia.setText("Cirugias:");

        totalAyudantia.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        totalAyudantia.setForeground(new java.awt.Color(255, 204, 255));
        totalAyudantia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalAyudantia.setText("Ayudant.:");

        totalVideo.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        totalVideo.setForeground(new java.awt.Color(153, 255, 153));
        totalVideo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalVideo.setText("Video:");

        totalInterconsulta.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        totalInterconsulta.setForeground(new java.awt.Color(51, 204, 255));
        totalInterconsulta.setText("Intercon.:");

        totalMio.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        totalMio.setForeground(new java.awt.Color(255, 204, 153));
        totalMio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalMio.setText("Mio:");

        cirugia.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        cirugia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cirugia.setText("0");

        ayudantia.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        ayudantia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ayudantia.setText("0");

        interconsulta.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        interconsulta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        interconsulta.setText("0");

        video.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        video.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        video.setText("0");

        mio.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        mio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mio.setText("0");

        jLabel19.setText("Total:");

        sumaTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumaTotal.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(totalVideo)
                                .addGap(15, 15, 15)
                                .addComponent(video, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(totalMio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalInterconsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalAyudantia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalCirugia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cirugia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ayudantia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(interconsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sumaTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalCirugia)
                    .addComponent(cirugia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalAyudantia)
                    .addComponent(ayudantia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(video))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalInterconsulta)
                    .addComponent(interconsulta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalMio)
                    .addComponent(mio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(sumaTotal))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(253, 253, 253)
                                        .addComponent(botonAgregarCirujano)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botonQuitarCirujano, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(panelCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panelCirugia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonQuitarCirujano, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonAgregarCirujano, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Practica Medico IPSST");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Path Imagen-PDF");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Reportes");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Buscar Cirugias");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botonUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonUploadActionPerformed
        SeleccionarArchivo();
    }//GEN-LAST:event_botonUploadActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void botonCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCalcularActionPerformed
    }//GEN-LAST:event_botonCalcularActionPerformed

    private void botonCrearRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearRegistroActionPerformed
        try {
            controlador.crearRegistro();
        } catch (JSONException ex) {
            Logger.getLogger(FormCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonCrearRegistroActionPerformed

    private void dateFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateFechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                controlador.calcularDiasVTO(dateFecha.getDate());
            } catch (Exception e) {}
        }
    }//GEN-LAST:event_dateFechaKeyPressed

    private void dateFechaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_dateFechaAncestorAdded
        try {
            controlador.calcularDiasVTO(dateFecha.getDate());
        } catch (Exception e) {}
    }//GEN-LAST:event_dateFechaAncestorAdded

    private void textCuilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textCuilKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(getTextCuil().getText().equals("")){
                controlador.cargarTablaCirugiaGral();
            }else{
                String maskedCUIT = maskCUIT(getTextCuil().getText());
                controlador.getCirugiaAfiliadoPorCuiL(maskedCUIT);
                getTextCuil().setText(maskedCUIT);
            }
        }
    }//GEN-LAST:event_textCuilKeyPressed

    private void botonCalcularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCalcularMouseClicked
        controlador.calcularDiasVTO(dateFecha.getDate());
    }//GEN-LAST:event_botonCalcularMouseClicked

    private void tablaCirugiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCirugiaMouseClicked
        if(controlador.cirugia == null)
            controlador.prepareObjetoCirugia(getIdusuario());
        if(evt.getClickCount() >= 2){
            controlador.initBotonesCarga(true);
        }else{
            controlador.initBotonesCarga(false);
            getTxtNombreCirujano().setText("-");
            getLabelMatricula().setText("-");
            getTxtNombreCirujano().setText("-");
            getLabelCodme().setText("-");
            getBotonUpload().setEnabled(false);
            getCheckPedido().setSelected(false);
            getCheckProtocolo().setSelected(false);
            getCheckHC().setSelected(false);
            getCheckOtro().setSelected(false);
            getPanelCirugia().setBackground(new java.awt.Color(255, 255, 255));
        }
    }//GEN-LAST:event_tablaCirugiaMouseClicked

    private void botonAgregarCirujanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarCirujanoActionPerformed
        dialogo = new DialogoAgregarMedico(parent, true, controlador, true);
        dialogo.setVisible(true);
    }//GEN-LAST:event_botonAgregarCirujanoActionPerformed

    private void botonAgregarAyudanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarAyudanteActionPerformed
        dialogo = new DialogoAgregarMedico(parent, true, controlador, false);
        dialogo.setVisible(true);
    }//GEN-LAST:event_botonAgregarAyudanteActionPerformed

    private void textCuilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCuilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCuilActionPerformed

    private void dateFechaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFechaMouseClicked
        System.out.println("Damian");
    }//GEN-LAST:event_dateFechaMouseClicked

    private void modificarFecha(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_modificarFecha
        // TODO add your handling code here:
    }//GEN-LAST:event_modificarFecha

    public JButton getBotonPracticas() {
        return botonPracticas;
    }

    public void setBotonPracticas(JButton botonPracticas) {
        this.botonPracticas = botonPracticas;
    }

    private void tablaAyudanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAyudanteMouseClicked
        if(evt.getClickCount() == 2){
            int mensaje = JOptionPane.showConfirmDialog(null,"¿Desea eliminar el Ayudante?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION);
            if(mensaje == JOptionPane.YES_OPTION){
                try {
                    controlador.eliminarAyudante();
                } catch (JSONException ex) {}
            }
        }
    }//GEN-LAST:event_tablaAyudanteMouseClicked

    private void botonQuitarCirujanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonQuitarCirujanoActionPerformed
        int mensaje = JOptionPane.showConfirmDialog(null,"¿Desea eliminar el Cirujano?", "Mensaje de confirmación", JOptionPane.YES_NO_OPTION);
        if(mensaje == JOptionPane.YES_OPTION){
            try {
                controlador.eliminarCirujano();
            } catch (JSONException ex) {}
        }
    }//GEN-LAST:event_botonQuitarCirujanoActionPerformed

    private void botonPracticasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPracticasActionPerformed
        DialogoImagenPractica dialogo = new DialogoImagenPractica(parent, true, controlador);
        dialogo.setVisible(true);
    }//GEN-LAST:event_botonPracticasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DialogoImagen dialogo = new DialogoImagen(parent, true, controlador);
        dialogo.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        controlador.modificarCirugia();
    }//GEN-LAST:event_botonModificarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DialogoPracticaMedico dialogo = new DialogoPracticaMedico(parent, true);
        dialogo.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        DialogoPathIMGPDF dialogo = new DialogoPathIMGPDF(parent, true, controlador);
        dialogo.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            Conexion conection = new Conexion();
            List<Map<String, Object>> listaCirugia = SqlCirugia.listaCirugiaParaReporte(false, idusuario, tipoUsuario, 2, connection);
            String usuario = SqlCirugia.getUsuarioPorId(idusuario, conection.GetConnection5());
            DialogoReporte dialogo = new DialogoReporte(parent, true, idusuario, usuario, tipoUsuario, listaCirugia, connection);
            dialogo.setVisible(true);
        } catch (JSONException ex) {
            Logger.getLogger(FormCirugia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DialogoBuscarCirugia dialogo = new DialogoBuscarCirugia(parent, true, controlador);
        dialogo.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    public JComboBox<String> getComboSanatorio() {
        return comboSanatorio;
    }

    public void setComboSanatorio(JComboBox<String> comboSanatorio) {
        this.comboSanatorio = comboSanatorio;
    }

    public JTable getTablaCirugia() {
        return tablaCirugia;
    }

    public void setTablaCirugia(JTable tablaCirugia) {
        this.tablaCirugia = tablaCirugia;
    }

    public JButton getBotonAgregarCirujano() {
        return botonAgregarCirujano;
    }

    public void setBotonAgregarCirujano(JButton botonAgregarCirujano) {
        this.botonAgregarCirujano = botonAgregarCirujano;
    }

    public JButton getBotonAgregarAyudante() {
        return botonAgregarAyudante;
    }

    public void setBotonAgregarAyudante(JButton botonAgregarCirujano2) {
        this.botonAgregarAyudante = botonAgregarCirujano2;
    }

    public JButton getBotonCalcular() {
        return botonCalcular;
    }

    public void setBotonCalcular(JButton botonCalcular) {
        this.botonCalcular = botonCalcular;
    }

    public JButton getBotonQuitarAyudante() {
        return botonQuitarAyudante;
    }

    public void setBotonQuitarAyudante(JButton botonQuitarAyudante) {
        this.botonQuitarAyudante = botonQuitarAyudante;
    }

    public JButton getBotonQuitarCirujano() {
        return botonQuitarCirujano;
    }

    public void setBotonQuitarCirujano(JButton botonQuitarCirujano) {
        this.botonQuitarCirujano = botonQuitarCirujano;
    }

    public JLabel getAyudantia() {
        return ayudantia;
    }

    public void setAyudantia(JLabel ayudantia) {
        this.ayudantia = ayudantia;
    }

    public JLabel getCirugia() {
        return cirugia;
    }

    public void setCirugia(JLabel cirugia) {
        this.cirugia = cirugia;
    }

    public JLabel getInterconsulta() {
        return interconsulta;
    }

    public void setInterconsulta(JLabel interconsulta) {
        this.interconsulta = interconsulta;
    }

    public JLabel getMio() {
        return mio;
    }

    public void setMio(JLabel mio) {
        this.mio = mio;
    }

    public JLabel getVideo() {
        return video;
    }

    public void setVideo(JLabel video) {
        this.video = video;
    }

    public JLabel getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(JLabel sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public void SeleccionarArchivo() {
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//        int option = fileChooser.showOpenDialog(null);
//        if (option == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            JOptionPane.showMessageDialog(null, "Archivo seleccionado: " + selectedFile.getAbsolutePath());
//        } else {
//            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario");
//        }
        controlador.descargarImagen();
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormCirugia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormCirugia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormCirugia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormCirugia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormCirugia().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ayudantia;
    private javax.swing.JButton botonAgregarAyudante;
    private javax.swing.JButton botonAgregarCirujano;
    private javax.swing.JButton botonCalcular;
    private javax.swing.JButton botonCrearRegistro;
    private javax.swing.JButton botonModificar;
    private javax.swing.JButton botonPracticas;
    private javax.swing.JButton botonQuitarAyudante;
    private javax.swing.JButton botonQuitarCirujano;
    private javax.swing.JButton botonUpload;
    private javax.swing.JCheckBox checkHC;
    private javax.swing.JCheckBox checkOtro;
    private javax.swing.JCheckBox checkPedido;
    private javax.swing.JCheckBox checkProtocolo;
    private javax.swing.JLabel cirugia;
    private javax.swing.JComboBox<String> comboEstado;
    private javax.swing.JComboBox<String> comboSanatorio;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JLabel interconsulta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCodme;
    private javax.swing.JLabel labelFechaVTO;
    private javax.swing.JLabel labelMatricula;
    private javax.swing.JLabel mio;
    private javax.swing.JPanel panelCirugia;
    private javax.swing.JSpinner shora;
    private javax.swing.JSpinner sminuto;
    private javax.swing.JLabel sumaTotal;
    private javax.swing.JTable tablaAyudante;
    private javax.swing.JTable tablaCirugia;
    private javax.swing.JTextField textCuil;
    private javax.swing.JTextField textNombreAfiliado;
    private javax.swing.JLabel totalAyudantia;
    private javax.swing.JLabel totalCirugia;
    private javax.swing.JLabel totalInterconsulta;
    private javax.swing.JLabel totalMio;
    private javax.swing.JLabel totalVideo;
    private javax.swing.JLabel txtNombreCirujano;
    private javax.swing.JLabel video;
    // End of variables declaration//GEN-END:variables
}
