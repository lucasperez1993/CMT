package MDA;

import Conexion.Conexion;
import Controller.ControladorMesaAyuda;
import Controller.VistaControlador;
import Login.Login;
import Models.ModeloMesa;
import Models.SqlMesaDeAyuda;
import cirugia.FormCirugia;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import objeto.Cargador;
import objeto.Prestadores;
import objeto.jdb_permiso;
import org.json.JSONException;
import org.json.JSONObject;
import otro.Constante;
import util.Reflection;
/**
 *
 * @author lperez
 */
public class Dashboard extends javax.swing.JFrame {

    public List<Map<String, Object>> listaMedicos;
    public List<Map<String, Object>> usuarios;
    public Connection connection;
    public Connection connection5;
    public String idcargador;
    public int codme;
    public int id_cargador;
    public Date javaDate;
    private int idusuario;
    private int tipoUsuario;
    public String user;
    public static boolean defaultColor;
    public boolean esFaltante;
    public int EstadoId;
    public String NroSocio;
    boolean contieneClaveGestion;
    Cargador objetoCargador;
    ControladorMesaAyuda controlador = null;
    VistaControlador vistaControlador = new VistaControlador();
    DialogoConsulta dialogoconsulta;
//    Cambios cambios;
//    CentroMedico cenmed;
    Dashboard dashboard;
    private FueraDeTermino fTermino;
    Login login;
    SqlMesaDeAyuda mesa;
    Excel excel;
    JSONObject permisoJson;
    public boolean existe;

