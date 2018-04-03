package com.example.tugas1.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.FakultasModel;
import com.example.tugas1.model.MahasiswaModel;
import com.example.tugas1.model.ProgramStudiModel;
import com.example.tugas1.model.TabelDataUnivModel;
import com.example.tugas1.service.MahasiswaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MahasiswaController {
	@Autowired
    MahasiswaService mahasiswaDAO;
	
	@RequestMapping("/")
    public String index (Model model){
    	model.addAttribute ("title", "Homepage");
        return "index";
    }
	
	@RequestMapping("/mahasiswa/tambah")
    public String add (Model model){
		MahasiswaModel mahasiswa = new MahasiswaModel();
    	model.addAttribute ("mahasiswa", mahasiswa);
        return "form-add";
    }
	
	@RequestMapping("/mahasiswa/tambah/submit")
	public String addSubmit(@ModelAttribute MahasiswaModel mahasiswa)
    {
		String tahun_masuk = mahasiswa.getTahun_masuk().substring(2);
		log.info("tahun_masuk " +tahun_masuk);
		String kode_univ = mahasiswaDAO.selectKodeUniversitasMahasiswa(mahasiswa.getId_prodi());
		log.info("kode_univ " +kode_univ);
		String kode_prodi = mahasiswaDAO.selectProdiMahasiswa(mahasiswa.getId_prodi());
		log.info("kode_prodi " +kode_prodi);
		String jalur_masuk = getJalurMasuk(mahasiswa.getJalur_masuk());
		log.info("jalur_masuk " +mahasiswa.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);
		
		String current_npm = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
		log.info("npm_sementara " +current_npm);
		
		String max_npm = mahasiswaDAO.selectNpm(current_npm);
		if(max_npm==null) {
			max_npm= current_npm+ "001";
		}
		log.info("max_npm " +max_npm);
		
		String nomor_urut = getNomorUrut(max_npm);
		log.info("nomor_urut " +nomor_urut);
		
		String npm = current_npm + nomor_urut;
		log.info("npm " +npm);
		
		mahasiswa.setNpm(npm);
		log.info("Mahasiswa " +mahasiswa);
    	
    	if(mahasiswaDAO.selectMahasiswa(npm) == null) {
    		mahasiswaDAO.addMahasiswa(mahasiswa);
            return "success-add";
    	}else {
    		return "already-add";
    	} 
    }
	
	public String getNomorUrut(String max_npm) {
    	String result = "0";
    	String nomor_urut = max_npm.substring(9);
    	String new_nomor_urut = String.valueOf(Integer.parseInt(nomor_urut) + 1) ;
    	if(new_nomor_urut.length()==1) {
    		result = "00"+new_nomor_urut;
    	}else if(new_nomor_urut.length()==2) {
    		result = "0"+new_nomor_urut;
    	}else {
    		result = new_nomor_urut;
    	}
    	return result;
    }
	
	public String getJalurMasuk(String jalur_masuk) {
    	String id = "0";
    	if(jalur_masuk.equals("Undangan Olimpiade")) {
    		id = "53";
    	}else if(jalur_masuk.equals("Undangan Reguler/SNMPTN")) {
    		id = "54";
    	}else if(jalur_masuk.equals("Undangan Paralel/PPKB")) {
    		id = "55";
    	}else if(jalur_masuk.equals("Ujian Tulis Bersama/SBMPTN")) {
    		id = "57";
    	}else if(jalur_masuk.equals("Ujian Tulis Mandiri")) {
    		id = "62";
    	}
    	return id;
    }
	
	@RequestMapping("/mahasiswa")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
		String namaProdi = mahasiswaDAO.selectNamaProdiMahasiswa(mahasiswa.getId_prodi());
		String namaFakultas = mahasiswaDAO.selectNamaFakultasMahasiswa(mahasiswa.getId_prodi());
		String namaUniversitas = mahasiswaDAO.selectNamaUniversitasMahasiswa(mahasiswa.getId_prodi());
		
        model.addAttribute ("title", "View Mahasiswa Page");

        if (mahasiswa != null) {
            model.addAttribute ("mahasiswa", mahasiswa);
            model.addAttribute("namaProdi", namaProdi);
            model.addAttribute("namaFakultas", namaFakultas);
            model.addAttribute("namaUniversitas", namaUniversitas);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
	
	@RequestMapping("/mahasiswa/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
		String namaProdi = mahasiswaDAO.selectNamaProdiMahasiswa(mahasiswa.getId_prodi());
		String namaFakultas = mahasiswaDAO.selectNamaFakultasMahasiswa(mahasiswa.getId_prodi());
		String namaUniversitas = mahasiswaDAO.selectNamaUniversitasMahasiswa(mahasiswa.getId_prodi());
        model.addAttribute ("title", "View Mahasiswa Page");

        if (mahasiswa!= null) {
            model.addAttribute ("mahasiswa", mahasiswa);
            model.addAttribute("namaProdi", namaProdi);
            model.addAttribute("namaFakultas", namaFakultas);
            model.addAttribute("namaFakultas", namaUniversitas);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
	
	@RequestMapping("/mahasiswa/ubah/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm)
    {
		MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
		log.info("Mahasiswa ubah " +mahasiswa);
        model.addAttribute ("title", "Halaman Ubah Mahasiswa");

        if (mahasiswa != null) {
            model.addAttribute ("mahasiswa", mahasiswa);
            log.info("Mahasiswa ubah update" +mahasiswa);
            return "form-update";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
	
	@RequestMapping(value = "/mahasiswa/ubah/submit")
    public String updateSubmit (Model model, @ModelAttribute MahasiswaModel mahasiswa)
    {
		String tahun_masuk = mahasiswa.getTahun_masuk().substring(2);
		log.info("tahun_masuk " +tahun_masuk);
		String kode_univ = mahasiswaDAO.selectKodeUniversitasMahasiswa(mahasiswa.getId_prodi());
		log.info("kode_univ " +kode_univ);
		String kode_prodi = mahasiswaDAO.selectProdiMahasiswa(mahasiswa.getId_prodi());
		log.info("kode_prodi " +kode_prodi);
		String jalur_masuk = getJalurMasuk(mahasiswa.getJalur_masuk());
		log.info("jalur_masuk " +mahasiswa.getJalur_masuk());
    	log.info("id jalur_masuk " +jalur_masuk);
    	log.info("npm " +mahasiswa.getNpm());
    	log.info("id " +mahasiswa.getId());
		
		String current_npm = tahun_masuk + kode_univ + kode_prodi + jalur_masuk;
		log.info("npm_sementara " +current_npm);
		
		String npm = current_npm + "001";
		log.info("npm " +npm);
		
		mahasiswa.setNpm(npm);
		log.info("Mahasiswa " +mahasiswa);
		
		mahasiswaDAO.updateMahasiswa(mahasiswa);
        model.addAttribute ("title", "Submit Halaman Ubah");
        return "success-update";
    }
	
	@RequestMapping("/kelulusan")
    public String kelulusan (Model model)
    {
		return"lihat-kelulusan";
    }
	
	@RequestMapping("/kelulusan/submit")
    public String viewKelulusan (Model model,
    		@RequestParam(value = "tahun_masuk", required=false) String tahun_masuk, @RequestParam(value = "id_prodi", required=false)int id_prodi )
    {
		log.info("tahun_masuk " +tahun_masuk);
		log.info("id_prodi " +id_prodi);
		
        int total_mhs= mahasiswaDAO.selectMahasiswaByTahunMasuk(tahun_masuk, id_prodi);
        log.info("total_mhs " +total_mhs);
        
        int mhs_lulus = mahasiswaDAO.selectMahasiswaLulus(tahun_masuk, id_prodi);
        log.info("mhs_lulus " +mhs_lulus);
        
        double persen_lulus = ((double) mhs_lulus / (double) total_mhs) * 100;
        log.info("persen_lulus " +persen_lulus);
        
        String persentase = Double.toString(persen_lulus);
        log.info("persentase " +persentase);
        
        String program_studi = mahasiswaDAO.selectNamaProdiMahasiswa(id_prodi);
        String fakultas = mahasiswaDAO.selectNamaFakultasMahasiswa(id_prodi);
        String universitas = mahasiswaDAO.selectNamaUniversitasMahasiswa(id_prodi);
        
        model.addAttribute("total_mhs", total_mhs);
        model.addAttribute("mhs_lulus", mhs_lulus);
        model.addAttribute("presentase", persentase);
        model.addAttribute("tahun_masuk", tahun_masuk);
        model.addAttribute("program_studi", program_studi);
        model.addAttribute("fakultas", fakultas);
        model.addAttribute("universitas", universitas);

        return "presentase-kelulusan";
    }
	
	 @RequestMapping("/mahasiswa/cari")
	    public String cariMahasiswa(Model model,
	                                @RequestParam(value = "univ", required = false) String universitas,
	                                @RequestParam(value = "fakultas", required = false) String fakultas,
	                                @RequestParam(value = "prodi", required = false) String prodi)
	    {

	        if(universitas == null && fakultas == null && prodi == null){
	        	List<TabelDataUnivModel> listUniv = mahasiswaDAO.selectAllUnivesritas();
				model.addAttribute("listUniv", listUniv);
				//return "form-search";
	        	return "cari-mahasiswa";
	        }else{
	            //List<TabelDataMhsModel> mahasiswa2 = mahasiswaDAO.selectTabelDataMhs(Integer.parseInt(universitas), Integer.parseInt(fakultas), Integer.parseInt(prodi));
	        	String nama_universitas = mahasiswaDAO.selectNamaUniversitasMahasiswa(Integer.parseInt(prodi));
				String nama_fakultas = mahasiswaDAO.selectNamaFakultasMahasiswa(Integer.parseInt(prodi));
				String nama_prodi = mahasiswaDAO.selectNamaProdiMahasiswa(Integer.parseInt(prodi));
				List<MahasiswaModel> list_mhs = mahasiswaDAO.selectAllMahasiswabyProdi(prodi);
	        	
	        	model.addAttribute("univ", nama_universitas);
	            model.addAttribute("fakultas", nama_fakultas);
	            model.addAttribute("prodi", nama_prodi);
	            model.addAttribute("mahasiswa2", list_mhs);
	            return "viewall";
	        }
	    }
	 
	 @RequestMapping(value = "/getFakultasbyUniv", method = RequestMethod.GET)
		public List<FakultasModel> findAllFakultasbyUniv(
		        @RequestParam(value = "idUniv", required = true) int idUniv) {
		    return mahasiswaDAO.selectAllFakultasbyUniv(idUniv);
		}
		
		@RequestMapping(value = "/getProdibyFakultas", method = RequestMethod.GET)
		public List<ProgramStudiModel> findAllProdibyFakultas(
		        @RequestParam(value = "idFakultas", required = true) int idFakultas) {
			return mahasiswaDAO.selectAllProdibyFakultas(idFakultas);
		}

}
