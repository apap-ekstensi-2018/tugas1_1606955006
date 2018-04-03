package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MahasiswaModel {
	private String id;
    private String npm;
    private String nama;
    private String tempat_lahir;
    private String tanggal_lahir;
    private Integer jenis_kelamin;
    private String agama;
    private String golongan_darah;
    private String status = "Aktif";
    private String tahun_masuk;
    private String jalur_masuk;
    private Integer  id_prodi;
}