    public Dashboard(int idusuario, int tipoUsuario, JSONObject permisoJson) throws JSONException, ParseException {
        initComponents();
        this.idusuario = idusuario;
        this.tipoUsuario = tipoUsuario;
        btnDesbloquear.hide();
        btnEstado.hide();
        itemGuiamed.setEnabled(false);
        this.permisoJson = permisoJson;
        crearCarpetaMesa();
        habilitarBotones();
        Conexion con = new Conexion();
        connection = con.GetConnectionCloud();
        btnSendCMT.setEnabled(false);
        btnSendIPSST.setEnabled(false);
        btnCambioClave.setEnabled(false);
        lblUser.setText(Login.username);
        this.dashboard = dashboard;
        lblCantidad.setVisible(false);
        btnAlta.setVisible(false);
    }
    
    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    private void habilitarBotones() throws JSONException, ParseException {
        btnCambioClave.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_MODIFICAR_CLAVE, connection));
        btnDelegaciones.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_DELEGACION, connection));
        btnSendCMT.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_ENVIAR_MAIL_CMT, connection));
        btnSendIPSST.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_ENVIAR_MAIL_IPSST, connection));
        btnControl.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_CONTROL_TODOS, connection));
        btnConsulta.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_CREAR_CONSULTA, connection));
    }

    public void estaBloqueado() throws SQLException {
        NroSocio = txtCodme.getText();
        String sql1 = "SELECT EstadoId FROM Usuario WHERE NroSocio = '" + NroSocio + "' AND EstadoId = 0";
        ArrayList arrayNom = new ArrayList();
        List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql1, arrayNom, connection);
        if (lista.size() > 0) {
            btnDesbloquear.show();
            btnDesbloquear.setEnabled(true);
        } else {
            btnAlta.setVisible(!Prestadores.existeSocio(Integer.valueOf(txtCodme.getText()), connection));
        }
    }

    public void cargarDatos() throws SQLException, JSONException, ParseException {
        controlador = null;
        habilitarBotones();
        codme = Integer.parseInt(txtCodme.getText());
        objetoCargador = new Cargador();
        int c = 0;
        if (codme < 100000) {
            String sql1 = "SELECT p.tsocio, p.nombre, p.gannro, p.mail, p.tecel, p.matric, cl.clave, i.clave AS claveipsst "
                    + "FROM prestadores p "
                    + "LEFT JOIN cmt_clave cl ON p.codme = cl.codme "
                    + "LEFT JOIN cmt_claveipsst i ON p.codme = i.codme "
                    + "WHERE p.codme = ? AND tsocio in (1,2,3,4,5,6,9)";
            ArrayList arrayNom = new ArrayList();
            arrayNom.add(codme);
            List<Map<String, Object>> lista = Reflection.getMapQueryResultByPreparedStatement(sql1, arrayNom, connection);
            if (lista.size() > 0) {
                btnEspe.setEnabled(true);
                lblCantidad.setVisible(true);
                btnCambioClave.setEnabled(true);
                btnEspe.setEnabled(true);
                try {
                    this.btnSendCMT.setEnabled(jdb_permiso.getPermisoPorDelegacion(this.permisoJson, Constante.PERMISO_ENVIAR_MAIL_CMT, this.connection));
                    this.btnSendIPSST.setEnabled(jdb_permiso.getPermisoPorDelegacion(this.permisoJson, Constante.PERMISO_ENVIAR_MAIL_IPSST, this.connection));
                } catch (ParseException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                int tsocio = -1;
                try {
                    tsocio = Integer.valueOf(lista.get(0).get(".tsocio").toString());
                } catch (Exception ex2) {
                }
                getEstadoSocioPorTsocio(tsocio);
                controlador = new ControladorMesaAyuda(codme, this, connection);
                contieneClaveGestion = controlador.esUsuarioWeb();
                btnSendCMT.setEnabled(true);
                btnSendIPSST.setEnabled(true);
                objetoCargador.codme = codme;
                objetoCargador.nombre = lista.get(0).get(".nombre").toString().trim();
                objetoCargador.dni = lista.get(0).get(".gannro").toString().trim().replaceAll("-", "");
                objetoCargador.mail = lista.get(0).get(".mail").toString().trim();
                objetoCargador.telefono = lista.get(0).get(".tecel").toString().trim();
                objetoCargador.matricula = lista.get(0).get(".matric").toString().trim();
                try {
                    objetoCargador.claveIpsst = lista.get(0).get(".claveipsst").toString().trim();
                } catch (Exception e) {
                    objetoCargador.claveIpsst = "SIN CLAVE";
                }
                if (contieneClaveGestion) {
                    objetoCargador.claveGestion = lista.get(0).get(".clave").toString().trim();
                    txtClaveCMT.setText(objetoCargador.claveGestion);
                } else {
                    txtClaveCMT.setText(otro.Constante.SIN_CLAVE);
                }
                txtNombre.setText(objetoCargador.nombre);
                txtCUIT.setText(objetoCargador.dni);
                txtMail.setText(objetoCargador.mail);
                txtTelefono.setText(objetoCargador.telefono);
                txtMatricula.setText(objetoCargador.matricula);
                txtClaveCMT.setText(objetoCargador.claveGestion);
                txtClaveIPSST.setText(objetoCargador.claveIpsst);
                itemGuiamed.setEnabled(jdb_permiso.getPermisoPorDelegacion(permisoJson, Constante.PERMISO_AGREGAR_GUIAMED_CMT, connection));
                btnSendCMT.setVisible(true);
                datosDelegado();
            } else {
                btnEspe.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No existe el socio", "Mensaje del sistema", JOptionPane.WARNING_MESSAGE);
                existe = false;
            }
        } else {
            String sql2 = "SELECT c.nombre, c.dni, c.mail, c.telefono, c.idcargador, cl.clave "
                    + "FROM cmt_cargador c LEFT JOIN cmt_clave cl ON c.codme = cl.codme "
                    + "WHERE c.codme = ?";
            ArrayList arrayCargador = new ArrayList();
            arrayCargador.add(codme);
            List<Map<String, Object>> listaCargador = Reflection.getMapQueryResultByPreparedStatement(sql2, arrayCargador, connection);
            if (listaCargador.size() > 0) {
                btnCambioClave.setEnabled(true);
                lblCantidad.setVisible(true);
                lblCantidad.setVisible(true);
                btnEspe.setEnabled(false);
                btnSendCMT.setEnabled(false);
                btnSendIPSST.setEnabled(false);
                for (int indice = 0; indice < listaCargador.size(); indice++) {
                    objetoCargador.codme = codme;
                    objetoCargador.nombre = listaCargador.get(indice).get(".nombre").toString().trim();
                    objetoCargador.dni = listaCargador.get(indice).get(".dni").toString().trim();
                    objetoCargador.mail = listaCargador.get(indice).get(".mail").toString().trim();
                    objetoCargador.telefono = listaCargador.get(indice).get(".telefono").toString().trim();
                    objetoCargador.id_cargador = listaCargador.get(indice).get(".idcargador").toString().trim();
                    objetoCargador.claveGestion = listaCargador.get(indice).get(".clave").toString().trim();
                    txtNombre.setText(objetoCargador.nombre);
                    txtCUIT.setText(objetoCargador.dni);
                    txtMail.setText(objetoCargador.mail);
                    txtTelefono.setText(objetoCargador.telefono);
                    txtMatricula.setText("-");
                    txtClaveCMT.setText(objetoCargador.claveGestion);
                    txtClaveIPSST.setText("-");
                }
            }
            datosMedico();
            c = listaMedicos.size();
            lblCantidad.setText(String.valueOf(c));
        }
    }

    public void datosMedico() {
        final String query = "SELECT p.codme, p.nombre, p.gannro, p.tecel FROM prestadores p "
                + "INNER JOIN cmt_medicocargador m ON p.codme=m.codme WHERE m.idcargador = " + this.objetoCargador.id_cargador + " AND m.estado = 1";
        try {
            ArrayList arrayMed = new ArrayList();
            listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayMed, this.connection);
            getTblCargadores().setModel(new ModeloMesa(this.listaMedicos));
            lblCantidad.setText("" + this.listaMedicos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void datosDelegado() {
        codme = Integer.parseInt(txtCodme.getText());
        int c = 0;
        try {
            String query = "SELECT c.codme, c.nombre, c.dni, c.telefono "
                    + "FROM cmt_cargador c "
                    + "LEFT JOIN cmt_medicocargador m ON c.idcargador = m.idcargador "
                    + "WHERE m.codme = ? AND m.estado = ?";
            ArrayList arrayDel = new ArrayList();
            arrayDel.add(codme);
            arrayDel.add(1);
            listaMedicos = Reflection.getMapQueryResultByPreparedStatement(query, arrayDel, connection);
            getTblCargadores().setModel(new ModeloMesa(listaMedicos));
            c = listaMedicos.size();
            lblCantidad.setText(String.valueOf(c));
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tieneWhatsApp() throws SQLException {
        if (this.connection5 == null) {
            final Conexion con = new Conexion();
            this.connection5 = con.GetConnection5();
        }
        int estado = 0;
        String wp = "SELECT codme, estado FROM whatsapp WHERE codme = " + Integer.valueOf(this.txtCodme.getText()) + "";
        ArrayList arrayWP = new ArrayList();
        List<Map<String, Object>> listaWP = Reflection.getMapQueryResultByPreparedStatement(wp, arrayWP, this.connection5);
        if (listaWP.size() > 0) {
            estado = Integer.valueOf(listaWP.get(0).get(".estado").toString());
            if (estado == 0) {
                lblWp.setText("NO");
                lblWp.setForeground(new java.awt.Color(255, 0, 0));
            } else {
                lblWp.setText("SI");
                lblWp.setForeground(new java.awt.Color(255, 255, 255));
            }
        }
    }

    public void getEstadoSocioPorTsocio(int tsocio) throws SQLException {
        String estado = "-";
        switch (tsocio) {
            case 1: {
                estado = "ACTIVO";
                btnEstado.setBackground(new java.awt.Color(0, 153, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 2: {
                estado = "VITALICIO";
                btnEstado.setBackground(new java.awt.Color(0, 51, 255));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 3: {
                estado = "ACTIVO NO FACT.";
                btnEstado.setBackground(new java.awt.Color(204, 204, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 4: {
                estado = "SUSP. TRANS.";
                btnEstado.setBackground(new java.awt.Color(204, 204, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 5: {
                estado = "RENUNCIA";
                btnEstado.setBackground(new java.awt.Color(255, 102, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 6: {
                estado = "FALLECIDO";
                btnEstado.setBackground(new java.awt.Color(0, 0, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
            case 9: {
                estado = "BAJA";
                btnEstado.setBackground(new java.awt.Color(204, 0, 0));
                btnEstado.setForeground(new java.awt.Color(255, 255, 255));
                btnEstado.setText(estado);
                btnEstado.setVisible(true);
                break;
            }
        }
        tieneWhatsApp();
    }

    public void crearCarpetaMesa() {
        File directorio = new File("C:/Mesa de Ayuda/");
        try {
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al crear directorio");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodme = new javax.swing.JTextField();
        btnBorrar = new javax.swing.JButton();
        txtTelefono = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtClaveIPSST = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnSendIPSST = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtClaveCMT = new javax.swing.JTextField();
        btnSendCMT = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCUIT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        btnEspe = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblWp = new javax.swing.JLabel();
        btnDesbloquear = new javax.swing.JButton();
        btnEstado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCargadores = new javax.swing.JTable();
        btnAlta = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnReiniciar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnControl = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnDelegaciones = new javax.swing.JButton();
        btnCambioClave = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemGuiamed = new javax.swing.JMenuItem();
        itemComunicar = new javax.swing.JMenuItem();
        itemAltaCenmed = new javax.swing.JMenuItem();
        itemMigrarResumen = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mesa de Ayuda Prestacional");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Matrícula:");

        txtCodme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodmeKeyPressed(evt);
            }
        });

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        txtTelefono.setEditable(false);
        txtTelefono.setToolTipText("");

        jLabel2.setText("CUIT:");

        txtClaveIPSST.setEditable(false);
        txtClaveIPSST.setToolTipText("");

        jLabel3.setText("Clave IPSST:");

        btnSendIPSST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mail.png"))); // NOI18N
        btnSendIPSST.setToolTipText("Enviar Clave IPSST");
        btnSendIPSST.setEnabled(false);
        btnSendIPSST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendIPSSTActionPerformed(evt);
            }
        });

        jLabel4.setText("ID Usuario:");

        txtNombre.setEditable(false);

        jLabel5.setText("Mail:");

        txtMail.setEditable(false);

        jLabel6.setText("Clave CMT:");

        txtClaveCMT.setEditable(false);

        btnSendCMT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mail.png"))); // NOI18N
        btnSendCMT.setToolTipText("Enviar Clave CMT");
        btnSendCMT.setEnabled(false);
        btnSendCMT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendCMTActionPerformed(evt);
            }
        });

        jLabel7.setText("Nombre:");

        txtCUIT.setEditable(false);
        txtCUIT.setToolTipText("");

        jLabel8.setText("Teléfono:");

        txtMatricula.setEditable(false);
        txtMatricula.setToolTipText("");

        btnEspe.setText("ESPECIALIDAD");
        btnEspe.setEnabled(false);
        btnEspe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEspeActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));

        lblUser.setForeground(new java.awt.Color(51, 153, 255));

        jLabel10.setText("Usuario:");

        jLabel11.setText("WhatsApp:");

        jLabel9.setText("Cantidad Deleg.:");

        jLabel12.setText("Estado del Socio:");

        btnDesbloquear.setBackground(new java.awt.Color(204, 0, 0));
        btnDesbloquear.setForeground(new java.awt.Color(255, 255, 255));
        btnDesbloquear.setText("DESBLOQUEAR");
        btnDesbloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesbloquearActionPerformed(evt);
            }
        });

        btnEstado.setBackground(new java.awt.Color(0, 153, 0));
        btnEstado.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnDesbloquear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDesbloquear)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblWp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblCargadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblCargadores);

        btnAlta.setBackground(new java.awt.Color(0, 0, 255));
        btnAlta.setForeground(new java.awt.Color(255, 255, 255));
        btnAlta.setText("Dar de Alta");
        btnAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel2))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 13, Short.MAX_VALUE)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(txtClaveIPSST, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSendIPSST)))
                            .addComponent(txtCUIT, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClaveCMT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSendCMT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(173, 173, 173))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEspe, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                    .addComponent(txtTelefono)))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodme, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrar)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCUIT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSendCMT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEspe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAlta))
                    .addComponent(btnSendIPSST, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtClaveIPSST)
                    .addComponent(txtClaveCMT, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnReiniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnReiniciar.setToolTipText("Reiniciar");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/offlog.png"))); // NOI18N
        btnSalir.setToolTipText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnControl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sound-mixer.png"))); // NOI18N
        btnControl.setToolTipText("Control");
        btnControl.setEnabled(false);
        btnControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnControlActionPerformed(evt);
            }
        });

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnConsulta.setToolTipText("Agregar Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        btnDelegaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/relations_1.png"))); // NOI18N
        btnDelegaciones.setToolTipText("Delegaciones");
        btnDelegaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelegacionesActionPerformed(evt);
            }
        });

        btnCambioClave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/passwordbtn.png"))); // NOI18N
        btnCambioClave.setToolTipText("Cambiar Clave");
        btnCambioClave.setEnabled(false);
        btnCambioClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambioClaveActionPerformed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Logo Colegio chico.png"))); // NOI18N

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/searchbtn.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar Medico/Cargador");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCambioClave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConsulta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelegaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnControl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReiniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCambioClave, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelegaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnControl, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenu1.setText("Opciones");

        itemGuiamed.setText("Guía Médica");
        itemGuiamed.setEnabled(false);
        itemGuiamed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGuiamedActionPerformed(evt);
            }
        });
        jMenu1.add(itemGuiamed);

        itemComunicar.setText("Comunicación con Prestador");
        itemComunicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemComunicarActionPerformed(evt);
            }
        });
        jMenu1.add(itemComunicar);

        itemAltaCenmed.setText("Alta Centro Médico");
        itemAltaCenmed.setEnabled(false);
        jMenu1.add(itemAltaCenmed);

        itemMigrarResumen.setText("Migrar Resumen");
        itemMigrarResumen.setEnabled(false);
        jMenu1.add(itemMigrarResumen);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Cirugias");

        jMenuItem1.setText("Carga");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void itemGuiamedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGuiamedActionPerformed
        boolean modal = true;
        int codme = this.codme;
        vistaControlador.vistaGuiaMedica(this, modal, dashboard, codme, login.user, connection);
    }//GEN-LAST:event_itemGuiamedActionPerformed

    private void txtCodmeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodmeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCodme.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campo vacío");
            } else {
                try {
                    cargarDatos();
                    txtCodme.requestFocus();
                    estaBloqueado();
                    if (existe) {
                        btnAlta.setVisible(!Prestadores.existeSocio(Integer.valueOf(txtCodme.getText()), connection));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_txtCodmeKeyPressed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        txtCodme.setText("");
        txtClaveCMT.setText("");
        txtCUIT.setText("");
        txtMail.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtMatricula.setText("");
        txtClaveIPSST.setText("");
        lblCantidad.setText("");
        lblCantidad.setVisible(false);
        objetoCargador = null;
        TableModel newModel = new DefaultTableModel();
        tblCargadores.setModel(newModel);
        txtCodme.requestFocus();
        btnEspe.setEnabled(false);
        btnCambioClave.setEnabled(false);
        btnSendCMT.setEnabled(false);
        btnSendIPSST.setEnabled(false);
        btnDesbloquear.hide();
        btnAlta.hide();
        btnEstado.hide();
        itemGuiamed.setEnabled(false);
        //btnCorreo.setEnabled(false);
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnDesbloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesbloquearActionPerformed
        Desbloquear desbloquear = new Desbloquear();
        int dialogButton = JOptionPane.showConfirmDialog(null, "¿Desbloquear usuario?", "Mensaje del Sistema", 0);
        if (dialogButton == 1) {
            JOptionPane.showMessageDialog(null, "Cancelado por el Usuario");
        }
        if (dialogButton == 0) {
            desbloquear.updateDesbloqueo(objetoCargador, connection);
            sendMailDesbloq();
            JOptionPane.showMessageDialog(null, "Usuario Desbloqueado");
            try {
                try {
                    cargarDatos();
                } catch (JSONException ex) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, (Throwable) ex);
                } catch (ParseException ex2) {
                    Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex2);
                }
                this.btnDesbloquear.hide();
            } catch (SQLException ex3) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex3);
            }
        }
    }//GEN-LAST:event_btnDesbloquearActionPerformed

    private void sendMailDesbloq() {
        Mail mail = new Mail();
        mail.sendMailDesbloqueado(objetoCargador);
    }

    private void btnEspeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEspeActionPerformed
        vistaControlador.vistaEspecialidad(this, false, this, connection);
    }//GEN-LAST:event_btnEspeActionPerformed

    private void btnAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaActionPerformed
        try {
            codme = Integer.parseInt(txtCodme.getText());
            if (!Prestadores.existePrestador(codme, connection)) {
            }
            boolean alta;
            try {
                String execSql = "exec alta_usuario_web " + codme + "";
                connection.createStatement().execute(execSql);
                alta = true;
            } catch (SQLException ex4) {
                alta = false;
            }
            if (alta) {
                JOptionPane.showMessageDialog(null, "Alta generada.");
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo generar el alta.");
                cargarDatos();
            }
            StringBuilder append = new StringBuilder().append("INSERT INTO cmt_auditoria_mda VALUES ('");
            String execSql = append.append(Login.username).append("','").append(Constante.ALTA_USUARIO_WEB).append("', ").append(objetoCargador.codme).append(",GETDATE(),'").append(alta).append("')").toString();
            connection.createStatement().execute(execSql);
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex2) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, (Throwable) ex2);
        } catch (ParseException ex3) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex3);
        }
    }//GEN-LAST:event_btnAltaActionPerformed

    private void itemComunicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemComunicarActionPerformed
        vistaControlador.vistaFtermino(this, true, connection);
    }//GEN-LAST:event_itemComunicarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        vistaControlador.vistaBuscar(this, false, this, connection);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCambioClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambioClaveActionPerformed
        vistaControlador.vistaCambiarClave(this, true, objetoCargador, controlador, dialogoconsulta, user, connection);
    }//GEN-LAST:event_btnCambioClaveActionPerformed

    private void btnDelegacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelegacionesActionPerformed
        vistaControlador.vistaDelegaciones(this, true, connection);
    }//GEN-LAST:event_btnDelegacionesActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        vistaControlador.vistaDialogoConsulta(this, objetoCargador, dashboard, connection);
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnControlActionPerformed
        vistaControlador.vistaDialogoControl(this, dashboard, excel, connection);
    }//GEN-LAST:event_btnControlActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        try {
            this.dispose();
            VistaControlador vista = new VistaControlador();
            vista.vistaDashboard(idusuario, tipoUsuario, permisoJson);
        } catch (JSONException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void btnSendCMTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendCMTActionPerformed
        Mail mail = new Mail();
        mail.sendMailCMT(objetoCargador);
    }//GEN-LAST:event_btnSendCMTActionPerformed

    private void btnSendIPSSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendIPSSTActionPerformed
        Mail mail = new Mail();
        mail.sendMailIPSST(objetoCargador);
    }//GEN-LAST:event_btnSendIPSSTActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            FormCirugia form = new FormCirugia(this, false, idusuario, tipoUsuario, connection);
            form.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlta;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCambioClave;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnControl;
    private javax.swing.JButton btnDelegaciones;
    private javax.swing.JButton btnDesbloquear;
    private javax.swing.JButton btnEspe;
    private javax.swing.JButton btnEstado;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSendCMT;
    private javax.swing.JButton btnSendIPSST;
    private javax.swing.JMenuItem itemAltaCenmed;
    private javax.swing.JMenuItem itemComunicar;
    private javax.swing.JMenuItem itemGuiamed;
    private javax.swing.JMenuItem itemMigrarResumen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblWp;
    private javax.swing.JTable tblCargadores;
    private javax.swing.JTextField txtCUIT;
    public javax.swing.JTextField txtClaveCMT;
    private javax.swing.JTextField txtClaveIPSST;
    public javax.swing.JTextField txtCodme;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JLabel getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(javax.swing.JLabel lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public javax.swing.JTable getTblCargadores() {
        return tblCargadores;
    }

    public void setTblCargadores(javax.swing.JTable tblCargadores) {
        this.tblCargadores = tblCargadores;
    }

    public FueraDeTermino getfTermino() {
        return fTermino;
    }

    public void setfTermino(FueraDeTermino fTermino) {
        this.fTermino = fTermino;
    }

    public javax.swing.JLabel getLblWp() {
        return lblWp;
    }

    public void setLblWp(javax.swing.JLabel lblWp) {
        this.lblWp = lblWp;
    }

    public javax.swing.JTextField getTxtClaveCMT() {
        return txtClaveCMT;
    }

    public void setTxtClaveCMT(javax.swing.JTextField txtClaveCMT) {
        this.txtClaveCMT = txtClaveCMT;
    }
}
