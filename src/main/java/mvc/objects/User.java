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
    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Size(min = 6, message = "{name.size.error}")
    private String name;

    @Size(min = 6,max = 10,message = "{password.size.error}")
    private String password;

    private Boolean admin;


}
