package com.yahoo.identity.services.storage.sql;

import com.yahoo.identity.services.session.SessionCreate;

import javax.annotation.Nonnull;

public class SqlSessionCreate implements SessionCreate {

    private final SessionModel session = new SessionModel();

    @Override
    @Nonnull
    public SessionCreate setUsername(@Nonnull String username) {
        session.setUsername(username);
        return this;
    }

    @Override
    @Nonnull
    public String getUsername() {
        return session.getUsername();
    }

    @Override
    @Nonnull
    public String getPassword() {
        return session.getPassword();
    }

    @Override
    @Nonnull
    public SessionCreate setPassword(@Nonnull String password) {
        session.setPassword(password);
        return this;
    }

    @Override
    @Nonnull
    public SessionCreate setCredential(@Nonnull String credStr) {
        session.setCredential(credStr);
        return this;
    }

    @Override
    @Nonnull
    public SessionCreate initCredential() {
        session.initCredential();
        return this;
    }

    @Override
    @Nonnull
    public String create() {
        return this.session.getCredentialString();
    }
}
