package com.accenture.aria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fragments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fragment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FragmentType type;
    
    @Column(nullable = false, length = 500)
    private String text;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
