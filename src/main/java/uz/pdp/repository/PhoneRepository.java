package uz.pdp.repository;

import uz.pdp.model.Phone;

import java.util.ArrayList;
import java.util.UUID;

public class PhoneRepository extends BaseRepository<Phone> {

    public static final PhoneRepository photoRepository = new PhoneRepository();

    public static PhoneRepository getInstance() {
        return photoRepository;
    }

    public PhoneRepository() {
        super.path = "src/main/resources/phone.json";
        super.type = Phone.class;
    }

    public void update(UUID id,Phone phone) {
        ArrayList<Phone> list = readValue();
        int i = 0;
        for (Phone phone1 : list) {
            if (phone1.getId().equals(id)) {
                list.set(i, phone);
                break;
            }
            i++;
        }
        writeValue(list);
    }
}
