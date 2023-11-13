package com.paranoidal97.demo.model.enums;

import com.paranoidal97.demo.exception.IllegalApointmentTransition;
import lombok.RequiredArgsConstructor;

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
        if (appointmentStatus.isPatientRequired) {
            if (!(allowedTransitions.contains(appointmentStatus.toString()) && isPatientNotNull)) {
                throw new IllegalApointmentTransition(
                        "This visit transition from state is illegal or patient is missing."
                );
            }
            return true;
        } else {
            if (!(allowedTransitions.contains(appointmentStatus.toString()))) {
                throw new IllegalApointmentTransition(
                        "This visit transition from state is illegal or patient is missing."
                );
            }
            return true;
        }
    }
}
