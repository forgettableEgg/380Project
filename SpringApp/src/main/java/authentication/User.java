package authentication;

public class User {
    private int Id;
    private String Email;
    private String LastName;
    private String FirstName;
    private String Token;
    private String Picture;
    private Long Token_Expiration;

    User() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public Long getToken_Expiration() {
        return Token_Expiration;
    }

    public void setToken_Expiration(Long token_Expiration) {
        Token_Expiration = token_Expiration;
    }
}