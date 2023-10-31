package com.paranoidal97.demo.model.enums;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public enum VisitType {
    CREATED(Set.of("SCHEDULED", "OUTDATED"), false),
    SCHEDULED(Set.of("CANCELLED", "COMPLETED"), true),
    CANCELLED(Set.of(), true),
    COMPLETED(Set.of(), true),
    OUTDATED(Set.of(), false);

    private final Set<String> allowedTransitions;
    private final boolean isPatientRequired;

    public boolean isTransitionAllowed(VisitType appointmentStatus, boolean isPatientNotNull) {
        return Optional.of(allowedTransitions.contains(appointmentStatus.toString()) && isPatientNotNull == isPatientRequired)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("This visit transition from state is illegal or patient is missing.")
                ));
    }
}
