package com.yahoo.identity.services.account;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface AccountService {
    @Nonnull
    AccountCreate newAccountCreate();

    Account getAccount(@Nonnull String id);

    @Nonnull
    AccountUpdate newAccountUpdate(@Nonnull String id);
}
