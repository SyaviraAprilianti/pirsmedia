/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

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

public class Transaksi extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel modelnya = new DefaultTableModel();

    public void menu(){
        new Dashboard().setVisible(true);
        this.setVisible(false);
    }
   
    public Transaksi() {
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
        totalharga.setText("0");
        txtbayar.setText(null);
        kembaliannya.setText("0");
     }

     private void cancel(){
            int i = tabletransaksi.getSelectedRow();
            
            String idtransaksi = model.getValueAt(i, 0).toString();
        
        //panggil koneksi
        Connection conn = koneksi.getKoneksi();
        
        String query = "DELETE FROM `transaksi` WHERE `transaksi`.`id_transaksi` = "+idtransaksi+"";
        
        try{
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(query);
            ps.execute();
            JOptionPane.showMessageDialog(null, "belanja berhasil di cancel");
         
        }catch(Exception e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "belanja gagal di cancel");
        }finally{
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

//    private void totalbayar() {
//        Connection conn = koneksi.getKoneksi();
//        String sql = "SELECT SUM(total_harga) AS totalnya FROM transaksi";
//        
//        int totalhrg = Integer.parseInt(totalharga.getText());
//        int bayar = Integer.parseInt(txtbayar.getText());
//        int kembali = Integer.parseInt(kembaliannya.getText());
//        
////        
//        try {
//            Statement st = (Statement) koneksi.getKoneksi().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            while (rs.next()) {
//                totalharga.setText(rs.getString(1));
//            }
//            st.close();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//    }


@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kembalian = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablebuku = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbkaryawan = new javax.swing.JComboBox();
        txtbuku = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtjumlah = new javax.swing.JTextField();
        date = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabletransaksi = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        totalharga = new javax.swing.JLabel();
        kembaliannya = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kembalian.setBackground(new java.awt.Color(0, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TRANSAKSI PIRSMEDIA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(293, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(280, 280, 280))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

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
        jScrollPane2.setViewportView(tablebuku);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Nama Karyawan");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Judul Buku");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Harga");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Jumlah");

        cbkaryawan.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbkaryawan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anisa", "Dian", "Saffa", "Syahdah", "Nila" }));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Tanggal");

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
        jScrollPane1.setViewportView(tabletransaksi);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\5a229c5fc8c295.7183583515122176958223.png")); // NOI18N
        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\123.png")); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\toppng.com-free-sweet-icons-easy-home-icon-blue-513x500.png")); // NOI18N
        jButton3.setText("Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Total Harga");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setText("Total pembayaran");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Total Pengembalian");

        txtbayar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });
        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\pngguru.com.png")); // NOI18N
        jButton4.setText("Save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Rp.");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Rp.");

        totalharga.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        totalharga.setText("0");

        kembaliannya.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        kembaliannya.setText("0");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Id Buku");

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\user\\Downloads\\hiclipart.com (3).png")); // NOI18N
        jButton5.setText("Cancel");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton6.setText("Cetak Struk");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kembalianLayout = new javax.swing.GroupLayout(kembalian);
        kembalian.setLayout(kembalianLayout);
        kembalianLayout.setHorizontalGroup(
            kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(295, 295, 295)
                                .addComponent(jButton4))
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(kembalianLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, kembalianLayout.createSequentialGroup()
                                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel12))
                                        .addGap(33, 33, 33)
                                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbkaryawan, 0, 179, Short.MAX_VALUE)
                                            .addComponent(txtid))))
                                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(kembalianLayout.createSequentialGroup()
                                        .addGap(79, 79, 79)
                                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(kembalianLayout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(63, 63, 63))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(55, 55, 55)))
                                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                                        .addGap(149, 149, 149)
                                        .addComponent(jButton1)
                                        .addGap(29, 29, 29)
                                        .addComponent(jButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3)))))
                        .addGap(53, 53, 53))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(22, 22, 22)
                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kembalianLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(29, 29, 29)
                        .addComponent(kembaliannya))
                    .addGroup(kembalianLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(27, 27, 27)
                        .addComponent(totalharga))
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(116, 116, 116))
        );
        kembalianLayout.setVerticalGroup(
            kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kembalianLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kembalianLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtharga))
                            .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addComponent(cbkaryawan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(kembalianLayout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton3))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kembalianLayout.createSequentialGroup()
                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtbuku, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kembalianLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel12))
                                    .addComponent(txtjumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)))
                        .addGap(23, 23, 23)
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(jButton5))
                            .addComponent(jLabel10)
                            .addComponent(totalharga))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(kembaliannya))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(27, 27, 27))
                    .addGroup(kembalianLayout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
        // TODO add your handling code here:
       String total = totalharga.getText().toString();
       int tothar = Integer.parseInt(total);
       
       String bayar = txtbayar.getText().toString();
       int byr = Integer.parseInt(bayar);
       
       int kembali = byr - tothar ;
       
       String kembalian = String.valueOf(kembali);
       
       kembaliannya.setText(kembalian);
       
    }//GEN-LAST:event_txtbayarKeyReleased

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

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        add();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabletransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabletransaksiMouseClicked
        // TODO add your handling code here:
       String sql = "SELECT SUM(total_harga) AS totalnya FROM transaksi";
        try{
            Statement s = koneksi.getKoneksi().createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                totalharga.setText(rs.getString("totalnya"));
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_tabletransaksiMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
       
//       
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
           saveclear();
        }
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        menu();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtbayarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        cancel();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

   
    
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
            java.util.logging.Logger.getLogger(Transaksi.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbkaryawan;
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel kembalian;
    private javax.swing.JLabel kembaliannya;
    private javax.swing.JTable tablebuku;
    private javax.swing.JTable tabletransaksi;
    private javax.swing.JLabel totalharga;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtbuku;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtjumlah;
    // End of variables declaration//GEN-END:variables
}
