package com.example.vardansharma.contact_app.data;

import com.squareup.moshi.Json;

public class UpdateUserRequest {
    @Json (name = "first_name")
    private String firstName;

    @Json (name = "last_name")
    private String lastName;

    private String email;
    @Json (name = "phone_number")
    private String phoneNumber;

    private UpdateUserRequest(Builder builder) {
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setEmail(builder.email);
        setPhoneNumber(builder.phoneNumber);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public static final class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;

        public Builder() {
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(this);
        }
    }
}
