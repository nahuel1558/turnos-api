package apiTurnos.common.constants;
/**
 * Centraliza paths para evitar strings repetidos en controllers.
 * Facilita versionado /api/v1 y refactors.
 */
public final class ApiPaths {

    private ApiPaths() {}

    public static final String API_V1 = "/api/v1";

    public static final String USERS = API_V1 + "/users";
    public static final String APPOINTMENTS = API_V1 + "/appointments";
    public static final String BARBERS = API_V1 + "/barbers";
    public static final String SERVICES = API_V1 + "/services";
}
