package peaksoft.services;


import org.springframework.stereotype.Service;
import peaksoft.models.Hospital;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
public interface HospitalService {
    List<Hospital> findAll();

    void save(Hospital hospital);

    void deleteHospital(Long hospitalId);

    Hospital getHospitalById(Long hospitalId);

    void update(Hospital hospital);

}
