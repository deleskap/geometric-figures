package pl.kurs.geometricfigures.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.kurs.geometricfigures.security.AppUser;
import pl.kurs.geometricfigures.service.Identificationable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class Shape implements Serializable, Identificationable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Version
    private int version;
    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "createdBy_id", nullable = false)
    private AppUser createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "lastModifiedBy_id", nullable = false)
    private AppUser lastModifiedBy;

    public abstract Double getArea();

    public abstract double getPerimeter();

}