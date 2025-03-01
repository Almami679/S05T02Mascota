package ItAcademyJavaSpringBoot.AircraftFleet.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccessoryNotFound(AccessoryNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BattleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBattleNotFound(BattleNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HealthPlaneIsFullException.class)
    public ResponseEntity<Map<String, String>> handleHealthPlaneIsFull(HealthPlaneIsFullException ex) {
        // Podrías usar 409 (CONFLICT) si ya está lleno y no se puede realizar la acción
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientCreditsException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientCredits(InsufficientCreditsException ex) {
        // 400 (BAD_REQUEST) si la lógica indica que no tienes créditos suficientes
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessoryFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoAccessoryFound(NoAccessoryFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHavePermissionsException.class)
    public ResponseEntity<Map<String, String>> handleNoHavePermissions(NoHavePermissionsException ex) {
        // 403 (FORBIDDEN) si el usuario no tiene permisos
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoPlaneFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoPlaneFound(NoPlaneFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotPlayerAvailableException.class)
    public ResponseEntity<Map<String, String>> handleNotPlayerAvailable(NotPlayerAvailableException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlaneHasFullFuelException.class)
    public ResponseEntity<Map<String, String>> handlePlaneHasFullFuel(PlaneHasFullFuelException ex) {
        // 409 (CONFLICT) si el avión ya tiene el combustible lleno
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlayerHasNoPlanesException.class)
    public ResponseEntity<Map<String, String>> handlePlayerHasNoPlanes(PlayerHasNoPlanesException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlaneNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePlaneNotFoundException(PlaneNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameIsInUseException.class)
    public ResponseEntity<Map<String, String>> handleUsernameIsInUse(UsernameIsInUseException ex) {
        // 409 (CONFLICT) si el nombre de usuario ya está en uso
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Error due to designated value NULL: {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Credenciales erróneas");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

}

