package digital.extreme.quarkus01.quarkussocial.rest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class CreateUserRequest {

    @NotBlank(message = "Nome é obrigatório!")
    private String name;

    @NotNull(message = "Idade é obrigatória!")
    private Integer age;

}
