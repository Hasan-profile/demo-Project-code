package com.rest.app;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "bookrecord")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @NonNull
    private String name;

    @NonNull
    private String summary;

    @NonNull
    private String rating;

}
