package xyz.navyd.mbg.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author navyd
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
    private Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
