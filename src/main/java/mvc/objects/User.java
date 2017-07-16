package mvc.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@XmlRootElement
public class User {

    @Size(min = 6, message = "Имя должно быть 6 знаков")
    private String name;

    @Size(min = 6,max = 10,message = "Пароль должен быть от 5 до 10 знаков")
    private String password;

    private Boolean admin;


}
