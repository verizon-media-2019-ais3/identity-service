package org.openapitools.api.impl;

import com.yahoo.identity.Identity;
import com.yahoo.identity.services.account.AccountCreate;
import com.yahoo.identity.services.account.AccountService;
import com.yahoo.identity.services.account.AccountUpdate;
import org.openapitools.api.AccountsApiService;
import org.openapitools.api.ApiResponseMessage;
import org.openapitools.api.NotFoundException;
import org.openapitools.model.Account;

import java.time.Instant;
import javax.annotation.Nonnull;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2019-05-14T20:17:48.996+08:00[Asia/Taipei]")
public class AccountsApiServiceImpl extends AccountsApiService {
    private final Identity identity;

    public AccountsApiServiceImpl(@Nonnull Identity identity) {
        this.identity = identity;
    }

    @Override
    public Response accountsIdGet(String username, SecurityContext securityContext) throws NotFoundException {
        String uid = identity.getAccountService().getAccount(username).getUid();
        String firstName = identity.getAccountService().getAccount(username).getFirstName();
        String lastName = identity.getAccountService().getAccount(username).getLastName();
        String email = identity.getAccountService().getAccount(username).getEmail();
        String password = identity.getAccountService().getAccount(username).getPassword();
        Instant createTs = identity.getAccountService().getAccount(username).getCreateTime();
        Instant updateTs = identity.getAccountService().getAccount(username).getUpdateTime();
        String description = identity.getAccountService().getAccount(username).getDescription();
        Instant blockUntil = identity.getAccountService().getAccount(username).getBlockUntil();
        int nthTrial = identity.getAccountService().getAccount(username).getNthTrial();

        String msg = "username: " + username + ", description: " + description;
        return Response.ok().entity(new ApiResponseMessage(Response.Status.OK.getStatusCode(), msg)).build();
    }

    @Override
    public Response accountsmeGet(String token, SecurityContext securityContext) throws NotFoundException {
        try {
            // TODO: take username and cookie from token
            String username = "Alice";
            //TODO: get cookie by username and validate cookie
            String cookie = "from token";
            if (cookie.equals("from token")) {
                AccountService accountService = identity.getAccountService();
                String firstName = accountService.getAccount(username).getFirstName();
                String lastName = accountService.getAccount(username).getLastName();
                String email = accountService.getAccount(username).getEmail();
                String description = accountService.getAccount(username).getDescription();

                String msg = "username: " + username + ", firstname: " + firstName + ", lastname: " + lastName + ", email: " + email + ", description: " + description;
                return Response.ok().entity(new ApiResponseMessage(Response.Status.OK.getStatusCode(), msg)).build();
            } else {
                ApiResponseMessage errorMsg = new ApiResponseMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "Invalid cookie credential is used.");
                return Response.status(Response.Status.UNAUTHORIZED).entity(errorMsg).build();
            }
        } catch (Exception e) {
            ApiResponseMessage errorMsg = new ApiResponseMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unknown error occurs.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
        }
    }

    @Override
    public Response accountsPost(Account account, SecurityContext securityContext) throws NotFoundException {
        try {
            Boolean mockVerified = true;

            AccountCreate accountCreate = identity.getAccountService().newAccountCreate();
            accountCreate.setUsername(account.getUsername());
            accountCreate.setFirstName(account.getFirstName());
            accountCreate.setLastName(account.getLastName());
            accountCreate.setEmail(account.getEmail(), mockVerified);

            accountCreate.setPassword(account.getPassword());
            accountCreate.setCreateTime(account.getCreateTime().toInstant());
            accountCreate.setUpdateTime(account.getUpdateTime().toInstant());
            accountCreate.setDescription(account.getDescription());
            accountCreate.setBlockUntil(account.getBlockUntil().toInstant());
            accountCreate.setNthTrial(account.getNthTrial());
            accountCreate.create();

            NewCookie cookie = new NewCookie("ButterCookie","123112131232");

            ApiResponseMessage successMsg = new ApiResponseMessage(Response.Status.CREATED.getStatusCode(), "The account is created successfully");
            return Response.ok().entity(successMsg).cookie(cookie).build();

        }catch (Exception e) {
            ApiResponseMessage errorMsg = new ApiResponseMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unknown error occurs.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
        }
    }

    @Override
    public Response accountsmePut(String token, Account account, SecurityContext securityContext) throws NotFoundException {

        try {
            NewCookie mockCookie = new NewCookie("ButterCookie","123112131232");
            if (mockCookie.getValue() == "123112131232") {
                Boolean mockVerified = true;

                if (mockVerified) {
                    AccountUpdate accountUpdate = identity.getAccountService().newAccountUpdate(account.getUsername());
                    accountUpdate.setEmail(account.getEmail(), mockVerified);
                    accountUpdate.setPassword(account.getPassword());
                    accountUpdate.setDescription(account.getDescription());
                    accountUpdate.setUpdateTime(account.getUpdateTime().toInstant());
                    accountUpdate.setBlockUntil(account.getBlockUntil().toInstant());
                    accountUpdate.setNthTrial(account.getNthTrial());
                    accountUpdate.update();

                    ApiResponseMessage successMsg = new ApiResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "Successfully updated the account.");
                    return Response.ok().entity(successMsg).build();
                }
                else{
                    return null;
                }
            }
            else{
                ApiResponseMessage errorMsg = new ApiResponseMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "Invalid cookie credential is used.");
                return Response.status(Response.Status.UNAUTHORIZED).entity(errorMsg).build();
            }
        }catch (Exception e){
            ApiResponseMessage errorMsg = new ApiResponseMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unknown error occurs.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMsg).build();
        }
    }
}