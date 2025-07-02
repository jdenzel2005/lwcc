package de.lexware.cc.shared.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@ApiResponses(value = {
    @ApiResponse(
        responseCode = "401",
        description = "Unauthorized",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ProblemDetail.class
                )
            )
        }
    ),
    @ApiResponse(
        responseCode = "403",
        description = "Forbidden",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ProblemDetail.class
                )
            )
        }
    ),
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(
                    implementation = ProblemDetail.class
                )
            )
        }
    )
})
public interface LwccApi {
}
