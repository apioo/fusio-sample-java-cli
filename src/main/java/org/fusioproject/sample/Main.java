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

        System.out.println("Routes:");
        for (int i = 0; i < operations.getEntry().length; i++) {
            System.out.println("* " + operations.getEntry()[i].getHttpPath());
        }
    }

}
