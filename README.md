
Fusio Java CLI sample
=====

# About

This is a simple Java CLI application which shows how to use the Java SDK to access a Fusio instance.
In this example we simply output all registered routes.
Fusio is an open source API management which helps to build and manage great APIs more information at:
https://www.fusio-project.org/

```java
package org.fusioproject.sample;

import app.sdkgen.client.Exception.ClientException;
import app.sdkgen.client.TokenStore.MemoryTokenStore;
import app.sdkgen.client.TokenStoreInterface;
import org.fusioproject.sdk.Client;
import org.fusioproject.sdk.backend.OperationCollection;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ClientException {
        List<String> scopes = new ArrayList<>();
        scopes.add("backend");
        TokenStoreInterface tokenStore = new MemoryTokenStore();

        Client client = new Client("https://demo.fusio-project.org/", "test", "FRsNh1zKCXlB", scopes, tokenStore);

        OperationCollection operations = client.backend().operation().getAll(0, 16, "");

        System.out.println("Operations:");
        for (int i = 0; i < operations.getEntry().length; i++) {
            System.out.println("* " + operations.getEntry()[i].getHttpMethod() + " " + operations.getEntry()[i].getHttpPath());
        }
    }

}

```
