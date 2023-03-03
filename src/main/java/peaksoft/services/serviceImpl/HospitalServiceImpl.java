package peaksoft.services.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.models.Hospital;
import peaksoft.repositories.HospitalRepo;
import peaksoft.services.HospitalService;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepo hospitalRepo;

    @Override
    public List<Hospital> findAll() {
        return hospitalRepo.findAll();
    }

    @Override
    public void save(Hospital hospital) {
        hospitalRepo.save(hospital);
    }

    @Override
    public Hospital getHospitalById(Long hospitalId) {
        return hospitalRepo.findById(hospitalId).get();
    }

    @Override
    public void update(Hospital updateHospital) {
        hospitalRepo.save(updateHospital);
    }


    @Override
    public void deleteHospital(Long hospitalId) {
        hospitalRepo.delete(hospitalRepo.findById(hospitalId).get());
    }
}
