package marlon.leoner.projeto.avaliacao.tecnica.domain;


import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    protected String id;

    @Column(name = "created_at")
    private Date createdAt;

    public BaseEntity() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
    }
}
