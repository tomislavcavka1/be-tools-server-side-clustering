package hr.axion.serverside.clustering.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
public class Country {

    @Id
    @Column("country_id")
    private Integer id;

    private String name;

    public Country(String name) {
        this.name = name;
    }
}
