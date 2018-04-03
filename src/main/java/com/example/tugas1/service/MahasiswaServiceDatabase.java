package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.MahasiswaMapper;
import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.MahasiswaModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.TabelDataUnivModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MahasiswaServiceDatabase implements MahasiswaService {

	public MahasiswaServiceDatabase() {
		
	}
	
	public MahasiswaServiceDatabase(MahasiswaMapper mahasiswaMapper) {
		this.mahasiswaMapper = mahasiswaMapper;
	}
	
	@Autowired
    private MahasiswaMapper mahasiswaMapper;
	
	@Override
	public MahasiswaModel selectMahasiswa(String npm) {
		// TODO Auto-generated method stub
		log.info ("select mahasiswa with npm {}", npm);
        return mahasiswaMapper.selectMahasiswa(npm);
	}
	
	@Override
	public String selectNamaProdiMahasiswa(int idProdi) {
		// TODO Auto-generated method stub
		log.info ("select mahasiswa with idProdi {}", idProdi);
        return mahasiswaMapper.selectNamaProdiMahasiswa(idProdi);
	}
	
	@Override
	public String selectNamaFakultasMahasiswa(int idProdi) {
		// TODO Auto-generated method stub
		log.info ("select fakultas mahasiswa");
        return mahasiswaMapper.selectNamaFakultasMahasiswa(idProdi);
	}
	
	@Override
	public String selectNamaUniversitasMahasiswa(int idProdi) {
		// TODO Auto-generated method stub
		log.info ("select universitas mahasiswa");
        return mahasiswaMapper.selectNamaUniversitasMahasiswa(idProdi);
	}

	@Override
	public List<MahasiswaModel> selectAllMahasiswa() {
		// TODO Auto-generated method stub
		log.info ("select all mahasiswa");
        return mahasiswaMapper.selectAllMahasiswa ();
	}

	@Override
	public void addMahasiswa(MahasiswaModel mahasiswa) {
		// TODO Auto-generated method stub
		mahasiswaMapper.addStudent (mahasiswa);
		log.info ("Student with " +mahasiswa.getNpm()+ " added");
		
	}

	@Override
	public void deleteMahasiswa(String npm) {
		// TODO Auto-generated method stub
		log.info ("student " + npm + " deleted");
	}

	@Override
	public void updateMahasiswa(MahasiswaModel mahasiswa) {
		// TODO Auto-generated method stub
		mahasiswaMapper.updateMahasiswa (mahasiswa);
		log.info ("student " + mahasiswa.getNpm() + " updated");
	}

	@Override
	public String selectProdiMahasiswa(int idProdi) {
		// TODO Auto-generated method stub
		log.info ("select mahasiswa with idProdi {}", idProdi);
        return mahasiswaMapper.selectProdiMahasiswa(idProdi);
	}

	@Override
	public String selectKodeUniversitasMahasiswa(int idProdi) {
		// TODO Auto-generated method stub
		log.info ("select universitas mahasiswa");
        return mahasiswaMapper.selectKodeUniversitasMahasiswa(idProdi);
	}
	
	@Override
    public String selectNpm (String npm)
    {
    	log.info("select NPM like (to calculate)");
    	return mahasiswaMapper.selectNpm(npm);
    }
	
	@Override
    public Integer selectMahasiswaByTahunMasuk(String tahun_masuk, int id_prodi)
    {
        log.info("select graduate student");
        return mahasiswaMapper.selectMahasiswaByTahunMasuk(tahun_masuk, id_prodi);
    }
	
	@Override
    public Integer selectMahasiswaLulus(String tahun_masuk, int id_prodi)
    {
        log.info("select graduate student");
        return mahasiswaMapper.selectMahasiswaLulus(tahun_masuk, id_prodi);
    }

	@Override
	public List<MahasiswaModel> selectAllMahasiswabyProdi(String prodi) {
		// TODO Auto-generated method stub
		log.info("select all student");
		return mahasiswaMapper.selectAllMahasiswabyProdi(prodi);
	}

	@Override
	public List<TabelDataUnivModel> selectAllUnivesritas() {
		// TODO Auto-generated method stub
		log.info("select all universitas");
		return mahasiswaMapper.selectAllUnivesritas();
	}
	
	public List<FakultasModel> selectAllFakultasbyUniv(int idUniv)
    {
        log.info ("select all fakultas with id Universitas {}", idUniv);
        return mahasiswaMapper.selectAllFakultasbyUniv(idUniv);
    }
	
	@Override
    public List<ProgramStudiModel> selectAllProdibyFakultas(int idFakultas)
    {
        log.info ("select all prodi with idFakultas {}", idFakultas);
        return mahasiswaMapper.selectAllProdibyFakultas(idFakultas);
    }

}
