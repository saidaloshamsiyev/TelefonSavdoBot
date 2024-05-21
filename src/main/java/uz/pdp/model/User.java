package uz.pdp.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User extends BaseModel {

    private String firstName;

    private String username;

    private Long chatId;
    private UserState userState;


}