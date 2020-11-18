/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;
import com.sun.javafx.collections.MappingChange.Map;
import employe.Dashboardkaryawan;
import koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import login.login;

public class transaksi2 extends javax.swing.JFrame {
 DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modelnya = new DefaultTableModel();
    
    public void homea(){
        new Dashboard().setVisible(true);
        this.setVisible(false);
    }
    
    public void homeb(){
        new Dashboardkaryawan().setVisible(true);
        this.setVisible(false);
    }
    
    public transaksi2() {
        initComponents();
        //memanggil method getkoneksi di class koneksi
        
        
        koneksi kon = new koneksi();
        kon.getKoneksi();

        //memberikan nama pada tabel
        tablebuku.setModel(model);
        model.addColumn("Id Buku");
        model.addColumn("Judul Buku");
        model.addColumn("Harga Jual");
        model.addColumn("stok");
        tampilDatabuku();

        tabletransaksi.setModel(modelnya);
        modelnya.addColumn("Id Transaksi");
        modelnya.addColumn("Id Buku");
        modelnya.addColumn("Nama Karyawan");
        modelnya.addColumn("Judul Buku");
        modelnya.addColumn("Tanggal Transaksi");
        modelnya.addColumn("Harga");
        modelnya.addColumn("Jumlah");
        modelnya.addColumn("Total Harga");
        tampilDatatransaksi();
    }
    
