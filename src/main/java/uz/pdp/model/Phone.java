package uz.pdp.model;

import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class Phone extends BaseModel {

    private List<String> photoId;
    private String owner;

    private String model;

    private String ram;

    private String document;

    private String address;

    private String price;

    private String phoneNumber;

    private String extra;

    private String forReference;

    private String send;

    private boolean isCreating;

    private UUID ownerId;


    @Override
    public String toString() {

        return
                "SOTILADI \n" +
                        "\uD83D\uDCBCEgasi:\t " + owner + "\n" +
                        "\uD83D\uDCF1Rusumi:\t" + model + "\n" +
                        "\uD83D\uDCBERam/Operativka:\t" + ram + "\n" +
                        "\uD83D\uDCD1Document:\t" + document + "\n" +
                        "\uD83C\uDFE0Manzil:\t" + address + "\n" +
                        "\uD83D\uDCB8Narxi:\t" + price + "\n" +
                        "\uD83D\uDCF1Tel:\t" + phoneNumber + "\n" +
                        "\uD83D\uDCD8Qoʻshimcha:\t" + extra + "\n" +
                        "\uD83D\uDCF1 Murojaat:\t" + forReference;

    }
}
