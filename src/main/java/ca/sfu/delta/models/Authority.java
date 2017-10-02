package ca.sfu.delta.models;

public class Authority {

    // Not sure if String is appropriate type for AuthId
    private String authId;
    private String name;
    private String phone;
    private String sfuId;
    private String signature;

    public Authority(){

    }
    public Authority(String AuthId, String name, String phone, String sfuId, String signature){
        this.authId = AuthId;
        this.name = name;
        this.phone = phone;
        this.sfuId = sfuId;
        this.signature = signature;
    }

    // Getters and Setters ------------------------------------------------
    public String getAuthId(){ return authId; }

    public void setAuthId(){
        if(authId != null && !authId.isEmpty()){
            this.authId = authId;
        } else {
            throw new IllegalArgumentException("ID cannot be null, or an empty String.");
        }
    }

    public String getName(){ return name; }

    public void setName(){
        if(name != null && !name.isEmpty()){
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be null, or an empty String.");
        }
    }

    public String getPhone(){ return phone; }

    public void setPhone(){

//        Not sure if value for phone has to be specified. It maybe null?

//        if(phone != null && !phone.isEmpty()){
            this.phone = phone;
//        } else {
//            throw new IllegalArgumentException("Phone cannot be null.");
//        }
    }

    public String getSfuId(){ return sfuId; }

    public void setSfuId(){
        if(sfuId != null && !sfuId.isEmpty()){
            this.sfuId = sfuId;
        } else {
            throw new IllegalArgumentException("SFU Id cannot be null, or an empty String.");
        }
    }
    public String getSignature(){ return signature; }

    public void setSignature(){
        if(signature != null && !signature.isEmpty()){
            this.signature = signature;
        } else {
            throw new IllegalArgumentException("Signature cannot be null, or an empty String.");
        }
    }

}