    private void tampilDatabuku() {
        int row = tablebuku.getRowCount();
        for (int a = 0; a < row; a++) {
            model.removeRow(0);
        }

        String query = "SELECT * FROM `buku`";

        try {
            Connection conn = koneksi.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                //langkah ini berguna untuk menampung data sementara
                String idbuku = rs.getString("id_buku");
                String judulbuku = rs.getString("judul_buku");
                String hargajual = rs.getString("harga_jual");
                String stok = rs.getString("stok");

                String[] data = {idbuku, judulbuku, hargajual, stok};

                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void tampilDatatransaksi() {
        int row = tabletransaksi.getRowCount();
        for (int a = 0; a < row; a++) {
            modelnya.removeRow(0);
        }

        String query = "SELECT * FROM `transaksi`";

        try {
            Connection conn = koneksi.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                //langkah ini berguna untuk menampung data sementara
                String idtransaksi = rs.getString("id_transaksi");
                String id_buku = rs.getString("id_buku");
                String tanggaltransaksi = rs.getString("tanggal_transaksi");
                String namakaryawan = rs.getString("nama_karyawan");
                String judulbuku = rs.getString("judul_buku");
                String harga = rs.getString("harga_jual");
                String jumlah = rs.getString("jumlah");
                String totalharga = rs.getString("total_harga");
                

                String[] data = {idtransaksi, id_buku, namakaryawan, judulbuku, tanggaltransaksi, harga, jumlah, totalharga};

                modelnya.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void tambahDatauang(){
        // menampung data sementara data yang di input
        String belanja = totalbelanja.getText();
        String bayar = txtbayar.getText();
        String kembali = kembaliannya.getText();
        //panggil koneksinya
        Connection conn = koneksi.getKoneksi();
        //buat query untuk memasukan data
        String query = "INSERT INTO `uang` (`id_transaksi`,`total_belanja`, `total_pembayaran`, `total_kembalian`) "
                + "VALUES (NULL,'"+belanja+"', '"+bayar+"', '"+kembali+"');";
        
        
        try{
            //menyiapkan statement untuk di eksekusi
            PreparedStatement ps= (PreparedStatement) conn.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            
        }catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal disimpan");
        
        }
    }
    
    private void clear(){
        date.setDate(null);
        cbkaryawan.setSelectedIndex(0);
        txtid.setText(null);
        txtbuku.setText(null);
        txtharga.setText(null);
        txtjumlah.setText(null);
//        totalharga.setText("0");
//        txtbayar.setText(null);
//        kembaliannya.setText("0");
    }
    private void saveclear(){
        date.setDate(null);
        cbkaryawan.setSelectedIndex(0);
        txtid.setText(null);
        txtbuku.setText(null);
        txtharga.setText(null);
        txtjumlah.setText(null);
        totalbelanja.setText("0");
        txtbayar.setText(null);
        kembaliannya.setText("0");
     }
    
    private void cancel() {
        int i = tabletransaksi.getSelectedRow();

        String idtransaksi = model.toString();

        //panggil koneksi
        Connection conn = koneksi.getKoneksi();

        String query = "DELETE FROM `transaksi`";

        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Data berhasil di cancel");

        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data gagal di cancel");
        } finally {
            tampilDatatransaksi();
            clear();
        }
    }
    
    private void add() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String choosenDate = sdf.format(date.getDate());

        String namakaryawan = "";
        switch (cbkaryawan.getSelectedIndex()) {
            case 0:
                namakaryawan = "Anisa";
                break;
            case 1:
                namakaryawan = "Dian";
            case 2:
                namakaryawan = "Saffa";
            case 3:
                namakaryawan = "Syahdah";
            case 4:
                namakaryawan = " Nila";
        }
        String id = txtid.getText();
        String buku = txtbuku.getText();
        String harga = txtharga.getText();
        String jumlah = txtjumlah.getText();

        ///menjumlahkan harga 
        int harga1 = Integer.parseInt(harga);
        int jumlah1 = Integer.parseInt(jumlah);
        int totalnya = harga1 * jumlah1;
        String totaluy = Integer.toString(totalnya);

        Connection conn = koneksi.getKoneksi();
        String sql = "INSERT INTO `transaksi` (`id_transaksi`, `id_buku`, `tanggal_transaksi`, `nama_karyawan`, `judul_buku`, `harga_jual`, `jumlah`, `total_harga`) "
                + "VALUES (NULL, '" + id + "', '" + choosenDate + "', '" + namakaryawan + "', '" + buku + "', '" + harga + "', '" + jumlah + "', '" + totaluy + "');";

        try {
            PreparedStatement st = (PreparedStatement) conn.prepareStatement(sql);
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data berhasil ditambah");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data gagal di tambah");
        } finally {
            tampilDatatransaksi();
        }
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        date = new com.toedter.calendar.JDateChooser();
        caritxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablebuku = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cbkaryawan = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtbuku = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabletransaksi = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        totalbelanja = new javax.swing.JLabel();
        kembaliannya = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TRANSAKSI PIRSMEDIA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(259, 259, 259))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(26, 26, 26))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Tanggal");

        tablebuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablebuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablebukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablebuku);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Nama Karyawan");

        cbkaryawan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbkaryawan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anisa", "Dian", "Saffa", "Syahdah", "Nila" }));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Id Buku");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Judul Buku");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Harga");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Jumlah");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/5a229c5fc8c295.7183583515122176958223.png"))); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/123.png"))); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tabletransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabletransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabletransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabletransaksi);

        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/hiclipart.com (3).png"))); // NOI18N
        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Total Belanja");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Total Pembayaran");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Total Kembalian");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Rp.");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Rp.");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Rp.");

        totalbelanja.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        totalbelanja.setText("0");

        kembaliannya.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        kembaliannya.setText("0");

        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/pngguru.com.png"))); // NOI18N
        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admin/aaaa.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton7.setText("Cetak Struk");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton3.setText("home.A");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton8.setText("home.E");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtid)
                                    .addComponent(cbkaryawan, 0, 174, Short.MAX_VALUE)
                                    .addComponent(txtbuku))
                                .addGap(93, 93, 93)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addComponent(jButton1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtharga, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(txtjumlah))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8)
                                        .addGap(12, 12, 12))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButton4)
                        .addGap(207, 207, 207)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(37, 37, 37)
                                    .addComponent(kembaliannya))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(totalbelanja)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(487, 487, 487)
                        .addComponent(jButton7)
                        .addGap(35, 35, 35)
                        .addComponent(jButton5)))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(caritxt, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(caritxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbkaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton8)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jLabel11)
                        .addComponent(totalbelanja)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtbayar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel12)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13)
                    .addComponent(kembaliannya))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        add();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablebukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablebukuMouseClicked
        // TODO add your handling code here:
         int i = tablebuku.getSelectedRow();
         String id = model.getValueAt(i, 0).toString();
         String judulbuku = model.getValueAt(i, 1).toString();
         String harga = model.getValueAt(i, 2).toString();
         txtid.setText(id);
         txtbuku.setText(judulbuku);
         txtharga.setText(harga);   
    }//GEN-LAST:event_tablebukuMouseClicked

    private void tabletransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabletransaksiMouseClicked
        // TODO add your handling code here:
        String sql = "SELECT SUM(total_harga) AS totalnya FROM transaksi";
        try{
            Statement s = koneksi.getKoneksi().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                totalbelanja.setText(rs.getString("totalnya"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_tabletransaksiMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         int i = tabletransaksi.getSelectedRow();
            
            
        //panggil koneksi
        Connection conn = koneksi.getKoneksi();
        
        String query = "DELETE FROM `transaksi`";
        
        try{
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Data berhasil di Simpan");
         
        }catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data gagal di Simpan");
        }finally{
           tampilDatatransaksi();
//           tambahDatauang();
           saveclear();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
        // TODO add your handling code here:
        String total = totalbelanja.getText().toString();
       int tothar = Integer.parseInt(total);
       
       String bayar = txtbayar.getText().toString();
       int byr = Integer.parseInt(bayar);
       
       int kembali = byr - tothar ;
       
       String kembalian = String.valueOf(kembali);
       
       kembaliannya.setText(kembalian);
    }//GEN-LAST:event_txtbayarKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        int row = tablebuku.getRowCount();

        for (int i = 0; i < row; i++) {
            model.setNumRows(0);
        }

        String cari =caritxt.getText();
        String query = "SELECT * FROM buku WHERE judul_buku LIKE '%" + cari + "%'";

        try {
            Connection konek = koneksi.getKoneksi();
            Statement stat = konek.createStatement();
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                String kode = res.getString("id_buku");
                String nama = res.getString("judul_buku");
                String harga = res.getString("harga_jual");
                String jml = res.getString("stok");
               
                String[] datae = {kode, nama, harga, jml};

                model.addRow(datae);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
       JasperPrint jp = null;
      try {
         jp = JasperFillManager.fillReport(getClass().getResourceAsStream("struk.jasper"),null,koneksi.getKoneksi());
        } catch (JRException ex) {
            System.out.println(ex);
        }
      JasperViewer.viewReport(jp,false);
   
    }//GEN-LAST:event_jButton7MousePressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        cancel();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        homea();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        homeb();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(transaksi2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caritxt;
    private javax.swing.JComboBox cbkaryawan;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel kembaliannya;
    private javax.swing.JTable tablebuku;
    private javax.swing.JTable tabletransaksi;
    private javax.swing.JLabel totalbelanja;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtbuku;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtjumlah;
    // End of variables declaration//GEN-END:variables
}
