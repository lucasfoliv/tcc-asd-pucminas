package br.pucminas.gisa.identity.common.typing;

public enum FailureMessage {

    AccountInactive("User account is disabled"),
    ProfileInactive("User profile is disabled"),
    CredentialsMismatch("User credentials don't match"),
    NotFound("User not found");

    private final String cause;

    FailureMessage(final String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
