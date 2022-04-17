package br.pucminas.gisa.identity.common.typing;

public enum AuthorityType {
    RoleAdmin("ROLE_ADMIN");

    private final String alias;

    AuthorityType(final String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
