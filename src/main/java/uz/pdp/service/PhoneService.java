package uz.pdp.service;

import uz.pdp.model.Phone;
import uz.pdp.repository.PhoneRepository;

import java.util.UUID;

public class PhoneService extends BaseService<Phone, PhoneRepository> {

    public static final PhoneService phoneService = new PhoneService();

    public static PhoneService getInstance() {
        return phoneService;
    }

    public PhoneService() {
        super(PhoneRepository.getInstance());
    }

    public Phone getCreatingPhone(UUID id) {
        return getAll().stream().filter(p -> p.getOwnerId().equals(id) && p.isCreating()).toList().getLast();
    }

    public void update(UUID id, Phone phone) {
        repository.update(id, phone);
    }
}
