package dev.alexis.hourlypaytracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object for batch requests.
 * Contains a list of ids to be modified.
 */
@Getter
@Setter
@NoArgsConstructor
public class BatchOperationWithIdRequest {
    @NotNull
    private List<Long> ids;
}