-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 05, 2020 at 04:33 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apkpirsmedia`
--

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `id_buku` varchar(3) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `harga_jual` int(50) NOT NULL,
  `stok` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`id_buku`, `judul_buku`, `harga_jual`, `stok`) VALUES
('A1', 'Alaska', 120000, 195),
('A2', 'Altariksa', 135000, 194),
('A3', 'Athlas', 115000, 191),
('D1', 'Dignitate', 97000, 191),
('D2', 'Dilan 1990', 99000, 197),
('D3', 'Dilan 1991', 99000, 197),
('G1', 'Geez & Ann', 120000, 197),
('G2', 'Glen Anggara', 99000, 197),
('G3', 'Kata', 102000, 197),
('H1', 'Hujan', 120000, 197),
('L1', 'Light by you', 98000, 197),
('M1', 'Mariposa', 120000, 197),
('M2', 'Melodylan', 99000, 197),
('M3', 'Milea', 110000, 197),
('S1', 'Senior', 98000, 196),
('T1', 'The pilots lovers', 100000, 197),
('U2', 'Usik', 95000, 194);

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` varchar(3) NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `jenis_kelamin` enum('Perempuan','Laki - Laki') NOT NULL,
  `no_telepon` varchar(15) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `jenis_kelamin`, `no_telepon`, `alamat`) VALUES
('K01', 'Anisa', 'Perempuan', '0826362891', 'GG. Madrasah'),
('K02', 'Dian', 'Perempuan', '08662716218', 'Mekarsari'),
('K03', 'Saffa', 'Perempuan', '0892817211', 'Depok'),
('K04', 'Syahdah', 'Perempuan', '08726176281', 'Damon'),
('K05', 'Nila', 'Perempuan', '0897171219', 'GG. Nangka'),
('K06', 'Syfa', 'Perempuan', '098757667467', 'Cilodong');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `tanggal_transaksi` date NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `harga_jual` int(30) NOT NULL,
  `jumlah` int(30) NOT NULL,
  `total_harga` int(30) NOT NULL,
  `user` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`tanggal_transaksi`, `nama_karyawan`, `judul_buku`, `harga_jual`, `jumlah`, `total_harga`, `user`) VALUES
('2020-03-11', ' Nila', 'The pilots lovers', 100000, 1, 100000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Athlas', 115000, 1, 115000, ''),
('2020-03-11', ' Nila', 'Senior', 98000, 1, 98000, 'root@localhost'),
('2020-03-11', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-03-12', 'Anisa', 'Usik', 95000, 1, 95000, 'root@localhost'),
('2020-03-12', ' Nila', 'Milea', 110000, 1, 110000, 'root@localhost'),
('2020-03-14', ' Nila', 'Milea', 110000, 2, 220000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Milea', 110000, 2, 220000, 'root@localhost'),
('2020-03-14', 'Anisa', 'The pilots lovers', 100000, 2, 200000, 'root@localhost'),
('2020-03-14', 'Anisa', 'The pilots lovers', 100000, 2, 200000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-03-14', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-03-17', ' Nila', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-03-17', 'Anisa', 'The pilots lovers', 100000, 1, 100000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-03-17', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-03', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-03', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-04-03', ' Nila', 'Kata', 102000, 1, 102000, 'root@localhost'),
('2020-04-03', ' Nila', 'Light by you', 98000, 1, 98000, 'root@localhost'),
('2020-04-04', 'Anisa', 'Alaska', 120000, 2, 240000, 'root@localhost'),
('2020-04-04', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-04-04', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-04-04', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-04-05', ' Nila', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-05', ' Nila', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-05', ' Nila', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Usik', 95000, 1, 95000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Usik', 95000, 1, 95000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Alaska', 120000, 200000, -1769803776, 'root@localhost'),
('2020-04-05', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-05', 'Anisa', 'The pilots lovers', 100000, 1, 100000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Altariksa', 135000, 2, 270000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Senior', 98000, 1, 98000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Usik', 95000, 3, 285000, 'root@localhost'),
('2020-04-05', ' Nila', 'Altariksa', 135000, 1, 135000, 'root@localhost'),
('2020-04-05', ' Nila', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Dignitate', 97000, 3, 291000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Alaska', 120000, 1, 120000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Dignitate', 97000, 2, 194000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Athlas', 115000, 1, 115000, 'root@localhost'),
('2020-04-05', 'Anisa', 'Dignitate', 97000, 1, 97000, 'root@localhost');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(5) NOT NULL,
  `id_buku` varchar(3) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `judul_buku` varchar(100) NOT NULL,
  `harga_jual` int(30) NOT NULL,
  `jumlah` int(30) NOT NULL,
  `total_harga` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_buku`, `tanggal_transaksi`, `nama_karyawan`, `judul_buku`, `harga_jual`, `jumlah`, `total_harga`) VALUES
(6, 'A3', '2020-04-05', 'Anisa', 'Athlas', 115000, 3, 345000);

--
-- Triggers `transaksi`
--
DELIMITER $$
CREATE TRIGGER `laporan_penjualan` AFTER DELETE ON `transaksi` FOR EACH ROW BEGIN
insert into penjualan(
    tanggal_transaksi,	
    nama_karyawan,
    judul_buku,
    harga_jual,
    jumlah,
    total_harga,
    user)
VALUES(
    OLD.tanggal_transaksi,	
    OLD.nama_karyawan,
    OLD.judul_buku,
    OLD.harga_jual,
    OLD.jumlah,
    OLD.total_harga,
	CURRENT_USER);
    END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stokbuku` AFTER INSERT ON `transaksi` FOR EACH ROW BEGIN
UPDATE buku SET
stok = stok - NEW.jumlah WHERE
id_buku = NEW.id_buku;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `option` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `option`) VALUES
(1, 'syavira', '12345', 'Admin'),
(2, 'nise', '1234', 'Employe');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`id_buku`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `fk_idbuku` (`id_buku`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_idbuku` FOREIGN KEY (`id_buku`) REFERENCES `buku` (`id_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
