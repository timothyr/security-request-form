package ca.sfu.delta.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorityTest {

    private String authId = "A1001";
    private String name = "Big Danny";
    private String phone = "909-909-9009";
    private String sfuId = "sfuID@sfu.ca";
    private String signature = "BDanSignature";

    private Authority auth = new Authority(authId, name, phone, sfuId, signature);

    @Test
    public void getAuthId() throws Exception {
        assertEquals(authId, auth.getAuthId());
    }

    @Test
    public void setAuthId() throws Exception {
//        auth.setAuthId(authId);
        assertEquals(authId, auth.getAuthId());
    }

    @Test
    public void getName() throws Exception {
    }

    @Test
    public void setName() throws Exception {
    }

    @Test
    public void getPhone() throws Exception {
    }

    @Test
    public void setPhone() throws Exception {
    }

    @Test
    public void getSfuId() throws Exception {
    }

    @Test
    public void setSfuId() throws Exception {
    }

    @Test
    public void getSignature() throws Exception {
    }

    @Test
    public void setSignature() throws Exception {
    }

}