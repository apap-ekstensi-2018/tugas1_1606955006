package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.MahasiswaModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.TabelDataUnivModel;

@Mapper
public interface MahasiswaMapper {
	
	@Select("select * from mahasiswa where npm = #{npm}")
    @Results( value = {
    		@Result(property = "id", column = "id"),
            @Result(property = "npm", column = "npm"),
            @Result(property = "nama", column = "nama"),
            @Result(property = "tempat_lahir", column = "tempat_lahir"),
            @Result(property = "tanggal_lahir", column = "tanggal_lahir"),
            @Result(property = "jenis_kelamin", column = "jenis_kelamin"),
            @Result(property = "agama", column = "agama"),
            @Result(property = "golongan_darah", column = "golongan_darah"),
            @Result(property = "status", column = "status"),
            @Result(property = "tahun_masuk", column = "tahun_masuk"),
            @Result(property = "jalur_masuk", column = "jalur_masuk"),
            @Result(property = "id_prodi", column = "id_prodi")
    })
	MahasiswaModel selectMahasiswa (@Param("npm") String npm);
	
	@Select("select max(npm) from mahasiswa where npm like CONCAT(#{npm},'%') limit 1")
    String selectNpm (@Param("npm") String npm);
	
	@Select("select program_studi.nama_prodi from program_studi where program_studi.id = #{idProdi}")
	String selectNamaProdiMahasiswa (int idProdi);
	
	@Select("select program_studi.kode_prodi from program_studi where program_studi.id = #{idProdi}")
	String selectProdiMahasiswa (int idProdi);
	
	@Select("select nama_fakultas from fakultas inner join program_studi on(fakultas.id = program_studi.id_fakultas) "
			+ "where program_studi.id = #{idProdi}")
	String selectNamaFakultasMahasiswa (int idProdi);
	
	@Select("select nama_univ from universitas inner join fakultas on(universitas.id = fakultas.id_univ) "
			+ "inner join program_studi on(fakultas.id = program_studi.id_fakultas) "
			+ "where program_studi.id = #{idProdi}")
	String selectNamaUniversitasMahasiswa(int idProdi);
	
	@Select("select kode_univ from universitas inner join fakultas on(universitas.id = fakultas.id_univ) "
			+ "inner join program_studi on(fakultas.id = program_studi.id_fakultas) "
			+ "where program_studi.id = #{idProdi}")
	String selectKodeUniversitasMahasiswa(int idProdi);

    @Select("select npm, name, gpa from mahasiswa")
    List<MahasiswaModel> selectAllMahasiswa ();
    
    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi) "
    		+ "VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, "
    		+ "#{status}, #{tahun_masuk}, #{jalur_masuk}, #{id_prodi})")
    void addStudent (MahasiswaModel mahasiswa);
    
    @Delete("DELETE FROM mahasiswa where npm = #{npm}")
    void deleteMahasiswa(@Param("npm") String npm);
    
    @Update("UPDATE mahasiswa SET npm = #{npm}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
    		+ "jenis_kelamin = #{jenis_kelamin}, agama = #{agama}, golongan_darah = #{golongan_darah}, "
    		+ "status = #{status}, tahun_masuk = #{tahun_masuk}, jalur_masuk = #{jalur_masuk}, id_prodi = #{id_prodi} where id = #{id}")
    void updateMahasiswa(MahasiswaModel mahasiswa);
    
    @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{id_prodi}")
    Integer selectMahasiswaByTahunMasuk(@Param("tahun_masuk") String tahun_masuk, @Param("id_prodi") int id_prodi);
    
    @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{id_prodi} and status='Lulus'")
    Integer selectMahasiswaLulus(@Param("tahun_masuk") String tahun_masuk, @Param("id_prodi") int id_prodi);
    
    @Select("select mhs.npm, mhs.nama, mhs.id_prodi as idProdi, prodi.nama_prodi as namaProdi"
			+ ", mhs.tahun_masuk as tahunMasuk, mhs.jalur_masuk as jalurMasuk"
			+ " from mahasiswa mhs inner join program_studi prodi on prodi.id = mhs.id_prodi"
			+ " where mhs.id_prodi = #{idProdi}")
    List<MahasiswaModel> selectAllMahasiswabyProdi(@Param("prodi") String prodi);
    
    @Select("SELECT DISTINCT universitas.id AS id, universitas.`nama_univ`AS namaUniv, universitas.`kode_univ`AS kodeUniv FROM universitas "
    		+ "INNER JOIN fakultas INNER JOIN program_studi")
    List<TabelDataUnivModel> selectAllUnivesritas();
    
    @Select("SELECT DISTINCT fakultas.id, fakultas.kode_fakultas AS kodeFakultas, fakultas.nama_fakultas AS namaFakultas FROM fakultas INNER JOIN universitas " + 
    		"where fakultas.id_univ = #{idUniv}")
    List<FakultasModel> selectAllFakultasbyUniv(@Param("idUniv") int idUniv);
    
    @Select("SELECT DISTINCT program_studi.id, program_studi.kode_prodi AS kodeProdi, program_studi.nama_prodi AS namaProdi FROM program_studi INNER JOIN fakultas ON program_studi.id_fakultas=fakultas.id " + 
    		"where fakultas.id = #{idFakultas}")
    List<ProgramStudiModel> selectAllProdibyFakultas(@Param("idFakultas") int idFakultas);
}
