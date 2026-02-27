package apiTurnos.user.command;

/**
 * UseCase para registrar usuario.
 * (Te sirve si querés testear fácil o desacoplar controllers de implementación.)
 */
public interface RegisterUserUseCase {
    String handle(RegisterUserCommand command);
}
