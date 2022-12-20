package pl.kurs.geometricfigures.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.kurs.geometricfigures.security.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shape_changes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShapeChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shape_id")
    private Shape shape;

    @CreatedDate
    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    private String fieldName;
    private Double oldValue;
    private Double newValue;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "modifiedBy_id")
    private AppUser modifiedBy;
}
