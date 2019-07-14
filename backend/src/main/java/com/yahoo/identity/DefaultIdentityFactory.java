package com.yahoo.identity;

import com.yahoo.identity.services.account.AccountService;
import com.yahoo.identity.services.account.AccountServiceImpl;
import com.yahoo.identity.services.credential.CredentialService;
import com.yahoo.identity.services.credential.CredentialServiceImpl;
import com.yahoo.identity.services.key.KeyService;
import com.yahoo.identity.services.key.KeyServiceImpl;
import com.yahoo.identity.services.password.PasswordService;
import com.yahoo.identity.services.password.PasswordServiceImpl;
import com.yahoo.identity.services.session.SessionService;
import com.yahoo.identity.services.session.SessionServiceImpl;
import com.yahoo.identity.services.storage.Storage;
import com.yahoo.identity.services.storage.sql.SqlStorage;
import com.yahoo.identity.services.system.SystemService;
import com.yahoo.identity.services.token.TokenService;
import com.yahoo.identity.services.token.TokenServiceImpl;

import javax.annotation.Nonnull;

public class DefaultIdentityFactory implements IdentityFactory {

    @Nonnull
    @Override
    public Identity create() {
        SystemService systemService = new SystemService();
        Storage storage = new SqlStorage(systemService);
        KeyService keyService = new KeyServiceImpl();
        PasswordService passwordService = new PasswordServiceImpl();
        AccountService accountService = new AccountServiceImpl(storage, passwordService, systemService);
        CredentialService credentialService = new CredentialServiceImpl(keyService, accountService, systemService);

        TokenService tokenService = new TokenServiceImpl(keyService);
        SessionService sessionService = new SessionServiceImpl(tokenService, accountService, credentialService);

        return new Identity(sessionService);
    }
}
