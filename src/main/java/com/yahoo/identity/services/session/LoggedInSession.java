package com.yahoo.identity.services.session;

import com.yahoo.identity.services.account.Account;
import com.yahoo.identity.services.credential.Credential;

import javax.annotation.Nonnull;

public interface LoggedInSession {

    @Nonnull
    Credential getCredential();

    @Nonnull
    Account getAccount();

    @Nonnull
    String getUsername();

    @Nonnull
    LoggedInSession sessionAccountUpdate(@Nonnull Account account);
}
