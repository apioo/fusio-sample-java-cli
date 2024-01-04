
Fusio Java CLI sample
=====

# About

This is a simple Java CLI application which shows how to use the Java SDK to access a Fusio instance.
In this example we simply output all registered routes.
Fusio is an open source API management which helps to build and manage great APIs more information at:
https://www.fusio-project.org/

```java
package org.fusioproject.sample;

import app.sdkgen.client.Credentials.OAuth2;
import app.sdkgen.client.CredentialsInterface;
import app.sdkgen.client.Exception.ClientException;
import app.sdkgen.client.TokenStore.MemoryTokenStore;
import app.sdkgen.client.TokenStoreInterface;
import org.fusioproject.sdk.BackendOperationCollection;
import org.fusioproject.sdk.Client;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClientException {
        List<String> scopes = new ArrayList<>();
        scopes.add("backend");
        TokenStoreInterface tokenStore = new MemoryTokenStore();

        CredentialsInterface credentials = new OAuth2("test", "FRsNh1zKCXlB", "https://demo.fusio-project.org/authorization/token", "", tokenStore, scopes);
        Client client = new Client("https://demo.fusio-project.org/", credentials);

        BackendOperationCollection operations = client.backend().operation().getAll(0, 16, "");

        System.out.println("Operations:");
        for (int i = 0; i < operations.getEntry().size(); i++) {
            System.out.println("* " + operations.getEntry().get(i).getHttpMethod() + " " + operations.getEntry().get(i).getHttpPath());
        }
    }

}

```
