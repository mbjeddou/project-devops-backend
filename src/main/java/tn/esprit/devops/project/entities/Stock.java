package tn.esprit.devops.project.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idStock;
    String title;
    int realStock; // Stock réel
    int reserveStock; // Stock de réserve

    @OneToMany(mappedBy = "stock")
   private Set<Product> products;
}
