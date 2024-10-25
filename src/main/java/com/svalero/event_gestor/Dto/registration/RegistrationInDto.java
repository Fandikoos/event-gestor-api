package com.svalero.event_gestor.Dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationInDto {

    @NotNull(message = "Event ID is required")
    private long eventId;

    @NotNull(message = "User ID is required")
    private long userId;

    @NotNull(message = "Registration date is required")
    private LocalDateTime registrationDate;
}
