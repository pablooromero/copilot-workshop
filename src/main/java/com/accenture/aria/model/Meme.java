package com.accenture.aria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String character;
    
    @Column(nullable = false, length = 500)
    private String description;
}
