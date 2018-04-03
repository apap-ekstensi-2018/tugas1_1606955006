package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.MahasiswaModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.TabelDataUnivModel;

public interface MahasiswaService {
	
	MahasiswaModel selectMahasiswa (String npm);
	
	String selectNpm (String npm);
	
	String selectProdiMahasiswa (int idProdi);
	
	String selectNamaProdiMahasiswa (int idProdi);
	
	String selectNamaFakultasMahasiswa(int idProdi);
	
	String selectNamaUniversitasMahasiswa(int idProdi);
	
	String selectKodeUniversitasMahasiswa(int idProdi);
	
	List<MahasiswaModel> selectAllMahasiswa ();

	void addMahasiswa (MahasiswaModel mahasiswa);

	void deleteMahasiswa (String npm);
	    
	void updateMahasiswa (MahasiswaModel mahasiswa);
	
	Integer selectMahasiswaByTahunMasuk(String tahun_masuk, int id_prodi);
	
	Integer selectMahasiswaLulus(String tahun_masuk, int id_prodi);
	
	List<MahasiswaModel> selectAllMahasiswabyProdi(String prodi);
	
	List<TabelDataUnivModel> selectAllUnivesritas();
	
	List<FakultasModel> selectAllFakultasbyUniv(int idUniv);
	
	List<ProgramStudiModel> selectAllProdibyFakultas(int idFakultas);
}

