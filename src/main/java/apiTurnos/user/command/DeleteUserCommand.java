package apiTurnos.user.command;

/**
 * Command CQRS: eliminar (soft delete) usuario.
 */
public record DeleteUserCommand(String id) { }

